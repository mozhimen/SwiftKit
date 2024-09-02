package com.mozhimen.taskk.executor.commons

import androidx.annotation.IntRange
import com.mozhimen.taskk.executor.TaskKExecutor
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import java.util.concurrent.ThreadPoolExecutor

/**
 * @ClassName ITaskKExecutor
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/6/6 23:06
 * @Version 1.0
 */
interface ITaskKExecutor {
    fun getThreadPoolExecutor(): ThreadPoolExecutor

    fun getTaskKExecutorCoroutineDispatcher(): ExecutorCoroutineDispatcher

    fun execute(name: String, runnable: Runnable)

    /**
     * 插入任务
     * @param name String
     * @param priority Int 越小,优先级越大
     * @param runnable Runnable
     */
    fun execute(name: String, @IntRange(from = 0, to = 10) priority: Int, runnable: Runnable)
    fun execute(name: String, runnable: TaskKExecutor.ExecutorKCallable<*>)

    /**
     * 插入任务
     * @param name String
     * @param priority Int 越小,优先级越大
     * @param runnable ExecutorKCallable<*>
     */
    fun execute(name: String, @IntRange(from = 0, to = 10) priority: Int, runnable: TaskKExecutor.ExecutorKCallable<*>)

    /**
     * 暂停
     */
    fun pause()

    /**
     * 继续
     */
    fun resume()

    /**
     * 释放
     */
    fun release()
}