package com.gaofei.app.act;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.view.View;

import com.gaofei.app.R;
import com.gaofei.app.databinding.ActReflectBinding;
import com.gaofei.library.base.BaseAct;
import com.gaofei.library.utils.LogUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * Created by gaofei on 2017/9/18.
 */

public class ReflectAct extends BaseAct {
    private ActReflectBinding mBinding;

    public static class ReflectTimeInfo {
        public int n;
        public long forName;
        public long reflectInstanceTime;
        public long noReflectInstanceTime;
        public long reflectGetMethodsTime;
        public long reflectGetMethodWithParamTime;
        public long reflectGetMethodWithNoParamTime;
        public long reflectMethodInvokeTime;
        public long noReflectMethodInvokeTime;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.act_reflect);
        mBinding.text.setMovementMethod(ScrollingMovementMethod.getInstance());
    }


    public void onStartTest(View view) {
        ReflectTimeInfo instance1 = objectInstanceTest(1);
        ReflectTimeInfo instance10 = objectInstanceTest(10);
        ReflectTimeInfo instance1000 = objectInstanceTest(100);
        ReflectTimeInfo instance10000 = objectInstanceTest(1000);
        StringBuilder sb = new StringBuilder();
        sb.append(getText(0, instance1, instance10, instance1000, instance10000));
        ReflectTimeInfo method1 = objectMethodTest(1);
        ReflectTimeInfo method10 = objectMethodTest(10);
        ReflectTimeInfo method100 = objectMethodTest(100);
        ReflectTimeInfo method1000 = objectMethodTest(1000);
        sb.append("\n");
        sb.append(getText(1, method1, method10, method100, method1000));
        mBinding.text.setText(sb.toString());
    }

    public String getText(int type, ReflectTimeInfo... reflectTimeInfos) {
        StringBuilder sb = new StringBuilder();
        sb.append(type == 0 ? "对象" : "方法");
        sb.append(":");
        for (ReflectTimeInfo reflectTimeInfo : reflectTimeInfos) {
            sb.append("\n");
            sb.append("   ").append(type == 0 ? getObjectFormatStr(reflectTimeInfo) : getMethodFormatStr(reflectTimeInfo));
        }
        return sb.toString();
    }


    private String getObjectFormatStr(ReflectTimeInfo reflectTimeInfo) {
        StringBuilder sb = new StringBuilder();
        sb.append("n = ").append(reflectTimeInfo.n);
        sb.append(" forName = ").append(reflectTimeInfo.forName);
        sb.append(" 反射对象 = ").append(reflectTimeInfo.reflectInstanceTime);
        sb.append(" 没有反射对象 = ").append(reflectTimeInfo.noReflectInstanceTime);
        sb.append(" 差 = ").append(reflectTimeInfo.reflectInstanceTime - reflectTimeInfo.noReflectInstanceTime);
        if (reflectTimeInfo.noReflectInstanceTime != 0) {
            sb.append(" 除 = ").append(reflectTimeInfo.reflectInstanceTime / reflectTimeInfo.noReflectInstanceTime);
        }
        return sb.toString();
    }

    private String getMethodFormatStr(ReflectTimeInfo reflectTimeInfo) {
        StringBuilder sb = new StringBuilder();
        sb.append("n = ").append(reflectTimeInfo.n);
        sb.append(" getDeclaredMethods = ").append(reflectTimeInfo.reflectGetMethodsTime);
        sb.append(" getDeclaredMethod = ").append(reflectTimeInfo.reflectGetMethodWithParamTime);
        sb.append(" getDeclaredMethod = ").append(reflectTimeInfo.reflectGetMethodWithNoParamTime);
        sb.append(" invoke = ").append(reflectTimeInfo.reflectMethodInvokeTime);
        sb.append(" no invoke = ").append(reflectTimeInfo.noReflectMethodInvokeTime);
        sb.append(" 差 = ").append(reflectTimeInfo.reflectMethodInvokeTime - reflectTimeInfo.noReflectMethodInvokeTime);
        if (reflectTimeInfo.noReflectMethodInvokeTime != 0) {
            sb.append(" 除 = ").append(reflectTimeInfo.reflectMethodInvokeTime / reflectTimeInfo.noReflectMethodInvokeTime);
        }
        return sb.toString();
    }


    private ReflectTimeInfo objectInstanceTest(int n) {
        ReflectTimeInfo reflectTimeInfo = new ReflectTimeInfo();
        reflectTimeInfo.n = n;
        Class clazz = null;
        long start;
        try {
            start = System.currentTimeMillis();
            for (int i = 0; i < n; i++) {
                clazz = Class.forName("com.gaofei.app.act.ReflectAct$ReflectTest");
            }
            reflectTimeInfo.forName = System.currentTimeMillis() - start;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            start = System.currentTimeMillis();
            for (int i = 0; i < n; i++) {
                clazz.newInstance();
            }
            reflectTimeInfo.reflectInstanceTime = System.currentTimeMillis() - start;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        start = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            new ReflectTest();
        }
        reflectTimeInfo.noReflectInstanceTime = System.currentTimeMillis() - start;
        return reflectTimeInfo;
    }

    private ReflectTimeInfo objectMethodTest(int n) {
        ReflectTimeInfo reflectTimeInfo = new ReflectTimeInfo();
        reflectTimeInfo.n = n;
        ReflectTest reflectTest = new ReflectTest();
        long start = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            reflectTest.getClass().getDeclaredMethods();
        }
        reflectTimeInfo.reflectGetMethodsTime = System.currentTimeMillis() - start;
        try {
            start = System.currentTimeMillis();
            for (int i = 0; i < n; i++) {
                reflectTest.getClass().getDeclaredMethod("method", int.class, int.class);
            }
            reflectTimeInfo.reflectGetMethodWithParamTime = System.currentTimeMillis() - start;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            start = System.currentTimeMillis();
            for (int i = 0; i < n; i++) {
                reflectTest.getClass().getDeclaredMethod("method");
            }
            reflectTimeInfo.reflectGetMethodWithNoParamTime = System.currentTimeMillis() - start;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        Method method = null;
        try {
            method = reflectTest.getClass().getDeclaredMethod("method", int.class, int.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            start = System.currentTimeMillis();
            for (int i = 0; i < n; i++) {
                method.invoke(reflectTest, 1, 2);
            }
            reflectTimeInfo.reflectMethodInvokeTime = System.currentTimeMillis() - start;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        start = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            reflectTest.method(1, 2);
        }
        reflectTimeInfo.noReflectMethodInvokeTime = System.currentTimeMillis() - start;
        return reflectTimeInfo;
    }

    public static class ReflectTest {
        public int method(int a, int b) {
            if (a > 2) {
                a = 3;
            }
            return a + b;
        }

        public int method() {
            int a = 1;
            int b = 2;
            if (a > 2) {
                a = 3;
            }
            return a + b;
        }
    }
}
