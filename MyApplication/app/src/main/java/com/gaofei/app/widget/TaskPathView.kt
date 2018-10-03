package com.gaofei.app.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.*
import android.widget.FrameLayout
import com.gaofei.app.R
import com.gaofei.library.utils.CommonUtils


class TaskPathView : FrameLayout {
    private val mTaskTreasureBoxCount = 4
    private val mTreasureBoxIconViews = arrayOfNulls<ViewGroup>(mTaskTreasureBoxCount)
    private lateinit var mContext: Context
    private var mTaskTreasureBoxToCenterMargin: Float = 0f
    private var mTaskTreasureBoxToTreasureBoxMargin: Float = 0f
    private var mTaskTreasureBoxRadius: Float = 0f
    private var mTaskFirstTreasureBoxToParentTop: Float = 0f
    private var mTaskPathWidth = 0f
    private var mViewInfo = ViewInfo(0f, 0f, 1f)
    private lateinit var mPaint0: Paint // 宝箱背景
    private lateinit var mPaint1: Paint // 宝箱
    private lateinit var mPaint2: Paint // 路径
    private lateinit var mPaint3: Paint // 虚线
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
        mViewInfo = calculateBackGroundScale()
        initDraw()
        initView()
        layoutTreasureBoxIcon()
        layoutCoinTask()
    }


    /**
     */
    private fun calculateBackGroundScale(): ViewInfo {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(mContext.resources, R.drawable.task_illus_bg, options)
        val dwidth = options.outWidth.toFloat()
        val dheight = options.outHeight.toFloat()
        val vwidth = getViewWidth()
        val dpScale = mContext.resources.displayMetrics.densityDpi.toFloat() / 480f
        val widthScale = vwidth / (dpScale * dwidth)
        return ViewInfo(dpScale * dwidth * widthScale, dpScale * dheight * widthScale, widthScale)
    }

    private fun initDraw() {
        setWillNotDraw(false)
        this.mTaskTreasureBoxToCenterMargin = dip2px(43f).toFloat() * mViewInfo.scale
        this.mTaskTreasureBoxToTreasureBoxMargin = dip2px(128f) * mViewInfo.scale
        this.mTaskTreasureBoxRadius = dip2px(58f) * mViewInfo.scale
        this.mTaskFirstTreasureBoxToParentTop = dip2px(96f) * mViewInfo.scale
        this.mTaskPathWidth = dip2px(16f).toFloat() * mViewInfo.scale
        initPaint0()
        initPaint1()
        initPaint2()
        initPaint3()
    }


    private fun initView() {
        for (i in 0 until mTaskTreasureBoxCount) {
            mTreasureBoxIconViews[i] = obtainTaskTreasureBoxIconView(null)
        }
    }

    private var isReceive = false
    val mCoinTaskView = arrayOfNulls<ViewGroup>(4)
    val mCoinTaskSize = arrayOf<Float>(48f, 48f, 60f, 48f)

    private fun layoutCoinTask() {
        if (!isReceive) {
            for (i in 0 until mCoinTaskView.size) {
                val coinTaskInfo = CoinTaskInfo(i, mCoinTaskSize[i], isReceive)
                obtainCoinTaskView(coinTaskInfo)
            }


        }

    }

    private fun obtainCoinTaskView(coinTaskInfo: CoinTaskInfo): FrameLayout {
        val newTaskCoinView = LayoutInflater.from(mContext).inflate(R.layout.layout_new_task_coin_task, null) as FrameLayout
        val size = CommonUtils.dip2px(mContext, coinTaskInfo.size)
        val newTaskCoinViewLP = FrameLayout.LayoutParams(size, size)
        if (coinTaskInfo.i == 0) {
            newTaskCoinViewLP.topMargin = CommonUtils.dip2px(mContext, 76f)
            newTaskCoinViewLP.rightMargin = CommonUtils.dip2px(mContext, 60f)
            newTaskCoinViewLP.gravity = Gravity.TOP or Gravity.RIGHT
        }

        if (coinTaskInfo.i == 1) {
            newTaskCoinViewLP.topMargin = CommonUtils.dip2px(mContext, 236f)
            newTaskCoinViewLP.leftMargin = CommonUtils.dip2px(mContext, 43f)
            newTaskCoinViewLP.gravity = Gravity.TOP or Gravity.LEFT
        }

        if (coinTaskInfo.i == 2) {
            newTaskCoinViewLP.topMargin = CommonUtils.dip2px(mContext, 290f)
            newTaskCoinViewLP.rightMargin = CommonUtils.dip2px(mContext, 46f)
            newTaskCoinViewLP.gravity = Gravity.TOP or Gravity.RIGHT
        }

        if (coinTaskInfo.i == 3) {
            newTaskCoinViewLP.topMargin = CommonUtils.dip2px(mContext, 405f)
            newTaskCoinViewLP.leftMargin = CommonUtils.dip2px(mContext, 133f)
            newTaskCoinViewLP.gravity = Gravity.TOP or Gravity.LEFT
        }
        addView(newTaskCoinView, newTaskCoinViewLP)
        return newTaskCoinView
    }


    class CoinTaskInfo( val i: Int, val size: Float, var isReceive: Boolean)

    private fun obtainTaskTreasureBoxIconView(map: Map<String, Any>?): ViewGroup {
        val taskTreasureBoxIconView = LayoutInflater.from(mContext).inflate(R.layout.task_treasure_box_icon, null) as ViewGroup
        return taskTreasureBoxIconView
    }


    private fun layoutTreasureBoxIcon() {
        for (i in 0 until mTaskTreasureBoxCount) {
            val treasureBoxLocationInfo = calculateTreasureBoxCircleLocationInfo(i)
            mTreasureBoxIconViews[i]?.let {
                val width = mContext.resources.getDimension(R.dimen.task_treasure_box_icon_width)
                val height = mContext.resources.getDimension(R.dimen.task_treasure_box_icon_height)
                val lp = FrameLayout.LayoutParams(width.toInt(), height.toInt())
                lp.leftMargin = (treasureBoxLocationInfo.left.toInt() + (width * mViewInfo.scale - width) / 2).toInt()
                lp.topMargin = (treasureBoxLocationInfo.top.toInt() + (width * mViewInfo.scale - width) / 2).toInt()

                this.addView(it, lp)
                it.pivotX = width / 2
                it.pivotY = width / 2
                treasureBoxLocationInfo.pivotX = it.pivotX
                treasureBoxLocationInfo.pivotY = it.pivotY
                treasureBoxLocationInfo.scale = mViewInfo.scale
                it.setTag(R.id.task_treasure_box_tag_id, treasureBoxLocationInfo)
                it.scaleX = mViewInfo.scale
                it.scaleY = mViewInfo.scale
                val lp1 = FrameLayout.LayoutParams(50, 50)
                lp1.leftMargin = (treasureBoxLocationInfo.left.toInt() + mTaskTreasureBoxRadius).toInt()
                lp1.topMargin = (treasureBoxLocationInfo.top.toInt() + mTaskTreasureBoxRadius).toInt()
                val view = View(mContext)
                view.setBackgroundColor(Color.RED)
                this.addView(view, lp1)
            }

        }
    }


    private fun initPaint0() {
        mPaint0 = Paint()
        mPaint0.style = Paint.Style.FILL
        mPaint0.color = Color.parseColor("#FF2AA162")
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
        mPaint3.strokeWidth = dip2px(5f).toFloat()
        mPaint3.pathEffect = DashPathEffect(floatArrayOf(4f, 4f), 0f)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(calculateHeight(mTaskTreasureBoxCount), View.MeasureSpec.EXACTLY))
    }

    private fun calculateHeight(count: Int): Int {
        return mViewInfo.height.toInt()
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
            it.duration = 10000
            it.start()
        }
    }


    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        canvas?.let {
            drawTask(it, mAnimatedValue, mTaskTreasureBoxCount)
        }

    }


    override fun onDraw(canvas: Canvas) {
        drawTaskBackground(canvas)
        for (i in 0 until mTaskTreasureBoxCount - 1) {
            drawTaskPath(canvas, i, mAnimatedValue)
        }
        super.onDraw(canvas)

    }


    /**
     * 该方法在onDraw调用
     */
    private fun drawTaskBackground(canvas: Canvas) {
        val options = BitmapFactory.Options()
        options.inScaled = true
        val bitmap = BitmapFactory.decodeResource(mContext.resources, R.drawable.task_illus_bg, options)
        val matrix = Matrix()
        matrix.postScale(mViewInfo.scale, mViewInfo.scale)
        canvas.drawBitmap(bitmap, matrix, mPaint1)
    }


    private fun drawTask(canvas: Canvas, value: Float, count: Int) {


        for (i in 0 until count) {
            drawTaskTreasureBox(canvas, i, value)
        }
    }


    private fun drawTaskTreasureBox(canvas: Canvas, i: Int, value: Float) {
        val isLeft = (i + 1) % 2 != 0
        val circlePointF = calculateTreasureBoxCircleLocationInfo(i).pointF
//        canvas.drawCircle(circlePointF.x, circlePointF.y, mTaskTreasureBoxRadius, mPaint0)
//        drawTaskTreasureBoxIcon(canvas, i, value, circlePointF)
//        drawTaskTreasureBoxDes(canvas, i, value, circlePointF)
        if (value < values[i]) {
            mTreasureBoxIconViews[i]?.let {
                it.visibility = View.INVISIBLE
            }
            return
        }
        var v = value
        v += 0.6f
        if (v >= values[i + 1]) {
            v = values[i + 1]
        }
        val t = (v - values[i]) / (values[i + 1] - values[i])
        mTreasureBoxIconViews[i]?.let {
            it.visibility = View.VISIBLE
            val treasureBoxLocationInfo = it.getTag(R.id.task_treasure_box_tag_id) as TreasureBoxLocationInfo
            it.pivotX = treasureBoxLocationInfo.pivotX
            it.pivotY = treasureBoxLocationInfo.pivotY
            val scale = treasureBoxLocationInfo.scale * t
            it.scaleX = scale
            it.scaleY = scale
        }


    }

    private fun drawTaskTreasureBoxDes(canvas: Canvas, i: Int, value: Float, circlePointF: PointF) {
        val roundRectRectFLeft = circlePointF.x - mTaskTreasureBoxRadius
        val roundRectHeight = CommonUtils.dip2px(mContext, 40f)
        val roundRectRectFTop = circlePointF.y + CommonUtils.dip2px(mContext, 30f)
        val roundRectRectFRight = roundRectRectFLeft + 2 * mTaskTreasureBoxRadius
        val roundRectRectFBottom = roundRectRectFTop + roundRectHeight
        val roundRectRectF = RectF(roundRectRectFLeft, roundRectRectFTop, roundRectRectFRight, roundRectRectFBottom)
        val roundRectRX = roundRectHeight
        val roundRectRY = roundRectRX
        val path = Path()
        path.addRoundRect(roundRectRectF, roundRectRX.toFloat(), roundRectRY.toFloat(), Path.Direction.CCW)
    }

    private fun drawTaskTreasureBoxIcon(canvas: Canvas, i: Int, value: Float, circlePointF: PointF) {
        val options = BitmapFactory.Options()
        options.inScaled = false
        val bitmap = BitmapFactory.decodeResource(mContext.resources, R.drawable.task_box_lock_color, options)
        val matrix = Matrix()
        matrix.postScale(value, value)
        canvas.drawBitmap(bitmap, matrix, mPaint1)
    }

    val values = arrayOf(0f, 1f, 2f, 3f, 4f, 5f)


    private fun drawTaskPath(canvas: Canvas, i: Int, value: Float) {
        if (value < values[i]) {
            return
        }
        val path1 = Path()
        val path2 = Path()
        val circle1PointF = calculateTreasureBoxCircleLocationInfo(i).pointF
        path1.moveTo(circle1PointF.x, circle1PointF.y)
        path2.moveTo(circle1PointF.x, circle1PointF.y)
        val circle2PointF = calculateTreasureBoxCircleLocationInfo(i + 1).pointF
        val pathInfo = PathInfo(i, circle1PointF, circle2PointF)
        val circlePointF = pathInfo.getMovePointF(value)
        path1.lineTo(circlePointF.x, circlePointF.y)
        path2.lineTo(circlePointF.x, circlePointF.y)
        canvas.drawPath(path1, mPaint2)
        canvas.drawPath(path2, mPaint3)
    }

    inner class PathInfo(val i: Int, val startPointF: PointF, val endPointF: PointF) {
        init {

        }

        fun getMovePointF(value: Float): PointF {
            // y = ax+b
            var v = value

            if (v >= values[i + 1]) {
                v = values[i + 1]
            }
            val t = (v - values[i]) / (values[i + 1] - values[i])
            val a = (endPointF.y - startPointF.y) / (endPointF.x - startPointF.x)
            val b = startPointF.y - a * startPointF.x
            val x = startPointF.x + (endPointF.x - startPointF.x) * t
            val y = a * x + b
            return PointF(x, y)
        }
    }

    private fun getEndPointFY(value: Float): Float {
        val pointFY = mTaskFirstTreasureBoxToParentTop + mTaskTreasureBoxRadius + (mTaskTreasureBoxToTreasureBoxMargin + mTaskTreasureBoxRadius)
        return pointFY
    }


    private fun calculateTreasureBoxCircleLocationInfo(index: Int): TreasureBoxLocationInfo {
        val isLeft = (index + 1) % 2 != 0
        val treasureBoxStartPointFXOffset = mTaskTreasureBoxToCenterMargin + mTaskTreasureBoxRadius
        val treasureBoxStartPointFX = getViewWidth() / 2 + if (isLeft) treasureBoxStartPointFXOffset else -treasureBoxStartPointFXOffset
        val treasureStartBoxPointFY = mTaskFirstTreasureBoxToParentTop + index * 2 * mTaskTreasureBoxRadius + index * mTaskTreasureBoxToTreasureBoxMargin + mTaskTreasureBoxRadius
        return TreasureBoxLocationInfo(PointF(treasureBoxStartPointFX, treasureStartBoxPointFY), treasureBoxStartPointFX - mTaskTreasureBoxRadius, treasureStartBoxPointFY - mTaskTreasureBoxRadius, 0f, 0f, 1f)
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }


    private fun getViewWidth(): Int {
        val wm = mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.widthPixels

//        return 720
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

    fun dip2px(dipValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    data class ViewInfo(val width: Float, val height: Float,
                        val scale: Float)

    data class TreasureBoxLocationInfo(val pointF: PointF, val left: Float,
                                       val top: Float, var pivotX: Float, var pivotY: Float, var scale: Float)

}
