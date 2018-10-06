package com.gaofei.app.task

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
    private lateinit var mNewTaskTreasureBoxInfoList: List<NewTaskTreasureBoxInfo>
    private var isShowCoinTask: Boolean = true

    private lateinit var mPathPaint1: Paint
    private lateinit var mPathPaint2: Paint
    private var mTreasureBoxPathAnimatedValue: Float = 0f
    private var mTreasureBoxPathValueAnimator: ValueAnimator? = null
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
        initPathPaint1()
        initPathPaint2()
    }

    private fun initPathPaint1() {
        mPathPaint1 = Paint()
        mPathPaint1.style = Paint.Style.STROKE
        mPathPaint1.color = Color.parseColor("#FF2AA162")
        mPathPaint1.strokeWidth = mContext.dip2px(12f).toFloat()
    }

    private fun initPathPaint2() {
        mPathPaint2 = Paint()
        mPathPaint2.style = Paint.Style.STROKE
        mPathPaint2.color = Color.parseColor("#FFFFFFFF")
        mPathPaint2.strokeWidth = mContext.dip2px(5f).toFloat()
        mPathPaint2.pathEffect = DashPathEffect(floatArrayOf(4f, 4f), 0f)
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
        this.mNewTaskTreasureBoxInfoList = newTaskTreasureBoxInfoList
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
        mNewTaskTreasureBoxInfoList.forEachIndexed { index, treasureBoxGroupTaskInfo ->
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

    }

    private fun executeTreasureBoxPathAnimation() {
        if (mTreasureBoxPathValueAnimator != null) {
            mTreasureBoxPathValueAnimator?.let {
                if (it.isRunning) {
                    mTreasureBoxPathValueAnimator?.cancel()
                }
            }
        }

        mTreasureBoxPathValueAnimator = ValueAnimator.ofFloat(0f, 1f, 2f, 3f, 4f)
        mTreasureBoxPathValueAnimator?.let {
            it.addUpdateListener {
                mTreasureBoxPathAnimatedValue = it.animatedValue as Float
                updateTreasureBoxScale()
                invalidate()
            }
            it.duration = 10000
            it.start()
        }
    }

    private fun updateTreasureBoxScale() {

    }


    private fun clearExistAnimation() {
        if (mTreasureBoxPathValueAnimator != null) {
            mTreasureBoxPathValueAnimator?.let {
                if (it.isRunning) {
                    it.cancel()
                }
            }
        }

    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        canvas?.let {
            drawTask(it, mTreasureBoxPathAnimatedValue, mTaskTreasureBoxCount)
        }

    }


    override fun onDraw(canvas: Canvas) {
        for (i in 0 until mTaskTreasureBoxCount - 1) {
            drawTaskPath(canvas, i, mTreasureBoxPathAnimatedValue)
        }

        super.onDraw(canvas)
    }


    private fun drawTask(canvas: Canvas, value: Float, count: Int) {
        for (i in 0 until count) {
            drawTaskTreasureBox(canvas, i, value)
        }
    }


    private fun drawTaskTreasureBox(canvas: Canvas, i: Int, value: Float) {
        val isLeft = (i + 1) % 2 != 0
        val treasureBoxLocationInfo = calculateTreasureBoxCircleLocationInfo(i)
        val cx = treasureBoxLocationInfo.pointF.x
        val cy = treasureBoxLocationInfo.pointF.y
        canvas.drawCircle(cx, cy, 20f, mPathPaint2)
        val circlePointF = calculateTreasureBoxCircleLocationInfo(i).pointF
        if (value < values[i]) {
            mTreasureBoxViews[i]?.let {
                it.visibility = View.INVISIBLE
            }
            return
        }
        var v = value
        v += 0.6f
        if (v >= values[i + 1]) {
            v = values[i + 1]
        }
        val t = (v - values[i]) / (values[i + 1] - values[i])
        mTreasureBoxViews[i]?.let {
            it.visibility = View.VISIBLE
            it.pivotX = treasureBoxLocationInfo.pivotX
            it.pivotY = treasureBoxLocationInfo.pivotY
            val scale = mScale * t
            it.scaleX = scale
            it.scaleY = scale
        }


    }


    private fun drawTaskPath(canvas: Canvas, i: Int, value: Float) {
        if (value < values[i]) {
            return
        }
        val path1 = Path()
        val path2 = Path()
        val circle1PointF = calculateTreasureBoxCircleLocationInfo(i).pointF
        path1.moveTo(circle1PointF.x, circle1PointF.y)
        path2.moveTo(circle1PointF.x, circle1PointF.y)
        val circle2PointF = calculateTreasureBoxCircleLocationInfo(i + 1).pointF
        val pathInfo = PathInfo(i, circle1PointF, circle2PointF)
        val circlePointF = pathInfo.getMovePointF(value)
        path1.lineTo(circlePointF.x, circlePointF.y)
        path2.lineTo(circlePointF.x, circlePointF.y)
        canvas.drawPath(path1, mPathPaint1)
        canvas.drawPath(path2, mPathPaint2)
    }

    private fun calculateTreasureBoxCircleLocationInfo(i: Int): TreasureBoxLocationInfo {
        val treasureBoxView = mTreasureBoxViews[i]
        val treasureBoxCircleView = treasureBoxView.circle
        val pivotX = treasureBoxCircleView.width.toFloat() / 2
        val pivotY = treasureBoxCircleView.height.toFloat() / 2
        val pointFX = treasureBoxView.left + pivotX
        val pointFY = treasureBoxView.top + pivotY
        val density = context.getResources().getDisplayMetrics().density
        LogUtils.d("calculateTreasureBoxCircleLocationInfo ${i} ${treasureBoxView.left / density} ${treasureBoxView.top / density} ${pivotX / density} ${pivotY / density}")
        return TreasureBoxLocationInfo(PointF(pointFX, pointFY), pivotX, pivotY, 1f)
    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        clearExistAnimation()
    }


    inner class PathInfo(val i: Int, val startPointF: PointF, val endPointF: PointF) {
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

    data class TreasureBoxLocationInfo(val pointF: PointF, var pivotX: Float, var pivotY: Float, val scale: Float)


}
