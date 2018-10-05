package com.gaofei.app.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.gaofei.app.CanvasActivity
import com.gaofei.app.R

class TaskFrameLayout : FrameLayout {
    private lateinit var mContext: CanvasActivity

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
        this.mContext = context as CanvasActivity
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val scale = mContext.calculateBackGroundScale()
        val scaleWidth = scale * resources.getDimension(R.dimen.new_task_root_width)
        val scaleHeight = scale * resources.getDimension(R.dimen.new_task_root_height)
        setMeasuredDimension(MeasureSpec.makeMeasureSpec(scaleWidth.toInt(), MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(scaleHeight.toInt(), MeasureSpec.EXACTLY))
    }

}
