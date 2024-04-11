package com.mozhimen.basick.taskk.chain.helpers

import com.mozhimen.basick.taskk.chain.bases.BaseChainTask

/**
 * @ClassName TaskCompare
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Version 1.0
 */
class ChainTaskComparator : Comparator<BaseChainTask> {
    override fun compare(task1: BaseChainTask, task2: BaseChainTask): Int =
        if (task1.priority < task2.priority) 1
        else if (task1.priority > task2.priority) -1
        else 0
}