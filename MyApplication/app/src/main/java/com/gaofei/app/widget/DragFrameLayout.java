package com.gaofei.app.widget;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;

import com.gaofei.library.utils.DimenUtils;
import com.gaofei.library.utils.LogUtils;

import org.jetbrains.annotations.NotNull;

/**
 * Created by gaofei3 on 5/7/21
 * Describe: 直播间最小化可拖动FrameLayout
 */
public class DragFrameLayout extends FrameLayout implements NestedScrollingParent2 {
    private static final String TAG = "DragFrameLayout";
    private NestedScrollingParentHelper mNestedScrollingParentHelper;
    private float lastX; //上一次位置的X.Y坐标
    private float lastY;
    private float nowX;  //当前移动位置的X.Y坐标
    private float nowY;
    private int tranX; //悬浮窗移动位置的相对值
    private int tranY;
    private int mTouchSlop;
    private Mode mode = Mode.None;
    protected Point lp = new Point();
    private int screenWidth;
    private ViewGroup mParentView;
    private int parentHeight;
    private int statusHeight;
    private int rootHeight;
    private int selfWidth;
    private int selfHeight;
    private int shadowRadius;
    private boolean isRTL = false;

    public enum Mode {
        None,
        Common,
        Move
    }

    public DragFrameLayout(@NonNull Context context) {
        super(context);
        init(context);
    }


    public DragFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DragFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public DragFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        screenWidth = DimenUtils.getWindowWidth();
        parentHeight = DimenUtils.getWindowHeight();
        int aaHeight = DimenUtils.getScreenHeight(context);
        statusHeight = DimenUtils.dp2px(32);
        rootHeight = parentHeight;
        isRTL = false;
        LogUtils.d(TAG, " screenWidth = " + screenWidth + " screenHeight = " + parentHeight + " statusHeight = " + statusHeight + " aaHeight = " + aaHeight);
        LogUtils.d(TAG, " rootHeight = " + rootHeight);
    }


    @Override
    public boolean onStartNestedScroll(@NonNull @NotNull View child, @NonNull @NotNull View target, int axes, int type) {
        return true;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull @NotNull View child, @NonNull @NotNull View target, int axes, int type) {
        mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes);
    }

    @Override
    public void onStopNestedScroll(@NonNull @NotNull View target, int type) {
        mNestedScrollingParentHelper.onStopNestedScroll(target);
    }

    @Override
    public void onNestedScroll(@NonNull @NotNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
//        RoomLogUtils.d(TAG, " onNestedScroll dxUnconsumed = " + dxUnconsumed + " dyUnconsumed = " + dyUnconsumed);
//        updateLocation(-dxUnconsumed, -dyUnconsumed);
    }

    @Override
    public void onNestedPreScroll(@NonNull @NotNull View target, int dx, int dy, @NonNull @NotNull int[] consumed, int type) {

    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onStopNestedScroll(View child) {
        mNestedScrollingParentHelper.onStopNestedScroll(child);
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes);
    }

    @Override
    public int getNestedScrollAxes() {
        return mNestedScrollingParentHelper.getNestedScrollAxes();
    }

    private boolean isScroll = false;
    private float touchDownX;
    private float touchDownY;
    private boolean mScrolling = false;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtils.d("aaaaa", "onInterceptTouchEvent ACTION_DOWN");
                touchDownX = ev.getRawX();
                touchDownY = ev.getRawY();
                lastX = touchDownX;
                lastY = touchDownY;
                mScrolling = false;
                i = 0;
                j = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtils.d("aaaaa", "1 index = "+i+++" x = "+ev.getX()+ " y = "+ev.getY());
                int axes = getNestedScrollAxes();
                boolean isAxesHor = (axes & ViewCompat.SCROLL_AXIS_HORIZONTAL) == 0;
                boolean isAxesVer = (axes & ViewCompat.SCROLL_AXIS_VERTICAL) == 0 ;
                if (isAxesHor && Math.abs(touchDownX - ev.getRawX()) >= mTouchSlop) {
                    mScrolling = true;
                }
                if (!mScrolling && isAxesVer && Math.abs(touchDownY - ev.getRawY()) >= mTouchSlop) {
                    mScrolling = true;
                }
                mScrolling = true;
                break;
            case MotionEvent.ACTION_UP:
                mScrolling = false;
                break;
        }
        return mScrolling;
    }

    public int  i = 0;
    public int  j = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 获取按下时的X，Y坐标
                lastX = event.getRawX();
                lastY = event.getRawY();
                isScroll = false;
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtils.d("aaaaa", "2 index = "+j+++" x = "+event.getX()+ " y = "+event.getY());
                if (mode != Mode.Move) {
//                    if (isTouchSlop(event)) {
//                        mode = Mode.Common;
//                    } else {
                        mode = Mode.Move;
//                    }
                }
                nowX = event.getRawX();
                nowY = event.getRawY();
                if (mode == Mode.Move) {
                    // 计算XY坐标偏移量
                    tranX = (int) (nowX - lastX);
                    tranY = (int) (nowY - lastY);
                    // 移动悬浮窗
                    lp.x += tranX;
                    lp.y += tranY;
                    updateLocation(tranX, tranY);
                    //记录当前坐标作为下一次计算的上一次移动的位置坐标
                    isScroll = true;
                }
                lastX = nowX;
                lastY = nowY;
                break;
            case MotionEvent.ACTION_UP:
