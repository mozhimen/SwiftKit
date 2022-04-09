package com.mozhimen.basicsmk.taskmk.commons

import com.mozhimen.basicsmk.taskmk.TaskMK

/**
 * @ClassName ITaskMKCreator
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/29 21:09
 * @Version 1.0
 */
interface ITaskMKCreator {
    fun createTaskMK(taskMKName: String): TaskMK
}