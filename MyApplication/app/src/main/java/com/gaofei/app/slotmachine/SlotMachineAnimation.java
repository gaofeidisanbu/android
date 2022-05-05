package com.gaofei.app.slotmachine;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.util.Log;
import android.util.Pair;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;

import com.gaofei.library.utils.DimenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaofei3 on 2022/4/26
 * Describe: 老虎机动画
 */
public class SlotMachineAnimation {
    public final static String TAG = "SlotMachineAnimation";
    private final List<SlotMachineElementInfo> mMachineElementInfoList = new ArrayList<>();
    private OnSlotMachineAnimationListener mSlotMachineAnimationListener;
    private int mStartIndex;
    private int mEndIndex;
    private int mEndIndexInList;
    private int mElementWidth;
    private int mElementHeight;
    private int mLength;
    private int mTotalRound;
    private int mCurrRound = 0;
    private float mTotalScrollRange;
    private int mCurrStartIndex = mStartIndex;
    private float mStartOffset = 0;
    //回弹距离
    private int mSpringBackDistance;
    private int mStartDelay;

    private SlotMachineAnimation(@NonNull Builder builder) {
        this.mSpringBackDistance = DimenUtils.dp2px(20);
        this.mStartIndex = builder.mStartIndex;
        this.mEndIndex = builder.mEndIndex;
        List<SlotMachineElementInfo> elementInfoList = builder.elementInfoList;
        int len = elementInfoList.size();
        int startIndexInList = -1;
        for (int i = 0; i < len; i++) {
            SlotMachineElementInfo temp = elementInfoList.get(i);
            if (mStartIndex == temp.getIndex()) {
                startIndexInList = i;
                break;
            }
        }
        if (startIndexInList == -1) {
            throw new IllegalArgumentException("mStartIndex = " + mStartIndex);
        }
        this.mMachineElementInfoList.addAll(elementInfoList.subList(mStartIndex, len));
        if (mStartIndex > 0) {
            this.mMachineElementInfoList.addAll(elementInfoList.subList(0, mStartIndex));
        }
        int endIndexInList = -1;
        for (int i = 0; i < len; i++) {
            SlotMachineElementInfo temp = mMachineElementInfoList.get(i);
            if (mEndIndex == temp.getIndex()) {
                endIndexInList = i;
                break;
            }
        }
        if (endIndexInList == -1) {
            throw new IllegalArgumentException("mEndIndex = " + mEndIndex);
        }
        this.mEndIndexInList = endIndexInList;
        this.mSlotMachineAnimationListener = builder.slotMachineAnimationListener;
        this.mElementWidth = builder.elementWidth;
        this.mElementHeight = builder.elementHeight;
        this.mLength = mMachineElementInfoList.size();

    }

    public Animator getAnimator() {
        Log.d(TAG, " start ");
        this.mCurrRound = 0;
        this.mTotalRound = calculateTotalRound();
        this.mTotalScrollRange = (mLength * mTotalRound + mEndIndexInList) * mElementHeight;
        ValueAnimator firstAnimator = ValueAnimator.ofFloat(0, 1f);
        firstAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        firstAnimator.setDuration(1000);
        firstAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float currScrollRange = mTotalScrollRange * (Float) valueAnimator.getAnimatedValue();
                Pair<Integer, Float> pair = calculateScrollPosition(currScrollRange);
                SlotMachineElementInfo machineElementInfo = mMachineElementInfoList.get(pair.first);
                SlotMachineAnimation.this.mCurrStartIndex = machineElementInfo.getIndex();
                SlotMachineAnimation.this.mStartOffset = pair.second;
                Log.d(TAG, " mCurrStartIndex = " + mCurrStartIndex + " count = " + count++);
                if (mSlotMachineAnimationListener != null) {
                    mSlotMachineAnimationListener.onAnimationUpdate(machineElementInfo, pair.second);
                }
            }
        });
        ValueAnimator secondAnimator = ValueAnimator.ofFloat(mTotalScrollRange, mTotalScrollRange + mSpringBackDistance, mTotalScrollRange);
        secondAnimator.setInterpolator(new LinearInterpolator());
        secondAnimator.setDuration(200);
        secondAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Pair<Integer, Float> pair = calculateScrollPosition((Float) valueAnimator.getAnimatedValue());
                SlotMachineElementInfo machineElementInfo = mMachineElementInfoList.get(pair.first);
                SlotMachineAnimation.this.mCurrStartIndex = machineElementInfo.getIndex();
                SlotMachineAnimation.this.mStartOffset = pair.second;
                if (mSlotMachineAnimationListener != null) {
                    mSlotMachineAnimationListener.onAnimationUpdate(machineElementInfo, pair.second);
                }
            }
        });
        AnimatorSet animationSet = new AnimatorSet();
        animationSet.setStartDelay(mStartDelay);
        animationSet.playSequentially(firstAnimator, secondAnimator);
        return animationSet;
    }


    /**
     * 计算总共轮次
     *
     * @return
     */
    private int calculateTotalRound() {
        return 2;
    }

    private int count = 0;

    private Pair<Integer, Float> calculateScrollPosition(float currScrollRange) {
        int scrollNum = (int) ((currScrollRange / mElementHeight)) % mLength;
        float offset = (int) (currScrollRange % mElementHeight);
        int currPosition = scrollNum;
        return Pair.create(currPosition, offset);
    }

    public float getStartOffset() {
        return mStartOffset;
    }

    public SlotMachineElementInfo getStartElementInfo() {
        return mMachineElementInfoList.get(mCurrStartIndex);
    }


    public static Builder newBuilder() {
        return new Builder();
    }

    public void setStartDelay(int startDelay) {
        this.mStartDelay = startDelay;
    }

    public static class Builder {
        private List<SlotMachineElementInfo> elementInfoList;
        private int mStartIndex;
        private int mEndIndex;
        private int elementWidth;
        private int elementHeight;
        private int columnIndex;
        private OnSlotMachineAnimationListener slotMachineAnimationListener;

        public Builder setElementSize(int width, int height) {
            this.elementWidth = width;
            this.elementHeight = height;
            return this;
        }

        public Builder setStartIndex(int startIndex) {
            this.mStartIndex = startIndex;
            return this;
        }

        public Builder setEndIndex(int endIndex) {
            this.mEndIndex = endIndex;
            return this;
        }

        public Builder setElementInfoList(List<SlotMachineElementInfo> elementInfoList, int columnIndex) {
            this.elementInfoList = elementInfoList;
            this.columnIndex = columnIndex;
            return this;
        }

        public Builder setSlotMachineAnimationListener(@NonNull OnSlotMachineAnimationListener slotMachineAnimationListener) {
            this.slotMachineAnimationListener = slotMachineAnimationListener;
            return this;
        }


        public SlotMachineAnimation builder() {
            return new SlotMachineAnimation(this);
        }
    }

    public interface OnSlotMachineAnimationListener {

        void onAnimationStart();

        void onAnimationUpdate(SlotMachineElementInfo startElementInfo, float offset);

        void onAnimationEnd();
    }


}
