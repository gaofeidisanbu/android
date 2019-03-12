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

class CanvasActivity : BaseAct() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_canvas)
        chatView.setOnClickListener {
            chatView.start(null)
        }
    }





}
