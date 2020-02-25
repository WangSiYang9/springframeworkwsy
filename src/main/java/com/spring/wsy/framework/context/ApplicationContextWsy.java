package com.spring.wsy.framework.context;

import com.spring.wsy.framework.annotation.AutowiredWsy;
import com.spring.wsy.framework.annotation.ControllerWsy;
import com.spring.wsy.framework.annotation.ServiceWsy;
import com.spring.wsy.framework.aop.AopProxyWsy;
import com.spring.wsy.framework.aop.CglibAopProxyWsy;
import com.spring.wsy.framework.aop.JdkDynamicAopProxyWsy;
import com.spring.wsy.framework.aop.config.AopConfigWsy;
import com.spring.wsy.framework.aop.support.AdvisedSupportWsy;
import com.spring.wsy.framework.beans.BeanWrapperWsy;
import com.spring.wsy.framework.beans.config.BeanPostProcessorWsy;
import com.spring.wsy.framework.beans.support.BeanDefinitionReaderWsy;
import com.spring.wsy.framework.beans.support.DefaultListableBeanFactoryWsy;
import com.spring.wsy.framework.beans.BeanFactoryWsy;
import com.spring.wsy.framework.beans.config.BeanDefinitionWsy;


import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 王思洋
 * @version 1.0
 * @description
 * @date 2020-01-22 10:18
 */
public class ApplicationContextWsy extends DefaultListableBeanFactoryWsy implements BeanFactoryWsy {

    private String [] configLocations;

    private BeanDefinitionReaderWsy reader;

    //单例的IOC容器缓存
    private Map<String,Object> factoryBeanObjectCache = new ConcurrentHashMap<String, Object>();

    //通用的IOC容器
    private Map<String,BeanWrapperWsy> factoryBeanInstanceCache = new ConcurrentHashMap<String, BeanWrapperWsy>();


    //ClassPathXml
    public ApplicationContextWsy(String... configLocations){
        this.configLocations = configLocations;
        refresh();
    }



    @Override
    public void refresh(){
        //1.定位配置文件
        reader = new BeanDefinitionReaderWsy(this.configLocations);

        //2.加载配置文件，扫描相关类，把他们封装成BeanDefinition
        List<BeanDefinitionWsy> wBeanDefinitions = reader.loadBeanDefinitions();

        //3.注册，把配置信息放到容器里面（伪IOC容器）
        doRegisterBeanDefinition(wBeanDefinitions);
        
        //4.把不是延时加载的类提前初始化
        doAutowrited();

    }


