package com.mozhimen.basicsk.taskk

import android.os.Looper
import androidx.annotation.MainThread

/**
 * @ClassName TaskKMgr
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/30 21:52
 * @Version 1.0
 */
/**
 *对taskKRuntime的包装， 对外暴露的类，用于启动启动任务
 */
object TaskKMgr {
    fun addBlockTask(taskId: String): TaskKMgr {
        TaskKRuntime.addBlockTask(taskId)
        return this
    }

    fun addBlockTasks(vararg taskIds: String): TaskKMgr {
        TaskKRuntime.addBlockTasks(*taskIds)
        return this
    }

    //project任务组，也有可能是独立的一个task
    @MainThread
    fun start(taskK: TaskK) {
        assert(Thread.currentThread() == Looper.getMainLooper().thread) {
            "start method must be invoke on MainThread"
        }
        val startTask = if (taskK is TaskKGroup) taskK.startTask else taskK
        TaskKRuntime.traversalDependencyTreeAndInit(startTask)
        startTask.start()
        while (TaskKRuntime.hasBlockTasks()) {
            try {
                Thread.sleep(10)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

        //主线程唤醒之后，存在着等待队列的任务
        //那么让等待队列中的任务执行
        while (TaskKRuntime.hasBlockTasks()) {
            TaskKRuntime.runWaitingTasks()
        }
    }
}