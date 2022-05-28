package com.mozhimen.basick.taskk.commons

import com.mozhimen.basick.taskk.TaskK

/**
 * @ClassName TaskKListener
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/29 15:35
 * @Version 1.0
 */
interface ITaskKListener {
    fun onStart(taskK: TaskK)
    fun onRunning(taskK: TaskK)
    fun onFinished(taskK: TaskK)
}
