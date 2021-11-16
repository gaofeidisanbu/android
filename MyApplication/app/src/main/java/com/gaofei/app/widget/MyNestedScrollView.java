package com.gaofei.app.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.NestedScrollView;

import org.jetbrains.annotations.NotNull;

/**
 * Created by gaofei on 2017/8/10.
 */

public class MyNestedScrollView extends NestedScrollView {
    private final static String TAG = "Test11:MyNestedScrollView";
    public MyNestedScrollView(Context context) {
        super(context);
    }

    public MyNestedScrollView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyNestedScrollView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return super.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow, int type) {
        return super.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type);
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return super.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        super.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return super.hasNestedScrollingParent();
    }

    @Override
    public boolean hasNestedScrollingParent(int type) {
        return super.hasNestedScrollingParent(type);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow, int type) {
        return super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type);
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        super.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean startNestedScroll(int axes, int type) {
        return super.startNestedScroll(axes, type);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(target, dx, dy, consumed);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

    @Override
    public void onNestedPreScroll(@NonNull @NotNull View target, int dx, int dy, @NonNull @NotNull int[] consumed, int type) {
        super.onNestedPreScroll(target, dx, dy, consumed, type);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
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
//        Log.d(TAG,"onDraw");
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
        Log.d(TAG,"dispatchTouchEvent is = "+is+ " action = "+EventHelper.getEventName(event)+" scrollY = "+getScrollY());
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
