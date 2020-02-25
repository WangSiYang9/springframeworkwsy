package com.spring.wsy.framework.aop.aspect;

import com.spring.wsy.framework.aop.intercept.MethodInterceptorWsy;
import com.spring.wsy.framework.aop.intercept.MethodInvocationWsy;

import java.lang.reflect.Method;

/**
 * @author 王思洋
 * @version 1.0
 * @description
 * @date 2020-02-22 14:18
 */
public class AfterReturningAdviceInterceptorWsy extends AbstractAspectAdviceWsy implements MethodInterceptorWsy {

    private JoinPointWsy joinPoint;


    public AfterReturningAdviceInterceptorWsy(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }
    @Override
    public Object invoke(MethodInvocationWsy mi) throws Throwable {
        Object retVal = mi.proceed();
        this.joinPoint = mi;
        this.afterReturning(retVal,mi.getMethod(),mi.getArguments(),mi.getThis());
        return retVal;
    }

    private void afterReturning(Object retVal, Method method, Object[] arguments, Object aThis) throws Throwable {
        super.invokeAdviceMethod(this.joinPoint,retVal,null);
    }
}
