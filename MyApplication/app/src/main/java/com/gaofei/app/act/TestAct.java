package com.gaofei.app.act;

import android.animation.ValueAnimator;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
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
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.gaofei.app.R;
import com.gaofei.app.databinding.ActTestBinding;
import com.gaofei.library.base.BaseAct;
import com.gaofei.library.utils.CommonUtils;
import com.gaofei.library.utils.LogUtils;

/**
 * Created by gaofei on 2017/6/29.
 */

public class TestAct extends BaseAct {
    private ActTestBinding mBinding;
    private boolean isToolBarShow = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.act_test);
    }


}
