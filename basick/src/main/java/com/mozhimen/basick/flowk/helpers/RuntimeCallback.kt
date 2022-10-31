package com.mozhimen.basick.flowk.helpers

import android.util.Log
import com.mozhimen.basick.BuildConfig
import com.mozhimen.basick.flowk.FlowKNode
import com.mozhimen.basick.flowk.FlowKNodeGroup
import com.mozhimen.basick.flowk.FlowKRuntime.getTaskKRuntimeInfo
import com.mozhimen.basick.flowk.annors.AFlowKState
import com.mozhimen.basick.flowk.commons.IFlowKListener

/**
 * @ClassName TaskKRuntimeListener
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/29 15:50
 * @Version 1.0
 */
class RuntimeCallback : IFlowKListener {
    override fun onStart(flowKNode: FlowKNode) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "ITaskKRuntimeListener id ${flowKNode.id} method $METHOD_START")
        }
    }

    override fun onRunning(flowKNode: FlowKNode) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "ITaskKRuntimeListener id ${flowKNode.id} method $METHOD_RUNNING")
        }
    }

    override fun onFinished(flowKNode: FlowKNode) {
        logTaskKRuntimeInfo(flowKNode)
    }

    private fun logTaskKRuntimeInfo(flowKNode: FlowKNode) {
        val taskKRuntimeInfo = getTaskKRuntimeInfo(flowKNode.id) ?: return
        val startTime = taskKRuntimeInfo.stateTime[AFlowKState.START]
        val runningTime = taskKRuntimeInfo.stateTime[AFlowKState.RUNNING]
        val finishedTime = taskKRuntimeInfo.stateTime[AFlowKState.FINISHED]

        val builder = StringBuilder()
        builder.append(WRAPPER)
        builder.append(TAG)
        builder.append(WRAPPER)

        builder.append(WRAPPER)
        builder.append(HALF_LINE)
        builder.append(if (flowKNode is FlowKNodeGroup) "TaskKGroup" else "taskK ${flowKNode.id} " + METHOD_FINISHED)
        builder.append(HALF_LINE)

        addTaskInfoLineInfo(builder, DEPENDENCIES, getTaskDependenciesInfo(flowKNode))
        addTaskInfoLineInfo(builder, IS_BLOCK_TASK, taskKRuntimeInfo.isBlockTask.toString())
        addTaskInfoLineInfo(builder, THREAD_NAME, taskKRuntimeInfo.threadName!!)
        addTaskInfoLineInfo(builder, START_TIME, startTime.toString() + "ms")
        addTaskInfoLineInfo(builder, WAITING_TIME, (runningTime - startTime).toString() + "ms")
        addTaskInfoLineInfo(builder, TASK_CONSUME, (finishedTime - runningTime).toString() + "ms")
        addTaskInfoLineInfo(builder, FINISHED_TIME, finishedTime.toString() + "ms")
        builder.append(HALF_LINE)
        builder.append(HALF_LINE)
        builder.append(WRAPPER)
        builder.append(WRAPPER)
        if (BuildConfig.DEBUG) {
            Log.e(TAG, "logTaskKRuntimeInfo builder $builder")
        }
    }

    private fun getTaskDependenciesInfo(flowKNode: FlowKNode): String {
        val builder = StringBuilder()
        for (s in flowKNode.dependTasksName) {
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

    companion object {
        const val TAG: String = "TaskKRuntimeListener"
        const val METHOD_START = "-- onStart --"
        const val METHOD_RUNNING = "-- onRunning --"
        const val METHOD_FINISHED = "-- onFinished --"

        const val DEPENDENCIES = "依赖任务"
        const val THREAD_NAME = "线程名称"
        const val START_TIME = "开始执行时刻"
        const val WAITING_TIME = "等待执行耗时"
        const val TASK_CONSUME = "任务执行耗时"
        const val IS_BLOCK_TASK = "是否是阻塞任务"
        const val FINISHED_TIME = "任务结束时刻"
        const val WRAPPER = "\n"
        const val HALF_LINE = "==============================="
    }
}