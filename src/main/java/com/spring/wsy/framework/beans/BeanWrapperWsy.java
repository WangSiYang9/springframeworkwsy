package com.spring.wsy.framework.beans;

/**
 * @author 王思洋
 * @version 1.0
 * @description 单例工厂的顶层设计
 * @date 2020-02-11 20:20
 */
public class BeanWrapperWsy {

    private Object wrappedInstance;
    private Class<?> wrappedClass;

    public BeanWrapperWsy(Object wrappedInstance){
        this.wrappedInstance = wrappedInstance;
    }

    public Object getWrappedInstance(){
        return this.wrappedInstance;
    }

    // 返回代理以后的Class
    // 可能会是这个 $Proxy0
    public Class<?> getWrappedClass(){
        return this.wrappedInstance.getClass();
    }
}
