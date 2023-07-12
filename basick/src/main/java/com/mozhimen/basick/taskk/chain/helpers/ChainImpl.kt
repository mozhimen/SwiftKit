package com.mozhimen.basick.taskk.chain.helpers

import com.mozhimen.basick.BuildConfig
import com.mozhimen.basick.taskk.chain.temps.ChainTaskGroup
import com.mozhimen.basick.taskk.chain.bases.BaseChainTask
import com.mozhimen.basick.taskk.chain.annors.AChainState
import com.mozhimen.basick.taskk.chain.commons.IChainListener
import com.mozhimen.basick.taskk.chain.helpers.ChainRuntime.getTaskRuntimeInfo
import com.mozhimen.basick.elemk.cons.CMsg
import com.mozhimen.basick.utilk.android.util.dt
import com.mozhimen.basick.utilk.android.util.wt
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.bases.IUtilK

/**
 * @ClassName TaskKRuntimeListener
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/29 15:50
 * @Version 1.0
 */
class ChainImpl : IChainListener, IUtilK {
    override fun onStart(node: BaseChainTask) {
        if (BuildConfig.DEBUG) "ITaskKRuntimeListener id ${node.id} method -- onStart --".dt(TAG)
    }

    override fun onRunning(node: BaseChainTask) {
        if (BuildConfig.DEBUG) "ITaskKRuntimeListener id ${node.id} method -- onRunning --".dt(TAG)
    }

    override fun onFinished(node: BaseChainTask) {
        if (BuildConfig.DEBUG) logTaskKRuntimeInfo(node)
    }

    //////////////////////////////////////////////////////////////////////////////////

    private fun logTaskKRuntimeInfo(node: BaseChainTask) {
        val taskKRuntimeInfo = getTaskRuntimeInfo(node.id) ?: return
        val startTime = taskKRuntimeInfo.stateTime[AChainState.START]
        val runningTime = taskKRuntimeInfo.stateTime[AChainState.RUNNING]
        val finishedTime = taskKRuntimeInfo.stateTime[AChainState.FINISHED]

        //////////////////////////////////////////////////////////////////////////////////

        val builder = StringBuilder()
        builder.append(CMsg.LINE_BREAK)
        builder.append(TAG)
        builder.append(CMsg.LINE_BREAK)

        //////////////////////////////////////////////////////////////////////////////////

        builder.append(CMsg.LINE_BREAK)
        builder.append(CMsg.PART_LINE_BIAS)
        builder.append(if (node is ChainTaskGroup) "TaskKGroup" else "taskK ${node.id} -- onFinished --")
        builder.append(CMsg.PART_LINE_BIAS)

        //////////////////////////////////////////////////////////////////////////////////

        appendTaskInfoLineInfo(builder, "依赖任务", appendTaskDependenciesInfo(node))
        appendTaskInfoLineInfo(builder, "是否是阻塞任务", taskKRuntimeInfo.isBlockTask.toString())
        appendTaskInfoLineInfo(builder, "线程名称", taskKRuntimeInfo.threadName!!)
        appendTaskInfoLineInfo(builder, "开始执行时刻", startTime.toString() + "ms")
        appendTaskInfoLineInfo(builder, "等待执行耗时", (runningTime - startTime).toString() + "ms")
        appendTaskInfoLineInfo(builder, "任务执行耗时", (finishedTime - runningTime).toString() + "ms")
        appendTaskInfoLineInfo(builder, "任务结束时刻", finishedTime.toString() + "ms")

        //////////////////////////////////////////////////////////////////////////////////

        builder.append(CMsg.PART_LINE_BIAS)
        builder.append(CMsg.PART_LINE_BIAS)
        builder.append(CMsg.LINE_BREAK)
        builder.append(CMsg.LINE_BREAK)

        //////////////////////////////////////////////////////////////////////////////////

        if (BuildConfig.DEBUG) "logTaskKRuntimeInfo builder $builder".wt(TAG)
    }

    private fun appendTaskDependenciesInfo(node: BaseChainTask): String {
        val builder = StringBuilder()
        for (s in node.dependTasksName) {
            builder.append("$s ")
        }
        return builder.toString()
    }

    private fun appendTaskInfoLineInfo(builder: StringBuilder, key: String, value: String) {
        builder.append("I $key:$value")
    }
}