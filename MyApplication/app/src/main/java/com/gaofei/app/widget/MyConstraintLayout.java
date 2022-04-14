package com.gaofei.app.widget;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowInsets;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * Created by gaofei on 07/12/2017.
 */

public class MyConstraintLayout extends ConstraintLayout {
    public MyConstraintLayout(Context context) {
        super(context);
    }

    public MyConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    int count = 1;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            count++;
            if (count == 30) {
                Log.i("HelloEventMultiPoint", "onInterceptTouchEvent");
                return true;
            }
        }
        return super.onInterceptHoverEvent(event);
    }

    @Override
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        return super.onApplyWindowInsets(insets);
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable parcelable = super.onSaveInstanceState();
        Bundle bundle = new Bundle();
        bundle.putParcelable("super", parcelable);
        bundle.putSerializable("test", "test");
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Parcelable supera = null;
        if (state instanceof Bundle) {
            supera = ((Bundle) state).getParcelable("super");
            String test = ((Bundle) state).getString("test");
            Log.d("aaa",test );
        }
        super.onRestoreInstanceState(supera);
    }
}
