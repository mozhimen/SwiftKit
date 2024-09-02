package com.mozhimen.taskk.executor

import android.os.Handler
import android.os.Looper
import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper
import androidx.annotation.IntRange
import com.mozhimen.taskk.executor.commons.ITaskKExecutor
import com.mozhimen.kotlin.elemk.java.lang.PriorityRunnable
import com.mozhimen.kotlin.elemk.kotlin.cons.CSuppress
import com.mozhimen.kotlin.utilk.bases.BaseUtilK
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
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
 * @Version 1.0
 * 线程池能力监控,耗时任务检测,定时,延迟
 */
@Suppress(CSuppress.UNCHECKED_CAST)
object TaskKExecutor : ITaskKExecutor, BaseUtilK() {

    private var _isPaused = false
    private var _threadPoolExecutor: ThreadPoolExecutor
    private var _reentrantLock: ReentrantLock = ReentrantLock()
    private var _pauseCondition: Condition = _reentrantLock.newCondition()
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
            thread.name = "taskk_executor-thread${seq.getAndIncrement()}"//executork-thread0
            return@ThreadFactory thread
        }
        _threadPoolExecutor = object : ThreadPoolExecutor(
            corePoolSize,
            maxPoolSize,
            keepAliveTime,
            unit,
            blockingQueue as BlockingQueue<Runnable>,
            threadFactory
        ) {
            override fun beforeExecute(t: Thread?, r: Runnable?) {
                if (_isPaused) {
                    _reentrantLock.lock()
                    try {
                        _pauseCondition.await()
                    } finally {
                        _reentrantLock.unlock()
                    }
                }
            }

            override fun afterExecute(r: Runnable?, t: Throwable?) {
                //监控线程池耗时任务,线程创建数量,正在运行的数量
                val runnable = r as PriorityRunnable
                UtilKLogWrapper.v(TAG, "afterExecute: the task ${runnable.name} is finished, priority ${runnable.priority}")
            }
        }
    }

    override fun getThreadPoolExecutor(): ThreadPoolExecutor =
        _threadPoolExecutor

    override fun getTaskKExecutorCoroutineDispatcher(): ExecutorCoroutineDispatcher =
        getThreadPoolExecutor().asCoroutineDispatcher()

    override fun execute(name: String, runnable: Runnable) {
        execute(name, 0, runnable)
    }

    override fun execute(name: String, @IntRange(from = 0, to = 10) priority: Int, runnable: Runnable) {
        _threadPoolExecutor.execute(PriorityRunnable(name, priority, runnable))
    }

    override fun execute(name: String, runnable: ExecutorKCallable<*>) {
        execute(name, 0, runnable)
    }

    override fun execute(name: String, @IntRange(from = 0, to = 10) priority: Int, runnable: ExecutorKCallable<*>) {
        _threadPoolExecutor.execute(PriorityRunnable(name, priority, runnable))
    }

    @Synchronized
    override fun pause() {
        _reentrantLock.lock()
        try {
            _isPaused = true
            UtilKLogWrapper.w(TAG, "executork is paused")
        } finally {
            _reentrantLock.unlock()
        }
    }

    @Synchronized
    override fun resume() {
        _reentrantLock.lock()
        try {
            _isPaused = false
            _pauseCondition.signalAll()
            UtilKLogWrapper.w(TAG, "executork is resumed")
        } finally {
            _reentrantLock.unlock()
        }
    }

    @Synchronized
    override fun release() {
        _reentrantLock.lock()
        try {
            if (!_threadPoolExecutor.isShutdown) {
                _threadPoolExecutor.shutdown()
            }
            UtilKLogWrapper.w(TAG, "executork is shutdown")
        } finally {
            _reentrantLock.unlock()
        }
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
}