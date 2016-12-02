package com.example.well.myiocdemo.annotation;

/**
 * Created by Well on 2016/12/2.
 */

public @interface EventBus {
    /**
     * 监听事件的方法
     *
     * @return
     */
    String listenerSetter();

    /**
     * 事件类型
     *
     * @return
     */
    Class<?> listenerType();

    /**
     * 事件的回调方法
     */

    String callBackMethod();


}
