package com.mozhimen.basick.executork

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

    private var _isPaused = false
    private var _executork: ThreadPoolExecutor
    private var _lock: ReentrantLock = ReentrantLock()
    private var _pauseCondition: Condition = _lock.newCondition()
    private val _mainHandler = Handler(Looper.getMainLooper())

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

        _executork = object : ThreadPoolExecutor(
            corePoolSize,
            maxPoolSize,
            keepAliveTime,
            unit,
            blockingQueue as BlockingQueue<Runnable>,
            threadFactory
        ) {
            override fun beforeExecute(t: Thread?, r: Runnable?) {
                if (_isPaused) {
                    _lock.lock()
                    try {
                        _pauseCondition.await()
                    } finally {
                        _lock.unlock()
                    }
                }
            }

            override fun afterExecute(r: Runnable?, t: Throwable?) {
                //监控线程池耗时任务,线程创建数量,正在运行的数量
                val runnable = r as PriorityRunnable
                Log.d(TAG, "已执行完的任务${runnable.name}的优先级是：${runnable.priority}")
            }
        }
    }

    /**
     * 插入任务
     * @param name String
     * @param priority Int 越小,优先级越大
     * @param runnable Runnable
     */
    @JvmOverloads
    fun execute(name: String, @IntRange(from = 0, to = 10) priority: Int = 0, runnable: Runnable) {
        _executork.execute(PriorityRunnable(name, priority, runnable))
    }

    /**
     * 插入任务
     * @param name String
     * @param priority Int 越小,优先级越大
     * @param runnable ExecutorKCallable<*>
     */
    @JvmOverloads
    fun execute(name: String, @IntRange(from = 0, to = 10) priority: Int = 0, runnable: ExecutorKCallable<*>) {
        _executork.execute(PriorityRunnable(name, priority, runnable))
    }

    /**
     * 暂停
     */
    @Synchronized
    fun pause() {
        _lock.lock()
        try {
            _isPaused = true
            Log.w(TAG, "pause executork is paused")
        } finally {
            _lock.unlock()
        }
    }

    /**
     * 继续
     */
    @Synchronized
    fun resume() {
        _lock.lock()
        try {
            _isPaused = false
            _pauseCondition.signalAll()
        } finally {
            _lock.unlock()
        }
        Log.w(TAG, "resume executork is resumed")
    }


    abstract class ExecutorKCallable<T> : Runnable {
        override fun run() {
            _mainHandler.post { onPrepared() }
            val t: T? = onBackground()

            //移除所有的消息,防止需要执行onCompleted了, onPrepared还没执行,那就不需要执行了
            _mainHandler.removeCallbacksAndMessages(null)
            _mainHandler.post { onCompleted(t) }
        }

        open fun onPrepared() {}
        abstract fun onBackground(): T?
        abstract fun onCompleted(t: T?)
    }

    class PriorityRunnable(val name: String, val priority: Int, private val runnable: Runnable) : Runnable,
        Comparable<PriorityRunnable> {
        override fun run() {
            runnable.run()
        }

        override fun compareTo(other: PriorityRunnable): Int {
            return if (this.priority < other.priority) 1 else if (this.priority > other.priority) -1 else 0
        }
    }
}