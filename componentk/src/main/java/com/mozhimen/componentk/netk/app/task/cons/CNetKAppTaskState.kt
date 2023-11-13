package com.mozhimen.componentk.netk.app.task.cons

/**
 * @ClassName CTaskState
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/11/14 0:31
 * @Version 1.0
 */
object CNetKAppTaskState {
    //任务
    const val STATE_TASK_CREATE = 0//STATE_NOT_INSTALLED = 0//未安装 处于未下载，
    const val STATE_TASK_WAIT = 1//STATE_PENDING = 3//等待中
    const val STATE_TASKING = 2
    const val STATE_TASK_PAUSE = 3

    //    const val STATE_TASK_WAIT_CANCEL = 2//STATE_PENDING_CANCELED = 4//取消等待中
    const val STATE_TASK_CANCEL = 7//取消任务
    const val STATE_TASK_SUCCESS = 8//任务成功
    const val STATE_TASK_FAIL = 9//任务失败

    @JvmStatic
    fun isTasking(state: Int): Boolean =
        state in STATE_TASK_WAIT until STATE_TASK_CANCEL
}