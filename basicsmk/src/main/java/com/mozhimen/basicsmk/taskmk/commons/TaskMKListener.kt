package com.mozhimen.basicsmk.taskmk.commons

import com.mozhimen.basicsmk.taskmk.TaskMK

/**
 * @ClassName TaskMKListener
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/29 15:35
 * @Version 1.0
 */
interface TaskMKListener {
    fun onStart(taskMK: TaskMK)
    fun onRunning(taskMK: TaskMK)
    fun onFinished(taskMK: TaskMK)
}
