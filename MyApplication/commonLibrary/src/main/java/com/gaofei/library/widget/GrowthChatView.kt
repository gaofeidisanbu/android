package com.gaofei.library.widget

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import androidx.annotation.FloatRange
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.animation.AccelerateInterpolator
import com.example.mylibrary.R
import com.gaofei.library.utils.CommonUtils
import kotlin.math.roundToInt

class GrowthChatView : ConstraintLayout {


    private lateinit var mChatPaint: Paint
    private lateinit var mTextPaint: Paint
    // 柱状图颜色
    private var mChatColor: Int = Color.parseColor("#FF6666")
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

    private var mProgressValue = ProgressValue(0.0f)

    private var mCurrNumber = 0

    private var mTotalNumber = 3

    private val mChatGroupList = ArrayList<ChatGroup>(mTotalNumber)


    constructor(context: Context) : super(context) {
        init(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr)
    }

    private var chatGroupItemWidth: Float = 0f

    private var chatGroupItemHeight: Float = 0f

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        LayoutInflater.from(context).inflate(R.layout.layout_growth_chat, this, true)
        mChatPaint = Paint()
        mTextPaint = Paint()
        mTextPaint.textSize = CommonUtils.dip2px(context, 14f).toFloat()
        initAttribute(context, attrs, defStyleAttr)
        mChatPaint.color = mChatColor
        setWillNotDraw(false)
        chatGroupItemWidth = CommonUtils.dip2px(context, 20f).toFloat()
        chatGroupItemHeight = CommonUtils.dip2px(context, 100f).toFloat()
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


    fun start() {
        if (isStartPlay) {
            return
        }
        resetProgress()
        val valueAnimator = ValueAnimator.ofFloat(0f, 1f)
        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            calculateProgress(value)
            invalidate()

        }

        valueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                isStartPlay = false
            }

            override fun onAnimationCancel(animation: Animator?) {
                isStartPlay = false
            }

            override fun onAnimationStart(animation: Animator?) {
            }

        })
        valueAnimator.duration = 300
        valueAnimator.interpolator = AccelerateInterpolator()
        valueAnimator.start()
        isStartPlay = true
    }

    private fun calculateProgress(progress: Float) {
        Log.d("mCurrProgressValue", progress.toString())
        mProgressValue.progress = progress
        updateChatItem()

    }

    private fun updateChatItem() {
        mChatGroupList.forEach { chatGroup ->
            chatGroup.chatGroupItem.forEach { chatGroupItem ->
                updateChatGroupItemTop(chatGroup, chatGroupItem)
            }
        }

    }

    private fun resetProgress() {

    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        initChatGroup()
    }

    private fun initChatGroup() {
        for (i in 0 until mTotalNumber) {
            val chatGroupToChatGroupMargin = CommonUtils.dip2px(context, 12f).toFloat()
            val dWidth = measuredWidth
            val dHeight = measuredHeight
            val chatGroupWidth = (dWidth - chatGroupToChatGroupMargin * 2) / 3
            val chatGroupHeight = dHeight.toFloat()
            val chatDrawLeft = chatGroupWidth * i + chatGroupToChatGroupMargin * i
            val chatDrawTop = 0f
            val chatDrawRight = chatDrawLeft + chatGroupWidth
            val chatDrawBottom = chatGroupHeight
            val chatGroupItemList = ArrayList<ChatGroupItem>()
            val chatGroup = ChatGroup(i, 2, chatGroupWidth, chatGroupHeight, chatDrawLeft, chatDrawTop, chatDrawRight, chatDrawBottom, chatGroupItemList)
            initChatGroupItem(chatGroup, chatGroupItemList)
            mChatGroupList.add(chatGroup)
        }
    }

    private fun initChatGroupItem(chatGroup: ChatGroup, chatGroupItemList: ArrayList<ChatGroupItem>) {

        val chatGroupItemToChatGroupItemMargin = CommonUtils.dip2px(context, 12f).toFloat()
        for (i in 0 until chatGroup.count) {
            val isAnimated = (i % 2) == 1
            val chatGroupItemMargin = (chatGroup.chatWidth - chatGroup.count * chatGroupItemWidth - (chatGroup.count - 1) * chatGroupItemToChatGroupItemMargin) / 2
            val chatGroupItemLeft = chatGroup.chatDrawLeft + chatGroupItemMargin + i * chatGroupItemWidth + i * chatGroupItemToChatGroupItemMargin
            // 进度相关图表高度
            val usedChatTotalHeight = chatGroupItemHeight - mChatBottomMargin - mChatTopMargin - mChatMinimumHeight
            // 进度相关当前图表高度
            val currUsedChatHeight = usedChatTotalHeight * mChatRate
            // 顶部高度
            val chatGroupItemTop = chatGroup.chatHeight - mChatBottomMargin - (mChatMinimumHeight + currUsedChatHeight) * (if (isAnimated) mProgressValue.progress else 1f)
            val chatGroupItemRight = chatGroupItemLeft + chatGroupItemWidth
            val chatGroupItemBottom = chatGroup.chatHeight - mChatBottomMargin
            chatGroupItemList.add(ChatGroupItem(chatGroup.index, i, chatGroupItemWidth, chatGroupItemHeight, chatGroupItemLeft, chatGroupItemTop, chatGroupItemRight, chatGroupItemBottom, isAnimated, currUsedChatHeight, 10, 10.toString()))
        }
    }

    private fun updateChatGroupItemTop(chatGroup: ChatGroup, chatGroupItem: ChatGroupItem) {
        chatGroupItem.chatDrawTop = chatGroup.chatHeight - mChatBottomMargin - (mChatMinimumHeight + chatGroupItem.chatUsedHeight) * (if (chatGroupItem.isAnimated) mProgressValue.progress else 1f)
        chatGroupItem.progressNumberStr = if (chatGroupItem.isAnimated)( mProgressValue.progress * chatGroupItem.number).roundToInt().toString() else chatGroupItem.number.toString()
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        if (isPlayAnimator && isStartPlay) {

        }
        val dWidth = measuredWidth
        val dHeight = measuredHeight
        if (dWidth > 0 && dHeight > 0) {
            mChatGroupList.forEach { chatGroup ->
                chatGroup.chatGroupItem.forEach { chatGroupItem ->
                    drawChatRect(canvas, chatGroup, chatGroupItem)
                    drawChatArc(canvas, chatGroup, chatGroupItem)
                    drawChatText(canvas, chatGroup, chatGroupItem)
                }
            }

        }
    }

    private fun drawChatText(canvas: Canvas, chatGroup: ChatGroup, chatGroupItem: ChatGroupItem) {
        val toChatTop = CommonUtils.dip2px(context, 10f).toFloat()
        val top = chatGroupItem.chatDrawTop
        val left = chatGroupItem.chatDrawLeft
        canvas.drawText(chatGroupItem.progressNumberStr, left, top, mTextPaint )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

    }

    private fun drawChatRect(canvas: Canvas, chatGroup: ChatGroup, chatGroupItem: ChatGroupItem) {
        val rectLeft = chatGroupItem.chatDrawLeft
        // 去掉圆弧部分
        val rectTop = chatGroupItem.chatDrawTop + chatGroupItem.chatWidth / 2
        val rectRight = chatGroupItem.chatDrawRight
        val rectBottom = chatGroupItem.chatDrawBottom
        canvas.drawRect(rectLeft, rectTop, rectRight, rectBottom, mChatPaint)
    }

    private fun drawChatArc(canvas: Canvas, chatGroup: ChatGroup, chatGroupItem: ChatGroupItem) {
        val arcLeft = chatGroupItem.chatDrawLeft
        val arcTop = chatGroupItem.chatDrawTop
        val arcRight = chatGroupItem.chatDrawRight
        val arcBottom = chatGroupItem.chatDrawTop + chatGroupItem.chatWidth
        val rectF = RectF(arcLeft, arcTop, arcRight, arcBottom)
        canvas.drawArc(rectF, -180f, 180f, true, mChatPaint)
    }


    data class ProgressValue(var progress: Float)

    data class ChatGroup(val index: Int, val count: Int, var chatWidth: Float, var chatHeight: Float, var chatDrawLeft: Float, var chatDrawTop: Float, var chatDrawRight: Float, var chatDrawBottom: Float, val chatGroupItem: List<ChatGroupItem>)

    data class ChatGroupItem(val groupIndex: Int, val index: Int, var chatWidth: Float, var chatHeight: Float, var chatDrawLeft: Float, var chatDrawTop: Float, var chatDrawRight: Float, var chatDrawBottom: Float, val isAnimated: Boolean, val chatUsedHeight: Float, val number: Int, var progressNumberStr: String)

}