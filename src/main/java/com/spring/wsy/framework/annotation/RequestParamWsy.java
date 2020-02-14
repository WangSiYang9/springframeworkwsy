package com.spring.wsy.framework.annotation;

import java.lang.annotation.*;

/**
 * @author 王思洋
 * @version 1.0
 * @description 请求参数映射
 * @date 2020-02-02 20:50
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestParamWsy {
	
	String value() default "";
	
	boolean required() default true;

}