    private void doRegisterBeanDefinition(List<BeanDefinitionWsy> beanDefinitions) {
        for (BeanDefinitionWsy beanDefinition:beanDefinitions) {
            super.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(),beanDefinition);
        }
    }

    //只处理非延时加载的情况
    private void doAutowrited() {
        for (Map.Entry<String, BeanDefinitionWsy> beanDefinitionEntry : super.beanDefinitionMap.entrySet()) {
            String beanName = beanDefinitionEntry.getKey();
            if(!beanDefinitionEntry.getValue().isLazyInit()) {
                try {
                    getBean(beanName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    public Object getBean(Class<?> beanClass) throws Exception {
        return getBean(beanClass.getName());
    }
    @Override
    public Object getBean(String beanName) throws Exception {
        BeanDefinitionWsy beanDefinitionWsy = this.beanDefinitionMap.get(beanName);
        Object instance = null;
        //这个逻辑还不严谨，自己可以去参考Spring源码
        //工厂模式 + 策略模式
        BeanPostProcessorWsy postProcessor = new BeanPostProcessorWsy();

        postProcessor.postProcessBeforeInitialization(instance,beanName);
        //1.初始化
//        //class A{ B b;}
//        //class B{ A a;}
//        //先有鸡还是先有蛋的问题，一个方法是搞不定的，要分两次
        instance = instantiateBean(beanName, beanDefinitionWsy);

        BeanWrapperWsy beanWrapperWsy = new BeanWrapperWsy(instance);

        //2、拿到BeanWraoper之后，把BeanWrapper保存到IOC容器中去
        this.factoryBeanInstanceCache.put(beanName,beanWrapperWsy);
        postProcessor.postProcessAfterInitialization(instance,beanName);

        //3.注入
        populateBean(beanName,beanDefinitionWsy,beanWrapperWsy);

        return this.factoryBeanInstanceCache.get(beanName).getWrappedInstance();
    }

    private Object instantiateBean(String beanName, BeanDefinitionWsy beanDefinitionWsy) {
        //1、拿到要实例化的对象的类名
        String className = beanDefinitionWsy.getBeanClassName();
        //2、反射实例化，得到一个对象
        Object instance = null;
        try {
//            gpBeanDefinition.getFactoryBeanName()
            //假设默认就是单例,细节暂且不考虑，先把主线拉通
            if(this.factoryBeanObjectCache.containsKey(className)){
                instance = this.factoryBeanObjectCache.get(className);
            }else {
                Class<?> clazz = Class.forName(className);
                instance = clazz.newInstance();

                AdvisedSupportWsy config = instantionAopConfig(beanDefinitionWsy);
                config.setTargetClass(clazz);
                config.setTarget(instance);
                //符合PointCut的规则的话，闯将代理对象
                if(config.pointCutMatch()) {
                    instance = createProxy(config).getProxy();
                }

                this.factoryBeanObjectCache.put(className,instance);
                this.factoryBeanObjectCache.put(beanDefinitionWsy.getFactoryBeanName(),instance);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return instance;
    }

    private AopProxyWsy createProxy(AdvisedSupportWsy config) throws Exception {
        Class targetClass = config.getTargetClass();
        if(targetClass.getInterfaces().length > 0){
            return new JdkDynamicAopProxyWsy(config);
        }
        return new CglibAopProxyWsy(config);
    }

    private AdvisedSupportWsy instantionAopConfig(BeanDefinitionWsy beanDefinitionWsy) {
        AopConfigWsy config = new AopConfigWsy();
        config.setPointCut(this.reader.getConfig().getProperty("pointCut"));
        config.setAspectClass(this.reader.getConfig().getProperty("aspectClass"));
        config.setAspectBefore(this.reader.getConfig().getProperty("aspectBefore"));
        config.setAspectAfter(this.reader.getConfig().getProperty("aspectAfter"));
        config.setAspectAfterThrow(this.reader.getConfig().getProperty("aspectAfterThrow"));
        config.setAspectAfterThrowingName(this.reader.getConfig().getProperty("aspectAfterThrowingName"));
        return new AdvisedSupportWsy(config);
    }

    private void populateBean(String beanName, BeanDefinitionWsy beanDefinitionWsy, BeanWrapperWsy beanWrapperWsy) {
        Object instance = beanWrapperWsy.getWrappedInstance();
        Class<?> clazz = beanWrapperWsy.getWrappedClass();

        //判断只有加了注解的类，才执行依赖注入
        if(!(clazz.isAnnotationPresent(ControllerWsy.class) || clazz.isAnnotationPresent(ServiceWsy.class))){
            return;
        }
        //获得所有的fields
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if(!field.isAnnotationPresent(AutowiredWsy.class)){ continue;}

            AutowiredWsy autowired = field.getAnnotation(AutowiredWsy.class);

            String autowiredBeanName =  autowired.value().trim();
            if("".equals(autowiredBeanName)){
                autowiredBeanName = field.getType().getName();
            }

            field.setAccessible(true);

            try {

                //为什么会为NULL，先留个坑
                if(this.factoryBeanInstanceCache.get(autowiredBeanName) == null){ continue; }
//                if(instance == null){
//                    continue;
//                }
                field.set(instance,this.factoryBeanInstanceCache.get(autowiredBeanName).getWrappedInstance());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }

    }


    public String[] getBeanDefinitionNames() {
        return this.beanDefinitionMap.keySet().toArray(new  String[this.beanDefinitionMap.size()]);
    }

    public Properties getConfig(){
        return this.reader.getConfig();
    }

}
