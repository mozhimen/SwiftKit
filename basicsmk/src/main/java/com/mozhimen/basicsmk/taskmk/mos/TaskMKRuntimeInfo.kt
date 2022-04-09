package com.mozhimen.basicsmk.taskmk.mos

import android.util.SparseArray
import com.mozhimen.basicsmk.taskmk.TaskMK
import com.mozhimen.basicsmk.taskmk.annors.TaskMKState

/**
 * @ClassName TaskMKRuntimeInfo
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/30 16:16
 * @Version 1.0
 */
/**
 * 本用以记录每一个task实例的运行时的信息的封装
 */
class TaskMKRuntimeInfo(val taskMK: TaskMK) {
    val stateTime = SparseArray<Long>()
    var isBlockTask = false
    var threadName: String? = null

    fun setStateTime(@TaskMKState state: Int, time: Long) {
        stateTime.put(state, time)
    }

    fun isSameTask(task: TaskMK?): Boolean {
        return task != null && this.taskMK === task
    }

    override fun toString(): String {
        return "TaskRuntimeInfo{" +
                "stateTime=" + stateTime +
                ", isBlockTask=" + isBlockTask +
                ", task=" + taskMK +
                ", threadName='" + threadName + '\'' +
                '}'
    }
}