package com.gaofei.app.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import com.gaofei.app.R
import com.gaofei.library.utils.CommonUtils
import com.gaofei.library.utils.LogUtils
import kotlinx.coroutines.experimental.channels.NULL_VALUE


class TaskPathView : FrameLayout {

    private lateinit var mContext: Context
    private var mTaskTreasureBoxToCenterMargin: Int = 0
    private var mTaskTreasureBoxToTreasureBoxMargin: Int = 0
    private var mTaskTreasureBoxRadius: Float = 0f
    private var mTaskFirstTreasureBoxToParentTop: Int = 0
    private var mTaskFirstTreasureBoxToParentBottom: Int = 0
    private val mTaskTreasureBoxCount = 4
    private var mTaskPathWidth = 0f
    private lateinit var mPaint1: Paint // 宝箱
    private lateinit var mPaint2: Paint // 路径
    private lateinit var mPaint3: Paint // 虚线
    private lateinit var mPath: Path
    private var mAnimatedValue: Float = 0f
    private var mmValueAnimator: ValueAnimator? = null

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
        this.mContext = context
        setWillNotDraw(false)
        this.mTaskTreasureBoxToCenterMargin = mContext.dip2px(43f)
        this.mTaskTreasureBoxToTreasureBoxMargin = mContext.dip2px(120f)
        this.mTaskTreasureBoxRadius = mContext.dip2px(58f).toFloat()
        this.mTaskFirstTreasureBoxToParentTop = mContext.dip2px(96f)
        this.mTaskFirstTreasureBoxToParentBottom = mContext.dip2px(24f)
        this.mTaskPathWidth = mContext.dip2px(16f).toFloat()
        initPaint1()
        initPaint2()
        initPaint3()
        mPath = Path()
    }


    private fun initPaint1() {
        mPaint1 = Paint()
        mPaint1.color = Color.parseColor("#FF2AA162")
    }

    private fun initPaint2() {
        mPaint2 = Paint()
        mPaint2.style = Paint.Style.STROKE
        mPaint2.color = Color.parseColor("#FF2AA162")
        mPaint2.strokeWidth = mTaskPathWidth
    }

    private fun initPaint3() {
        mPaint3 = Paint()
        mPaint3.style = Paint.Style.STROKE
        mPaint3.color = Color.parseColor("#FFFFFFFF")
        mPaint3.strokeWidth = mContext.dip2px(5f).toFloat()
        mPaint3.pathEffect = DashPathEffect(floatArrayOf(4f, 4f), 0f)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(calculateHeight(mTaskTreasureBoxCount), View.MeasureSpec.EXACTLY))
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        startAnimator()
    }

    private fun startAnimator() {
        if (mmValueAnimator != null) {
            mmValueAnimator?.let {
                if (it.isRunning) {
                    mmValueAnimator?.cancel()
                }
            }
        }
        mmValueAnimator = ValueAnimator.ofFloat(0f, 1f, 2f, 3f, 4f)
        mmValueAnimator?.let {
            it.addUpdateListener {
                mAnimatedValue = it.animatedValue as Float
                invalidate()
            }
            it.duration = 2000
            it.start()
        }
    }

    private fun calculateHeight(count: Int): Int {
        val totalCircleHeight = count * 2 * mTaskTreasureBoxRadius
        var totalTaskTreasureBoxToTreasureBoxMargin = if (count > 0) {
            (count - 1) * 2 * mTaskTreasureBoxToTreasureBoxMargin
        } else {
            0
        }
        return (mTaskFirstTreasureBoxToParentTop + totalCircleHeight + totalTaskTreasureBoxToTreasureBoxMargin + mTaskFirstTreasureBoxToParentBottom).toInt()
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        calculateBackGroundScale()
        drawTaskBackground(canvas)
        drawTask(canvas, mAnimatedValue, mPath, mTaskTreasureBoxCount)
    }


    /**
     * 该方法在onDraw调用
     */
    private fun drawTaskBackground(canvas: Canvas) {
        val options = BitmapFactory.Options()
        options.inScaled = false
        val bitmap = BitmapFactory.decodeResource(mContext.resources, R.drawable.task_illus_bg, options)
        val dwidth = bitmap.width
        val dheight = bitmap.height
        val vwidth = width
        var scale = calculateBackGroundScale()
        var dx: Float = (vwidth - dwidth * scale) * 0.5f
        val matrix = Matrix()
        matrix.postScale(scale, scale)
        matrix.postTranslate(Math.round(dx).toFloat(), 0f)
        canvas.drawBitmap(bitmap, matrix, mPaint1)
    }

    /**
     * 该方法在onDraw调用
     */
    private fun calculateBackGroundScale(): Float {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        val bitmap = BitmapFactory.decodeResource(mContext.resources, R.drawable.task_illus_bg, options)
        val dwidth = options.outWidth
        val dheight = options.outHeight
        val vwidth = width
        val vheight = height
        return if (dwidth * vheight > vwidth * dheight) {
            vheight / dheight.toFloat()
        } else {
            vwidth / dwidth.toFloat()
        }
    }


    private fun drawTask(canvas: Canvas, value: Float, path: Path, count: Int) {
        for (i in 0 until count) {
            drawTaskPath(canvas, i, path, value)
        }

        for (i in 0..count) {
            drawTaskTreasureBox(canvas, i, path, value)
        }
        canvas.drawPath(mPath, mPaint1)
    }

    private fun drawTaskTreasureBox(canvas: Canvas, i: Int, path: Path, value: Float) {
        val isLeft = (i + 1) % 2 != 0
        val circlePointF = getTreasureBoxCirclePointF(isLeft, i)
        path.addCircle(circlePointF.x, circlePointF.y, mTaskTreasureBoxRadius, Path.Direction.CCW)
        val roundRectRectFLeft = circlePointF.x - mTaskTreasureBoxRadius
        val roundRectHeight = CommonUtils.dip2px(mContext, 40f)
        val roundRectRectFTop = circlePointF.y + CommonUtils.dip2px(mContext, 30f)
        val roundRectRectFRight = roundRectRectFLeft + 2 * mTaskTreasureBoxRadius
        val roundRectRectFBottom = roundRectRectFTop + roundRectHeight
        val roundRectRectF = RectF(roundRectRectFLeft, roundRectRectFTop, roundRectRectFRight, roundRectRectFBottom)
        val roundRectRX = roundRectHeight
        val roundRectRY = roundRectRX
        path.addRoundRect(roundRectRectF, roundRectRX.toFloat(), roundRectRY.toFloat(), Path.Direction.CCW)

    }


    private fun drawTaskPath(canvas: Canvas, i: Int, path: Path, value: Float) {
        val path1 = Path()
        val path2 = Path()
        val isLeft = (i + 1) % 2 != 0
        val circle1PointF = getTreasureBoxCirclePointF(isLeft, i)
        path1.moveTo(circle1PointF.x, circle1PointF.y)
        path2.moveTo(circle1PointF.x, circle1PointF.y)
        val circle2PointF = getTreasureBoxCirclePointF(!isLeft, i + 1)
        path1.lineTo(circle2PointF.x, circle2PointF.y)
        path2.lineTo(circle2PointF.x, circle2PointF.y)
        canvas.drawPath(path1, mPaint2)
        canvas.drawPath(path2, mPaint3)
    }

    private fun getEndPointFY(value: Float): Float {
        val pointFY = mTaskFirstTreasureBoxToParentTop + mTaskTreasureBoxRadius + (mTaskTreasureBoxToTreasureBoxMargin + mTaskTreasureBoxRadius)
        return pointFY
    }


    private fun getTreasureBoxCirclePointF(isLeft: Boolean, index: Int): PointF {
        val treasureBoxStartPointFXOffset = mTaskTreasureBoxToCenterMargin + mTaskTreasureBoxRadius
        val treasureBoxStartPointFX = mContext.getScreenWidth() / 2 + if (isLeft) treasureBoxStartPointFXOffset else -treasureBoxStartPointFXOffset
        val treasureStartBoxPointFY = mTaskFirstTreasureBoxToParentTop + index * 2 * mTaskTreasureBoxRadius + index * mTaskTreasureBoxToTreasureBoxMargin + mTaskTreasureBoxRadius
        return PointF(treasureBoxStartPointFX, treasureStartBoxPointFY)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }


    private fun Context.getScreenWidth(): Int {
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.widthPixels
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    private fun Context.getScreenHeight(): Int {
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.heightPixels
    }

    fun Context.dip2px(dipValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }


}
