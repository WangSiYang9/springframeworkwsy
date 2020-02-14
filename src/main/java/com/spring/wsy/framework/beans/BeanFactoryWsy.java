package com.spring.wsy.framework.beans;

/**
 * @author 王思洋
 * @version 1.0
 * @description 单例工厂的顶层设计
 * @date 2020-01-22 10:20
 */
public interface BeanFactoryWsy {

    Object getBean(Class<?> beanClass) throws Exception;

    /**
     * @author 王思洋
     * @version 1.0
     * @description 根据bean从容器中获得一个实例
     * @date 2020-01-22 10:20
     * @param beanName
     * @return
     */
    Object getBean(String beanName) throws Exception;

}
