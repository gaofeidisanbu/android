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
    //开始索引
    private int mStartIndex;
    //结束索引
    private int mEndIndex;
    /**
     * 动画结束索引，在{#mMachineElementInfoList}索引
     */
    private int mEndIndexInList;
    //元素宽度
    private int mElementWidth;
    //元素高度
    private int mElementHeight;
    //元素个数
    private int mElementLength;
    //轮次
    private int mTotalRound;
    //当前轮次
    private int mCurrRound = 0;
    //动画总共滚动距离
    private float mTotalScrollRange;
    //当前索引
    private int mCurrStartIndex = mStartIndex;
    //开始位置偏移（底部第一个元素）
    private float mStartOffset = 0;
    //回弹距离
    private int mSpringBackDistance;
    //动画延迟执行时间
    private int mStartDelay;

    private SlotMachineAnimation(@NonNull Builder builder) {
        this.mStartIndex = builder.mStartIndex;
        this.mEndIndex = builder.mEndIndex;
        this.mSlotMachineAnimationListener = builder.slotMachineAnimationListener;
        this.mElementWidth = builder.elementWidth;
        this.mElementHeight = builder.elementHeight;
        List<SlotMachineElementInfo> elementInfoList = builder.elementInfoList;
        int len = elementInfoList.size();
        //方便计算，把mStartIndex移动到索引0位置
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
        mMachineElementInfoList.clear();
        this.mMachineElementInfoList.addAll(elementInfoList.subList(mStartIndex, len));
        if (mStartIndex > 0) {
            this.mMachineElementInfoList.addAll(elementInfoList.subList(0, mStartIndex));
        }
        //寻找在list里结束索引位置
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
        this.mElementLength = mMachineElementInfoList.size();
        this.mSpringBackDistance = DimenUtils.dp2px(20);
    }

    public Animator getAnimator() {
        Log.d(TAG, " start ");
        this.mCurrRound = 0;
        this.mTotalRound = calculateTotalRound();
        //计算总共滚动的距离
        this.mTotalScrollRange = (mElementLength * mTotalRound + mEndIndexInList) * mElementHeight;
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
     * 计算总共动画执行轮次
     *
     * @return 轮次数
     */
    private int calculateTotalRound() {
        return 2;
    }


    private Pair<Integer, Float> calculateScrollPosition(float currScrollRange) {
        int scrollNum = (int) ((currScrollRange / mElementHeight)) % mElementLength;
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
