package com.mozhimen.basick.taskk.chain

import androidx.annotation.MainThread
import com.mozhimen.basick.taskk.chain.bases.BaseChainTask
import com.mozhimen.basick.taskk.chain.commons.IChain
import com.mozhimen.basick.taskk.chain.helpers.ChainRuntime
import com.mozhimen.basick.taskk.chain.temps.ChainTaskGroup
import com.mozhimen.basick.utilk.android.os.UtilKLooper

/**
 * @ClassName TaskKMgr
 * @Description 对taskKRuntime的包装， 对外暴露的类，用于启动启动任务
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/30 21:52
 * @Version 1.0
 */
object TaskKChain : IChain {
    override fun addBlockTask(taskId: String): TaskKChain {
        ChainRuntime.addBlockTask(taskId)
        return this
    }

    override fun addBlockTasks(vararg taskIds: String): TaskKChain {
        ChainRuntime.addBlockTasks(*taskIds)
        return this
    }

    @MainThread
    override fun start(task: BaseChainTask) {
        assert(UtilKLooper.isMainThread()) { "start method must be invoke on MainThread" }
        val startTask = if (task is ChainTaskGroup) task.startTask else task
        ChainRuntime.traversalDependencyTreeAndInit(startTask)
        startTask.start()
        while (ChainRuntime.hasBlockTasks()) {
            try {
                Thread.sleep(10)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        //主线程唤醒之后，存在着等待队列的任务
        //那么让等待队列中的任务执行
        while (ChainRuntime.hasBlockTasks()) {
            ChainRuntime.runWaitingTasks()
        }
    }
}