package com.spring.wsy.framework.webmvc.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 王思洋
 * @version 1.0
 * @description
 * @date 2020-02-13 21:22
 */
public class HandlerAdapterWsy {
    public boolean supports(Object handler){ return (handler instanceof HandlerMappingWsy);}
    ModelAndViewWsy handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        return null;
    }

}
