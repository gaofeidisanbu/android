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


    private lateinit var mTreasureBoxCoinReceiveAnimatorSet: AnimatorSet
    override fun playTreasureBoxCoinReceiveAnimation() {
        mRewardCoinsView.forEach {
            it.scaleX = 0.8f
            it.scaleY = 0.8f
            it.alpha = 0f
            it.translationX = 0f
            it.translationY = 0f
        }
        rewardCoinRoot.visibility = View.VISIBLE
        rewardCoinRoot.background.alpha = 0
        val duration = 2000f
        mTreasureBoxCoinReceiveAnimatorSet = AnimatorSet()
        val treasureBoxCoinReceiveScaleAnimation = ValueAnimator.ofFloat(0f, duration)
        mTreasureBoxCoinReceiveAnimatorSet.setDuration(duration.toLong())
        mTreasureBoxCoinReceiveAnimatorSet.setInterpolator(AccelerateInterpolator())
        treasureBoxCoinReceiveScaleAnimation.addUpdateListener {
            updateTreasureBoxCoinReceiveScale(it.animatedValue as Float)
        }
        val treasureBoxCoinReceiveAlphaAnimation = ValueAnimator.ofFloat(0f, duration)
        treasureBoxCoinReceiveAlphaAnimation.setDuration(duration.toLong())
        treasureBoxCoinReceiveAlphaAnimation.interpolator = LinearInterpolator()
        treasureBoxCoinReceiveAlphaAnimation.addUpdateListener {
            updateTreasureBoxCoinReceiveAlpha(it.animatedValue as Float)
            updateTreasureBoxCoinReceiveTranslate(it.animatedValue as Float)
        }
        mTreasureBoxCoinReceiveAnimatorSet.playTogether(treasureBoxCoinReceiveScaleAnimation, treasureBoxCoinReceiveAlphaAnimation)
        mTreasureBoxCoinReceiveAnimatorSet.addListener(object : Animator.AnimatorListener {
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
        mTreasureBoxCoinReceiveAnimatorSet.start()
    }

    private fun updateTreasureBoxCoinReceiveScale(valueAnimator: Float) {
        mRewardCoinsView.forEachIndexed { index, view ->
            val rewardCoinStartTime = index * 100f
            val rewardCoinEndTime = index * 100f + 200f
            if (valueAnimator in rewardCoinStartTime..rewardCoinEndTime) {
                val scale = calculateAnimatorValue(0.8f, 1f, rewardCoinStartTime, rewardCoinEndTime, valueAnimator)
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
            val rewardCoinStartTime = index * 100f
            val rewardCoinEndTime = index * 100f + 100f
            if (valueAnimator in rewardCoinStartTime..rewardCoinEndTime) {
                val alpha = calculateAnimatorValue(0.8f, 1f, rewardCoinStartTime, rewardCoinEndTime, valueAnimator)
                view.alpha = alpha
            }
        }

    }

    private fun updateTreasureBoxCoinReceiveTranslate(valueAnimator: Float) {
        mRewardCoinsView.forEachIndexed { index, view ->
            val rewardCoinStartTime = index * 200f
            val rewardCoinEndTime = index * 200f + 600f
            if (valueAnimator in rewardCoinStartTime..rewardCoinEndTime) {
                val pointF = calculateCoinTranslate(view, rewardCoinStartTime, rewardCoinEndTime, valueAnimator)
                view.translationX = pointF.x
                view.translationY = pointF.y
            }
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
        currProgress = 1f
        val progressLocation = intArrayOf(0, 0)
        progressCoin.getLocationOnScreen(progressLocation)
        val viewLocation = intArrayOf(0, 0)
        view.getLocationOnScreen(viewLocation)
        val x = (progressLocation[0] - viewLocation[0]) * currProgress + viewLocation[0]
        val y = (progressLocation[1] - viewLocation[1]) * currProgress + viewLocation[1]
        LogUtils.d("calculateCoinTranslate ${view.id} ${progressLocation[0]} ${progressLocation[1]} ${viewLocation[0]} ${viewLocation[1]} ${x} ${y}")
        return PointF(x, y)
    }


    private fun clearAllAnimation() {
        if (mTreasureBoxCoinReceiveAnimatorSet != null) {
            mTreasureBoxCoinReceiveAnimatorSet.cancel()
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
