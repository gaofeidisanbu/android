package com.gaofei.app.act;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.gaofei.app.R;
import com.gaofei.library.ProjectApplication;
import com.gaofei.library.base.BaseAct;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaofei on 2017/7/10.
 */

public class ScreenshotAct extends BaseAct {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_screen_shot);
    }

}
