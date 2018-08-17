package com.gaofei.app.act

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Bitmap
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
import android.view.animation.AccelerateInterpolator
import android.widget.FrameLayout

import com.gaofei.app.R
import com.gaofei.library.base.BaseAct
import com.gaofei.library.utils.CommonUtils
import com.gaofei.library.utils.LogUtils
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.act_barrage_animation.*
import kotlinx.android.synthetic.main.peer_pressure_user_item.view.*
import kotlin.collections.ArrayList


class BarrageAnimationAct : BaseAct() {

    private val mUserList = ArrayList<UserItemData>()
    private val mUserItemCacheViews = ArrayList<View>(3)
    private val mCurrItemViews = ArrayList<View>()
    private val mHandler = Handler()
    private var mUserItemHeight: Int = 0
    private var mUserItemRightMargin: Int = 0
    private var mUserItemRightAnimationMargin: Int = 0
    private var mUserItemSpace: Int = 0
    private val mUserItemNum = 3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_barrage_animation)
        init()
        setBarrageVisible(false)
        img.setOnClickListener {
            setBarrageVisible(true)
            setBarrageBackground()
            startBarrageAnimation()
        }
        close_icon.setOnClickListener {
            setBarrageVisible(false)
            stopBarrageAnimation()
            clearUserItemView()
        }
        close_text.setOnClickListener {
            setBarrageVisible(false)
            stopBarrageAnimation()
            clearUserItemView()
        }
        initUserData()
    }

    private fun clearUserItemView() {
        barrageFL?.let {
            barrageFL.removeAllViews()
        }
    }


    private fun init() {
        mUserItemHeight = CommonUtils.dip2px(this, 64f)
        mUserItemRightMargin = CommonUtils.dip2px(this, 20f)
        mUserItemRightAnimationMargin = CommonUtils.dip2px(this, 24f)
        mUserItemSpace = CommonUtils.dip2px(this, 12f)
    }

    private fun initUserData() {
        Observable.just(0, 1, 2, 3, 4, 5, 6)
                .map {
                    return@map UserItemData(mapOf(Pair("name", "魔法少女小圆-$it"), Pair("topicName", "QB是个骗子-$it")), it)
                }
                .toList()
                .subscribe(Consumer {
                    mUserList.addAll(it)
//                    attachUserItemViewTest(mUserList.removeAt(0))
                })

    }


    data class UserItemData(val data: Map<String, String>, val index: Int)

    private fun processUserItemView() {
        if (mUserList.size > 0) {
            val userData = mUserList.removeAt(0)
            attachUserItemView(userData)
            mHandler.postDelayed({
                processUserItemView()
            }, 1000)
        }
    }

    private fun attachUserItemViewTest(userData: UserItemData) {
        val view = getUserItemView()
        barrageFL?.let {
            val itemLP = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, mUserItemHeight)
            itemLP.gravity = Gravity.BOTTOM or Gravity.LEFT
            itemLP.bottomMargin = CommonUtils.dip2px(this, 108f)
            itemLP.marginStart = mUserItemRightMargin
            barrageFL.addView(view, itemLP)
        }
        setUserItemViewData(view, userData.data)

    }

    private fun attachUserItemView(userData: UserItemData) {
        val view = getUserItemView()
        barrageFL?.let {
            val itemLP = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, mUserItemHeight)
            itemLP.gravity = Gravity.BOTTOM or Gravity.LEFT
            itemLP.marginStart = mUserItemRightMargin + mUserItemRightAnimationMargin
            barrageFL.addView(view, itemLP)
            addUserItemView(view)
            executeUserItemAnimation(view)
        }
        setUserItemViewData(view, userData.data)

    }

    private val mUserItemViewStartScale = 0.2f


    private fun executeUserItemAnimation(view: View) {
        view.scaleX = mUserItemViewStartScale
        view.scaleY = mUserItemViewStartScale
        view.alpha = 0f
        val valueAnimator = ValueAnimator.ofFloat(0f, 1f)
        valueAnimator.duration = 800
        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            val currScale = getFirstUserItemViewScale(value)
            val translationX = getFirstUserItemViewTranslateX(value)
            val alpha = getFirstUserItemViewAlpha(value)
            LogUtils.d("value = $value currScale = $currScale translationX = $translationX alpha = $alpha")
            triggerOthersUserItemViewAnimation(value)
            view.scaleX = currScale
            view.scaleY = currScale
            view.translationX = -translationX
            view.alpha = alpha

        }

        valueAnimator.start()
    }


    private fun triggerOthersUserItemViewAnimation(value: Float) {
        val size = mCurrItemViews.size
        var currIndex = size - 1
        while (currIndex >= 0) {
            var view = mCurrItemViews.get(currIndex)
            if (currIndex == mUserItemNum - 1) {
                view.alpha = getLastUserItemViewAlpha(value)
                if (value == 1f) {
//                    removeUserItemView(view)
                }
            }
//            if (currIndex != 0) {
                view.translationY = getFirstUserItemViewTranslationY(currIndex, value)
//            }
            currIndex--
        }

    }

    private fun removeUserItemView(view: View) {
        barrageFL.removeView(view)
        recycleUserItemView(view)

    }

    private fun recycleUserItemView(view: View) {
        mUserItemCacheViews.add(view)
    }

    private fun getFirstUserItemViewTranslationY(index: Int, value: Float): Float {
        return -(index * (mUserItemHeight + mUserItemSpace) + value * (mUserItemHeight + mUserItemSpace))
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
        return value / (1f - 0f) * mUserItemRightAnimationMargin
    }


    private fun setUserItemViewData(view: View, userData: Map<String, String>) {
        val userName = userData["name"]
        val topicName = userData["topicName"]
        view.name.text = userName
        view.img.setImageResource(R.drawable.my_avatar_rounded)
        view.topicText.text = topicName
    }


    private fun getUserItemView(): View {
        return if (mUserItemCacheViews.size > 0) {
            val cacheView = mUserItemCacheViews.removeAt(0)
//            mCurrItemView.add(0, cacheView)
            cacheView
        } else {
            val newView = createUserItemView()
//            mCurrItemView.add(0, newView)
            newView
        }
    }

    private fun addUserItemView(view: View) {
        mCurrItemViews.add(0, view)
    }


    private fun createUserItemView(): View {
        val userItemView = View.inflate(this, R.layout.peer_pressure_user_item, null);
        return userItemView
    }

    private var objectAnimatorClose: ObjectAnimator? = null

    private fun startBarrageAnimation() {
        startBarrageAnimationClose()
        processUserItemView()
    }

    private fun startBarrageAnimationClose() {
        objectAnimatorClose = ObjectAnimator.ofFloat(close_icon, "rotation", 0f, -90f)
        objectAnimatorClose?.let {
            it.duration = 300
            it.interpolator = AccelerateInterpolator()
            it.start()
        }

    }

    private fun stopBarrageAnimation() {
        stopBarrageAnimationClose()
    }

    private fun stopBarrageAnimationClose() {
        objectAnimatorClose?.let {
            it.end()
        }
        objectAnimatorClose = null
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
