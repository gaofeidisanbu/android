package com.gaofei.app.widget;

import android.content.Context;
import android.graphics.Canvas;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by gaofei on 2017/8/10.
 */

public class MyTextView extends AppCompatTextView{
    private final static String TAG = "MyTextView";
    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG,"onDraw");
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_CANCEL) {
            Log.d(TAG,"ACTION_CANCEL ");
        }
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

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(TAG,"onAttachedToWindow");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(TAG,"onDetachedFromWindow");
    }

    @Override
    public void onStartTemporaryDetach() {
        super.onStartTemporaryDetach();
        Log.d(TAG,"onStartTemporaryDetach");
    }

    @Override
    public void onFinishTemporaryDetach() {
        super.onFinishTemporaryDetach();
        Log.d(TAG,"onFinishTemporaryDetach");
    }
}
