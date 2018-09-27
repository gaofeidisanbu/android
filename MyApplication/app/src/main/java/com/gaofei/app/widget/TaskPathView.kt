package com.gaofei.app.widget

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
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
import com.tencent.smtt.utils.w
import android.R.attr.bitmap


class TaskPathView : FrameLayout {

    private lateinit var mContext: Context
    private var mTaskTreasureBoxToCenterMargin: Int = 0
    private var mTaskTreasureBoxToTreasureBoxMargin: Int = 0
    private var mTaskTreasureBoxRadius: Float = 0f
    private var mTaskFirstTreasureBoxToParentTop: Int = 0
    private var mTaskFirstTreasureBoxToParentBottom: Int = 0
    private val mTaskTreasureBoxCount = 4
    private lateinit var mPaint1: Paint
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
        this.mTaskTreasureBoxToCenterMargin = mContext.dip2px(40f)
        this.mTaskTreasureBoxToTreasureBoxMargin = mContext.dip2px(100f)
        this.mTaskTreasureBoxRadius = mContext.dip2px(56f).toFloat()
        this.mTaskFirstTreasureBoxToParentTop = mContext.dip2px(80f)
        this.mTaskFirstTreasureBoxToParentBottom = mContext.dip2px(80f)
        mPaint1 = Paint()
        mPaint1.color = Color.parseColor("#FF2AA162")
        mPath = Path()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(calculateHeight(mTaskTreasureBoxCount), View.MeasureSpec.EXACTLY))
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawTreasureBoxBackground(canvas)
        drawTreasureBox(canvas, mPath, mTaskTreasureBoxCount)
        canvas.drawPath(mPath, mPaint1)
    }

    /**
     * 疑问
     */
    private fun drawTreasureBoxBackground(canvas: Canvas) {
        val bitmap = BitmapFactory.decodeResource(mContext.resources, R.drawable.task_illus_bg)
        val dWidth = bitmap.width.toFloat()
        val dHeight = bitmap.height.toFloat()
        val sWidth = width.toFloat()
        val sHeight = height.toFloat()
        var scale = 1f
        if (dWidth < sWidth || dHeight < sHeight) {
            scale = if (dWidth < sWidth && dHeight < sHeight) {
                val scaleX = sWidth / dWidth
                val scaleY = sHeight / dHeight
                if (scaleY > scaleX) scaleY else scaleX
            } else if (dWidth < sWidth) {
                sWidth / dWidth
            } else {
                sHeight / dHeight
            }
        }
        val nWidth = scale * dWidth
        val dx = (nWidth - sWidth) / 2
        val matrix = Matrix()
        matrix.postScale(scale, scale)
        matrix.postTranslate(0f, dx)
        val bmp = Bitmap.createBitmap(bitmap, 0, 0, dWidth.toInt(), dHeight.toInt(), matrix, true)
        canvas.drawBitmap(bmp, 0f, 0f, mPaint1)
        LogUtils.d("drawTreasureBoxBackground $dWidth $dHeight ${context.resources.displayMetrics.densityDpi}")

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


    private fun drawTreasureBox(canvas: Canvas, path: Path, count: Int) {
        for (i in 0..count) {
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
