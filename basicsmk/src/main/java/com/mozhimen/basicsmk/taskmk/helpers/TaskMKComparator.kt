package com.mozhimen.basicsmk.taskmk.helpers

import com.mozhimen.basicsmk.taskmk.TaskMK

/**
 * @ClassName TaskMKComparator
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/29 17:07
 * @Version 1.0
 */
object TaskMKComparator {
    fun compareTaskMK(taskMK1: TaskMK, taskMK2: TaskMK): Int {
        if (taskMK1.priority < taskMK2.priority) {
            return 1
        }
        if (taskMK1.priority > taskMK2.priority) {
            return -1
        }
        return 0
    }
}