//                        nowX = event.getX();
//                        nowY = event.getY();
                if (mode == Mode.Move) {
//                            correctLocation(nowX, nowY);
                }
                mode = Mode.None;
                break;
        }
        return true;
    }


    public void updateLocation(int tranX, int tranY) {
        if (mParentView == null) {
            mParentView = ((ViewGroup) this.getParent());
        }
        parentHeight = mParentView.getHeight();
        int parentHeightExceptPaddingBottom = parentHeight - mParentView.getPaddingBottom();
        int beforeTranX = (int) this.getTranslationX();
        int beforeTranY = (int) this.getTranslationY();
        int targetTranX = 0;
        int targetTranY = 0;
        int[] screenLocationArr = new int[2];
        getLocationOnScreen(screenLocationArr);
        int screenLocationX = screenLocationArr[0];
        int screenLocationY = screenLocationArr[1];
        int width = getWidth();
        int height = getHeight();
        if (tranX < 0) {
            if (screenLocationX + tranX < 0) {
                targetTranX = -screenLocationX;
            } else {
                targetTranX = tranX;
            }
        } else if (tranX > 0) {
            if (screenLocationX + tranX + width > screenWidth) {
                targetTranX = screenWidth - width - screenLocationX;
            } else {
                targetTranX = tranX;
            }
        } else {
            targetTranX = 0;
        }
        if (tranY < 0) {
            if (screenLocationY + tranY < statusHeight) {
                targetTranY = statusHeight - screenLocationY;
            } else {
                targetTranY = tranY;
            }
        } else if (tranY > 0) {
            if (screenLocationY + height + tranY > parentHeightExceptPaddingBottom) {
                targetTranY = parentHeightExceptPaddingBottom - screenLocationY - height;
            } else {
                targetTranY = tranY;
            }
        } else {
            targetTranY = 0;
        }
//        RoomLogUtils.d(TAG, "beforeTran = "+beforeTranY+" tranY = " + tranY + " targetTranY = " + targetTranY);
        setTranslationX(beforeTranX + targetTranX);
        setTranslationY(beforeTranY + targetTranY);
    }




    /**
     * 判断是否是轻微滑动
     */
    private boolean isTouchSlop(MotionEvent event) {
        float x = event.getRawX();
        float y = event.getRawY();
        if (Math.abs(x - lastX) < mTouchSlop / 4f && Math.abs(y - lastY) < mTouchSlop / 4f) {
            return true;
        }
        return false;
    }

    public OnDragListener onDragListener;

    public void setOnDragListener(OnDragListener listener) {
        this.onDragListener = listener;
    }

    public interface OnDragListener {
        void onDismiss();
    }

    public static class AnimInfo {
        public int start;
        public int end;
        public boolean isEnd;
        public boolean isHand = false;
    }

}
