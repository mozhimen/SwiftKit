package com.mozhimen.basick.flowk.mos

import android.util.SparseArray
import com.mozhimen.basick.flowk.FlowKNode
import com.mozhimen.basick.flowk.annors.FlowKState

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
class FlowKNodeRuntimeInfo(val flowKNode: FlowKNode) {
    val stateTime = SparseArray<Long>()
    var isBlockTask = false
    var threadName: String? = null

    fun setStateTime(@FlowKState state: Int, time: Long) {
        stateTime.put(state, time)
    }

    fun isSameTask(task: FlowKNode?): Boolean {
        return task != null && this.flowKNode == task
    }

    override fun toString(): String {
        return "TaskRuntimeInfo{" +
                "stateTime=" + stateTime +
                ", isBlockTask=" + isBlockTask +
                ", task=" + flowKNode +
                ", threadName='" + threadName + '\'' +
                '}'
    }
}