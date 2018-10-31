package com.gaofei.app

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.PointF
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import com.gaofei.app.task.NewTaskCoinTaskInfo
import com.gaofei.app.task.NewTaskTreasureBoxInfo

import com.gaofei.library.base.BaseAct
import com.gaofei.library.utils.LogUtils
import com.google.gson.GsonBuilder
import com.yangcong345.android.phone.component.task2.NewTaskContract
import com.yangcong345.android.phone.component.task2.NewTaskPresenter
import kotlinx.android.synthetic.main.act_canvas.*

class CanvasActivity : BaseAct(), NewTaskContract.View {


    override fun showTreasureBoxUI(newTaskCoinTaskInfoList: ArrayList<NewTaskCoinTaskInfo>, newTaskTreasureBoxInfoList: ArrayList<NewTaskTreasureBoxInfo>) {
        taskTreasureBoxView.bindData(newTaskCoinTaskInfoList, newTaskTreasureBoxInfoList)
    }

    override fun onBack() {
    }

    override fun updateTaskProgress(pair: Pair<Int, Int>) {
    }

    override fun showTaskTip(message: String, type: Int) {
    }

    override fun setPresenter(t: NewTaskContract.Presenter) {
    }

    private lateinit var mPresenter: NewTaskContract.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_canvas)
        mPresenter = NewTaskPresenter(this)
        taskTreasureBoxView.setPresenter(mPresenter)
        initView()
    }

    private lateinit var mRewardCoinsView: ArrayList<View>

    private fun initView() {
        mRewardCoinsView = arrayListOf(findViewById(R.id.rewardCoin9), findViewById(R.id.rewardCoin8), findViewById(R.id.rewardCoin7), findViewById(R.id.rewardCoin6),
                findViewById(R.id.rewardCoin5), findViewById(R.id.rewardCoin4), findViewById(R.id.rewardCoin3), findViewById(R.id.rewardCoin2), findViewById(R.id.rewardCoin1))

    }


    private var mTreasureBoxCoinReceiveAnimatorSet: AnimatorSet? = null
    override fun playTreasureBoxCoinReceiveAnimation() {
        clearAllAnimation()
        mRewardCoinsView.forEach {
            it.scaleX = 0.8f
            it.scaleY = 0.8f
            it.alpha = 0f
            it.translationX = 0f
            it.translationY = 0f
        }
        rewardCoinRoot.visibility = View.VISIBLE
        rewardCoinRoot.background.alpha = 0
        val duration = 4000f
        val treasureBoxCoinReceiveScaleAnimation = ValueAnimator.ofFloat(0f, duration)
        treasureBoxCoinReceiveScaleAnimation.setDuration(duration.toLong())
        treasureBoxCoinReceiveScaleAnimation.setInterpolator(AccelerateInterpolator())
        treasureBoxCoinReceiveScaleAnimation.addUpdateListener {
            updateTreasureBoxCoinReceiveScale(it.animatedValue as Float)
            updateTreasureBoxRewardCoinNumberScale(it.animatedValue as Float)
            updateTreasureBoxRewardCoinBackgroundScale(it.animatedValue as Float)
        }
        val treasureBoxCoinReceiveAlphaAnimation = ValueAnimator.ofFloat(0f, duration)
        treasureBoxCoinReceiveAlphaAnimation.setDuration(duration.toLong())
        treasureBoxCoinReceiveAlphaAnimation.interpolator = LinearInterpolator()
        treasureBoxCoinReceiveAlphaAnimation.addUpdateListener {
            updateTreasureBoxCoinReceiveAlpha(it.animatedValue as Float)
            updateTreasureBoxCoinReceiveTranslate(it.animatedValue as Float)
            updateTreasureBoxRewardCoinNumberAlpha(it.animatedValue as Float)
            updateTreasureBoxRewardCoinNumberTranslate(it.animatedValue as Float)
            updateTreasureBoxRewardCoinBackgroundAlpha(it.animatedValue as Float)

        }
        mTreasureBoxCoinReceiveAnimatorSet = AnimatorSet()
        mTreasureBoxCoinReceiveAnimatorSet?.let {

            it.playTogether(treasureBoxCoinReceiveScaleAnimation, treasureBoxCoinReceiveAlphaAnimation)
            it.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {

                }

                override fun onAnimationEnd(animation: Animator?) {
                    rewardCoinRoot.visibility = View.GONE
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }

            })
            it.start()
        }
    }

    private fun updateTreasureBoxCoinReceiveScale(valueAnimator: Float) {
        mRewardCoinsView.forEachIndexed { index, view ->
            val rewardCoinFirstStartTime = index * 100f
            val rewardCoinFirstEndTime = index * 100f + 200f
            if (valueAnimator in rewardCoinFirstStartTime..rewardCoinFirstEndTime) {
                val scale = calculateAnimatorValue(0.8f, 1f, rewardCoinFirstStartTime, rewardCoinFirstEndTime, valueAnimator)
                LogUtils.d("updateTreasureBoxCoinReceiveScale ${scale}")
                view.pivotX = (view.width / 2).toFloat()
                view.pivotY = (view.height / 2).toFloat()
                view.scaleX = scale
                view.scaleY = scale
            }

            val rewardCoinSecondStartTime = index * 200f + 1500f
            val rewardCoinSecondEndTime = index * 200f + 200f + 1500f
            if (valueAnimator in rewardCoinSecondStartTime..rewardCoinSecondEndTime) {
                val scale = calculateAnimatorValue(1f, 0.8f, rewardCoinSecondStartTime, rewardCoinSecondEndTime, valueAnimator)
                LogUtils.d("updateTreasureBoxCoinReceiveScale ${scale}")
                view.pivotX = (view.width / 2).toFloat()
                view.pivotY = (view.height / 2).toFloat()
                view.scaleX = scale
                view.scaleY = scale
            }
        }
    }

    private fun updateTreasureBoxCoinReceiveAlpha(valueAnimator: Float) {
        // 遮罩
        val bgStartTime = 0f
        val bgEndTime = 300f
        if (valueAnimator in bgStartTime..bgEndTime) {
            rewardCoinRoot.background.alpha = calculateAnimatorValue(0f, 179f, bgStartTime, bgEndTime, valueAnimator).toInt()
        }

        mRewardCoinsView.forEachIndexed { index, view ->
            val rewardCoinFirstStartTime = index * 100f
            val rewardCoinFirstEndTime = index * 100f + 100f
            if (valueAnimator in rewardCoinFirstStartTime..rewardCoinFirstEndTime) {
                val alpha = calculateAnimatorValue(0.8f, 1f, rewardCoinFirstStartTime, rewardCoinFirstEndTime, valueAnimator)
                view.alpha = alpha
            }

            val rewardCoinSecondStartTime = index * 100f + 1600f
            val rewardCoinSecondEndTime = index * 100f + 100f + 1600f
            if (valueAnimator in rewardCoinSecondStartTime..rewardCoinSecondEndTime) {
                val alpha = calculateAnimatorValue(1f, 0f, rewardCoinSecondStartTime, rewardCoinSecondEndTime, valueAnimator)
                view.alpha = alpha
            }
        }

    }

    private fun updateTreasureBoxCoinReceiveTranslate(valueAnimator: Float) {
        mRewardCoinsView.forEachIndexed { index, view ->
            val rewardCoinStartTime = index * 200f + 1000f
            val rewardCoinEndTime = index * 200f + 600f + 1000f
            if (valueAnimator in rewardCoinStartTime..rewardCoinEndTime) {
                val pointF = calculateCoinTranslate(view, rewardCoinStartTime, rewardCoinEndTime, valueAnimator)
                view.x = pointF.x
                view.y = pointF.y
            }
        }

    }

    private fun updateTreasureBoxRewardCoinNumberScale(valueAnimator: Float) {
        val firstStartTime = 0f
        val firstEndTime = 200f
        if (valueAnimator in firstStartTime..firstEndTime) {
            val scale = calculateAnimatorValue(0.8f, 1f, firstStartTime, firstEndTime, valueAnimator)
            rewardCoinNumber.pivotX = (rewardCoinNumber.width / 2).toFloat()
            rewardCoinNumber.pivotY = (rewardCoinNumber.height / 2).toFloat()
            rewardCoinNumber.scaleX = scale
            rewardCoinNumber.scaleY = scale
        }

    }

    private fun updateTreasureBoxRewardCoinNumberAlpha(valueAnimator: Float) {
        val firstStartTime = 0f
        val firstEndTime = 200f
        if (valueAnimator in firstStartTime..firstEndTime) {
            val alpha = calculateAnimatorValue(0f, 1f, firstStartTime, firstEndTime, valueAnimator)
            rewardCoinNumber.alpha = alpha
        }

        val secondStartTime = 0f + 1000f
        val secondEndTime = 200f + 1000f
        if (valueAnimator in secondStartTime..secondEndTime) {
            val alpha = calculateAnimatorValue(1f, 0f, secondStartTime, secondEndTime, valueAnimator)
            rewardCoinNumber.alpha = alpha
        }

    }

    private fun updateTreasureBoxRewardCoinNumberTranslate(valueAnimator: Float) {

    }

    private fun updateTreasureBoxRewardCoinBackgroundScale(valueAnimator: Float) {
        val firstStartTime = 0f
        val firstEndTime = 200f
        if (valueAnimator in firstStartTime..firstEndTime) {
            val scale = calculateAnimatorValue(0.8f, 1f, firstStartTime, firstEndTime, valueAnimator)
            rewardCoinBackGround.pivotX = (rewardCoinBackGround.width / 2).toFloat()
            rewardCoinBackGround.pivotY = (rewardCoinBackGround.height / 2).toFloat()
            rewardCoinBackGround.scaleX = scale
            rewardCoinBackGround.scaleY = scale
        }

    }

    private fun updateTreasureBoxRewardCoinBackgroundAlpha(valueAnimator: Float) {
        val firstStartTime = 0f
        val firstEndTime = 600f
        if (valueAnimator in firstStartTime..firstEndTime) {
            val alpha = calculateAnimatorValue(0f, 1f, firstStartTime, firstEndTime, valueAnimator)
            rewardCoinBackGround.alpha = alpha
        }
        val secondStartTime = 0f + firstEndTime
        val secondEndTime = 600f + firstEndTime
        if (valueAnimator in secondStartTime..secondEndTime) {
            val alpha = calculateAnimatorValue(1f, 0.5f, secondStartTime, secondEndTime, valueAnimator)
            rewardCoinBackGround.alpha = alpha
        }

        val thirdStartTime = 0f + secondEndTime
        val thirdEndTime = 600f + secondEndTime
        if (valueAnimator in thirdStartTime..thirdEndTime) {
            val alpha = calculateAnimatorValue(0.5f, 1f, thirdStartTime, thirdEndTime, valueAnimator)
            rewardCoinBackGround.alpha = alpha
        }

    }


    private fun calculateAnimatorValue(from: Float, to: Float, valueStart: Float, valueEnd: Float, valueCurr: Float): Float {
        val progress = (valueCurr - valueStart) / (valueEnd - valueStart)
        val value = from + (to - from) * progress
        LogUtils.d("calculateAnimatorValue ${value} ${from} ${to} ${valueStart} ${valueEnd} ${valueCurr}")
        return value
    }

    private fun calculateCoinTranslate(view: View, valueStart: Float, valueEnd: Float, valueCurr: Float): PointF {
        var currProgress = (valueCurr - valueStart) / (valueEnd - valueStart)
        val progressLocation = intArrayOf(0, 0)
        progressLocation[0] = progressRoot.left
        progressLocation[1] = progressRoot.top
        val viewLocation = intArrayOf(0, 0)
        viewLocation[0] = view.left
        viewLocation[1] = view.top
        val x = (progressLocation[0] - viewLocation[0]) * currProgress + viewLocation[0]
        val y = (progressLocation[1] - viewLocation[1]) * currProgress + viewLocation[1]
        LogUtils.d("calculateCoinTranslate ${view.id} ${progressLocation[0]} ${progressLocation[1]} ${viewLocation[0]} ${viewLocation[1]} ${x} ${y} ${currProgress}")
        return PointF(x, y)
    }


    private fun clearAllAnimation() {
        mTreasureBoxCoinReceiveAnimatorSet?.let {
            it.cancel()
//            mTreasureBoxCoinReceiveAnimatorSet.end()
        }
    }

    override fun onResume() {
        super.onResume()
        mPresenter.start()
    }


    /**
     */
    fun calculateBackGroundScale(): Float {
        val screenWidth = getScreenWidth()
        val vWidth = this.resources.getDimension(R.dimen.new_task_root_width)
        val widthScale = screenWidth / vWidth
        return widthScale
    }


    /**
     * 获取屏幕高度
     *
     * @return
     */
    private fun Context.getScreenWidth(): Int {
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.widthPixels
//        return CommonUtils.dip2px(this, 200f)
    }

    fun dip2px(dipValue: Float): Int {
        val scale = resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

}
