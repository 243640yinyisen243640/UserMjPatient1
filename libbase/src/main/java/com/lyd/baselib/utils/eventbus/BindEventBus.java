package com.lyd.baselib.utils.eventbus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 描述: EventBus注解类,
 * 注:继承 BaseViewBindingActivity 后,需要使用该注解
 * 注:继承 BaseEventBusActivity 后,不需要使用该注解
 * 作者: LYD
 * 创建日期: 2018/6/13 10:59
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)//反射
public @interface BindEventBus {

}
