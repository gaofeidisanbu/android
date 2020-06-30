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
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

//    public static int getDefaultSize(int size, int measureSpec) {
//        int result = size;
//        int specMode = MeasureSpec.getMode(measureSpec);
//        int specSize = MeasureSpec.getSize(measureSpec);
//
//        switch (specMode) {
//            case MeasureSpec.UNSPECIFIED:
//                result = size;
//                break;
//            case MeasureSpec.AT_MOST:
//            case MeasureSpec.EXACTLY:
//                result = 100;
//                break;
//        }
//        return result;
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("HelloEventMultiPoint", "Total pointer count is " + event.getPointerCount());

        String actionString = actionToString(event.getAction());
        Log.i("HelloEventMultiPoint", "Main action is " + actionString);

        int maskedAction = event.getActionMasked();
        int pointId = event.getActionIndex();
        Log.i("HelloEventMultiPoint", "Masked action is " + actionToString(maskedAction) + "\tpointId is " + pointId);
        Log.i("HelloEventMultiPoint", "====================");
        return true;
    }

    private String actionToString(int action) {
        String actionString = null;
        switch (action) {
            case MotionEvent.ACTION_CANCEL:
                actionString = "ACTION_CANCEL";
                break;
            case MotionEvent.ACTION_DOWN:
                actionString = "ACTION_DOWN";
                break;
            case MotionEvent.ACTION_MOVE:
                actionString = "ACTION_MOVE";
                break;
            case MotionEvent.ACTION_OUTSIDE:
                actionString = "ACTION_OUTSIDE";
                break;
            case MotionEvent.ACTION_UP:
                actionString = "ACTION_UP";
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                actionString = "ACTION_POINTER_DOWN";
                break;
            case MotionEvent.ACTION_POINTER_UP:
                actionString = "ACITON_POINTER_UP";
                break;
            default:
                actionString = "" + action;
        }

        return actionString;
    }
}
