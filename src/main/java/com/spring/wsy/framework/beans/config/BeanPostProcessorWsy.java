package com.spring.wsy.framework.beans.config;

/**
 * @author 王思洋
 * @version 1.0
 * @description
 * @date 2020-02-03 20:50
 */
public class BeanPostProcessorWsy {

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
        return bean;
    }
}
