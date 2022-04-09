package com.mozhimen.basicsmk.taskmk.commons

import android.util.Log
import com.mozhimen.basicsmk.BuildConfig
import com.mozhimen.basicsmk.taskmk.TaskMK
import com.mozhimen.basicsmk.taskmk.TaskMKGroup
import com.mozhimen.basicsmk.taskmk.TaskMKRuntime.getTaskMKRuntimeInfo
import com.mozhimen.basicsmk.taskmk.annors.TaskMKState

/**
 * @ClassName TaskMKRuntimeListener
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/29 15:50
 * @Version 1.0
 */
class TaskMKRuntimeListener : TaskMKListener {
    override fun onStart(taskMK: TaskMK) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, taskMK.id + METHOD_START)
        }
    }

    override fun onRunning(taskMK: TaskMK) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, taskMK.id + METHOD_RUNNING)
        }
    }

    override fun onFinished(taskMK: TaskMK) {
        logTaskMKRuntimeInfo(taskMK)
    }

    private fun logTaskMKRuntimeInfo(taskMK: TaskMK) {
        val taskMKRuntimeInfo = getTaskMKRuntimeInfo(taskMK.id) ?: return
        val startTime = taskMKRuntimeInfo.stateTime[TaskMKState.START]
        val runningTime = taskMKRuntimeInfo.stateTime[TaskMKState.RUNNING]
        val finishedTime = taskMKRuntimeInfo.stateTime[TaskMKState.FINISHED]

        val builder = StringBuilder()
        builder.append(WRAPPER)
        builder.append(TAG)
        builder.append(WRAPPER)

        builder.append(WRAPPER)
        builder.append(HALF_LINE)
        builder.append(if (taskMK is TaskMKGroup) "TaskMKGroup" else "taskMK ${taskMK.id} " + METHOD_FINISHED)
        builder.append(HALF_LINE)

        addTaskInfoLineInfo(builder, DEPENDENCIES, getTaskDependenciesInfo(taskMK))
        addTaskInfoLineInfo(builder, IS_BLOCK_TASK, taskMKRuntimeInfo.isBlockTask.toString())
        addTaskInfoLineInfo(builder, THREAD_NAME, taskMKRuntimeInfo.threadName!!)
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

    private fun getTaskDependenciesInfo(taskMK: TaskMK): String {
        val builder = StringBuilder()
        for (s in taskMK.dependTasksName) {
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
        const val TAG: String = "TaskMKRuntimeListener"
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