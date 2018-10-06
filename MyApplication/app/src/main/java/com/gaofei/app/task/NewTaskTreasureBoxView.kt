package com.gaofei.app.task

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.gaofei.app.CanvasActivity
import com.gaofei.app.R
import com.gaofei.library.utils.LogUtils
import com.yangcong345.android.phone.component.task2.NewTaskContract
import kotlinx.android.synthetic.main.task_treasure_box_icon.view.*

class NewTaskTreasureBoxView : RelativeLayout {
    private val mTaskTreasureBoxCount = 4
    private lateinit var mContext: CanvasActivity
    private lateinit var mPresenter: NewTaskContract.Presenter
    private var mScale: Float = 1f
    private lateinit var mCoinTaskViews: List<View>
    private lateinit var newTaskCoinTasks: List<NewTaskCoinTaskInfo>
    private lateinit var mTreasureBoxViews: List<View>
    private lateinit var mTreasureBoxInfoList: List<NewTaskTreasureBoxInfo>
    private var mTreasureBoxPositionInfoList: ArrayList<TreasureBoxPositionInfo> = ArrayList(mTaskTreasureBoxCount)
    private var isShowCoinTask: Boolean = true

    private lateinit var mTreasureBoxRoutePaint1: Paint
    private lateinit var mTreasureBoxRoutePaint2: Paint
    private lateinit var mTreasureBoxRoutePath1: Path
    private lateinit var mTreasureBoxRoutePath2: Path
    private var mTreasureBoxRouteAnimatedValue: Float = 0f
    private var mTreasureBoxPathAnimator: ValueAnimator? = null
    private var mCoinTaskAnimator: AnimatorSet? = null
    val values = arrayOf(0f, 1f, 2f, 3f, 4f, 5f)

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        mContext = context as CanvasActivity
        adapterScreen()
        initAnimation()

    }

    private fun adapterScreen() {
        mScale = mContext.calculateBackGroundScale()
        this.pivotX = 0f
        this.pivotY = 0f
        this.scaleX = mScale
        this.scaleY = mScale
    }

    private fun initAnimation() {
        setWillNotDraw(false)
        initTreasureBoxRoutePaint1()
        initTreasureBoxRoutePaint2()
        initTreasureBoxRoutePath()
    }


    private fun initTreasureBoxRoutePaint1() {
        mTreasureBoxRoutePaint1 = Paint()
        mTreasureBoxRoutePaint1.style = Paint.Style.STROKE
        mTreasureBoxRoutePaint1.color = Color.parseColor("#FF2AA162")
        mTreasureBoxRoutePaint1.strokeWidth = mContext.dip2px(12f).toFloat()
    }

    private fun initTreasureBoxRoutePaint2() {
        mTreasureBoxRoutePaint2 = Paint()
        mTreasureBoxRoutePaint2.style = Paint.Style.STROKE
        mTreasureBoxRoutePaint2.color = Color.parseColor("#FFFFFFFF")
        mTreasureBoxRoutePaint2.strokeWidth = mContext.dip2px(5f).toFloat()
        mTreasureBoxRoutePaint2.pathEffect = DashPathEffect(floatArrayOf(4f, 4f), 0f)
    }

    private fun initTreasureBoxRoutePath() {
        mTreasureBoxRoutePath1 = Path()
        mTreasureBoxRoutePath2 = Path()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        initView()
    }

    fun setPresenter(presenter: NewTaskContract.Presenter) {
        this.mPresenter = presenter
    }

    private fun initView() {
        mCoinTaskViews = arrayListOf(findViewById(R.id.coinTask1), findViewById(R.id.coinTask2), findViewById(R.id.coinTask3), findViewById(R.id.coinTask4))
        mTreasureBoxViews = arrayListOf(findViewById(R.id.treasureBox1), findViewById(R.id.treasureBox2), findViewById(R.id.treasureBox3), findViewById(R.id.treasureBox4))
    }


    fun bindData(newTaskCoinTasks: List<NewTaskCoinTaskInfo>, newTaskTreasureBoxInfoList: List<NewTaskTreasureBoxInfo>) {
        this.mTreasureBoxInfoList = newTaskTreasureBoxInfoList
        this.newTaskCoinTasks = newTaskCoinTasks
        notifyUpdate()
    }

    private fun notifyUpdate() {
        updateUI()
    }

    private fun updateUI() {
        var isCoinTaskFinished = true
        newTaskCoinTasks.forEachIndexed { index, coinTaskInfo ->
            isCoinTaskFinished = isCoinTaskFinished and coinTaskInfo.isReceiveCoin
            updateCoinTaskView(mCoinTaskViews[index], coinTaskInfo)
        }
        mTreasureBoxInfoList.forEachIndexed { index, treasureBoxGroupTaskInfo ->
            updateTreasureBoxTaskGroupView(mTreasureBoxViews[index], treasureBoxGroupTaskInfo)
        }

        this.isShowCoinTask = !isCoinTaskFinished
        executeAnimation()

    }


    private fun updateCoinTaskView(view: View, newTaskCoinTaskInfo: NewTaskCoinTaskInfo) {
        view.visibility = if (!newTaskCoinTaskInfo.isReceiveCoin) {
            view.setOnClickListener {
                mPresenter.receiveCoinTask(newTaskCoinTaskInfo)
            }
            val textView = view.findViewById<TextView>(R.id.coin)
            textView.text = mContext.getString(R.string.new_task_coin_task_coin_text, newTaskCoinTaskInfo.coinCount)
            View.VISIBLE
        } else View.GONE

    }

    private fun updateTreasureBoxTaskGroupView(view: View, newTaskTreasureBoxInfo: NewTaskTreasureBoxInfo) {
        if (isShowCoinTask) {
            view.visibility = View.GONE
            mPresenter.showDurationTreasureBoxTaskGroup(newTaskTreasureBoxInfo)
        } else {
            view.visibility = View.VISIBLE
            view.setOnClickListener {
                mPresenter.clickTreasureBox(newTaskTreasureBoxInfo)
            }
            val nameView = view.findViewById<TextView>(R.id.name)
            val imageView = view.findViewById<ImageView>(R.id.image)
            nameView.text = mPresenter.getTreasureBoxTaskGroupName(newTaskTreasureBoxInfo)
            imageView.setImageResource(mPresenter.getTreasureBoxTaskGroupImage(newTaskTreasureBoxInfo))
            mPresenter.showDurationTreasureBoxTaskGroup(newTaskTreasureBoxInfo)
        }

    }

    private fun executeAnimation() {
        clearExistAnimation()
        if (isShowCoinTask) {
            executeCoinTaskAnimation()
        } else {
            executeTreasureBoxPathAnimation()
        }
    }


    private fun executeCoinTaskAnimation() {
        val offset = mContext.dip2px(20f).toFloat()
        mCoinTaskAnimator = AnimatorSet()
        mCoinTaskAnimator?.let {
            val coinTaskAnimatorRise = ValueAnimator.ofFloat(0f, offset)
//            val coinTaskAnimatorFall = ValueAnimator.ofFloat(offset, 0f)
            coinTaskAnimatorRise.addUpdateListener {
                val value = it.animatedValue as Float
                LogUtils.d("mCoinTaskAnimator ${value}")
                updateAllCoinTaskTranslate(value)
            }
            coinTaskAnimatorRise.repeatCount = ValueAnimator.INFINITE
            coinTaskAnimatorRise.repeatMode = ValueAnimator.REVERSE
            coinTaskAnimatorRise.duration = 2000
//            coinTaskAnimatorFall.addUpdateListener {
//                val value = it.animatedValue as Float
//                LogUtils.d("mCoinTaskAnimator ${value}")
//                updateAllCoinTaskTranslate(value)
//            }
//            coinTaskAnimatorFall.duration = 2000
//            coinTaskAnimatorFall.repeatCount = ValueAnimator.INFINITE
            it.playSequentially(coinTaskAnimatorRise)
            it.start()
        }
    }

    private fun updateAllCoinTaskTranslate(value: Float) {
        mCoinTaskViews.forEachIndexed { index, view ->
            view.translationY = value
        }
    }

    private fun executeTreasureBoxPathAnimation() {
        mTreasureBoxPathAnimator = ValueAnimator.ofFloat(0f, 1f, 2f, 3f, 4f)
        mTreasureBoxPathAnimator?.let {
            it.addUpdateListener {
                mTreasureBoxRouteAnimatedValue = it.animatedValue as Float
                LogUtils.d("mTreasureBoxRouteAnimatedValue ${mTreasureBoxRouteAnimatedValue}")
//                updateAllTreasureBoxScale(mTreasureBoxRouteAnimatedValue)
                invalidate()
            }
            it.duration = 10000
            it.start()
        }
    }

    private fun updateAllTreasureBoxScale(value: Float) {
        mTreasureBoxViews.forEachIndexed { index, view ->
            updateTreasureBoxScale(index, view, value)
        }
    }

    private fun updateTreasureBoxScale(i: Int, view: View, value: Float) {
        val treasureBoxPositionInfo = mTreasureBoxPositionInfoList[i]
        if (value < values[i]) {
            view.visibility = View.INVISIBLE
            return
        }
        var v = value
        v += 0.8f
        if (v >= values[i + 1]) {
            v = values[i + 1]
        }
        val t = (v - values[i]) / (values[i + 1] - values[i])
        view.visibility = View.VISIBLE
        view.pivotX = treasureBoxPositionInfo.pivotX
        view.pivotY = treasureBoxPositionInfo.pivotY
        val scale = mScale * t
        view.scaleX = scale
        view.scaleY = scale
    }


    private fun clearExistAnimation() {
        if (mCoinTaskAnimator != null) {
            mCoinTaskAnimator?.let {
                if (it.isRunning) {
                    mCoinTaskAnimator?.cancel()
                }
            }
        }
        if (mTreasureBoxPathAnimator != null) {
            mTreasureBoxPathAnimator?.let {
                if (it.isRunning) {
                    it.cancel()
                }
            }
        }

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        initAllTreasureBoxPositionInfo()

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }


    private fun initAllTreasureBoxPositionInfo() {
        mTreasureBoxPositionInfoList.clear()
        mTreasureBoxPositionInfoList?.let {
            for (i in 0 until mTaskTreasureBoxCount) {
                it.add(i, calculateTreasureBoxCirclePositionInfo(i))
            }
        }

    }

    private fun calculateTreasureBoxCirclePositionInfo(i: Int): TreasureBoxPositionInfo {
        val treasureBoxView = mTreasureBoxViews[i]
        val treasureBoxCircleView = treasureBoxView.circle
        val pivotX = treasureBoxCircleView.width.toFloat() / 2
        val pivotY = treasureBoxCircleView.height.toFloat() / 2
        val pointFX = treasureBoxView.left + pivotX
        val pointFY = treasureBoxView.top + pivotY
        val density = context.resources.displayMetrics.density
        LogUtils.d("calculateTreasureBoxCirclePositionInfo ${i} ${treasureBoxView.left / density} ${treasureBoxView.top / density} ${pivotX / density} ${pivotY / density}")
        return TreasureBoxPositionInfo(PointF(pointFX, pointFY), pivotX, pivotY, 1f)
    }


    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        canvas?.let {
            drawAllTreasureBoxCenterPoint(it, mTreasureBoxRouteAnimatedValue, mTaskTreasureBoxCount)
            updateAllTreasureBoxScale(mTreasureBoxRouteAnimatedValue)
        }

    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (i in 0 until mTaskTreasureBoxCount - 1) {
            drawTreasureBoxRoute(canvas, i, mTreasureBoxRouteAnimatedValue)
        }

    }


    private fun drawAllTreasureBoxCenterPoint(canvas: Canvas, value: Float, count: Int) {
        for (i in 0 until count) {
            drawTreasureBoxCenterPoint(canvas, i, value)
        }
    }


    private fun drawTreasureBoxCenterPoint(canvas: Canvas, i: Int, value: Float) {
        val treasureBoxLocationInfo = mTreasureBoxPositionInfoList[i]
        val cx = treasureBoxLocationInfo.pointF.x
        val cy = treasureBoxLocationInfo.pointF.y
        canvas.drawCircle(cx, cy, 20f, mTreasureBoxRoutePaint2)


    }


    private fun drawTreasureBoxRoute(canvas: Canvas, i: Int, value: Float) {
        if (value < values[i]) {
            return
        }
        mTreasureBoxRoutePath1.reset()
        mTreasureBoxRoutePath2.reset()
        val circle1PointF = mTreasureBoxPositionInfoList[i].pointF
        LogUtils.d("drawTreasureBoxRoute ${value} ${circle1PointF.x}")
        mTreasureBoxRoutePath1.moveTo(circle1PointF.x, circle1PointF.y)
        mTreasureBoxRoutePath2.moveTo(circle1PointF.x, circle1PointF.y)
        val circle2PointF = mTreasureBoxPositionInfoList[i + 1].pointF
        val pathInfo = TreasureBoxRouteInfo(i, circle1PointF, circle2PointF)
        val circlePointF = pathInfo.getMovePointF(value)
        mTreasureBoxRoutePath1.lineTo(circlePointF.x, circlePointF.y)
        mTreasureBoxRoutePath2.lineTo(circlePointF.x, circlePointF.y)
        canvas.drawPath(mTreasureBoxRoutePath1, mTreasureBoxRoutePaint1)
        canvas.drawPath(mTreasureBoxRoutePath2, mTreasureBoxRoutePaint2)
    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        clearExistAnimation()
    }


    inner class TreasureBoxRouteInfo(val i: Int, val startPointF: PointF, val endPointF: PointF) {
        init {

        }

        fun getMovePointF(value: Float): PointF {
            // y = ax+b
            var v = value

            if (v >= values[i + 1]) {
                v = values[i + 1]
            }
            val t = (v - values[i]) / (values[i + 1] - values[i])
            val a = (endPointF.y - startPointF.y) / (endPointF.x - startPointF.x)
            val b = startPointF.y - a * startPointF.x
            val x = startPointF.x + (endPointF.x - startPointF.x) * t
            val y = a * x + b
            return PointF(x, y)
        }
    }

    data class TreasureBoxPositionInfo(val pointF: PointF, var pivotX: Float, var pivotY: Float, val scale: Float)


}
