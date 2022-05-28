package com.mozhimen.basick.taskk.helpers

import com.mozhimen.basick.taskk.TaskK

/**
 * @ClassName TaskKComparator
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/29 17:07
 * @Version 1.0
 */
object TaskKComparator {
    fun compareTaskK(taskK1: TaskK, taskK2: TaskK): Int {
        if (taskK1.priority < taskK2.priority) {
            return 1
        }
        if (taskK1.priority > taskK2.priority) {
            return -1
        }
        return 0
    }
}