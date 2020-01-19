package com.gaofei.app.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaofei.app.R;
import com.gaofei.library.base.BaseAct;
import com.gaofei.library.utils.LogUtils;
import com.gaofei.library.utils.ToastManager;

/**
 * Created by gaofei on 07/12/2017.
 */

public class TouchEventAct extends BaseAct {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.act_touch_event);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
        }
    }
}
