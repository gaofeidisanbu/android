package com.example.gaofei.myapplication.act;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.gaofei.myapplication.BaseAct;
import com.example.gaofei.myapplication.plugin.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

/**
 * Created by gaofei on 2017/7/4.
 */

public class AnnoAct extends BaseAct {
    public int aa;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"aa = "+aa);
        processAnno();

    }


    public void processAnno(){
        Method[] declaredMethods = Test.class.getDeclaredMethods();
        for (Method method:declaredMethods){
            AnnoTest annoTest =  method.getAnnotation(AnnoTest.class);
            boolean isNull = (annoTest == null);
            Log.d(TAG,"isNull = "+isNull+" methodName = "+method.getName());
        }
    }


    @Retention(RetentionPolicy.CLASS)
    @Target({ElementType.METHOD})
    public @interface AnnoTest {

    }



}
