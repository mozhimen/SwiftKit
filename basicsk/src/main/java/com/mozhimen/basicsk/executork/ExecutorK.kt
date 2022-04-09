package com.mozhimen.basicsk.executork

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.IntRange
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock

/**
 * @ClassName Executor
 * @Description 支持按任务的优先级去执行,
 * 支持线程池暂停,恢复(批量文件下载,上传)
 * 异步结果主动回调主线程
 * @Author Kolin Zhao
 * @Date 2021/9/13 16:04
 * @Version 1.0
 * todo 线程池能力监控,耗时任务检测,定时,延迟
 */
object ExecutorK {
    private const val TAG = "ExecutorK>>>>>"

    private var isPaused = false

    private var executork: ThreadPoolExecutor
    private var lock: ReentrantLock = ReentrantLock()
    private var pauseCondition: Condition = lock.newCondition()
    private val mainHandler = Handler(Looper.getMainLooper())

    init {
        val cpuCount = Runtime.getRuntime().availableProcessors()
        val corePoolSize = cpuCount + 1
        val maxPoolSize = cpuCount * 2 + 1
        val blockingQueue: PriorityBlockingQueue<out Runnable> = PriorityBlockingQueue()
        val keepAliveTime = 30L
        val unit = TimeUnit.SECONDS

        val seq = AtomicLong()
        val threadFactory = ThreadFactory {
            val thread = Thread(it)
            //executork-thread0
            thread.name = "executork-thread${seq.getAndIncrement()}"
            return@ThreadFactory thread
        }

        executork = object : ThreadPoolExecutor(
            corePoolSize,
            maxPoolSize,
            keepAliveTime,
            unit,
            blockingQueue as BlockingQueue<Runnable>,
            threadFactory
        ) {
            override fun beforeExecute(t: Thread?, r: Runnable?) {
                if (isPaused) {
                    lock.lock()
                    try {
                        pauseCondition.await()
                    } finally {
                        lock.unlock()
                    }
                }
            }

            override fun afterExecute(r: Runnable?, t: Throwable?) {
                //monitor pool time waste thread number, create thread number, and running thread number
                Log.e(TAG, "now completed thread's priority is ${(r as PriorityRunnable).priority}")
            }
        }
    }

    @JvmOverloads
    fun execute(runnable: Runnable, @IntRange(from = 0, to = 10) priority: Int = 0) {
        executork.execute(PriorityRunnable(priority, runnable))
    }

    @JvmOverloads
    fun execute(runnable: ExecutorKCallback<*>, @IntRange(from = 0, to = 10) priority: Int = 0) {
        executork.execute(PriorityRunnable(priority, runnable))
    }


    abstract class ExecutorKCallback<T> : Runnable {
        override fun run() {
            mainHandler.post { onPrepared() }
            val t = onBackground()

            //移除所有的消息,防止需要执行onCompleted了, onPrepared还没执行,那就不需要执行了
            mainHandler.removeCallbacksAndMessages(null)
            mainHandler.post { onCompleted(t) }
        }

        open fun onPrepared() {}
        abstract fun onBackground(): T?
        abstract fun onCompleted(t: T?)
    }

    class PriorityRunnable(val priority: Int, private val runnable: Runnable) : Runnable,
        Comparable<PriorityRunnable> {
        override fun run() {
            runnable.run()
        }

        override fun compareTo(other: PriorityRunnable): Int {
            return if (this.priority < other.priority) 1 else if (this.priority > other.priority) -1 else 0
        }
    }

    @Synchronized
    fun pause() {
        lock.lock()
        try {
            isPaused = true
            Log.e(TAG, "executork is paused")
        } finally {
            lock.unlock()
        }
    }

    @Synchronized
    fun resume() {
        lock.lock()
        try {
            isPaused = false
            pauseCondition.signalAll()
        } finally {
            lock.unlock()
        }
        Log.e(TAG, "executork is resumed")
    }
}