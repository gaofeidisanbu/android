package com.yangcong345.android.phone.component.task2

import com.gaofei.app.R
import com.gaofei.app.task.NewTaskCoinTaskInfo
import com.gaofei.app.task.NewTaskTreasureBoxInfo
import com.gaofei.app.task.NewTaskTreasureBoxTaskInfo


class NewTaskPresenter(val view: NewTaskContract.View) : NewTaskContract.Presenter {
    override fun completeCoinTask() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun receiveCoinTask(newTaskCoinTaskInfo: NewTaskCoinTaskInfo) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clickTreasureBox(newTaskTreasureBoxInfo: NewTaskTreasureBoxInfo) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun requestTheme() {
//        val currChapterId = LearningCourseMeta.getLastChapterId()
//        val currSubjectId = LearningCourseMeta.getLastSubjectStage().first
//        val currStageId = LearningCourseMeta.getLastSubjectStage().second
//        val currPublisherId = LearningCourseMeta.getLastPublisherId(currSubjectId, currStageId)
//        val currSemesterId = LearningCourseMeta.getLastSemesterId(currSubjectId, currStageId)
//        NewTaskThemeRequest(currSubjectId, currStageId, currPublisherId, currSemesterId, currChapterId, "analysis")
//                .asObservable()
//                .subscribe {
//                    if (it.size > 0) {
//                        view.showThemeDetailUI(currChapterId, it[0])
//
//                    } else {
//
//                    }
//                }
    }


    override fun start() {
//        val ob1 = GetCoinTaskV2Status().strategy(Strategy.REMOTE_ONLY).asObservable()
//        val ob2 = GetTaskV2CategoryOnce().strategy(Strategy.REMOTE_ONLY).asObservable()
//        Observable.zip(ob1, ob2, BiFunction<Map<String, Any>, Map<String, Any>, Pair<Map<String, Any>, Map<String, Any>>> { t1, t2 ->
//            Pair(t1, t2)
//        }).compose(view.getRXActivity().bindToLifecycle()).subscribe {
        view.updateTaskProgress(Pair(50, 100))

        val newTaskCoinTaskInfo1 = NewTaskCoinTaskInfo(0, 100, true)
        val newTaskCoinTaskInfo2 = NewTaskCoinTaskInfo(1, 100, true)
        val newTaskCoinTaskInfo3 = NewTaskCoinTaskInfo(2, 100, true)
        val newTaskCoinTaskInfo4 = NewTaskCoinTaskInfo(3, 100, true)
        val newTaskCoinTaskInfoList = arrayListOf<NewTaskCoinTaskInfo>(newTaskCoinTaskInfo1, newTaskCoinTaskInfo2, newTaskCoinTaskInfo3, newTaskCoinTaskInfo4)
        val newTaskTreasureBoxTaskInfoList1 = arrayListOf(NewTaskTreasureBoxTaskInfo(0, "1", "已解锁", System.currentTimeMillis(), "concept"), NewTaskTreasureBoxTaskInfo(1, "1", "已解锁", System.currentTimeMillis(), "concept"))
        val newTaskTreasureBoxInfo1 = NewTaskTreasureBoxInfo(0, true, newTaskTreasureBoxTaskInfoList1)

        val newTaskTreasureBoxTaskInfoList2 = arrayListOf(NewTaskTreasureBoxTaskInfo(0, "1", "已解锁", System.currentTimeMillis(), "concept"), NewTaskTreasureBoxTaskInfo(1, "1", "已解锁", System.currentTimeMillis(), "concept"))
        val newTaskTreasureBoxInfo2 = NewTaskTreasureBoxInfo(1, true, newTaskTreasureBoxTaskInfoList2)

        val newTaskTreasureBoxTaskInfoList3 = arrayListOf(NewTaskTreasureBoxTaskInfo(0, "1", "已解锁", System.currentTimeMillis(), "concept"), NewTaskTreasureBoxTaskInfo(1, "1", "已解锁", System.currentTimeMillis(), "concept"))
        val newTaskTreasureBoxInfo3 = NewTaskTreasureBoxInfo(2, true, newTaskTreasureBoxTaskInfoList3)

        val newTaskTreasureBoxTaskInfoList4 = arrayListOf(NewTaskTreasureBoxTaskInfo(0, "1", "已解锁", System.currentTimeMillis(), "concept"), NewTaskTreasureBoxTaskInfo(1, "1", "已解锁", System.currentTimeMillis(), "concept"))
        val newTaskTreasureBoxInfo4 = NewTaskTreasureBoxInfo(3, true, newTaskTreasureBoxTaskInfoList4)

        val newTaskTreasureBoxInfoList = arrayListOf(newTaskTreasureBoxInfo1, newTaskTreasureBoxInfo2, newTaskTreasureBoxInfo3, newTaskTreasureBoxInfo4)

        view.showTreasureBoxUI(newTaskCoinTaskInfoList, newTaskTreasureBoxInfoList)
//        }
    }

    override fun showDurationTreasureBoxTaskGroup(newTaskTreasureBoxInfo: NewTaskTreasureBoxInfo) {
        if (newTaskTreasureBoxInfo.index == 1) {

        }
    }

    override fun getTreasureBoxTaskGroupName(newTaskTreasureBoxInfo: NewTaskTreasureBoxInfo): String {
        return "已解锁"
    }

    override fun getTreasureBoxTaskGroupImage(newTaskTreasureBoxInfo: NewTaskTreasureBoxInfo): Int {
        return R.drawable.task_box_lock_color
    }

}
