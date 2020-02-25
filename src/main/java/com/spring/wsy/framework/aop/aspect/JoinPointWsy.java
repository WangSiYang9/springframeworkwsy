package com.spring.wsy.framework.aop.aspect;

import java.lang.reflect.Method;

/**
 * @author 王思洋
 * @version 1.0
 * @description
 * @date 2020-02-22 16:59
 */
public interface JoinPointWsy {
    Object getThis();

    Object[] getArguments();

    Method getMethod();

    void setUserAttribute(String key, Object value);

    Object getUserAttribute(String key);
}
