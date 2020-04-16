package com.gaofei.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gaofei.library.base.BaseAct;

public class TaskAffinity extends Activity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_task_affinity);
    }
}
