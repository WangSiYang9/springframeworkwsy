package com.spring.wsy.framework.beans.support;

import com.spring.wsy.framework.context.support.AbstractRefreshableApplicationContextWsy;
import com.spring.wsy.framework.beans.BeanFactoryWsy;
import com.spring.wsy.framework.beans.config.BeanDefinitionWsy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 王思洋
 * @version 1.0
 * @description
 * @date 2020-01-22 10:18
 */
public abstract class DefaultListableBeanFactoryWsy extends AbstractRefreshableApplicationContextWsy implements BeanFactoryWsy {

    //存储注册信息的BeanDefinition 伪IOC容器，在Spring中真的IOC容器是BeanWrapperMap）
    protected final Map<String, BeanDefinitionWsy> beanDefinitionMap = new ConcurrentHashMap<>(256);

}
