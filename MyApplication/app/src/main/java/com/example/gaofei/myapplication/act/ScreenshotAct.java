package com.example.gaofei.myapplication.act;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

import com.example.gaofei.myapplication.BaseAct;
import com.example.gaofei.myapplication.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by gaofei on 2017/7/10.
 */

public class ScreenshotAct extends BaseAct{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_screen_shot);
        Log.d(TAG+"ccad",Thread.currentThread().getId()+"");
        try {
            json();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void json() throws JSONException {
        Map<String,Object> map = new HashMap<>();
        map.put("aaa","bb");
        User user = new User();
        user.name = "高飞";
        user.id = "1";
//        map.put("user",user);
        JSONObject jsonObject = new JSONObject(map);
        jsonObject.put("user",user);
        Log.d(TAG+"ccad"," map to jsonObject = "+jsonObject.toString());

        Map<String,Object> map1 = new HashMap<>();
        map1.put("aaa","bb");
        map1.put("map1",jsonObject);
        JSONObject jsonObject1 = new JSONObject(map1);
        Log.d(TAG+"ccad"," put jsonObject to map "+jsonObject1.toString());


        Map<String,Object> map2 = new HashMap<>();
        map2.put("aaa","bb");
        map2.put("map2",jsonObject.toString());
        JSONObject jsonObject2 = new JSONObject(map2);
        Log.d(TAG+"ccad","put jsonObject tostring to map"+jsonObject2.toString());

        JSONObject jsonObject3 = new JSONObject(jsonObject1.toString());
        Log.d(TAG+"ccad"," jsonObject3 = "+jsonObject3.get("map1").getClass().getName());

        JSONObject jsonObject4 = new JSONObject(jsonObject2.toString());
        Log.d(TAG+"ccad"," jsonObject4 = "+jsonObject4.get("map2").getClass().getName());

        Gson json = new GsonBuilder().create();
        Object object1 = json.fromJson(jsonObject3.get("map1").toString(),Object.class);
        Log.d(TAG+"ccad"," object1 = "+object1.getClass().getName());
        Object object2 = json.fromJson(jsonObject4.get("map2").toString(),Object.class);
        Log.d(TAG+"ccad"," object2 = "+object2.getClass().getName()+" ccad = "+object2);
    }

    public static class User{
        public String name;
        public String id;
    }
}
