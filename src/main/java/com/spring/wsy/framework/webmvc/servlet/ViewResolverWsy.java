package com.spring.wsy.framework.webmvc.servlet;

import java.io.File;
import java.util.Locale;

/**
 * @author 王思洋
 * @version 1.0
 * @description
 * @date 2020-02-14 19:45
 */
public class ViewResolverWsy {

    private final String DEFAULT_TEMPLATE_SUFFX = ".html";

    private File templateRootDir;

    public ViewResolverWsy(String templateRoot) {
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();
        templateRootDir = new File(templateRootPath);
    }

    public ViewWsy resolveViewName(String viewName, Locale locale) throws Exception{
        if(null == viewName || "".equals(viewName.trim())){return null;}
        viewName = viewName.endsWith(DEFAULT_TEMPLATE_SUFFX) ? viewName : (viewName + DEFAULT_TEMPLATE_SUFFX);
        File templateFile = new File((templateRootDir.getPath() + "/" + viewName).replaceAll("/+","/"));
        return new ViewWsy(templateFile);
    }
}
