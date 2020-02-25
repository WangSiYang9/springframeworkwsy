package com.spring.wsy.framework.aop.config;

import lombok.Data;

/**
 * @author 王思洋
 * @version 1.0
 * @description
 * @date 2020-02-20 16:21
 */
@Data

public class AopConfigWsy  {

    private String pointCut;
    private String aspectBefore;
    private String aspectAfter;
    private String aspectClass;
    private String aspectAfterThrow;
    private String aspectAfterThrowingName;
}
