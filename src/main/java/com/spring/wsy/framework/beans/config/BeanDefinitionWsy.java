package com.spring.wsy.framework.beans.config;

import lombok.Data;

/**
 * @author 王思洋
 * @version 1.0
 * @description Bean信息类，存储注册信息
 * @date 2020-01-22 10:18
 */
@Data
public class BeanDefinitionWsy {
    //com.spring.wsy.framework.beans.config.BeanDefinitionWsy
    private String beanClassName;
    private boolean lazyInit = false;
    //beanDefinitionWsy
    private String factoryBeanName;
    private boolean isSingleton = true;
}
