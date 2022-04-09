package com.mozhimen.basicsk.taskk.commons

import com.mozhimen.basicsk.taskk.TaskK

/**
 * @ClassName TaskKListener
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/29 15:35
 * @Version 1.0
 */
interface TaskKListener {
    fun onStart(taskK: TaskK)
    fun onRunning(taskK: TaskK)
    fun onFinished(taskK: TaskK)
}
