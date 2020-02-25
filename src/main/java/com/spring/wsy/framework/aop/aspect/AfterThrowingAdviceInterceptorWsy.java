package com.spring.wsy.framework.aop.aspect;

import com.spring.wsy.framework.aop.intercept.MethodInterceptorWsy;
import com.spring.wsy.framework.aop.intercept.MethodInvocationWsy;

import java.lang.reflect.Method;

/**
 * @author 王思洋
 * @version 1.0
 * @description
 * @date 2020-02-22 14:23
 */
public class AfterThrowingAdviceInterceptorWsy  extends AbstractAspectAdviceWsy implements MethodInterceptorWsy {

    private String throwingName;

    public AfterThrowingAdviceInterceptorWsy(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(MethodInvocationWsy mi) throws Throwable {
        try {
            return mi.proceed();
        }catch (Throwable e){
            invokeAdviceMethod(mi,null,e.getCause());
            throw e;
        }
    }

    public void setThrowName(String throwName){
        this.throwingName = throwName;
    }

}
