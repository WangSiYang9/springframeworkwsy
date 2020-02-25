package com.spring.wsy.framework.aop.aspect;

import com.spring.wsy.framework.aop.intercept.MethodInterceptorWsy;
import com.spring.wsy.framework.aop.intercept.MethodInvocationWsy;

import java.lang.reflect.Method;

/**
 * @author 王思洋
 * @version 1.0
 * @description
 * @date 2020-02-22 14:17
 */
public class MethodBeforeAdviceInterceptorWsy  extends AbstractAspectAdviceWsy implements MethodInterceptorWsy {

    private JoinPointWsy joinPoint;

    public MethodBeforeAdviceInterceptorWsy(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    private void before(Method method,Object[] args,Object target) throws Throwable{
        //传送了给织入参数
        //method.invoke(target);
        super.invokeAdviceMethod(this.joinPoint,null,null);

    }
    @Override
    public Object invoke(MethodInvocationWsy mi) throws Throwable {
        //从被织入的代码中才能拿到，JoinPoint
        this.joinPoint = mi;
        before(mi.getMethod(), mi.getArguments(), mi.getThis());
        return mi.proceed();
    }
}
