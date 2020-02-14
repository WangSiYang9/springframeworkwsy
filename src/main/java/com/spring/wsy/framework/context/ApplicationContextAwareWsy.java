package com.spring.wsy.framework.context;

/**
 * @author 王思洋
 * @version 1.0
 * @description 通过解耦的方式获得IOC容器的顶层设计，
 * 后面间通过一个监听器去扫描所有的类，只要实现了此接口，
 * 将自动调用setApplicationContext()方法 ,从而将IOC容器植入到目标类中
 * @date 2020-01-10 14:27
 */
public interface ApplicationContextAwareWsy {
    void setApplicationContext(ApplicationContextWsy applicationContextWsy);
}
