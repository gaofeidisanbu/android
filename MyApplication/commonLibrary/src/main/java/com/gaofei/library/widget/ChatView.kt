package com.gaofei.library.widget

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import androidx.annotation.FloatRange
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.FrameLayout
import com.gaofei.library.utils.CommonUtils
import kotlin.math.roundToInt

class ChatView : FrameLayout {
    private lateinit var mChatPaint: Paint
    private lateinit var mTextPaint: Paint
    // 柱状图颜色
    private var mChatColor: Int = Color.parseColor("#FFFFFF")
    // 柱状图距离顶部margin
    private var mChatTopMargin: Float = 0f
    // 柱状距离底部高度
    private var mChatBottomMargin: Float = 0f
    // 柱状图最小高度，不算在业务计算（为0高度）
    private var mChatMinimumHeight: Float = 0f
    private var mChatMaxValue: Float = 0f
    private var mChatValue: Float = 0f

    @FloatRange(from = 0.0, to = 1.0)
    private var mChatRate: Float = 0f

    private var isPlayAnimator = true

    private var isStartPlay = false

//    private var mProgressValue = ProgressValue(0.0f, 0, "0", 0f, 0f, 0f, 0f)

//    private var mDrawAttribute = ChatAttributeValue(0f, 0f)

    private var mCurrNumber = 0


    constructor(context: Context) : super(context) {
        init(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        mChatPaint = Paint()
        mTextPaint = Paint()
        mTextPaint.textSize = CommonUtils.dip2px(context, 14f).toFloat()
        initAttribute(context, attrs, defStyleAttr)
        mChatPaint.color = mChatColor
        setWillNotDraw(false)
    }

    private fun initAttribute(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        mChatColor = Color.parseColor("#ff6666")
        mChatTopMargin = CommonUtils.dip2px(context, 20f).toFloat()
        mChatBottomMargin = CommonUtils.dip2px(context, 0f).toFloat()
        mChatMinimumHeight = CommonUtils.dip2px(context, 10f).toFloat()
        mChatMaxValue = CommonUtils.dip2px(context, 100f).toFloat()
        mChatValue = CommonUtils.dip2px(context, 80f).toFloat()
        mChatRate = mChatValue / mChatMaxValue
    }


//    fun start() {
//        if (isStartPlay) {
//            return
//        }
//        resetProgress()
//        val valueAnimator = ValueAnimator.ofFloat(0f, 1f)
//        valueAnimator.addUpdateListener {
//            val value = it.animatedValue as Float
//            calculateProgress(value)
//            invalidate()
//
//        }
//
//        valueAnimator.addListener(object : Animator.AnimatorListener {
//            override fun onAnimationRepeat(animation: Animator?) {
//
//            }
//
//            override fun onAnimationEnd(animation: Animator?) {
//                isStartPlay = false
//            }
//
//            override fun onAnimationCancel(animation: Animator?) {
//                isStartPlay = false
//            }
//
//            override fun onAnimationStart(animation: Animator?) {
//            }
//
//        })
//        valueAnimator.duration = 300
//        valueAnimator.interpolator = AccelerateInterpolator()
//        valueAnimator.start()
//        isStartPlay = true
//    }
//
//    private fun calculateProgress(progress: Float) {
//        Log.d("mCurrProgressValue", progress.toString())
//        mProgressValue.progressValue = progress
//        mProgressValue.realChatNumber = (mChatValue * progress).roundToInt()
//        mProgressValue.realChatNumberStr = mProgressValue.realChatNumber.toString()
//    }
//
//    private fun resetProgress() {
//
//    }
//
//    override fun onDraw(canvas: Canvas) {
////        super.onDraw(cawnvas)
//        if (isPlayAnimator && isStartPlay) {
//
//        }
//        val dWidth = measuredWidth
//        val dHeight = measuredHeight
//        calculateDrawAttribute(canvas)
//        if (dWidth > 0 && dHeight > 0) {
//            drawChatRect(canvas)
//            drawChatArc(canvas)
////            drawChatText(canvas)
//        }
//
//    }
//
//    private fun calculateDrawAttribute(canvas: Canvas) {
//        val dWidth = measuredWidth
//        val dHeight = measuredHeight
//        mDrawAttribute.width = dWidth.toFloat()
//        mDrawAttribute.height = dHeight.toFloat()
//        mProgressValue.chatDrawLeft = 0f
//        // 进度相关图表高度
//        val usedChatTotalHeight = mDrawAttribute.height - mChatBottomMargin - mChatTopMargin - mChatMinimumHeight
//        // 进度相关当前图表高度
//        val currUsedChatHeight = usedChatTotalHeight * 1
//        // 顶部高度
////        mProgressValue.chatDrawTop = mDrawAttribute.height - mChatBottomMargin - (mChatMinimumHeight + currUsedChatHeight) * mProgressValue.progressValue
//        mProgressValue.chatDrawTop = mDrawAttribute.height - mChatBottomMargin - (mChatMinimumHeight + currUsedChatHeight)
//        mProgressValue.chatDrawRight = mDrawAttribute.width
//        mProgressValue.chatDrawBottom = mDrawAttribute.height - mChatBottomMargin
//
//    }
//
//    private fun drawChatRect(canvas: Canvas) {
//        val rectLeft = mProgressValue.chatDrawLeft
//        // 去掉圆弧部分
//        val rectTop = mProgressValue.chatDrawTop + mDrawAttribute.width / 2
//        val rectRight = mProgressValue.chatDrawRight
//        val rectBottom = mProgressValue.chatDrawBottom
//        canvas.drawRect(rectLeft, rectTop, rectRight, rectBottom, mChatPaint)
//    }
//
//    private fun drawChatArc(canvas: Canvas) {
//        val arcLeft = mProgressValue.chatDrawLeft
//        val arcTop = mProgressValue.chatDrawTop
//        val arcRight = mProgressValue.chatDrawRight
//        val arcBottom = mProgressValue.chatDrawTop + mDrawAttribute.width
//        val rectF = RectF(arcLeft, arcTop, arcRight, arcBottom)
//        canvas.drawArc(rectF, -180f, 180f, true, mChatPaint)
//    }
//
//
//    private fun drawChatText(canvas: Canvas) {
//        val text = mProgressValue.realChatNumberStr
//        val toChatTop = CommonUtils.dip2px(context, 10f).toFloat()
//        val top = mProgressValue.chatDrawTop + toChatTop
//        canvas.drawText(text, 0f, top, mChatPaint)
//    }

//
//    data class ProgressValue(var progressValue: Float, var realChatNumber: Int, var realChatNumberStr: String, var chatDrawLeft: Float, var chatDrawTop: Float, var chatDrawRight: Float, var chatDrawBottom: Float)
//
//
//    data class ChatAttributeValue(var width: Float, var height: Float)

}