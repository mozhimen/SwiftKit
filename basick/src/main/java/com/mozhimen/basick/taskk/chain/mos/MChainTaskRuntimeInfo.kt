package com.mozhimen.basick.taskk.chain.mos

import android.util.SparseArray
import com.mozhimen.basick.taskk.chain.bases.BaseChainTask
import com.mozhimen.basick.taskk.chain.annors.AChainState

/**
 * @ClassName TaskKRuntimeInfo
 * @Description
 * 本用以记录每一个task实例的运行时的信息的封装
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/30 16:16
 * @Version 1.0
 */
/**
 *
 */
class MChainTaskRuntimeInfo(val chainKTask: BaseChainTask) {
    val stateTime = SparseArray<Long>()
    var threadName: String? = null
    var isBlockTask = false

    fun setStateTime(@AChainState state: Int, time: Long) {
        stateTime.put(state, time)
    }

    fun isSameTask(task: BaseChainTask?): Boolean {
        return task != null && this.chainKTask == task
    }

    override fun toString(): String {
        return "TaskRuntimeInfo{" +
                "stateTime=" + stateTime +
                ", isBlockTask=" + isBlockTask +
                ", task=" + chainKTask +
                ", threadName='" + threadName + '\'' +
                '}'
    }
}