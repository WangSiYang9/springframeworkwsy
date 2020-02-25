package com.spring.wsy.framework.aop.intercept;

/**
 * @author 王思洋
 * @version 1.0
 * @description
 * @date 2020-02-21 10:40
 */
public interface MethodInterceptorWsy {

    Object invoke(MethodInvocationWsy invocation) throws Throwable;

}
