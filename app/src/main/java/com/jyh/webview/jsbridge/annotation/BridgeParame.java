package com.jyh.webview.jsbridge.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by jiangyaohui on 2017/7/19.
 * 因为android是dex文件，不能用spring的工
 * 具解析到方法的参数名字，jdk8以上才才支持
 * 反射获取方法的参数名。故写此注解
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BridgeParame {

    String value();
}
