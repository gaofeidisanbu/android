package com.gaofei.app.slotmachine;

import android.animation.ValueAnimator;
import android.util.Pair;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaofei3 on 2022/4/26
 * Describe: 老虎机动画
 */
public class SlotMachineAnimation extends ValueAnimator {
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

    private SlotMachineAnimation(@NonNull Builder builder) {
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


    /**
     * 计算总共轮次
     *
     * @return
     */
    private int calculateTotalRound() {
        return 5;
    }

    @Override
    public void start() {
        this.mCurrRound = 0;
        this.mTotalRound = calculateTotalRound();
        this.mTotalScrollRange = (mLength * mTotalRound + mEndIndexInList) * mElementHeight;
        setFloatValues(0, 1f, 1.1f, 1f);
        setInterpolator(new AccelerateDecelerateInterpolator());
        setDuration(50 * 1000);
        addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
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
        super.start();
    }


    private Pair<Integer, Float> calculateScrollPosition(float value) {
        float currScrollRange = mTotalScrollRange * value;
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

    public static class Builder {
        private List<SlotMachineElementInfo> elementInfoList;
        private int mStartIndex;
        private int mEndIndex;
        private int elementWidth;
        private int elementHeight;
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

        public Builder setElementInfoList(List<SlotMachineElementInfo> elementInfoList) {
            this.elementInfoList = elementInfoList;
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
