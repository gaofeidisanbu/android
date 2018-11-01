package com.gaofei.app.task

import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import com.gaofei.library.utils.LogUtils

class RewardCoinViewAnimation {
    var animationValue: Float = 0f
        set(value) {
            LogUtils.d("animationValue $value")
        }
    val propertyValuesHolder: PropertyValuesHolder

    constructor() {
        val keyframe1 = Keyframe.ofFloat(0f, 0f)
        val keyframe2 = Keyframe.ofFloat(0.5f, 100f)
        val keyframe3 = Keyframe.ofFloat(0.8f, 150f)
        val keyframe4 = Keyframe.ofFloat(1f, 200f)
        propertyValuesHolder = PropertyValuesHolder.ofKeyframe("animationValue", keyframe1, keyframe2, keyframe3, keyframe4)
    }


    companion object {
        fun play(rewardCoinViewAnimation: RewardCoinViewAnimation) {
            val valueAnimator = ObjectAnimator.ofPropertyValuesHolder(rewardCoinViewAnimation, rewardCoinViewAnimation.propertyValuesHolder)
            valueAnimator.duration = 300
            valueAnimator.start()
        }
    }
}