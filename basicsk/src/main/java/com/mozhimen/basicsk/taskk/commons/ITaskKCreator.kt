package com.mozhimen.basicsk.taskk.commons

import com.mozhimen.basicsk.taskk.TaskK

/**
 * @ClassName ITaskKCreator
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/29 21:09
 * @Version 1.0
 */
interface ITaskKCreator {
    fun createTaskK(taskKName: String): TaskK
}