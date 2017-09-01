package com.example.mylibrary.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by gaofei on 2017/8/10.
 */

public class MyRelativelayout extends RelativeLayout{
    public MyRelativelayout(Context context) {
        super(context);
    }

    public MyRelativelayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRelativelayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyRelativelayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
