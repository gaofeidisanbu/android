package com.gaofei.app.act

import android.animation.*
import android.content.Context
import android.graphics.Bitmap
import android.graphics.PointF
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Handler
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.widget.FrameLayout
import android.widget.ImageView

import com.gaofei.app.R
import com.gaofei.library.base.BaseAct
import com.gaofei.library.utils.CommonUtils
import com.gaofei.library.utils.LogUtils
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.act_barrage_root_view.*
import kotlinx.android.synthetic.main.peer_pressure_user_item.view.*
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.collections.ArrayList


class BarrageAnimationAct : BaseAct() {
    private val mFavorStarImages = intArrayOf(R.drawable.icon_stars_blue, R.drawable.icon_stars_cyan, R.drawable.icon_stars_green, R.drawable.icon_stars_orange, R.drawable.icon_stars_yellow)
    private val mBarrageData = CopyOnWriteArrayList<BarrageData>()
    private val mCacheBarrageViews = ArrayList<View>(3)
    private val mCurrBarrageViews = ArrayList<View>()
    private val mHandler = Handler()
    private var mBarrageViewHeight: Int = 0
    private var mBarrageViewToParentLeftMarginAnimationStart: Int = 0
    private var mBarrageViewToParentLeftMarginAnimationEnd: Int = 0
    private var mBarrageViewToTopMargin: Int = 0
    private val mBarrageViewMaxNum = 4
    private var isShowBarrageRootView = false
    private var index = 0
    private val mBarrageViewInitialScale = 0.2f
    private var mBarrageViewStartAnimator: ValueAnimator? = null
    private var mCloseAnimatorSet: AnimatorSet? = null
    private var mBarrageViewDisappearAlphaAnimation: ObjectAnimator? = null
    private var mDoFavorAnimatorSet: AnimatorSet? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_barrage_root_view)
        init()
        setBarrageRootViewVisible(false)
        img.setOnClickListener {
            setBarrageRootViewVisible(true)
            setBarrageRootViewBackground()
            processBarrageViewLoader()
            startBarrageCloseAnimation()
        }
        close_icon.setOnClickListener {
            setBarrageRootViewVisible(false)
            stopBarrageCloseAnimation()
            clearAllBarrageViewAndAnimation()
            clearAllStarViewAndAnimation()
        }
        close_text.setOnClickListener {
            setBarrageRootViewVisible(false)
            stopBarrageCloseAnimation()
            clearAllBarrageViewAndAnimation()
            clearAllStarViewAndAnimation()
        }
        initBarrageData()
    }


    private fun init() {
        mBarrageViewHeight = CommonUtils.dip2px(this, 64f)
        mBarrageViewToParentLeftMarginAnimationStart = CommonUtils.dip2px(this, 44f)
        mBarrageViewToParentLeftMarginAnimationEnd = CommonUtils.dip2px(this, 24f)
        mBarrageViewToTopMargin = CommonUtils.dip2px(this, 12f)
    }

    private fun initBarrageData() {
        Observable.just(++index, ++index, ++index)
//        Observable.just(++index, ++index, ++index, ++index, ++index, ++index, ++index)
                .map {
                    return@map BarrageData(mapOf(Pair("name", "魔法少女小圆-$it"), Pair("topicName", "QB是个骗子-$it")), it)
                }
                .toList()
                .subscribe(Consumer {
                    mBarrageData.addAll(it)
                    notifyBarrageDataUpdate()
                })

    }

    private fun setBarrageRootViewVisible(isShowBarrage: Boolean) {
        this.isShowBarrageRootView = isShowBarrage
        barrageRoot.visibility = if (isShowBarrage) View.VISIBLE else View.GONE
    }

    private fun setBarrageRootViewBackground() {
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


    private fun notifyBarrageDataUpdate() {
        if (isShowBarrageRootView) {
            processBarrageViewLoader()
        }
    }


    private fun startBarrageCloseAnimation() {
        startBarrageAnimationClose()
        processBarrageViewLoader()
    }

    private fun startBarrageAnimationClose() {
        if (mCloseAnimatorSet != null) {
            mCloseAnimatorSet?.end()
        }
        mCloseAnimatorSet = AnimatorSet()
        val mCloseViewAnimatorRotation = ObjectAnimator.ofFloat(close_icon, "rotation", 0f, -90f)
        val closeStartScaleValue = 0.8f
        close_icon.scaleX = closeStartScaleValue
        close_icon.scaleY = closeStartScaleValue
        val mCloseViewAnimatorZoom = ObjectAnimator.ofFloat(closeStartScaleValue, 1f)
        mCloseViewAnimatorZoom?.let {
            it.addUpdateListener {
                val animatedValue = it.animatedValue as Float
                close_icon.scaleX = animatedValue
                close_icon.scaleY = animatedValue

            }
        }
        mCloseAnimatorSet?.let {
            it.duration = 200
            it.interpolator = AccelerateInterpolator()
            it.playTogether(mCloseViewAnimatorRotation, mCloseViewAnimatorZoom)
            it.start()
        }

    }


    private fun processBarrageViewLoader() {
        if (mBarrageData.size > 0) {
            val barrageData = mBarrageData.removeAt(0)
            attachBarrageView(barrageData)
        } else {
            if (!mCurrBarrageViews.isEmpty()) {
                var firstDuration = 600L
                mCurrBarrageViews.reversed().forEachIndexed { index, view ->
                    mHandler.postDelayed({
                        firstDuration += 500L
                        barrageViewAnimationDisappear(view, firstDuration)
                    }, 200)
                }
            }
        }
    }

    private fun attachBarrageView(barrageData: BarrageData) {
        val barrageView = getBarrageView()
        barrageView.setOnClickListener { v ->
            doFavorBarrage(v, barrageData)
        }
        barrageViewParent?.let {
            val barrageLP = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, mBarrageViewHeight)
            barrageLP.gravity = Gravity.BOTTOM or Gravity.LEFT
            barrageLP.marginStart = mBarrageViewToParentLeftMarginAnimationStart
            it.addView(barrageView, barrageLP)
            addBarrageViewToList(barrageView)
            executeBarrageViewAnimation(barrageView)
        }
        setBarrageViewData(barrageView, barrageData.data)

    }

    private fun executeBarrageViewAnimation(view: View) {
        view.scaleX = mBarrageViewInitialScale
        view.scaleY = mBarrageViewInitialScale
        view.alpha = 0f
        mBarrageViewStartAnimator = ValueAnimator.ofFloat(0f, 1f)
        mBarrageViewStartAnimator?.let {
            it.duration = 600
            it.removeAllListeners()
            it.addUpdateListener {
                val animatedValue = it.animatedValue as Float
                val currScale = getBarrageViewScale(animatedValue)
                val translationX = getBarrageViewTranslateX(animatedValue)
                val alpha = getBarrageViewAlpha(animatedValue)
                view.pivotX = view.width.toFloat()
                view.pivotY = (mBarrageViewHeight / 2).toFloat()
                view.scaleX = currScale
                view.scaleY = currScale
                view.translationX = -translationX
                view.alpha = alpha
                triggerOtherBarrageViewAnimation(animatedValue)
            }
            it.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    recycleBarrageView()
                    LogUtils.d("aaaaaa onAnimationEnd ${it.animatedValue}")
//                    mHandler.postDelayed({
                    processBarrageViewLoader()
//                    },1000)


                }

            })
            it.start()
        }
    }

    private fun triggerOtherBarrageViewAnimation(animatedValue: Float) {
        val size = mCurrBarrageViews.size
        var currIndex = size - 1
        while (currIndex >= 0) {
            var barrageView = mCurrBarrageViews[currIndex]
            // 最后一个item消失
            if (currIndex == mBarrageViewMaxNum - 1) {
                barrageView.alpha = getLastBarrageViewAlpha(animatedValue)
            }
            //除第一个item之外，做y轴位移动画
            if (currIndex != 0) {
                barrageView.translationY = getOtherBarrageViewTranslationY(currIndex, animatedValue)
            }
            currIndex--
        }

    }

    private fun barrageViewAnimationDisappear(view: View, duration: Long) {
        mBarrageViewDisappearAlphaAnimation = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f)
        mBarrageViewDisappearAlphaAnimation?.let {
            it.duration = duration
            it.interpolator = AccelerateDecelerateInterpolator()
            it.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    barrageViewParent?.let {
                        it.removeView(view)
                        mCurrBarrageViews?.let {
                            mCurrBarrageViews.remove(view)
                        }

                    }
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }

            })
            it.start()
        }

    }


    private fun cacheBarrageView(barrageView: View) {
        if (mCacheBarrageViews.size < mBarrageViewMaxNum) {
            mCacheBarrageViews.add(barrageView)
        }
    }

    private fun getOtherBarrageViewTranslationY(index: Int, value: Float): Float {
        return -((index - 1) * (mBarrageViewHeight + mBarrageViewToTopMargin) + value * (mBarrageViewHeight + mBarrageViewToTopMargin))
    }

    private fun getLastBarrageViewAlpha(animatedValue: Float): Float {

        return 1 - animatedValue
    }


    private fun getBarrageViewAlpha(animatedValue: Float): Float {
        return animatedValue
    }

    private fun getBarrageViewScale(animatedValue: Float): Float {
        return mBarrageViewInitialScale + animatedValue / (1f - 0f) * 0.8f
    }

    private fun getBarrageViewTranslateX(animatedValue: Float): Float {
        return animatedValue / (1f - 0f) * (mBarrageViewToParentLeftMarginAnimationStart - mBarrageViewToParentLeftMarginAnimationEnd)
    }


    private fun setBarrageViewData(barrageView: View, userData: Map<String, String>) {
        val userName = userData["name"]
        val topicName = userData["topicName"]
        barrageView.name.text = userName
        barrageView.img.setImageResource(R.drawable.my_avatar_rounded)
        barrageView.topicText.text = topicName
    }


    private fun getBarrageView(): View {
        return if (mCacheBarrageViews.size > 0) {
            val cacheBarrageView = mCacheBarrageViews.removeAt(0)
//            mCurrItemView.add(0, cacheView)
            cacheBarrageView
        } else {
            val newBarrageView = createBarrageView()
//            mCurrItemView.add(0, newView)
            newBarrageView
        }
    }

    private fun addBarrageViewToList(barrageView: View) {
        mCurrBarrageViews.add(0, barrageView)
    }


    private fun createBarrageView(): View {
        return View.inflate(this, R.layout.peer_pressure_user_item, null)
    }

    private fun recycleBarrageView() {
        val size = mCurrBarrageViews.size
        val maxNum = mBarrageViewMaxNum - 1
        if (size >= maxNum) {
            for (i in maxNum until size - 1) {
                val view = mCurrBarrageViews.removeAt(i)
                LogUtils.d("maxNum = $maxNum i = $i")
                barrageViewParent?.let {
                    barrageViewParent.removeView(view)
                }
                // 清除状态
                view.translationY = 0f
//                view.setOnClickListener(null)
                cacheBarrageView(view)

            }
        }

    }


    private fun stopBarrageCloseAnimation() {
        mCloseAnimatorSet?.let {
            it.end()
        }
        mCloseAnimatorSet = null
    }


    private fun clearAllBarrageViewAndAnimation() {
        mBarrageViewStartAnimator?.let {
            it.end()
        }
        mBarrageViewStartAnimator = null
        mBarrageViewDisappearAlphaAnimation?.let {
            it.end()
        }
        mBarrageViewDisappearAlphaAnimation = null
        barrageViewParent?.let {
            barrageViewParent.removeAllViews()
        }
    }


    private fun doFavorBarrage(barrageView: View, barrageData: BarrageData) {
        doFavorAnimation(barrageView)
    }


    private fun doFavorAnimation(barrageView: View) {
        val starView = View.inflate(this, R.layout.peer_pressure_barrage_favar_star, null) as ImageView
        starView.setImageResource(getDoFavorStar())
        barrageStarParent?.let {
            val starViewLP = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
            starView.x = barrageView.x + barrageView.width - CommonUtils.dip2px(barrageView.context, 12f)
            starView.y = barrageView.y + CommonUtils.dip2px(barrageView.context, 36f)
            it.addView(starView, starViewLP)
            mDoFavorAnimatorSet = AnimatorSet()
            val starViewBezierEvaluator = BezierEvaluator(getStarViewControlPointF1(starView), getStarViewControlPointF2(starView))
            val mStarViewBezierAnim = ValueAnimator.ofObject(starViewBezierEvaluator,
                    getStarViewPointFStart(starView),
                    getStarViewPointFEnd(starView))
            mStarViewBezierAnim.duration = 2500
            mStarViewBezierAnim.interpolator = AccelerateDecelerateInterpolator()
            mStarViewBezierAnim.addUpdateListener {
                val pointF = it.animatedValue as PointF
                starView.x = pointF.x
                starView.y = pointF.y
                LogUtils.d("doFavorAnimation x = ${starView.x} y = ${starView.y}")
                starView.alpha = 1 - it.animatedFraction
            }
            mStarViewBezierAnim.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    barrageStarParent?.let {
                        it.removeView(starView)
                    }
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }

            })

            val barrageViewZoomAnimation = ValueAnimator.ofFloat(1f, 0.9f, 1f)
            barrageViewZoomAnimation.duration = 200
            barrageViewZoomAnimation.interpolator = AccelerateDecelerateInterpolator()
            barrageViewZoomAnimation.addUpdateListener {
                barrageView.scaleX = it.animatedValue as Float
                barrageView.scaleY = it.animatedValue as Float

            }

            starView.scaleX = 0.8f
            starView.scaleY = 0.8f
            val starViewZoomAnimation = ValueAnimator.ofFloat(0.8f, 1f)
            starViewZoomAnimation.duration = 100
            starViewZoomAnimation.interpolator = AccelerateDecelerateInterpolator()
            starViewZoomAnimation.addUpdateListener {
                starView.scaleX = it.animatedValue as Float
                starView.scaleY = it.animatedValue as Float

            }
            mDoFavorAnimatorSet?.let {
                it.playTogether(mStarViewBezierAnim, barrageViewZoomAnimation, starViewZoomAnimation)
                it.start()
            }
        }


    }

    private fun getDoFavorStar(): Int {
        val random = Random()
        val randomValue = random.nextInt(mFavorStarImages.size)
        return mFavorStarImages[randomValue]
    }


    private fun getStarViewControlPointF1(starView: ImageView): PointF {
        val dp = CommonUtils.dip2px(this, 120f)
        val x = starView.x + dp - (Math.random() * 2 * dp)
        val y = starView.y - CommonUtils.dip2px(this, 40f)
        return PointF(x.toFloat(), y)
    }

    private fun getStarViewControlPointF2(starView: ImageView): PointF {
        val dp = CommonUtils.dip2px(this, 80f)
        val x = starView.x + dp - (Math.random() * 2 * dp)
        val y = starView.y - 2 * CommonUtils.dip2px(this, 80f)
        return PointF(x.toFloat(), y)
    }

    private fun getStarViewPointFStart(starView: ImageView): PointF {
        val dp = CommonUtils.dip2px(this, 5f)
        val pointFX = starView.x + dp - (Math.random() * 2 * dp)
        return PointF(pointFX.toFloat(), starView.y)
    }

    private fun getStarViewPointFEnd(starView: View): PointF {
        val random = Random()
        val screenWidth = getScreenWidth()
        val screenHeight = getScreenHeight()
        val pointFX = starView.x + screenWidth - Math.random() * 2 * screenWidth
        val targetY = starView.y
        val diff = screenHeight - targetY
        var pointFY = 0
        if (diff > 0) {
            pointFY = random.nextInt((diff / 3).toInt())
        }
        LogUtils.d("pointFX = $pointFX pointFY = $pointFY")
        return PointF(pointFX.toFloat(), pointFY.toFloat())
    }

    private fun clearAllStarViewAndAnimation() {
        mDoFavorAnimatorSet?.let {
            it.end()
        }
        mDoFavorAnimatorSet = null
        barrageStarParent?.let {
            it.removeAllViews()
        }
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

    override fun onDestroy() {
        super.onDestroy()
        stopBarrageCloseAnimation()
        clearAllBarrageViewAndAnimation()
        clearAllStarViewAndAnimation()
    }

}



class BezierEvaluator(controlP1: PointF, controlP2: PointF) : TypeEvaluator<PointF> {
    private val mControlP1 = controlP1
    private val mControlP2 = controlP2
    override fun evaluate(time: Float, start: PointF, end: PointF): PointF {
        val timeLeft = 1.0f - time
        val point = PointF()

        point.x = timeLeft * timeLeft * timeLeft * (start.x) + 3 * timeLeft * timeLeft * time *
                (mControlP1.x) + 3 * timeLeft * time *
                time * (mControlP2.x) + time * time * time * (end.x)

        point.y = timeLeft * timeLeft * timeLeft * (start.y) + 3 * timeLeft * timeLeft * time *
                (mControlP1.y) + 3 * timeLeft * time *
                time * (mControlP2.y) + time * time * time * (end.y)
        return point
    }
}

data class BarrageData(val data: Map<String, String>, val index: Int)
