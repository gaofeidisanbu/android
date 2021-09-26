package com.gaofei.app.widget


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

/**
 * @author: drawf
 * @date: 2019/4/3
 * @see: <a href=""></a>
 * @description:
 */
class FooMask @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : MaskViewProcessor(context, attrs, defStyleAttr) {

    override fun getMaskView(): View {
        return object : View(context) {
            init {
                setBackgroundColor(Color.parseColor("#B2000000"))
            }

            override fun draw(canvas: Canvas?) {
                digRoundRect(
                        canvas,
                        RectF(targetOnScreenX.toFloat() + dp2px(20f),
                                targetOnScreenY.toFloat() + dp2px(10f),
                                targetOnScreenX.toFloat() + targetWidth - dp2px(6f),
                                targetOnScreenY.toFloat() + targetHeight - dp2px(10f)),
                        dp2px(4f),
                        dp2px(4f)
                ) {
                    super.draw(canvas)
                }
            }
        }.apply {
            setOnClickListener {
                hide()
            }
        }
    }

}