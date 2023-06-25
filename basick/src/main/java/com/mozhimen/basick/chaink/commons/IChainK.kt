package com.mozhimen.basick.chaink.commons

import com.mozhimen.basick.chaink.ChainK
import com.mozhimen.basick.chaink.bases.BaseChainKTask

/**
 * @ClassName IChainK
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/25 11:58
 * @Version 1.0
 */
interface IChainK {
    fun addBlockTask(taskId: String): IChainK
    fun addBlockTasks(vararg taskIds: String): ChainK

    /**
     * project任务组，也有可能是独立的一个task
     * @param task ChainKTask
     */
    fun start(task: BaseChainKTask)
}