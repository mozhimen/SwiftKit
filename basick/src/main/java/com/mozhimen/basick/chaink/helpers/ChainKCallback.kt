package com.mozhimen.basick.chaink.helpers

import android.util.Log
import androidx.annotation.CallSuper
import com.mozhimen.basick.BuildConfig
import com.mozhimen.basick.chaink.mos.ChainKNode
import com.mozhimen.basick.chaink.mos.ChainKNodeGroup
import com.mozhimen.basick.chaink.helpers.ChainKRuntime.getTaskKRuntimeInfo
import com.mozhimen.basick.chaink.annors.AChainKState
import com.mozhimen.basick.chaink.commons.IChainKListener
import com.mozhimen.basick.elemk.cons.CMsg
import com.mozhimen.basick.utilk.bases.BaseUtilK

/**
 * @ClassName TaskKRuntimeListener
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/29 15:50
 * @Version 1.0
 */
class ChainKCallback : IChainKListener, BaseUtilK() {
    @CallSuper
    override fun onStart(node: ChainKNode) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "ITaskKRuntimeListener id ${node.id} method -- onStart --")
        }
    }

    override fun onRunning(node: ChainKNode) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "ITaskKRuntimeListener id ${node.id} method -- onRunning --")
        }
    }

    override fun onFinished(node: ChainKNode) {
        logTaskKRuntimeInfo(node)
    }

    private fun logTaskKRuntimeInfo(node: ChainKNode) {
        val taskKRuntimeInfo = getTaskKRuntimeInfo(node.id) ?: return
        val startTime = taskKRuntimeInfo.stateTime[AChainKState.START]
        val runningTime = taskKRuntimeInfo.stateTime[AChainKState.RUNNING]
        val finishedTime = taskKRuntimeInfo.stateTime[AChainKState.FINISHED]

        val builder = StringBuilder()
        builder.append(CMsg.LINE_BREAK)
        builder.append(TAG)
        builder.append(CMsg.LINE_BREAK)

        builder.append(CMsg.LINE_BREAK)
        builder.append(CMsg.PART_LINE)
        builder.append(if (node is ChainKNodeGroup) "TaskKGroup" else "taskK ${node.id} -- onFinished --")
        builder.append(CMsg.PART_LINE)

        addTaskInfoLineInfo(builder, "依赖任务", getTaskDependenciesInfo(node))
        addTaskInfoLineInfo(builder, "是否是阻塞任务", taskKRuntimeInfo.isBlockTask.toString())
        addTaskInfoLineInfo(builder, "线程名称", taskKRuntimeInfo.threadName!!)
        addTaskInfoLineInfo(builder, "开始执行时刻", startTime.toString() + "ms")
        addTaskInfoLineInfo(builder, "等待执行耗时", (runningTime - startTime).toString() + "ms")
        addTaskInfoLineInfo(builder, "任务执行耗时", (finishedTime - runningTime).toString() + "ms")
        addTaskInfoLineInfo(builder, "任务结束时刻", finishedTime.toString() + "ms")
        builder.append(CMsg.PART_LINE)
        builder.append(CMsg.PART_LINE)
        builder.append(CMsg.LINE_BREAK)
        builder.append(CMsg.LINE_BREAK)
        if (BuildConfig.DEBUG) {
            Log.e(TAG, "logTaskKRuntimeInfo builder $builder")
        }
    }

    private fun getTaskDependenciesInfo(node: ChainKNode): String {
        val builder = StringBuilder()
        for (s in node.dependTasksName) {
            builder.append("$s ")
        }
        return builder.toString()
    }

    private fun addTaskInfoLineInfo(
        builder: StringBuilder,
        key: String,
        value: String
    ) {
        builder.append("I $key:$value")
    }
}