package com.gaofei.app.slotmachine;

import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

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


    public void startSpin(@NonNull SlotMachinePlayInfo playInfo, @NonNull OnSlotMachinePlayerListener listener, @NonNull OnPlayerListener onPlayerListener) {
        if (mAnimatorSet != null && mAnimatorSet.isRunning()) {
            return;
        }
        this.mOnPlayerListener = onPlayerListener;
        SlotMachineAnimation slotMachineAnimation0 = createSlotMachineAnimation(mColumnInfo0, mStartIndex0, mStartIndex2 + 4, new SlotMachineAnimation.OnSlotMachineAnimationListener() {
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
        SlotMachineAnimation slotMachineAnimation1 = createSlotMachineAnimation(mColumnInfo2, mStartIndex1, mStartIndex2 + 4, new SlotMachineAnimation.OnSlotMachineAnimationListener() {
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
        SlotMachineAnimation slotMachineAnimation2 = createSlotMachineAnimation(mColumnInfo2, mStartIndex2, mStartIndex2 + 4, new SlotMachineAnimation.OnSlotMachineAnimationListener() {
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
        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playTogether(slotMachineAnimation0, slotMachineAnimation1, slotMachineAnimation2);
        mAnimatorSet.start();
    }


    private SlotMachineAnimation createSlotMachineAnimation(List<SlotMachineElementInfo> slotMachineElementInfoList, int startIndex, int endIndex, SlotMachineAnimation.OnSlotMachineAnimationListener slotMachineAnimationListener) {
        return SlotMachineAnimation
                .newBuilder()
                .setElementInfoList(slotMachineElementInfoList)
                .setElementSize(mCanvas.getWidth(), mCanvas.getHeight())
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
        draw();
        mCanvas.bitblt(canvas, false);
    }


    private void draw() {
        mCanvas.clear(0);
        draw(mColumnInfo0, 0, mStartIndex0, mStartOffset0);
        draw(mColumnInfo0, 1, mStartIndex1, mStartOffset1);
        draw(mColumnInfo0, 2, mStartIndex2, mStartOffset2);
    }

    private void draw(@NonNull List<SlotMachineElementInfo> slotMachineElementInfoList, int columnIndex, int startIndex, float startOffset) {
        int drawLen = 3;
        if (startOffset != 0) {
            drawLen = 4;
        }
        int len = slotMachineElementInfoList.size();
        int currIndex = startIndex;
        for (int i = 0; i < drawLen; i++) {
            SlotMachineElementInfo slotMachineElementInfo = slotMachineElementInfoList.get(currIndex);
            draw(slotMachineElementInfo, columnIndex, i, startOffset);
            if (currIndex + 1 >= len) {
                currIndex = 0;
            }
        }
    }

    private void draw(@NonNull SlotMachineElementInfo elementInfo, int columnIndex, int rowIndex, float startOffset) {
        int width = mCanvas.getWidth();
        int height = mCanvas.getHeight();
        float x = (width / 3f) * columnIndex;
        float y = height - (height * (rowIndex + 1) / 3f - startOffset);
        float centerX = (x + x + width / 3f) / 2f;
        float centerY = (y + y + height / 3f) / 2f;
        mCanvas.draw(getBitmap(elementInfo.getKey()), centerX, centerY, 1, 1f);
    }

    private Bitmap getBitmap(String url) {
        Bitmap bitmap = mCacheBitmap.get(url);
        if (bitmap != null) {
            return bitmap;
        }
        return BitmapFactory.decodeResource(mContext.getResources(), R.drawable.app_icon);
    }


    private boolean validateSlotMachineColumnInfo(@NonNull SlotMachineColumnInfo columnInfo) {
        return true;
    }


}
