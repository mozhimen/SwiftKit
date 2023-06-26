package com.mozhimen.basick.chaink.helpers

import com.mozhimen.basick.BuildConfig
import com.mozhimen.basick.chaink.temps.ChainKTaskGroup
import com.mozhimen.basick.chaink.bases.BaseChainKTask
import com.mozhimen.basick.chaink.annors.AChainKState
import com.mozhimen.basick.chaink.commons.IChainKListener
import com.mozhimen.basick.chaink.helpers.ChainKRuntime.getTaskRuntimeInfo
import com.mozhimen.basick.elemk.cons.CMsg
import com.mozhimen.basick.utilk.android.util.dt
import com.mozhimen.basick.utilk.android.util.wt
import com.mozhimen.basick.utilk.bases.BaseUtilK

/**
 * @ClassName TaskKRuntimeListener
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/29 15:50
 * @Version 1.0
 */
class ChainKImpl : IChainKListener, BaseUtilK() {
    override fun onStart(node: BaseChainKTask) {
        if (BuildConfig.DEBUG) "ITaskKRuntimeListener id ${node.id} method -- onStart --".dt(TAG)
    }

    override fun onRunning(node: BaseChainKTask) {
        if (BuildConfig.DEBUG) "ITaskKRuntimeListener id ${node.id} method -- onRunning --".dt(TAG)
    }

    override fun onFinished(node: BaseChainKTask) {
        if (BuildConfig.DEBUG) logTaskKRuntimeInfo(node)
    }

    //////////////////////////////////////////////////////////////////////////////////

    private fun logTaskKRuntimeInfo(node: BaseChainKTask) {
        val taskKRuntimeInfo = getTaskRuntimeInfo(node.id) ?: return
        val startTime = taskKRuntimeInfo.stateTime[AChainKState.START]
        val runningTime = taskKRuntimeInfo.stateTime[AChainKState.RUNNING]
        val finishedTime = taskKRuntimeInfo.stateTime[AChainKState.FINISHED]

        //////////////////////////////////////////////////////////////////////////////////

        val builder = StringBuilder()
        builder.append(CMsg.LINE_BREAK)
        builder.append(TAG)
        builder.append(CMsg.LINE_BREAK)

        //////////////////////////////////////////////////////////////////////////////////

        builder.append(CMsg.LINE_BREAK)
        builder.append(CMsg.PART_LINE_BIAS)
        builder.append(if (node is ChainKTaskGroup) "TaskKGroup" else "taskK ${node.id} -- onFinished --")
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

    private fun appendTaskDependenciesInfo(node: BaseChainKTask): String {
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