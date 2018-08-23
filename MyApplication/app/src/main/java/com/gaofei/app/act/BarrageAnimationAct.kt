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
    private val mUserItemViewStartScale = 0.2f
    private var mBarrageViewStartAnimator: ValueAnimator? = null
    private var mCloseAnimatorSet: AnimatorSet? = null
    private var mBarrageViewDisappearAlphaAnimation: ObjectAnimator? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_barrage_root_view)
        init()
        setBarrageRootViewVisible(false)
        img.setOnClickListener {
            //            initBarrageData()
            setBarrageRootViewVisible(true)
            setBarrageRootViewBackground()
            processBarrageViewLoader()
            startBarrageCloseAnimation()
        }
        close_icon.setOnClickListener {
            setBarrageRootViewVisible(false)
            stopBarrageCloseAnimation()
            clearBarrageView()
        }
        close_text.setOnClickListener {
            setBarrageRootViewVisible(false)
            stopBarrageCloseAnimation()
            clearBarrageView()
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

    private fun notifyBarrageDataUpdate() {
        if (isShowBarrageRootView) {
            processBarrageViewLoader()
        }
    }


    private fun processBarrageViewLoader() {
        if (mBarrageData.size > 0) {
            val userData = mBarrageData.removeAt(0)
            attachBarrageView(userData)
        } else {
            if (!mCurrBarrageViews.isEmpty()) {
                var firstDuration = 600L
                mCurrBarrageViews.reversed().forEachIndexed { index, view ->
                    mHandler.postDelayed({
                        firstDuration += 500L
                        barrageEndDisappear(view, firstDuration)
                    }, 200)
                }
            }
        }
    }

    private fun attachBarrageView(barrageData: BarrageData) {
        val view = getBarrageView()
        view.setOnClickListener { v ->
            doFavorBarrage(v, barrageData)
        }
        barrageFL?.let {
            val barrageLP = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, mBarrageViewHeight)
            barrageLP.gravity = Gravity.BOTTOM or Gravity.LEFT
            barrageLP.marginStart = mBarrageViewToParentLeftMarginAnimationStart
            barrageFL.addView(view, barrageLP)
            addBarrageViewToList(view)
            executeBarrageViewAnimation(view)
        }
        setBarrageViewData(view, barrageData.data)

    }

    private fun executeBarrageViewAnimation(view: View) {
        view.scaleX = mUserItemViewStartScale
        view.scaleY = mUserItemViewStartScale
        view.alpha = 0f
        mBarrageViewStartAnimator = ValueAnimator.ofFloat(0f, 1f)
        mBarrageViewStartAnimator?.let {
            it.duration = 600
            it.removeAllListeners()
            it.addUpdateListener {
                val value = it.animatedValue as Float
                val currScale = getFirstUserItemViewScale(value)
                val translationX = getFirstUserItemViewTranslateX(value)
                val alpha = getFirstUserItemViewAlpha(value)
                LogUtils.d("value = $value currScale = $currScale translationX = $translationX alpha = $alpha")
                triggerOthersBarrageViewStartAnimation(value)
                view.pivotX = view.width.toFloat()
                view.pivotY = (mBarrageViewHeight / 2).toFloat()
                view.scaleX = currScale
                view.scaleY = currScale
                view.translationX = -translationX
                view.alpha = alpha
            }
            it.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                    LogUtils.d("aaaaaa onAnimationStart")
                }

                override fun onAnimationEnd(animation: Animator?) {
                    recycleUserItemView()
                    LogUtils.d("aaaaaa onAnimationEnd ${it.animatedValue}")
//                    mHandler.postDelayed({
                    processBarrageViewLoader()
//                    },1000)


                }

            })
            it.start()
        }
    }

    private fun triggerOthersBarrageViewStartAnimation(value: Float) {
        val size = mCurrBarrageViews.size
        var currIndex = size - 1
        while (currIndex >= 0) {
            var view = mCurrBarrageViews[currIndex]
            if (currIndex == mBarrageViewMaxNum - 1) {
                view.alpha = getLastUserItemViewAlpha(value)
            }
            if (currIndex != 0) {
                view.translationY = getFirstUserItemViewTranslationY(currIndex, value)
            }
            currIndex--
        }

    }

    private fun barrageEndDisappear(view: View, duration: Long) {
        mBarrageViewDisappearAlphaAnimation = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f)
        mBarrageViewDisappearAlphaAnimation?.let {
            it.duration = duration
            it.interpolator = AccelerateDecelerateInterpolator()
            it.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    barrageFL?.let {
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


    private fun doFavorBarrage(view: View, barrageData: BarrageData) {
        doFavorAnimation(view)
    }


    private fun doFavorAnimation(view: View) {
        val starView = View.inflate(this, R.layout.peer_pressure_barrage_favar_star, null) as ImageView
        starView.setImageResource(getFavorStar())
        barrageFL?.let {
            val starLP = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
            starView.x = view.x + view.width - CommonUtils.dip2px(view.context, 12f)
            starView.y = view.y + CommonUtils.dip2px(view.context, 36f)
            barrageFL.addView(starView, starLP)
            val animatorSet = AnimatorSet()
            val bezierEvaluator = BezierEvaluator(getControlPointF1(starView), getControlPointF2(starView))
            val mBezierAnim = ValueAnimator.ofObject(bezierEvaluator,
                    getStarPointFStart(starView),
                    getStarPointFEnd(starView))
            mBezierAnim.duration = 2500
            mBezierAnim.interpolator = AccelerateDecelerateInterpolator()
            mBezierAnim.addUpdateListener {
                val pointF = it.animatedValue as PointF
                starView.x = pointF.x
                starView.y = pointF.y
                LogUtils.d("doFavorAnimation x = ${starView.x} y = ${starView.y}")
                starView.alpha = 1 - it.animatedFraction
            }
            mBezierAnim.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    barrageFL?.let {
                        it.removeView(starView)
                    }
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }

            })

            val zoomAnimation = ValueAnimator.ofFloat(1f, 0.9f, 1f)
            zoomAnimation.duration = 200
            zoomAnimation.interpolator = AccelerateDecelerateInterpolator()
            zoomAnimation.addUpdateListener {
                view.scaleX = it.animatedValue as Float
                view.scaleY = it.animatedValue as Float

            }

            starView.scaleX = 0.8f
            starView.scaleY = 0.8f
            val zoomAnimation2 = ValueAnimator.ofFloat(0.8f, 1f)
            zoomAnimation2.duration = 100
            zoomAnimation2.interpolator = AccelerateDecelerateInterpolator()
            zoomAnimation2.addUpdateListener {
                starView.scaleX = it.animatedValue as Float
                starView.scaleY = it.animatedValue as Float

            }
            animatorSet.playTogether(mBezierAnim, zoomAnimation, zoomAnimation2)
            animatorSet.start()
        }

    }

    private fun getFavorStar(): Int {
        val random = Random()
        val randomValue = random.nextInt(mFavorStarImages.size)
        return mFavorStarImages[randomValue]
    }


    private fun getControlPointF1(starView: ImageView): PointF {
        val dp = CommonUtils.dip2px(this, 120f)
        val x = starView.x + dp - (Math.random() * 2 * dp)
        val y = starView.y - CommonUtils.dip2px(this, 40f)
        return PointF(x.toFloat(), y)
    }

    private fun getControlPointF2(starView: ImageView): PointF {
        val dp = CommonUtils.dip2px(this, 80f)
        val x = starView.x + dp - (Math.random() * 2 * dp)
        val y = starView.y - 2 * CommonUtils.dip2px(this, 80f)
        return PointF(x.toFloat(), y)
    }

    private fun getStarPointFStart(starView: ImageView): PointF {
        val dp = CommonUtils.dip2px(this, 5f)
        val pointFX = starView.x + dp - (Math.random() * 2 * dp)
        return PointF(pointFX.toFloat(), starView.y)
    }

    private fun getStarPointFEnd(targetView: View): PointF {
        val random = Random()
        val screenWidth = getScreenWidth()
        val screenHeight = getScreenHeight()
        val pointFX = targetView.x + screenWidth - Math.random() * 2 * screenWidth
        val targetY = targetView.y
        val diff = screenHeight - targetY
        var pointFY = 0
        if (diff > 0) {
            pointFY = random.nextInt((diff / 3).toInt())
        }
        LogUtils.d("pointFX = $pointFX pointFY = $pointFY")
        return PointF(pointFX.toFloat(), pointFY.toFloat())
    }


    private fun recycleUserItemView() {
        val size = mCurrBarrageViews.size
        val maxNum = mBarrageViewMaxNum - 1
        if (size >= maxNum) {
            for (i in maxNum until size - 1) {
                val view = mCurrBarrageViews.removeAt(i)
                LogUtils.d("maxNum = $maxNum i = $i")
                barrageFL?.let {
                    barrageFL.removeView(view)
                }
                // 清除状态
                view.translationY = 0f
//                view.setOnClickListener(null)
                cacheUserItemView(view)

            }
        }

    }

    private fun cacheUserItemView(view: View) {
        if (mCacheBarrageViews.size < mBarrageViewMaxNum) {
            mCacheBarrageViews.add(view)
        }
    }

    private fun getFirstUserItemViewTranslationY(index: Int, value: Float): Float {
        return -((index - 1) * (mBarrageViewHeight + mBarrageViewToTopMargin) + value * (mBarrageViewHeight + mBarrageViewToTopMargin))
    }

    private fun getLastUserItemViewAlpha(value: Float): Float {

        return 1 - value
    }


    private fun getFirstUserItemViewAlpha(value: Float): Float {
        return value
    }

    private fun getFirstUserItemViewScale(value: Float): Float {
        return mUserItemViewStartScale + value / (1f - 0f) * 0.8f
    }

    private fun getFirstUserItemViewTranslateX(value: Float): Float {
        return value / (1f - 0f) * (mBarrageViewToParentLeftMarginAnimationStart - mBarrageViewToParentLeftMarginAnimationEnd)
    }


    private fun setBarrageViewData(view: View, userData: Map<String, String>) {
        val userName = userData["name"]
        val topicName = userData["topicName"]
        view.name.text = userName
        view.img.setImageResource(R.drawable.my_avatar_rounded)
        view.topicText.text = topicName
    }


    private fun getBarrageView(): View {
        return if (mCacheBarrageViews.size > 0) {
            val cacheView = mCacheBarrageViews.removeAt(0)
//            mCurrItemView.add(0, cacheView)
            cacheView
        } else {
            val newView = createUserItemView()
//            mCurrItemView.add(0, newView)
            newView
        }
    }

    private fun addBarrageViewToList(view: View) {
        mCurrBarrageViews.add(0, view)
    }


    private fun createUserItemView(): View {
        return View.inflate(this, R.layout.peer_pressure_user_item, null)
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

    private fun stopBarrageCloseAnimation() {
        stopBarrageAnimationClose()
        stopBarrageAnimationUserItem()
    }

    private fun stopBarrageAnimationUserItem() {
        mBarrageViewStartAnimator?.let {
            it.end()
        }
    }

    private fun stopBarrageAnimationClose() {
        mCloseAnimatorSet?.let {
            it.end()
        }
        mCloseAnimatorSet = null
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

    private fun clearBarrageView() {
        barrageFL?.let {
            barrageFL.removeAllViews()
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

class BezierEvaluator(controP1: PointF, controP2: PointF) : TypeEvaluator<PointF> {
    val mControlP1 = controP1
    val mControlP2 = controP2
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
