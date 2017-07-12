package com.example.gaofei.myapplication;

import android.util.Log;

import com.example.gaofei.myapplication.act.AnnoAct;
import com.example.gaofei.myapplication.plugin.Test;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by gaofei on 2017/7/7.
 */

public class TestMain {
    public static final String TAG = "TestMain";

    public static void main(String[] args) {
        try {
            fun3();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error");
            System.out.println(e);
        }
    }

    public static void fun() {
        String str = "{\"a\":1,\"data\":[1]}";
        Gson gson = new Gson();
        Object object = gson.fromJson(str, Object.class);
        if (object != null) {
            Map<String, Object> map = (Map) object;
            List list = (List) map.get("data");
            System.out.println(object.getClass().getName() + " value = " + object.toString() + " type = " + list.get(0).getClass().getName());
        } else {
            System.out.println("null");
        }
    }

    public static void fun1() {
        Method[] declaredMethods = com.example.gaofei.myapplication.plugin.Test.class.getDeclaredMethods();
        for (Method method : declaredMethods) {
            Class[] paramterTypes = method.getParameterTypes();
            for (Class cla : paramterTypes) {
                System.out.println("method1 = " + cla.toString()+" type = "+cla.isPrimitive());
            }
            Class[] exceptions = method.getParameterTypes();
            for (Class cla : exceptions) {
                System.out.println("method2 = " + cla.getName());
            }

        }
    }


    public static void fun2() {
        try {
            Test<String> test = new Test<>();
            Class clazz = test.getClass();
            Object object = clazz.newInstance();
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                Class[] paramterTypes = method.getParameterTypes();
                for (Class cla : paramterTypes) {
                    System.out.println("method1 = " + cla.toString()+" type = "+cla.isPrimitive());
                }
                Integer aa = 3;
                method.invoke(object, aa);

            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    public static void fun3() {
        System.out.println(isMethodTypeMatch(int.class,int.class));
        System.out.println(isMethodTypeMatch(boolean.class,Boolean.TYPE));
        System.out.println(isMethodTypeMatch(Map.class,Map.class));
        System.out.println(isMethodTypeMatch(Map.class,HashMap.class));
        System.out.println((Class<Integer>) Integer[].class.getComponentType());
    }

    public static boolean isMethodTypeMatch(Class methodPara ,Class inputPara){
        if(methodPara == null && inputPara == null){
            return true;
        }
        if(methodPara == null || inputPara == null){
            return false;
        }

        if(methodPara.isPrimitive() && inputPara.isPrimitive()){
            return methodPara == inputPara;
        }

        if(!methodPara.isPrimitive() && !inputPara.isPrimitive()){
            return methodPara.isAssignableFrom(inputPara);
        }

        

        return false;
    }

    public static class Mytest implements Iterable<Mytest> {
        public int size = 5;

        @Override
        public Iterator iterator() {
            return new MyIterator();
        }

        public class MyIterator implements Iterator {

            @Override
            public boolean hasNext() {
                if (size > 0) {
                    size--;
                    return true;
                }
                return false;
            }

            @Override
            public Object next() {
                return new Object();
            }
        }
    }

}