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
        mBinding.shadow.setVisibility(View.INVISIBLE);
        mBinding.toolbar.setAlpha(0);
        mBinding.scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int diff = CommonUtils.dip2px(TestAct.this, 15);
                int currDirection = getDirection(scrollY, oldScrollY);
                LogUtils.d("onScrollChange  scrollY = " + scrollY + " oldScrollY = " + oldScrollY + " currDirection = " + currDirection + " isToolBarShow = " + isToolBarShow);
                if (currDirection == 1 && !isToolBarShow && scrollY > diff) {
                    showAlphaAnimation(mBinding.toolbar, 0, 1, 700, isToolBarShow);
                    isToolBarShow = true;
                } else if (currDirection == -1 && isToolBarShow && scrollY < diff) {
                    isToolBarShow = false;
                    showAlphaAnimation(mBinding.toolbar, 1, 0, 500, isToolBarShow);
                }

            }
        });

        mBinding.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlphaAnimation(mBinding.toolbar, 0, 1, 2000, isToolBarShow);
            }
        });
    }


    private int getDirection(int scrollY, int oldScrollY) {
        if (scrollY - oldScrollY > 0) {
            return 1;
        } else if (scrollY - oldScrollY == 0) {
            return 0;
        } else {
            return -1;
        }
    }

    private AlphaAnimation animation;
    private ValueAnimator valueAnimator;

    private void showAlphaAnimation(final View view, float start, float end, long duration, final boolean isShow) {
        if (valueAnimator != null) {
            valueAnimator.cancel();
        } else {
            valueAnimator = ValueAnimator.ofFloat();
            valueAnimator.setDuration(duration);
        }
        valueAnimator.setFloatValues(start, end);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float) animation.getAnimatedValue();
                LogUtils.d("currentValue = " + currentValue);
                view.setAlpha(currentValue);
                if (isShow) {
                    mBinding.shadow.setVisibility(View.VISIBLE);
                } else {
                    mBinding.shadow.setVisibility(View.INVISIBLE);
                }
            }
        });
        valueAnimator.start();
    }

}
