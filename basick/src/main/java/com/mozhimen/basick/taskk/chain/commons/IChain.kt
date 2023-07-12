package com.mozhimen.basick.taskk.chain.commons

import com.mozhimen.basick.taskk.chain.TaskKChain
import com.mozhimen.basick.taskk.chain.bases.BaseChainTask

/**
 * @ClassName IChainK
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/25 11:58
 * @Version 1.0
 */
interface IChain {
    fun addBlockTask(taskId: String): IChain
    fun addBlockTasks(vararg taskIds: String): TaskKChain

    /**
     * project任务组，也有可能是独立的一个task
     * @param task ChainKTask
     */
    fun start(task: BaseChainTask)
}