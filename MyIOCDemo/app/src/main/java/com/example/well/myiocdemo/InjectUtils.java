package com.example.well.myiocdemo;

import android.content.Context;
import android.view.View;

import com.example.well.myiocdemo.annotation.ContentView;
import com.example.well.myiocdemo.annotation.EventBus;
import com.example.well.myiocdemo.annotation.ListenerInvocationHandler;
import com.example.well.myiocdemo.annotation.OnClick;
import com.example.well.myiocdemo.annotation.ViewById;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

/**
 * Created by Well on 2016/12/2.
 */

public class InjectUtils {

    public static void inject(Context context) {
        injectContentView(context);
        injectViewById(context);
        iniectOnClick(context);
    }

    private static void iniectOnClick(final Context context) {
        Class<? extends Context> clazz = context.getClass();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            OnClick annotation = method.getAnnotation(OnClick.class);
            if (annotation == null) {
                continue;
            } else {
                Class<? extends Annotation> aClass1 = annotation.annotationType();
                EventBus eventBus = aClass1.getAnnotation(EventBus.class);
                if (eventBus == null) {
                    continue;
                } else {
                    String listenerSetter = eventBus.listenerSetter();
                    Class<?> aClass = eventBus.listenerType();
                    String backMethod = eventBus.callBackMethod();

                    HashMap<String,Method> map = new HashMap<>();
                    map.put(backMethod,method);

                    int[] ids = annotation.value();//获得id
                    for (int i :ids){
                        try {
                            Method findViewById = clazz.getMethod("findViewById", int.class);
                            View view = (View) findViewById.invoke(context, i);
                            if(null==view){
                                continue;
                            }
                            Method method1 = view.getClass().getMethod(listenerSetter, aClass);
                            ListenerInvocationHandler invocationHandler = new ListenerInvocationHandler(context,map);
                            Object o = Proxy.newProxyInstance(aClass.getClassLoader(), new Class[]{aClass}, invocationHandler);//实现了接口的
                            method1.invoke(view,o);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }


            }

        }


    }

    private static void injectViewById(Context context) {
        Class<? extends Context> clazz = context.getClass();
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            ViewById annotation = field.getAnnotation(ViewById.class);
            if ((null != annotation)) {
                int id = annotation.value();
                try {
                    Method findViewById = clazz.getMethod("findViewById", int.class);
                    Object View = findViewById.invoke(context, id);
                    field.setAccessible(true);
                    field.set(context, View);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                continue;
            }
        }
    }

    private static void injectContentView(Context context) {
        Class<?> clazz = context.getClass();
        ContentView annotation = clazz.getAnnotation(ContentView.class);
        int layoutId = annotation.value();
        try {
            Method method = clazz.getMethod("setContentView", int.class);
            method.invoke(context, layoutId);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
