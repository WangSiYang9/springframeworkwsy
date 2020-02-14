package com.spring.wsy.framework.webmvc.servlet;

import java.util.Map;

/**
 * @author 王思洋
 * @version 1.0
 * @description
 * @date 2020-02-03 15:46
 */
public class ModelAndViewWsy {
    private String viewName;
    private Map<String,?> model;

    public ModelAndViewWsy(String viewName) { this.viewName = viewName; }

    public ModelAndViewWsy(String viewName, Map<String, ?> model) {
        this.viewName = viewName;
        this.model = model;
    }

    public String getViewName() {
        return viewName;
    }

//    public void setViewName(String viewName) {
//        this.viewName = viewName;
//    }

    public Map<String, ?> getModel() {
        return model;
    }

//    public void setModel(Map<String, ?> model) {
//        this.model = model;
//    }
}
