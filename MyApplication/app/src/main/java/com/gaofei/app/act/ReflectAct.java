package com.gaofei.app.act;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gaofei.app.R;
import com.gaofei.app.databinding.ActReflectBinding;
import com.gaofei.library.base.BaseAct;
import com.gaofei.library.utils.LogUtils;

/**
 * Created by gaofei on 2017/9/18.
 */

public class ReflectAct extends BaseAct {
    private ActReflectBinding mBinding;

    public static class ReflectTimeInfo {
        public long reflectTime;
        public long noReflectTime;
        public long diffTime;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.act_reflect);
    }


    public void onStartTest() {

    }

    private ReflectTimeInfo objectInstance1Test() {
        ReflectTimeInfo reflectTimeInfo = new ReflectTimeInfo();
        long start = System.currentTimeMillis();
        try {
            Class clazz = Class.forName("com.gaofei.app.act$$ReflectTest");
            clazz.newInstance();
            reflectTimeInfo.reflectTime = System.currentTimeMillis() - start;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        start = System.currentTimeMillis();
        new ReflectTest();
        reflectTimeInfo.noReflectTime = System.currentTimeMillis() - start;
        return reflectTimeInfo;
    }

    public static class ReflectTest {
        public int method(int a, int b) {
            if (a > 2) {
                a = 3;
            }
            return a + b;
        }
    }
}
