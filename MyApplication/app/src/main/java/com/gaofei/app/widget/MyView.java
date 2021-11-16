package com.gaofei.app.widget;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by gaofei on 2017/8/10.
 */

public class MyView extends View{
    private static final String TAG = "MyView";

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        boolean is =  super.dispatchTouchEvent(event);
        Log.d(TAG,"dispatchTouchEvent is = "+is+ " action = "+EventHelper.getEventName(event));
        return is;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean is =  super.onTouchEvent(event);
        Log.d(TAG,"onTouchEvent is = "+is+ " action = "+EventHelper.getEventName(event));
        return is;
    }

}
