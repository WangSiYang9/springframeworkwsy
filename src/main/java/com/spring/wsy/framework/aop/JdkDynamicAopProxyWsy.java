package com.spring.wsy.framework.aop;

import com.spring.wsy.framework.aop.intercept.MethodInvocationWsy;
import com.spring.wsy.framework.aop.support.AdvisedSupportWsy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @author 王思洋
 * @version 1.0
 * @description
 * @date 2020-02-18 11:50
 */
public class JdkDynamicAopProxyWsy implements AopProxyWsy, InvocationHandler {

    private AdvisedSupportWsy advised;

    public JdkDynamicAopProxyWsy(AdvisedSupportWsy config) throws Exception {
        this.advised = config;
    }


    @Override
    public Object getProxy() {
        return getProxy(this.advised.getTargetClass().getClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return Proxy.newProxyInstance(classLoader,this.advised.getTargetClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List<Object> interceptorsAndDynamicInterceptionAdvice = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, this.advised.getTargetClass());
        MethodInvocationWsy invocation = new MethodInvocationWsy(proxy,this.advised.getTarget(),method,args,this.advised.getTargetClass(),interceptorsAndDynamicInterceptionAdvice);
        return invocation.proceed();
    }
}
