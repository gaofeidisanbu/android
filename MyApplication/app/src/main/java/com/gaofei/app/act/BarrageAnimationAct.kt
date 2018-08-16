package com.gaofei.app.act

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.view.animation.AccelerateInterpolator

import com.gaofei.app.R
import com.gaofei.library.base.BaseAct
import kotlinx.android.synthetic.main.act_barrage_animation.*


class BarrageAnimationAct : BaseAct() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_barrage_animation)
        setBarrageVisible(false)
        img.setOnClickListener {
            setBarrageVisible(true)
            setBarrageBackground()
            startBarrageAnimation()
        }
        close_icon.setOnClickListener {
            setBarrageVisible(false)
            stopBarrageAnimation()
        }
        close_text.setOnClickListener {
            setBarrageVisible(false)
            stopBarrageAnimation()
        }
        setBarrageBackground()
    }

    private fun startBarrageAnimation() {
        startBarrageAnimationClose()
    }

    private fun startBarrageAnimationClose() {
        val objectAnimator = ObjectAnimator.ofFloat(close_icon, "rotation", 0f, -90f)
        objectAnimator.duration = 200
        objectAnimator.interpolator = AccelerateInterpolator()
        objectAnimator.start()
    }

    private fun stopBarrageAnimation() {
        startBarrageAnimationClose()
    }

    private fun setBarrageVisible(isShowBarrage: Boolean) {
        barrageRoot.visibility = if (isShowBarrage) View.VISIBLE else View.GONE
    }

    private fun setBarrageBackground() {
        val bitmap = getViewBitmap(img)
        bitmap?.let {
            val bitmapDrawable = BitmapDrawable(resources, blur(it))
            barrageRoot.background = bitmapDrawable
        }
    }


    private fun getViewBitmap(view: View): Bitmap? {
        // 设置是否可以进行绘图缓存
        view.isDrawingCacheEnabled = true
        // 如果绘图缓存无法，强制构建绘图缓存
        view.buildDrawingCache()
        // 返回这个缓存视图
        return view.drawingCache
    }


    private fun blur(bitmap: Bitmap): Bitmap {
        val resultBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val rs = RenderScript.create(this)
        val blur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
        val inAllocation = Allocation.createFromBitmap(rs, bitmap)
        val outAllocation = Allocation.createFromBitmap(rs, resultBitmap)
        blur.setRadius(25f)
        blur.setInput(inAllocation)
        blur.forEach(outAllocation)
        outAllocation.copyTo(resultBitmap)
        rs.destroy()
        return resultBitmap
    }

    private fun getScreenWidth(): Int {
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
    private fun getScreenHeight(): Int {
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.heightPixels
    }

}
