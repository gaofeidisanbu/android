package com.gaofei.app.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.mylibrary.base.BaseAct;
import com.gaofei.app.plugin.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

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
        Test test = new Test();
        Method[] declaredMethods = Test.class.getDeclaredMethods();
        for (Method method:declaredMethods){
            AnnoTest annoTest =  method.getAnnotation(AnnoTest.class);
            Type type = method.getReturnType();
            if(type  == Void.TYPE){
                Log.d(TAG,"isNull = class "+(type  == Void.TYPE));
            }
            boolean isNull = (annoTest == null);
//            try {
////                Object object = method.invoke(test);
                Log.d(TAG,"isNull = "+isNull+" methodName = "+method.getName()+" return = "+type+" ");
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            }
        }
    }


    @Retention(RetentionPolicy.CLASS)
    @Target({ElementType.METHOD})
    public @interface AnnoTest {

    }



}
