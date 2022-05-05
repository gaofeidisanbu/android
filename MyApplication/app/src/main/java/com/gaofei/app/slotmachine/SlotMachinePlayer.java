package com.gaofei.app.slotmachine;

import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import androidx.annotation.NonNull;

import com.gaofei.app.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gaofei3 on 2022/4/24
 * Describe:老虎机播放器
 */
public class SlotMachinePlayer {
    private final String TAG = "SlotMachinePlayer";
    private final static int LENGTH = 3;
    private final Map<String, Bitmap> mCacheBitmap = new HashMap<>();
    private Context mContext;
    private AnimatorSet mAnimatorSet;
    private List<SlotMachineElementInfo> mColumnInfo0;
    private int mStartIndex0;
    private float mStartOffset0;
    private List<SlotMachineElementInfo> mColumnInfo1;
    private int mStartIndex1;
    private float mStartOffset1;
    private List<SlotMachineElementInfo> mColumnInfo2;
    private int mStartIndex2;
    private float mStartOffset2;
    private SlotMachineCanvas mCanvas;
    private boolean isInitSuccess;
    private OnPlayerListener mOnPlayerListener;

    public SlotMachinePlayer(Context context, SlotMachineCanvas canvas) {
        this.mContext = context;
        this.mCanvas = canvas;
    }

    public boolean init(@NonNull List<SlotMachineElementInfo> columnInfo0, @NonNull List<SlotMachineElementInfo> columnInfo1, @NonNull List<SlotMachineElementInfo> columnInfo2) {
        isInitSuccess = false;
        this.mColumnInfo0 = columnInfo0;
        this.mColumnInfo1 = columnInfo1;
        this.mColumnInfo2 = columnInfo2;
        isInitSuccess = true;

        return true;
    }

    private static final int ANIMATION_DELAY = 600;

    public void startSpin(@NonNull SlotMachinePlayInfo playInfo, @NonNull OnSlotMachinePlayerListener listener, @NonNull OnPlayerListener onPlayerListener) {
        if (mAnimatorSet != null && mAnimatorSet.isRunning()) {
            return;
        }
        this.mOnPlayerListener = onPlayerListener;
        Log.d(SlotMachineAnimation.TAG, "startSpin");
        //mStartIndex2 + 4有问题
        SlotMachineAnimation slotMachineAnimation0 = createSlotMachineAnimation(0, mColumnInfo0, mStartIndex0, mStartIndex2 + 4, new SlotMachineAnimation.OnSlotMachineAnimationListener() {
            @Override
            public void onAnimationStart() {

            }

            @Override
            public void onAnimationUpdate(SlotMachineElementInfo startElementInfo, float offset) {
                SlotMachinePlayer.this.mStartIndex0 = startElementInfo.getIndex();
                SlotMachinePlayer.this.mStartOffset0 = offset;
                doFrameUpdate();
            }

            @Override
            public void onAnimationEnd() {

            }
        });
        SlotMachineAnimation slotMachineAnimation1 = createSlotMachineAnimation(1, mColumnInfo2, mStartIndex1, mStartIndex2 + 4, new SlotMachineAnimation.OnSlotMachineAnimationListener() {
            @Override
            public void onAnimationStart() {

            }

            @Override
            public void onAnimationUpdate(SlotMachineElementInfo startElementInfo, float offset) {
                SlotMachinePlayer.this.mStartIndex1 = startElementInfo.getIndex();
                SlotMachinePlayer.this.mStartOffset1 = offset;
                doFrameUpdate();
            }

            @Override
            public void onAnimationEnd() {

            }
        });
        slotMachineAnimation1.setStartDelay(ANIMATION_DELAY);
        SlotMachineAnimation slotMachineAnimation2 = createSlotMachineAnimation(2, mColumnInfo2, mStartIndex2, mStartIndex2 + 4, new SlotMachineAnimation.OnSlotMachineAnimationListener() {
            @Override
            public void onAnimationStart() {

            }

            @Override
            public void onAnimationUpdate(SlotMachineElementInfo startElementInfo, float offset) {
                SlotMachinePlayer.this.mStartIndex2 = startElementInfo.getIndex();
                SlotMachinePlayer.this.mStartOffset2 = offset;
                doFrameUpdate();
            }

            @Override
            public void onAnimationEnd() {

            }
        });
        slotMachineAnimation2.setStartDelay(ANIMATION_DELAY * 2);
        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playTogether(slotMachineAnimation0.getAnimator(), slotMachineAnimation1.getAnimator(), slotMachineAnimation2.getAnimator());
        mAnimatorSet.start();
    }


    private SlotMachineAnimation createSlotMachineAnimation(int columnIndex, List<SlotMachineElementInfo> slotMachineElementInfoList, int startIndex, int endIndex, SlotMachineAnimation.OnSlotMachineAnimationListener slotMachineAnimationListener) {
        return SlotMachineAnimation
                .newBuilder()
                .setElementInfoList(slotMachineElementInfoList, columnIndex)
                .setElementSize(mCanvas.getWidth() / LENGTH, mCanvas.getHeight() / LENGTH)
                .setStartIndex(startIndex)
                .setEndIndex(endIndex)
                .setSlotMachineAnimationListener(slotMachineAnimationListener).builder();
    }

    private void doFrameUpdate() {
        if (mOnPlayerListener != null) {
            mOnPlayerListener.onFrameUpdate();
        }
    }

    public void onDraw(@NonNull Canvas canvas) {
        draw(canvas);
    }


    private void draw(Canvas canvas) {
        draw(canvas, mColumnInfo0, 0, mStartIndex0, mStartOffset0);
        draw(canvas, mColumnInfo1, 1, mStartIndex1, mStartOffset1);
        draw(canvas, mColumnInfo2, 2, mStartIndex2, mStartOffset2);
    }

    private void draw(Canvas canvas, @NonNull List<SlotMachineElementInfo> slotMachineElementInfoList, int columnIndex, int startIndex, float startOffset) {
        int drawLen = 3;
        if (startOffset != 0) {
            drawLen = 4;
        }
        int len = slotMachineElementInfoList.size();
        int currIndex = startIndex;
        for (int i = 0; i < drawLen; i++) {
            SlotMachineElementInfo slotMachineElementInfo = slotMachineElementInfoList.get(currIndex);
            draw(canvas, slotMachineElementInfo, columnIndex, i, startOffset);
            if (currIndex + 1 == len) {
                currIndex = 0;
            } else {
                currIndex++;
            }
        }
    }

    private void draw(Canvas canvas, @NonNull SlotMachineElementInfo elementInfo, int columnIndex, int rowIndex, float startOffset) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        Log.d(TAG, "center width = "+width+ " height = "+height);
        float x = (width / 3f) * columnIndex;
        float y = height - (height * (rowIndex + 1) / 3f - startOffset);
        float centerX = (x + x + width / 3f) / 2f;
        float centerY = (y + y + height / 3f) / 2f;
        mCanvas.draw(canvas, getBitmap(elementInfo.getKey()), centerX, centerY, 1, 1f, elementInfo.getIndex());
    }


    private Bitmap getBitmap(String url) {
        Bitmap bitmap = mCacheBitmap.get(url);
        if (bitmap != null) {
            return bitmap;
        }
        bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.app_icon);
        mCacheBitmap.put(url, bitmap);
        return bitmap;
    }


    private boolean validateSlotMachineColumnInfo(@NonNull SlotMachineColumnInfo columnInfo) {
        return true;
    }


}
