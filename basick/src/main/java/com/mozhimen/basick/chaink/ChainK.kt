package com.mozhimen.basick.chaink

import androidx.annotation.MainThread
import com.mozhimen.basick.chaink.helpers.ChainKRuntime
import com.mozhimen.basick.chaink.mos.ChainKNode
import com.mozhimen.basick.chaink.mos.ChainKNodeGroup
import com.mozhimen.basick.utilk.os.thread.UtilKThread

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
object ChainK {
    fun addBlockTask(taskId: String): ChainK {
        ChainKRuntime.addBlockTask(taskId)
        return this
    }

    fun addBlockTasks(vararg taskIds: String): ChainK {
        ChainKRuntime.addBlockTasks(*taskIds)
        return this
    }

    //project任务组，也有可能是独立的一个task
    @MainThread
    fun start(node: ChainKNode) {
        assert(UtilKThread.isMainThread()) {
            "start method must be invoke on MainThread"
        }
        val startTask = if (node is ChainKNodeGroup) node.startTask else node
        ChainKRuntime.traversalDependencyTreeAndInit(startTask)
        startTask.start()
        while (ChainKRuntime.hasBlockTasks()) {
            try {
                Thread.sleep(10)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

        //主线程唤醒之后，存在着等待队列的任务
        //那么让等待队列中的任务执行
        while (ChainKRuntime.hasBlockTasks()) {
            ChainKRuntime.runWaitingTasks()
        }
    }
}