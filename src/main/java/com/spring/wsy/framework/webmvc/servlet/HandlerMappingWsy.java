package com.spring.wsy.framework.webmvc.servlet;

import lombok.Data;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * @author 王思洋
 * @version 1.0
 * @description
 * @date 2020-02-13 15:01
 */
@Data
public class HandlerMappingWsy {
    private Object controller;	//保存方法对应的实例
    private Method method;		//保存映射的方法
    private Pattern pattern;    //URL的正则匹配

    public HandlerMappingWsy(Pattern pattern,Object controller, Method method) {
        this.controller = controller;
        this.method = method;
        this.pattern = pattern;
    }
}
