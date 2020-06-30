package com.gaofei.app.widget

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.gaofei.app.R
import com.gaofei.library.utils.CommonUtils
import com.gaofei.library.utils.LogUtils
import kotlinx.android.synthetic.main.layout_city_view.view.*
import kotlinx.android.synthetic.main.layout_sun_view.view.*
import java.io.Serializable

/**
 * 指导学太阳动画
 */
class CityAnimationView : FrameLayout {

    private var mAnimatorSet: AnimatorSet? = null
    private var mCity1Animator: java.util.ArrayList<Animator> = ArrayList()
    private var mCity2Animator: java.util.ArrayList<Animator> = ArrayList()

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
        val width = CommonUtils.dip2px(context, 110f)
        val marginTop = CommonUtils.dip2px(context, 10f)
        val sunView = LayoutInflater.from(context).inflate(R.layout.layout_city_view, this, false)
        addView(sunView)
        initCity1()
        initCity2()
        initSun1Animator()
        initSun2Animator()
    }

    private fun initSun1Animator() {
        mCity1Animator.clear()
        val animator = ObjectAnimator.ofInt(0, 1000)
        animator.interpolator = LinearInterpolator()
        animator.duration = 3000
        var lastValue = 0
        animator.addUpdateListener {
            val value = it.animatedValue as Int
            val xOffest = value - lastValue
            lastValue = value
            LogUtils.d("animatedValue $value $xOffest")
            if (xOffest > 0) {
                cityView1.scrollBy(xOffest, 0)
            }
        }

        mCity1Animator.add(animator)
    }


    private fun initSun2Animator() {
        mCity2Animator.clear()
        val animator = ObjectAnimator.ofInt(0, 300)
        animator.interpolator = LinearInterpolator()
        animator.duration = 3000
        var lastValue = 0
        animator.addUpdateListener {
            val value = it.animatedValue as Int
            val xOffest = value - lastValue
            lastValue = value
            LogUtils.d("animatedValue $value $xOffest")
            if (xOffest > 0) {
                cityView2.scrollBy(xOffest, 0)
            }
        }

        mCity2Animator.add(animator)
    }


    private fun initCity1() {
        val list = ArrayList<City>()
        list.add(City(R.drawable.plan_preview_loading_city_2))
        val llm = androidx.recyclerview.widget.LinearLayoutManager(context)
        llm.orientation = androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
        cityView1.layoutManager = llm
        cityView1.isNestedScrollingEnabled = false
        val adapter = City1Adapter()
        cityView1.adapter = adapter
        adapter.setNewData(list)
    }


    private fun initCity2() {
        val list = ArrayList<City>()
        list.add(City(R.drawable.plan_preview_loading_city_1))
        val llm = androidx.recyclerview.widget.LinearLayoutManager(context)
        llm.orientation = androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
        cityView2.isNestedScrollingEnabled = false
        cityView2.layoutManager = llm
        val adapter = City1Adapter()
        cityView2.adapter = adapter
        adapter.setNewData(list)
    }


    fun start(listener: Animator.AnimatorListener?) {
        clearViewAnimationStatus()
        this.visibility = View.VISIBLE
        val startAnimatorSet = AnimatorSet()
        val startAnimators = java.util.ArrayList<Animator>()
        mCity1Animator.forEach {
            startAnimators.add(it)
        }



        mCity2Animator.forEach {
            startAnimators.add(it)
        }
        startAnimatorSet.playTogether(startAnimators)
        mAnimatorSet = AnimatorSet()
        mAnimatorSet?.let {
            it.play(startAnimatorSet)
            listener?.let { listener ->
                it.addListener(listener)
            }
            it.start()
        }
//        cityView1.smoothScrollBy(1000, 0, LinearInterpolator())

    }

    private fun clearViewAnimationStatus() {

    }

    private fun clearAllAnimator() {
        mAnimatorSet?.let {
            it.cancel()
        }
    }

    fun stop() {

    }

    private fun updateViewScale(view: View, scale: Float) {
        setViewPivotScale(view, scale)
    }

    /**
     * 设置从view中心点缩放
     */
    private fun setViewPivotScale(view: View, scale: Float) {
        val width = view.width
        val height = view.height
        view.pivotX = (width / 2).toFloat()
        view.pivotY = (height / 2).toFloat()
        view.scaleX = scale
        view.scaleY = scale
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        clearAllAnimator()
    }

    data class City(val imageId: Int) : Serializable

    class City1Adapter : BaseQuickAdapter<City, BaseViewHolder>(R.layout.layout_city_item) {

        override fun convert(helper: BaseViewHolder?, item: City) {
            helper?.setImageResource(R.id.image, item.imageId)
        }

    }

}