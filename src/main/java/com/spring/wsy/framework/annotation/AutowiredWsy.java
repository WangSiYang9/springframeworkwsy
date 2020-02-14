package com.spring.wsy.framework.annotation;

import java.lang.annotation.*;

/**
 * @author 王思洋
 * @version 1.0
 * @description
 * @date 2020-02-02 20:50
 */
@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutowiredWsy {
    String value() default "";
}
