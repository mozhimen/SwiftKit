package com.mozhimen.basick.chaink.mos

import android.util.SparseArray
import com.mozhimen.basick.chaink.annors.AChainKState

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
class MNodeRuntimeInfo(val chainKNode: ChainKNode) {
    val stateTime = SparseArray<Long>()
    var isBlockTask = false
    var threadName: String? = null

    fun setStateTime(@AChainKState state: Int, time: Long) {
        stateTime.put(state, time)
    }

    fun isSameTask(task: ChainKNode?): Boolean {
        return task != null && this.chainKNode == task
    }

    override fun toString(): String {
        return "TaskRuntimeInfo{" +
                "stateTime=" + stateTime +
                ", isBlockTask=" + isBlockTask +
                ", task=" + chainKNode +
                ", threadName='" + threadName + '\'' +
                '}'
    }
}