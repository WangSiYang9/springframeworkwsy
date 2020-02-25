package com.spring.wsy.framework.aop.aspect;

import java.lang.reflect.Method;

/**
 * @author 王思洋
 * @version 1.0
 * @description
 * @date 2020-02-22 15:26
 */
public abstract class AbstractAspectAdviceWsy {
    private Method aspectMethod;
    private Object aspectTarget;
    public AbstractAspectAdviceWsy(Method aspectMethod, Object aspectTarget) {
        this.aspectMethod = aspectMethod;
        this.aspectTarget = aspectTarget;
    }

    public Object invokeAdviceMethod(JoinPointWsy joinPoint, Object returnValue, Throwable tx) throws Throwable{

        Class<?> [] paramTypes = this.aspectMethod.getParameterTypes();
        if(null == paramTypes || paramTypes.length == 0){
            return this.aspectMethod.invoke(aspectTarget);
        }else{
            Object [] args = new Object[paramTypes.length];
            for (int i = 0; i < paramTypes.length; i ++) {
                if(paramTypes[i] == JoinPointWsy.class){
                    args[i] = joinPoint;
                }else if(paramTypes[i] == Throwable.class){
                    args[i] = tx;
                }else if(paramTypes[i] == Object.class){
                    args[i] = returnValue;
                }
            }
            return this.aspectMethod.invoke(aspectTarget,args);
        }
    }


}
