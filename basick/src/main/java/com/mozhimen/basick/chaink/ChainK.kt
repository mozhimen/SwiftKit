package com.mozhimen.basick.chaink

import androidx.annotation.MainThread
import com.mozhimen.basick.chaink.bases.BaseChainKTask
import com.mozhimen.basick.chaink.commons.IChainK
import com.mozhimen.basick.chaink.helpers.ChainKRuntime
import com.mozhimen.basick.chaink.temps.ChainKTaskGroup
import com.mozhimen.basick.utilk.java.lang.UtilKThread

/**
 * @ClassName TaskKMgr
 * @Description 对taskKRuntime的包装， 对外暴露的类，用于启动启动任务
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/30 21:52
 * @Version 1.0
 */
object ChainK : IChainK {
    override fun addBlockTask(taskId: String): ChainK {
        ChainKRuntime.addBlockTask(taskId)
        return this
    }

    override fun addBlockTasks(vararg taskIds: String): ChainK {
        ChainKRuntime.addBlockTasks(*taskIds)
        return this
    }

    @MainThread
    override fun start(task: BaseChainKTask) {
        assert(UtilKThread.isMainThread()) { "start method must be invoke on MainThread" }
        val startTask = if (task is ChainKTaskGroup) task.startTask else task
        ChainKRuntime.traversalDependencyTreeAndInit(startTask)
        startTask.start()
        while (ChainKRuntime.hasBlockTasks()) {
            try {
                Thread.sleep(10)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        //主线程唤醒之后，存在着等待队列的任务
        //那么让等待队列中的任务执行
        while (ChainKRuntime.hasBlockTasks()) {
            ChainKRuntime.runWaitingTasks()
        }
    }
}