package com.spring.wsy.framework.aop;

import com.spring.wsy.framework.aop.support.AdvisedSupportWsy;

/**
 * @author 王思洋
 * @version 1.0
 * @description
 * @date 2020-02-18 11:51
 */
public class CglibAopProxyWsy implements AopProxyWsy {

    private AdvisedSupportWsy advised;

    public CglibAopProxyWsy(AdvisedSupportWsy config) throws Exception {
        this.advised = config;
    }


    @Override
    public Object getProxy() {
        return null;
    }

    @Override
    public Object getProxy(ClassLoader var1) {
        return null;
    }
}
