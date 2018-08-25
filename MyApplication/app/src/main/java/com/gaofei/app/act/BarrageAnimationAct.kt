package com.gaofei.app.act

import android.animation.*
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
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
import android.widget.TextView

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
    private val mBarrageViewMaxCacheNum = 4
    private val mCacheBarrageViews = ArrayList<View>(mBarrageViewMaxCacheNum)
    private val mStarViewMaxCacheNum = 10
    private val mCacheStarViews = ArrayList<View>(mStarViewMaxCacheNum)
    private val mCurrBarrageViews = ArrayList<View>()
    private val mHandler = Handler()
    private var mBarrageViewHeight: Int = 0
    private var mBarrageViewToParentLeftMarginAnimationStart: Int = 0
    private var mBarrageViewToParentLeftMarginAnimationEnd: Int = 0
    private var mStarViewWH: Int = 0
    private var mStarViewEndAnimationToTopDistance: Int = 0
    private var mBarrageViewToTopMargin: Int = 0
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
        mStarViewWH = CommonUtils.dip2px(this, 20f)
        mStarViewEndAnimationToTopDistance = CommonUtils.dip2px(this, 100f)
    }

    private fun initBarrageData() {
        Observable.just(++index, ++index, ++index, ++index, ++index, ++index, ++index)
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
            it.interpolator = AccelerateDecelerateInterpolator()
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
            val barrageViewLP = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, mBarrageViewHeight)
            barrageViewLP.gravity = Gravity.BOTTOM or Gravity.LEFT
            barrageViewLP.marginStart = mBarrageViewToParentLeftMarginAnimationStart
            it.addView(barrageView, barrageViewLP)
            addBarrageViewToList(barrageView)
            executeBarrageViewAnimation(barrageView)
        }
        setBarrageViewData(barrageView, barrageData.data)

    }

    private fun executeBarrageViewAnimation(barrageView: View) {
        barrageView.scaleX = mBarrageViewInitialScale
        barrageView.scaleY = mBarrageViewInitialScale
        barrageView.alpha = 0f
        mBarrageViewStartAnimator = ValueAnimator.ofFloat(0f, 1f)
        mBarrageViewStartAnimator?.let {
            it.duration = 600
            it.removeAllListeners()
            it.addUpdateListener {
                val animatedValue = it.animatedValue as Float
                val currScale = getBarrageViewScale(animatedValue)
                val translationX = getBarrageViewTranslateX(animatedValue)
                val alpha = getBarrageViewAlpha(animatedValue)
                barrageView.pivotX = barrageView.width.toFloat()
                barrageView.pivotY = (mBarrageViewHeight / 2).toFloat()
                barrageView.scaleX = currScale
                barrageView.scaleY = currScale
                barrageView.translationX = -translationX
                barrageView.alpha = alpha
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
            val barrageView = mCurrBarrageViews[currIndex]
            // 最后一个item消失
            if (currIndex == mBarrageViewMaxCacheNum - 1) {
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
                        mCurrBarrageViews.let {
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
        if (mCacheBarrageViews.size < mBarrageViewMaxCacheNum) {
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
        val maxNum = mBarrageViewMaxCacheNum - 1
        if (size > maxNum) {
            for (i in size - 1 downTo maxNum) {
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
        mCloseAnimatorSet?.end()
        mCloseAnimatorSet = null
    }


    private fun clearAllBarrageViewAndAnimation() {
        mBarrageViewStartAnimator?.end()
        mBarrageViewStartAnimator = null
        mBarrageViewDisappearAlphaAnimation?.end()
        mBarrageViewDisappearAlphaAnimation = null
        barrageViewParent?.removeAllViews()
        mCurrBarrageViews.clear()
        mCacheBarrageViews.clear()
    }


    private fun doFavorBarrage(barrageView: View, barrageData: BarrageData) {
        doFavorAnimation(barrageView)
    }


    private fun doFavorAnimation(barrageView: View) {
        val starView = obtainStarView() as ImageView
        starView.setImageResource(getDoFavorStar())
        barrageStarParent?.let {
            val starViewLP = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
            val starViewInitialPosition = calculateStartViewPoint(barrageView, starView)
            starView.x = starViewInitialPosition.x
            starView.y = starViewInitialPosition.y
            it.addView(starView, 0, starViewLP)
            mDoFavorAnimatorSet = AnimatorSet()
            val isLeftOffset = Math.random() <= 0.5
            val starViewControlPointF1 = getStarViewControlPointF1(starView, isLeftOffset)
            val starViewControlPointF2 = getStarViewControlPointF2(starView, isLeftOffset)
            val starViewPointFStart = getStarViewPointFStart(starView)
            val starViewPointFEnd = getStarViewPointFEnd(starView)
            val starViewBezierEvaluator = BezierEvaluator(starViewControlPointF1, starViewControlPointF2)
            val mStarViewBezierAnim = ValueAnimator.ofObject(starViewBezierEvaluator,
                    starViewPointFStart,
                    starViewPointFEnd)

            mStarViewBezierAnim.duration = 2500
            mStarViewBezierAnim.interpolator = AccelerateInterpolator()
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
                    barrageStarParent?.removeView(starView)
                    recycleStarView(starView)
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
            starViewZoomAnimation.interpolator = AccelerateInterpolator()
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


    private fun calculateStartViewPoint(barrageView: View, starView: View): PointF {
        val pointF = PointF(0f, 0f)
        val xMaxLeftOffset = CommonUtils.dip2px(this, 60f)
        val pointFXMax = barrageView.x + barrageView.width - mStarViewWH
        val random = Random()
        val xOffset = random.nextInt(xMaxLeftOffset)
        pointF.x = pointFXMax - xOffset
        pointF.y = barrageView.y + mStarViewWH
        return pointF
    }


    private fun getStarViewControlPointF1(starView: ImageView, isLeftOffset: Boolean): PointF {
        val dp = CommonUtils.dip2px(this, 60f)
        val x = starView.x + dp * (if (isLeftOffset) -1 else 1)
        val y = starView.y - (starView.y - mStarViewEndAnimationToTopDistance) / 3
//        addControlPointFView( PointF(x, y), "c1")
        return PointF(x, y)
    }

    private fun getStarViewControlPointF2(starView: ImageView, isLeftOffset: Boolean): PointF {
        val dp = CommonUtils.dip2px(this, 120f)
        val x = starView.x - dp * (if (isLeftOffset) -1 else 1)
        val y = starView.y - 2 * (starView.y - mStarViewEndAnimationToTopDistance) / 3
//        addControlPointFView( PointF(x, y), "c2")
        return PointF(x, y)
    }


    private fun addControlPointFView(controlPointF: PointF, text: String) {
        val controlView = TextView(this)
        controlView.setBackgroundColor(Color.RED)
        controlView.text = text
        val size = CommonUtils.dip2px(this, 20f)
        val controlViewLP = FrameLayout.LayoutParams(size, size)
        controlView.x = controlPointF.x
        controlView.y = controlPointF.y
        barrageStarParent?.addView(controlView, barrageStarParent.childCount, controlViewLP)
    }

    private fun getStarViewPointFStart(starView: ImageView): PointF {
        val pointFX = starView.x
        val pointFY = starView.y
//        addControlPointFView( PointF(pointFX, pointFY), "st")
        return PointF(pointFX, pointFY)
    }

    private fun getStarViewPointFEnd(starView: View): PointF {
        val pointF = PointF(0f, 0f)
        val screenWith = getScreenWidth()
        barrageStarParent?.let {
            val random = Random()
            val pointFX = screenWith / 3 + random.nextInt(screenWith / 3)
            var pointFY = mStarViewEndAnimationToTopDistance
            pointF.x = pointFX.toFloat()
            pointF.y = pointFY.toFloat()
//            addControlPointFView( pointF, "en")
        }
        return pointF
    }


    private fun obtainStarView(): View {
        return if (!mCacheStarViews.isEmpty()) {
            mCacheStarViews.removeAt(0)
        } else {
            View.inflate(this, R.layout.peer_pressure_barrage_favar_star, null)
        }
    }

    private fun recycleStarView(starView: ImageView) {
        if (mCacheStarViews.size < mStarViewMaxCacheNum) {
            mCacheStarViews.add(starView)
        }
    }

    private fun clearAllStarViewAndAnimation() {
        mDoFavorAnimatorSet?.end()
        mDoFavorAnimatorSet = null
        barrageStarParent?.removeAllViews()
        mCacheStarViews.clear()
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
