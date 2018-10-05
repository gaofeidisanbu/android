package com.gaofei.app

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.RelativeLayout

import com.gaofei.library.base.BaseAct
import com.gaofei.library.utils.ToastManager
import com.yangcong345.android.phone.component.task2.NewTaskContract
import com.yangcong345.android.phone.component.task2.NewTaskPresenter
import kotlinx.android.synthetic.main.act_canvas.*

class CanvasActivity : BaseAct() , NewTaskContract.View {
    override fun onBack() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateTaskProgress(pair: Pair<Int, Int>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showTaskTip(message: String, type: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setPresenter(t: NewTaskContract.Presenter) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private lateinit var mPresenter: NewTaskContract.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_canvas)
        mPresenter = NewTaskPresenter(this)
        taskTreasureBoxView.setPresenter(mPresenter)
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
