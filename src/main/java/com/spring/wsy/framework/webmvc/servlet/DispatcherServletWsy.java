package com.spring.wsy.framework.webmvc.servlet;

import com.spring.wsy.framework.annotation.ControllerWsy;
import com.spring.wsy.framework.annotation.RequestMappingWsy;
import com.spring.wsy.framework.context.ApplicationContextWsy;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 王思洋
 * @version 1.0
 * @description
 * @date 2020-02-03 17:40
 */
@Slf4j
public class DispatcherServletWsy extends HttpServlet {

    private final String CONTEXT_CONFIG_LOCATION = "contextConfigLocation";

    private ApplicationContextWsy context;

    private List<HandlerMappingWsy> handlerMappings = new ArrayList<HandlerMappingWsy>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            this.doDispatch(req,resp);
        }catch(Exception e){
            resp.getWriter().write("500 Exception,Details:\r\n" + Arrays.toString(e.getStackTrace()).replaceAll("\\[|\\]", "").replaceAll(",\\s", "\r\n"));
            e.printStackTrace();
//            new GPModelAndView("500");

        }
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //1、通过从request中拿到URL，去匹配一个HandlerMapping
        HandlerMappingWsy handler = getHandler(req);



    }
    private HandlerMappingWsy getHandler(HttpServletRequest req) throws Exception{
        if(this.handlerMappings.isEmpty()){ return null; }
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replace(contextPath, "").replaceAll("/+", "/");

        for (HandlerMappingWsy handler : this.handlerMappings) {
            try{
                Matcher matcher = handler.getPattern().matcher(url);
                //如果没有匹配上继续下一个匹配
                if(!matcher.matches()){ continue; }

                return handler;
            }catch(Exception e){
                throw e;
            }
        }
        return null;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        //1、初始化ApplicationContext
        context = new ApplicationContextWsy(config.getInitParameter(CONTEXT_CONFIG_LOCATION));
        //2、初始化Spring MVC 九大组件
        initStrategies(context);
    }


    //初始化策略
    protected void initStrategies(ApplicationContextWsy context) {
        //多文件上传的组件
        initMultipartResolver(context);
        //初始化本地语言环境
        initLocaleResolver(context);
        //初始化模板处理器
        initThemeResolver(context);


        //handlerMapping，必须实现
        initHandlerMappings(context);
        //初始化参数适配器，必须实现
        initHandlerAdapters(context);
        //初始化异常拦截器
        initHandlerExceptionResolvers(context);
        //初始化视图预处理器
        initRequestToViewNameTranslator(context);


        //初始化视图转换器，必须实现
        initViewResolvers(context);
        //参数缓存器
        initFlashMapManager(context);
    }

    private void initMultipartResolver(ApplicationContextWsy context) {
    }

    private void initLocaleResolver(ApplicationContextWsy context) {
    }

    private void initThemeResolver(ApplicationContextWsy context) {
    }

    // handlerMapping 主要迭代Controller和url一一对应的关系
    private void initHandlerMappings(ApplicationContextWsy context) {
        String [] beanNames = context.getBeanDefinitionNames();

        try {
            for (String beanName : beanNames) {

                Object controller = context.getBean(beanName);
                Class<?> clazz = controller.getClass();
                //判断是否属于ControllerWsy，不属于continue
                if (!clazz.isAnnotationPresent(ControllerWsy.class)){
                    continue;
                }
                String baseUrl = "";

                //判断是否属于RequestMappingWsy
                if (clazz.isAnnotationPresent(RequestMappingWsy.class)){
                    //类映射url
                    RequestMappingWsy requestMappingWsy = clazz.getAnnotation(RequestMappingWsy.class);
                    baseUrl = requestMappingWsy.value();
                }

                //获取method 的url配置
                Method[] methods = clazz.getMethods();

                for (Method method:methods){
                    if (method.isAnnotationPresent(RequestMappingWsy.class)){
                        //  /demo/query
                        //  (//demo//query)
                        //方法映射url
                        RequestMappingWsy requestMappingWsy = method.getAnnotation(RequestMappingWsy.class);
                        String regex = ("/" + baseUrl + "/" + requestMappingWsy.value().replaceAll("\\*",".*")).replaceAll("/+", "/");
                        Pattern pattern = Pattern.compile(regex);

                        this.handlerMappings.add(new HandlerMappingWsy(pattern,controller,method));
                        log.info("Mapped " + regex + "," + method);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //TODO
    private void initHandlerAdapters(ApplicationContextWsy context) {
        //把一个requet请求变成一个handler，参数都是字符串的，自动配到handler中的形参

        //可想而知，他要拿到HandlerMapping才能干活
        //就意味着，有几个HandlerMapping就有几个HandlerAdapter

    }

    private void initHandlerExceptionResolvers(ApplicationContextWsy context) {
    }

    private void initRequestToViewNameTranslator(ApplicationContextWsy context) {
    }

    //TODO
    private void initViewResolvers(ApplicationContextWsy context) {
    }

    private void initFlashMapManager(ApplicationContextWsy context) {
    }





}
