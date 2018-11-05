package com.gaofei.app.act.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.gaofei.library.utils.CommonUtils
import java.util.zip.CheckedOutputStream

class ClockCircleView : View {

    private lateinit var paint: Paint

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        paint = Paint()
        paint.isAntiAlias = true
        paint.strokeWidth = CommonUtils.dip2px(context, 1f).toFloat()
        paint.style = Paint.Style.STROKE
        setWillNotDraw(false)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawCircle1(canvas)
        drawCircle2(canvas)
        drawCircle3(canvas)
    }

    private fun drawCircle1(canvas: Canvas) {
        paint.color = Color.parseColor("#1AFFFFFF")
        val viewWidth = width.toFloat()
        val radius = viewWidth / 2
        val circleX = viewWidth / 2
        val circleY = viewWidth / 2
        canvas.drawCircle(circleX, circleY, radius, paint)
    }

    private fun drawCircle2(canvas: Canvas) {
        paint.color = Color.parseColor("#26FFFFFF")
        val viewWidth = width.toFloat()
        val radius = CommonUtils.dip2px(context, 70f).toFloat()
        val circleX = viewWidth / 2
        val circleY = viewWidth / 2
        canvas.drawCircle(circleX, circleY, radius, paint)
    }

    private fun drawCircle3(canvas: Canvas) {
        paint.color = Color.parseColor("#33FFFFFF")
        val viewWidth = width.toFloat()
        val radius = CommonUtils.dip2px(context, 50f).toFloat()
        val circleX = viewWidth / 2
        val circleY = viewWidth / 2
        canvas.drawCircle(circleX, circleY, radius, paint)
    }
}