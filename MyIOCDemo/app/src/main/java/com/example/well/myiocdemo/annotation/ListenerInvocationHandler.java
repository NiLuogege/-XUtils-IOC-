package com.example.well.myiocdemo.annotation;

import android.content.Context;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by Well on 2016/12/2.
 */

public class ListenerInvocationHandler implements InvocationHandler {
    private Context mContext;
    private Map<String, Method> mMap;

    public ListenerInvocationHandler(Context context, Map<String, Method> map) {
        mContext = context;
        mMap = map;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        String name = method.getName();
        Method method1 = mMap.get(name);
        if (method1 == null) {
            return method.invoke(o, objects);
        } else {
            method1.invoke(mContext, objects);
        }
        return null;
    }
}
