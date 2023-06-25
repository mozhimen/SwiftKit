package com.mozhimen.basick.chaink.mos

import android.util.SparseArray
import com.mozhimen.basick.chaink.bases.BaseChainKTask
import com.mozhimen.basick.chaink.annors.AChainKState

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
class MChainKTaskRuntimeInfo(val chainKTask: BaseChainKTask) {
    val stateTime = SparseArray<Long>()
    var threadName: String? = null
    var isBlockTask = false

    fun setStateTime(@AChainKState state: Int, time: Long) {
        stateTime.put(state, time)
    }

    fun isSameTask(task: BaseChainKTask?): Boolean {
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