package com.mozhimen.basick.chaink.helpers

import com.mozhimen.basick.chaink.bases.BaseChainKTask

/**
 * @ClassName TaskCompare
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/25 11:14
 * @Version 1.0
 */
class ChainKTaskComparator : Comparator<BaseChainKTask> {
    override fun compare(task1: BaseChainKTask, task2: BaseChainKTask): Int =
        if (task1.priority < task2.priority) 1
        else if (task1.priority > task2.priority) -1
        else 0
}