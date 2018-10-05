package com.yangcong345.android.phone.component.task2

import com.gaofei.app.R
import com.gaofei.app.task.NewTaskCoinTaskInfo
import com.gaofei.app.task.NewTaskTreasureBoxInfo


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
