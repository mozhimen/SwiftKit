package com.mozhimen.basicsk.taskk.mos

import android.util.SparseArray
import com.mozhimen.basicsk.taskk.TaskK
import com.mozhimen.basicsk.taskk.annors.TaskKState

/**
 * @ClassName TaskKRuntimeInfo
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/30 16:16
 * @Version 1.0
 */
/**
 * 本用以记录每一个task实例的运行时的信息的封装
 */
class TaskKRuntimeInfo(val taskK: TaskK) {
    val stateTime = SparseArray<Long>()
    var isBlockTask = false
    var threadName: String? = null

    fun setStateTime(@TaskKState state: Int, time: Long) {
        stateTime.put(state, time)
    }

    fun isSameTask(task: TaskK?): Boolean {
        return task != null && this.taskK === task
    }

    override fun toString(): String {
        return "TaskRuntimeInfo{" +
                "stateTime=" + stateTime +
                ", isBlockTask=" + isBlockTask +
                ", task=" + taskK +
                ", threadName='" + threadName + '\'' +
                '}'
    }
}