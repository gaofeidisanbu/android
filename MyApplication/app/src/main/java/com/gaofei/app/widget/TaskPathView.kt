package com.gaofei.app.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import com.gaofei.app.R
import com.gaofei.library.utils.CommonUtils
import com.gaofei.library.utils.LogUtils


class TaskPathView : FrameLayout {

    private lateinit var mContext: Context
    private var mTaskTreasureBoxToCenterMargin: Int = 0
    private var mTaskTreasureBoxToTreasureBoxMargin: Int = 0
    private var mTaskTreasureBoxRadius: Float = 0f
    private var mTaskFirstTreasureBoxToParentTop: Int = 0
    private var mTaskFirstTreasureBoxToParentBottom: Int = 0
    private val mTaskTreasureBoxCount = 4
    private var mTaskPathWidth = 0f
    private lateinit var mPaint1: Paint
    private lateinit var mPaint2: Paint
    private lateinit var mPath: Path

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
        mPaint1 = Paint()
        mPaint1.color = Color.parseColor("#FF2AA162")
        mPaint2 = Paint()
        mPaint2.color = Color.parseColor("#FF2AA162")
        mPaint2.strokeWidth = mContext.dip2px(30f).toFloat()
        mPath = Path()
        calculateBackGroundScale()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(calculateHeight(mTaskTreasureBoxCount), View.MeasureSpec.EXACTLY))
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
        drawTaskBackground(canvas)
        val valueAnimator = ValueAnimator.ofFloat(0f, 1f)
        valueAnimator.addUpdateListener {
            val animatedValue = it.animatedValue as Float

            drawTask(canvas, animatedValue, mPath, mTaskTreasureBoxCount)

        }

        valueAnimator.start()

    }


    private fun getEndPointFY(value: Float): Float {
        val pointFY = mTaskFirstTreasureBoxToParentTop + mTaskTreasureBoxRadius + (mTaskTreasureBoxToTreasureBoxMargin + mTaskTreasureBoxRadius)
        return pointFY
    }





    /**
     * 疑问
     */
    private fun drawTaskBackground(canvas: Canvas) {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        val bitmap = BitmapFactory.decodeResource(mContext.resources, R.drawable.task_illus_bg, options)
        val dwidth = options.outWidth.toFloat()
        val dheight = options.outHeight.toFloat()
        val vwidth = width.toFloat()
        val sheight = height.toFloat()
        var scale = 1f
        if (dwidth < vwidth || dheight < sheight) {
            scale = if (dwidth < vwidth && dheight < sheight) {
                val scaleX = vwidth / dwidth
                val scaleY = sheight / dheight
                if (scaleY > scaleX) scaleY else scaleX
            } else if (dwidth < vwidth) {
                vwidth / dwidth
            } else {
                sheight / dheight
            }
        }
        val nWidth = scale * dwidth
        val nHeight = scale * dheight
        val dx = (nWidth - vwidth) / 2
        val matrix = Matrix()
        matrix.postScale(scale, scale)
        matrix.postTranslate(0f, dx)
        val bmp = Bitmap.createBitmap(bitmap, 0, 0, dwidth.toInt(), dheight.toInt(), matrix, true)
        canvas.drawBitmap(bmp, 0f, 0f, mPaint1)
        LogUtils.d("drawTaskBackground $dwidth $dheight ${options.outWidth} ${context.resources.displayMetrics.densityDpi}")

    }


    private fun calculateBackGroundScale(): Int {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        val bitmap = BitmapFactory.decodeResource(mContext.resources, R.drawable.task_illus_bg, options)
        val scale = options.outWidth
        return 1
    }


    private fun drawTask(canvas: Canvas, value: Float, path: Path, count: Int) {
        for (i in 0 until count) {
            drawTaskPath(canvas, i, path)
        }

        for (i in 0..count) {
            drawTaskTreasureBox(canvas, i, path)
        }
//        canvas.drawPath(mPath, mPaint1)
    }

    private fun drawTaskTreasureBox(canvas: Canvas, i: Int, path: Path) {
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


    private fun drawTaskPath(canvas: Canvas, i: Int, path: Path) {
        val path1 = Path()
        val isLeft = (i + 1) % 2 != 0
        val circle1PointF = getTreasureBoxCirclePointF(isLeft, i)
        path1.moveTo(circle1PointF.x, circle1PointF.y)
        mPaint2.strokeWidth = mTaskPathWidth
        mPaint2.style = Paint.Style.STROKE
        val circle2PointF = getTreasureBoxCirclePointF(!isLeft, i + 1)
//        path1.lineTo(circle2PointF.x, circle2PointF.y)
        val controlX = circle1PointF.x - 70
        val controlY = circle1PointF.y + 90
        path1.quadTo(controlX, controlY, circle2PointF.x, circle2PointF.y)

        canvas.drawCircle(controlX, controlY, 20f, mPaint2)
        canvas.drawPath(path1, mPaint2)
    }

    private fun getTreasureBoxCirclePointF(isLeft: Boolean, index: Int): PointF {
        val treasureBoxStartPointFXOffset = mTaskTreasureBoxToCenterMargin + mTaskTreasureBoxRadius
        val treasureBoxStartPointFX = mContext.getScreenWidth() / 2 + if (isLeft) treasureBoxStartPointFXOffset else -treasureBoxStartPointFXOffset
        val treasureStartBoxPointFY = mTaskFirstTreasureBoxToParentTop + index * 2 * mTaskTreasureBoxRadius + index * mTaskTreasureBoxToTreasureBoxMargin + mTaskTreasureBoxRadius
        return PointF(treasureBoxStartPointFX, treasureStartBoxPointFY)
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
