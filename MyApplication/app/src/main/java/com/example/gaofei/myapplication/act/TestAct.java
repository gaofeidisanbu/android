package com.example.gaofei.myapplication.act;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.OnCompositionLoadedListener;
import com.example.gaofei.myapplication.BaseAct;
import com.example.gaofei.myapplication.R;
import com.example.gaofei.myapplication.utils.LogUtils;
import com.example.gaofei.myapplication.utils.ToastManager;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by gaofei on 2017/6/29.
 */

public class TestAct extends BaseAct implements View.OnClickListener{
    private Button mButton;
    private LottieAnimationView mLottieAnimationView;
    private LottieAnimationView lottie_bubble;
    private TextView mTextView;
    public static String BASE_H5_URL             = "https://h5.yangcong345.com";
    public static       String URL_TEACHER_GUIDE    = BASE_H5_URL + "/teacherH5-login.html#/welcome?token=%s&deviceType=%s";
    public static       String URL_FORGET_PSW       = BASE_H5_URL + "/mobile_forget_password.html?version=4";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(this);
        mLottieAnimationView = (LottieAnimationView) findViewById(R.id.img_icon_anim);
        lottie_bubble = (LottieAnimationView) findViewById(R.id.lottie_bubble);
        mTextView = (TextView) findViewById(R.id.text);
        addPubParam(URL_TEACHER_GUIDE);
        addPubParam(URL_FORGET_PSW);
        try {
            URL url = new URL(URL_TEACHER_GUIDE);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static String addPubParam(String url){
        if(!TextUtils.isEmpty(url)){
            Uri.Builder builder = Uri.parse(url).buildUpon();
            builder.appendQueryParameter("appVersion", "1.1.1");
            builder.appendQueryParameter("appVersion", "1.1.2");
            Log.d(TAG,builder.build().toString() + " "+builder.build().getQuery());
            return builder.build().toString();
        }
        return "";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                final LottieDrawable drawable = new LottieDrawable();
                drawable.loop(true);
                LottieComposition.Factory.fromAssetFileName(this, "Button_A.json", new OnCompositionLoadedListener() {
                    @Override
                    public void onCompositionLoaded(@Nullable LottieComposition composition) {
                        Log.d(TAG,"width = "+composition.getBounds().width()+" height = "+composition.getBounds().height());
                        drawable.setComposition(composition);
                        drawable.playAnimation();
                        mLottieAnimationView.setImageDrawable(drawable);
//                        mTextView.setBackgroundDrawable(drawable);

                    }
                });
//                mLottieAnimationView.setScale(0.5f);
//                mLottieAnimationView.setAnimation("data1.json");
//                mLottieAnimationView.loop(true);
//                mLottieAnimationView.playAnimation();
                 break;
        }
    }
}
