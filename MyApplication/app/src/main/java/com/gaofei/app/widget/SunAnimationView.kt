package com.gaofei.app.widget

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.FrameLayout
import com.gaofei.app.R
import com.gaofei.library.utils.CommonUtils
import kotlinx.android.synthetic.main.layout_sun_view.view.*

/**
 * 指导学太阳动画
 */
class SunAnimationView : FrameLayout {

    private var mAnimatorSet: AnimatorSet? = null
    private var mSunAnimator: java.util.ArrayList<Animator> = ArrayList()
    private var mSun1Animator: java.util.ArrayList<Animator> = ArrayList()
    private var mSun2Animator: java.util.ArrayList<Animator> = ArrayList()
    private var mSun3Animator: java.util.ArrayList<Animator> = ArrayList()

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context)
    }

    private fun init(context: Context) {
        val width = CommonUtils.dip2px(context, 110f)
        val marginTop = CommonUtils.dip2px(context, 10f)
        val sunView = LayoutInflater.from(context).inflate(R.layout.layout_sun_view, this, false)
        addView(sunView)
        initSunAnimator()
        initSun1Animator()
        initSun2Animator()
        initSun3Animator()
    }

    private fun initSunAnimator() {
        val translationY = CommonUtils.dip2px(context, 20f).toFloat()
        mSunAnimator.clear()
        val propertyValuesHolderStartAlpha = PropertyValuesHolder.ofFloat("alpha", 0f, 1f)
        val alphaAnimator = ObjectAnimator.ofPropertyValuesHolder(sunViewRoot, propertyValuesHolderStartAlpha)
        alphaAnimator.interpolator = AccelerateInterpolator()
        alphaAnimator.duration = 1000
        mSunAnimator.add(alphaAnimator)

        val propertyValuesHolderStartTranslationY = PropertyValuesHolder.ofFloat("translationY", translationY, 0f)
        val translationYAnimator = ObjectAnimator.ofPropertyValuesHolder(sunViewRoot, propertyValuesHolderStartTranslationY)
        translationYAnimator.interpolator = AccelerateInterpolator()
        translationYAnimator.duration = 2000
        mSunAnimator.add(translationYAnimator)
    }

    private fun initSun1Animator() {
        mSun1Animator.clear()
        val scaleAnimator = ObjectAnimator.ofFloat(0f, 1f)
        scaleAnimator.interpolator = AccelerateInterpolator()
        scaleAnimator.duration = 400
        scaleAnimator.addUpdateListener {
            val scale = it.animatedValue as Float
            updateViewScale(sunView1, scale)
        }

        mSun1Animator.add(scaleAnimator)
    }


    private fun initSun2Animator() {
        mSun2Animator.clear()
        val scaleAnimator = ObjectAnimator.ofFloat(0f, 1f)
        scaleAnimator.interpolator = AccelerateInterpolator()
        scaleAnimator.duration = 200
        scaleAnimator.startDelay = 200
        scaleAnimator.addUpdateListener {
            val scale = it.animatedValue as Float
            updateViewScale(sunView1, scale)
        }

        mSun2Animator.add(scaleAnimator)
    }

    private fun initSun3Animator() {
        mSun3Animator.clear()
        val scaleAnimator = ObjectAnimator.ofFloat(0f, 1f)
        scaleAnimator.interpolator = AccelerateInterpolator()
        scaleAnimator.duration = 400
        scaleAnimator.startDelay = 0
        scaleAnimator.addUpdateListener {
            val scale = it.animatedValue as Float
            updateViewScale(sunView1, scale)
        }

        mSun3Animator.add(scaleAnimator)
    }


    fun start(listener: Animator.AnimatorListener?) {
        clearViewAnimationStatus()
        this.visibility = View.VISIBLE
        val startAnimatorSet = AnimatorSet()
        val startAnimators = java.util.ArrayList<Animator>()
        mSunAnimator.forEach {
            startAnimators.add(it)
        }
        mSun1Animator.forEach {
            startAnimators.add(it)
        }

        mSun2Animator.forEach {
            startAnimators.add(it)
        }

        mSun3Animator.forEach {
            startAnimators.add(it)
        }
        startAnimatorSet.playTogether(startAnimators)
        mAnimatorSet = AnimatorSet()
        mAnimatorSet?.let {
            it.play(startAnimatorSet)
            listener?.let { listener ->
                it.addListener(listener)
            }
            it.start()
        }


    }

    private fun clearViewAnimationStatus() {

    }

    private fun clearAllAnimator() {
        mAnimatorSet?.let {
            it.cancel()
        }
    }

    fun stop() {

    }

    private fun updateViewScale(view: View, scale: Float) {
        setViewPivotScale(view, scale)
    }

    /**
     * 设置从view中心点缩放
     */
    private fun setViewPivotScale(view: View, scale: Float) {
        val width = view.width
        val height = view.height
        view.pivotX = (width / 2).toFloat()
        view.pivotY = (height / 2).toFloat()
        view.scaleX = scale
        view.scaleY = scale
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        clearAllAnimator()
    }


}