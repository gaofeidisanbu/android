package com.gaofei.app.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by gaofei on 2017/8/10.
 */

public class MyRecyclerView extends RecyclerView {
    private final static String TAG = "Test11:MyRecyclerView";
    public MyRecyclerView(Context context) {
        super(context);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG,"onMeasure");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.d(TAG,"onLayout");
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean is =  super.onInterceptTouchEvent(event);
        Log.d(TAG,"onInterceptTouchEvent is = "+is+ " action = "+EventHelper.getEventName(event));
        return is;
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

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }
}
