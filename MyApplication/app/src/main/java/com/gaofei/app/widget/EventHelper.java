package com.gaofei.app.widget;

import android.view.MotionEvent;

/**
 * Created by gaofei3 on 2021/10/26
 * Describe:
 */
public class EventHelper {
    public static String getEventName(MotionEvent event) {
        int actionMasked = event.getActionMasked();
        int action = event.getAction();
        int actionIndex = event.getActionIndex();
        int count = event.getPointerCount();
        StringBuilder sb = new StringBuilder();
        if (actionMasked == MotionEvent.ACTION_DOWN) {
            sb.append("ACTION_DOWN");
        } else if (actionMasked == MotionEvent.ACTION_UP) {
            sb.append("ACTION_UP");
        } else if (actionMasked == MotionEvent.ACTION_MOVE) {
            sb.append("ACTION_MOVE");
        } else if (actionMasked == MotionEvent.ACTION_CANCEL) {
            sb.append("ACTION_CANCEL");
        } else if (action == MotionEvent.ACTION_OUTSIDE) {
            sb.append("ACTION_OUTSIDE");
        } else if (actionMasked == MotionEvent.ACTION_POINTER_DOWN) {
            sb.append("ACTION_POINTER_DOWN");
        } else if (actionMasked == MotionEvent.ACTION_POINTER_UP) {
            sb.append("ACTION_POINTER_UP");
        } else if (actionMasked == MotionEvent.ACTION_HOVER_MOVE) {
            sb.append("ACTION_HOVER_MOVE");
        } else {
            sb.append("others");
        }
        sb.append(" actionMasked = " + actionMasked + " action = " + action + " actionIndex = " + actionIndex +" id= "+event.getPointerId(actionIndex)+" count = "+count+ " y = "+event.getY());
        return sb.toString();
    }
}
