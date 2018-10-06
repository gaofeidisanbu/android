package com.yangcong345.android.phone.component.task2

import com.gaofei.app.task.NewTaskCoinTaskInfo
import com.gaofei.app.task.NewTaskTreasureBoxInfo


interface NewTaskContract {
    interface View : BaseView<Presenter> {
        fun onBack()
        fun updateTaskProgress(pair: Pair<Int, Int>)
        fun showTaskTip(message: String, type: Int)
        fun showTreasureBoxUI(newTaskCoinTaskInfoList: ArrayList<NewTaskCoinTaskInfo>, newTaskTreasureBoxInfoList: ArrayList<NewTaskTreasureBoxInfo>)
//        fun getRXActivity(): RxActivity
//        fun showThemeDetailUI(chapterId: String, theme: NewTaskThemeRequest.Theme)
    }

    interface Presenter : BasePresenter {
        fun receiveCoinTask(newTaskCoinTaskInfo: NewTaskCoinTaskInfo)
        fun completeCoinTask()
        fun requestTheme()
        fun clickTreasureBox(newTaskTreasureBoxInfo: NewTaskTreasureBoxInfo)

        fun showDurationTreasureBoxTaskGroup(newTaskTreasureBoxInfo: NewTaskTreasureBoxInfo)
        fun getTreasureBoxTaskGroupName(newTaskTreasureBoxInfo: NewTaskTreasureBoxInfo): String
        fun getTreasureBoxTaskGroupImage(newTaskTreasureBoxInfo: NewTaskTreasureBoxInfo): Int
    }
}
