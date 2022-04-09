package com.mozhimen.basicsk.taskk.commons

import android.util.Log
import com.mozhimen.basicsk.BuildConfig
import com.mozhimen.basicsk.taskk.TaskK
import com.mozhimen.basicsk.taskk.TaskKGroup
import com.mozhimen.basicsk.taskk.TaskKRuntime.getTaskKRuntimeInfo
import com.mozhimen.basicsk.taskk.annors.TaskKState

/**
 * @ClassName TaskKRuntimeListener
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/29 15:50
 * @Version 1.0
 */
class TaskKRuntimeListener : TaskKListener {
    override fun onStart(taskK: TaskK) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, taskK.id + METHOD_START)
        }
    }

    override fun onRunning(taskK: TaskK) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, taskK.id + METHOD_RUNNING)
        }
    }

    override fun onFinished(taskK: TaskK) {
        logTaskKRuntimeInfo(taskK)
    }

    private fun logTaskKRuntimeInfo(taskK: TaskK) {
        val taskKRuntimeInfo = getTaskKRuntimeInfo(taskK.id) ?: return
        val startTime = taskKRuntimeInfo.stateTime[TaskKState.START]
        val runningTime = taskKRuntimeInfo.stateTime[TaskKState.RUNNING]
        val finishedTime = taskKRuntimeInfo.stateTime[TaskKState.FINISHED]

        val builder = StringBuilder()
        builder.append(WRAPPER)
        builder.append(TAG)
        builder.append(WRAPPER)

        builder.append(WRAPPER)
        builder.append(HALF_LINE)
        builder.append(if (taskK is TaskKGroup) "TaskKGroup" else "taskK ${taskK.id} " + METHOD_FINISHED)
        builder.append(HALF_LINE)

        addTaskInfoLineInfo(builder, DEPENDENCIES, getTaskDependenciesInfo(taskK))
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
            Log.e(TAG, builder.toString())
        }
    }

    private fun getTaskDependenciesInfo(taskK: TaskK): String {
        val builder = StringBuilder()
        for (s in taskK.dependTasksName) {
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