package com.mozhimen.basicsmk.taskmk

import android.os.Looper
import androidx.annotation.MainThread

/**
 * @ClassName TaskMKMgr
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/30 21:52
 * @Version 1.0
 */
/**
 *对taskMKRuntime的包装， 对外暴露的类，用于启动启动任务
 */
object TaskMKMgr {
    fun addBlockTask(taskId: String): TaskMKMgr {
        TaskMKRuntime.addBlockTask(taskId)
        return this
    }

    fun addBlockTasks(vararg taskIds: String): TaskMKMgr {
        TaskMKRuntime.addBlockTasks(*taskIds)
        return this
    }

    //project任务组，也有可能是独立的一个task
    @MainThread
    fun start(taskMK: TaskMK) {
        assert(Thread.currentThread() == Looper.getMainLooper().thread) {
            "start method must be invoke on MainThread"
        }
        val startTask = if (taskMK is TaskMKGroup) taskMK.startTask else taskMK
        TaskMKRuntime.traversalDependencyTreeAndInit(startTask)
        startTask.start()
        while (TaskMKRuntime.hasBlockTasks()) {
            try {
                Thread.sleep(10)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

        //主线程唤醒之后，存在着等待队列的任务
        //那么让等待队列中的任务执行
        while (TaskMKRuntime.hasBlockTasks()) {
            TaskMKRuntime.runWaitingTasks()
        }
    }
}