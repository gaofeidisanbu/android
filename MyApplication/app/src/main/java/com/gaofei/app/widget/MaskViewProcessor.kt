package com.gaofei.app.widget

/**
 * Created by gaofei3 on 2020/9/17
 * Describe:
 */

import android.app.Activity
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.Window
import android.widget.FrameLayout
import androidx.viewpager.widget.ViewPager

/**
 * @author: drawf
 * @date: 2019/4/2
 * @see: <a href=""></a>
 * @description: 通用的遮罩View处理器，实际的遮罩View为子类复写的【getMaskView()】
 */
abstract class MaskViewProcessor @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), ViewTreeObserver.OnGlobalLayoutListener {

    private var mRootViewId: Int? = null
    protected var mActivity: Activity? = null
    protected var mTargetTag: String? = null

    private var mDecorView: View? = null
    private var mRootParentView: FrameLayout? = null
    private var mMaskView: View? = null

    protected var targetOnScreenX: Int = 0
    protected var targetOnScreenY: Int = 0
    protected var targetWidth = 0
    protected var targetHeight = 0

    override fun onGlobalLayout() {
        mDecorView?.let { decorView ->
            decorView.viewTreeObserver.removeOnGlobalLayoutListener(this)

            decorView.findViewWithTag<View>(mTargetTag)?.let { target ->
                val outXYArray = IntArray(2)
                target.getLocationOnScreen(outXYArray)

                targetOnScreenX = outXYArray[0]
                targetOnScreenY = outXYArray[1] - getStatusBarHeight()

                targetWidth = target.measuredWidth
                targetHeight = target.measuredHeight

                findParentView(mRootViewId ?: Window.ID_ANDROID_CONTENT) {
                    mRootParentView = it
                    addMaskView()
                }
            }
        }
    }

    /**
     * 在DecorView中找到指定View的方法
     * @param viewId: Int
     * @param block: (FrameLayout) -> Unit) 函数
     */
    private fun findParentView(viewId: Int, block: (FrameLayout) -> Unit) {
        mDecorView?.run {

            findViewById<View?>(viewId)?.let { contentView ->
                if (contentView is FrameLayout) {
                    block(contentView)
                } else {//考虑指定的View不是FrameLayout
                    if (contentView.parent is ViewPager) {
                        throw IllegalStateException("不支持RootView的父布局为ViewPager类型，请手动嵌套一层FrameLayout")
                    }

                    val frameLayout = FrameLayout(context)

                    val parent = contentView.parent as ViewGroup
                    parent.removeView(contentView)//先移除
                    parent.addView(frameLayout, contentView.layoutParams)//添加新建的FrameLayout

                    frameLayout.addView(//把contentView加入
                            contentView,
                            FrameLayout.LayoutParams(
                                    FrameLayout.LayoutParams.MATCH_PARENT,
                                    FrameLayout.LayoutParams.MATCH_PARENT
                            )
                    )

                    block(frameLayout)
                }
            }

        }
    }

    private fun addMaskView() {
        mRootParentView?.run {
            mMaskView = getMaskView()
            addView(mMaskView)
        }

    }

    fun setRootViewId(rootViewId: Int): MaskViewProcessor {
        mRootViewId = rootViewId
        return this
    }

    fun setActivity(activity: Activity): MaskViewProcessor {
        this.mActivity = activity
        return this
    }

    fun setTargetTag(tag: String): MaskViewProcessor {
        this.mTargetTag = tag
        return this
    }


    /**
     * 展示遮罩
     */
    open fun show() {
        mDecorView = mActivity?.window?.decorView?.apply {
            viewTreeObserver.addOnGlobalLayoutListener(this@MaskViewProcessor)
        }
    }

    /**
     * 隐藏遮罩
     */
    open fun hide() {
        mRootParentView?.run {
            removeView(mMaskView)
        }
    }

    /**
     * 子类实现遮罩View
     */
    abstract fun getMaskView(): View

    /**
     * 获取状态栏高度最靠谱的方法
     */
    private fun getStatusBarHeight(): Int {
        return resources.getIdentifier(
                "status_bar_height",
                "dimen",
                "android"
        ).let { resourceId ->
            if (resourceId > 0) {
                resources.getDimensionPixelSize(resourceId)
            } else {
                0
            }
        }
    }

    /**
     * 工具方法
     */
    protected fun dp2px(dpValue: Float): Float {
        val scale = resources.displayMetrics.density
        return (dpValue * scale + 0.5f)
    }

    /**
     * 工具方法
     * 创建画笔
     */
    @JvmOverloads
    protected fun createPaint(colorString: String, color: Int? = null): Paint {
        return Paint().apply {
            this.color = color ?: Color.parseColor(colorString)
            this.isAntiAlias = true
            this.style = Paint.Style.FILL
        }
    }

    /**
     * 工具方法
     * 挖一个矩形洞，可设圆角
     */
    protected fun digRoundRect(canvas: Canvas?, rectF: RectF, rx: Float, ry: Float, block: (() -> Unit)? = null) {
        if (canvas == null) return
        canvas.saveLayer(
                RectF(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat()), null, Canvas.ALL_SAVE_FLAG
        )

        block?.invoke()

        // 绘制带有圆角的 Path
        val path = Path().apply {
            addRoundRect(
                    rectF,
                    rx,
                    ry,
                    Path.Direction.CW
            )
        }

        canvas.drawPath(path, createPaint("#FFFFFF").apply {
            this.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
        })
        canvas.restore()

    }

}