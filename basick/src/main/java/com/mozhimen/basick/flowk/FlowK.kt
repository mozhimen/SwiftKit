package com.mozhimen.basick.flowk

import androidx.annotation.MainThread
import com.mozhimen.basick.flowk.helpers.FlowKRuntime
import com.mozhimen.basick.flowk.mos.FlowKNode
import com.mozhimen.basick.flowk.mos.FlowKNodeGroup
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
object FlowK {
    fun addBlockTask(taskId: String): FlowK {
        FlowKRuntime.addBlockTask(taskId)
        return this
    }

    fun addBlockTasks(vararg taskIds: String): FlowK {
        FlowKRuntime.addBlockTasks(*taskIds)
        return this
    }

    //project任务组，也有可能是独立的一个task
    @MainThread
    fun start(flowKNode: FlowKNode) {
        assert(UtilKThread.isMainThread()) {
            "start method must be invoke on MainThread"
        }
        val startTask = if (flowKNode is FlowKNodeGroup) flowKNode.startTask else flowKNode
        FlowKRuntime.traversalDependencyTreeAndInit(startTask)
        startTask.start()
        while (FlowKRuntime.hasBlockTasks()) {
            try {
                Thread.sleep(10)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

        //主线程唤醒之后，存在着等待队列的任务
        //那么让等待队列中的任务执行
        while (FlowKRuntime.hasBlockTasks()) {
            FlowKRuntime.runWaitingTasks()
        }
    }
}