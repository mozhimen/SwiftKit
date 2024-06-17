package com.mozhimen.basick.taskk.handler

import android.os.Looper
import android.os.MessageQueue
import java.util.LinkedList
import java.util.Queue


/**
 * @ClassName TaskKIdleHandler
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/6/17
 * @Version 1.0
 */
object TaskKIdleHandler {
    private val _delayTasks: Queue<Runnable> = LinkedList<Runnable>()

    private val _idleHandler = MessageQueue.IdleHandler {
        if (_delayTasks.size > 0) {
            val task: Runnable? = _delayTasks.poll()
            task?.run()
        }
        !_delayTasks.isEmpty() //delayTasks非空时返回ture表示下次继续执行，为空时返回false系统会移除该IdleHandler不再执行
    }

    /////////////////////////////////////////////////////////////////////

    fun addTask(task: Runnable?): TaskKIdleHandler {
        _delayTasks.add(task)
        return this
    }

    fun start() {
        Looper.myQueue().addIdleHandler(_idleHandler)
    }
}