package com.example.gaofei.myapplication.act;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
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
import com.example.gaofei.myapplication.utils.CommonUtils;
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
        mTextView = (TextView) findViewById(R.id.name);
        String name = "198元/原价258元";
        int index = name.indexOf("/");
        int count = name.length();
        if (index > 0 && index < count - 1) {
            SpannableStringBuilder builder = new SpannableStringBuilder(name);
            ForegroundColorSpan colorSpan1 = new ForegroundColorSpan(getResources().getColor(R.color.yc_gray7));
            builder.setSpan(colorSpan1, 0, index + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            StyleSpan styleBold = new StyleSpan(Typeface.NORMAL);
            builder.setSpan(styleBold, 0, index + 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);



//            StrikethroughSpan strike = new StrikethroughSpan();
//            TextPaint textPaint = new TextPaint();
//            textPaint.setColor(Color.parseColor("#ff6666"));
//            textPaint.linkColor = Color.parseColor("#ff6666");
//            textPaint.bgColor = Color.parseColor("#ff6666");
//            strike.updateDrawState(textPaint);


            builder.setSpan(new StrikethroughSpan(){
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(Color.parseColor("#ff6666"));
//                    ds.baselineShift = Color.parseColor("#ff6666");
//                    ds.bgColor = Color.parseColor("#ff6666");
                    ds.setStrikeThruText(true);
                }
            }, index + 1, count, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            AbsoluteSizeSpan absoluteSizeSpan1 = new AbsoluteSizeSpan(CommonUtils.dip2px(this,20));
            builder.setSpan(absoluteSizeSpan1, 0, index + 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            AbsoluteSizeSpan  absoluteSizeSpan2 = new AbsoluteSizeSpan(CommonUtils.dip2px(this,15));
            builder.setSpan(absoluteSizeSpan2, index + 1, count, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            StyleSpan styleNormal = new StyleSpan(Typeface.NORMAL);
            builder.setSpan(styleNormal, index + 1, count, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(getResources().getColor(R.color.yc_gray3));
            builder.setSpan(colorSpan2, index + 1, count, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            mTextView.setText(builder);
        } else {
            mTextView.setText(name);
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
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                 break;
        }
    }
}
