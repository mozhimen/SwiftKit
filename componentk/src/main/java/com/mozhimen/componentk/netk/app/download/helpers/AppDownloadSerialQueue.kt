package com.mozhimen.componentk.netk.app.download.helpers

import com.liulishuo.okdownload.DownloadListener
import com.liulishuo.okdownload.DownloadTask
import com.liulishuo.okdownload.StatusUtil
import com.liulishuo.okdownload.core.Util
import com.liulishuo.okdownload.core.breakpoint.BreakpointStoreOnCache
import com.liulishuo.okdownload.core.cause.EndCause
import com.liulishuo.okdownload.core.listener.DownloadListener2
import com.liulishuo.okdownload.core.listener.DownloadListenerBunch
import java.util.Collections
import java.util.concurrent.Executor
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * @ClassName AppDownloadSerialQueue
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/12/4 18:11
 * @Version 1.0
 */

class AppDownloadSerialQueue internal constructor(listener: DownloadListener?, taskList: ArrayList<DownloadTask>) : DownloadListener2(), Runnable {
    private val taskList: ArrayList<DownloadTask>

    @Volatile
    var shutedDown = false

    @Volatile
    var looping = false

    @Volatile
    var paused = false

    @Volatile
    var runningTask: DownloadTask? = null
    var listenerBunch: DownloadListenerBunch

    init {
        listenerBunch = DownloadListenerBunch.Builder()
            .append(this)
            .append(listener).build()
        this.taskList = taskList
    }

    @JvmOverloads
    constructor(listener: DownloadListener? = null) : this(listener, ArrayList<DownloadTask>())

    fun setListener(listener: DownloadListener?) {
        listenerBunch = DownloadListenerBunch.Builder()
            .append(this)
            .append(listener).build()
    }

    /**
     * Enqueues the given task sometime in the serial queue. If the `task` is in the head of
     * the serial queue, the `task` will be started automatically.
     */
    @Synchronized
    fun enqueue(task: DownloadTask) {
        taskList.add(task)
        Collections.sort(taskList)
        if (!paused && !looping) {
            looping = true
            startNewLooper()
        }
    }

    @Synchronized
    fun remove(task: DownloadTask) {
        val iterator = taskList.iterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            if (next == task) {
                val status = StatusUtil.getStatus(next)
                if (status != StatusUtil.Status.RUNNING) {
                    iterator.remove()
                }
                break
            }
        }
    }

    /**
     * Pause the queue.
     *
     * @see .resume
     */
    @Synchronized
    fun pause() {
        if (paused) {
            Util.w(
                TAG, "require pause this queue(remain " + taskList.size + "), but"
                        + "it has already been paused"
            )
            return
        }
        paused = true
        if (runningTask != null) {
            runningTask!!.cancel()
            taskList.add(0, runningTask!!)
            runningTask = null
        }
    }

    /**
     * Resume the queue if the queue is paused.
     *
     * @see .pause
     */
    @Synchronized
    fun resume() {
        if (!paused) {
            Util.w(
                TAG, "require resume this queue(remain " + taskList.size + "), but it is"
                        + " still running"
            )
            return
        }
        paused = false
        if (!taskList.isEmpty() && !looping) {
            looping = true
            startNewLooper()
        }
    }

    /**
     * Returns the identify of the working task, if there is task is working, you will receive
     * [.ID_INVALID].
     *
     * @return the identify of the working task
     */
    fun getWorkingTaskId(): Int {
        return if (runningTask != null) runningTask!!.id else ID_INVALID
    }

    /**
     * Get the count of tasks which is waiting on this queue.
     *
     * @return the count of waiting tasks on this queue.
     */
    fun getWaitingTaskCount(): Int {
        return taskList.size
    }

    /**
     * Attempts to stop the working task, halts the processing of waiting tasks, and returns a list
     * of the tasks that were awaiting execution. These tasks are drained (removed) from the task
     * queue upon return from this method.
     */
    @Synchronized
    fun shutdown(): Array<DownloadTask?> {
        shutedDown = true
        if (runningTask != null) {
            runningTask!!.cancel()
        }
        val tasks = arrayOfNulls<DownloadTask>(taskList.size)
        taskList.toArray(tasks)
        taskList.clear()
        return tasks
    }

    override fun run() {
        while (!shutedDown) {
            val nextTask: DownloadTask
            synchronized(this) {
                if (taskList.isEmpty() || paused) {
                    runningTask = null
                    looping = false
                    break
                }
                nextTask = taskList.removeAt(0)
            }
            nextTask.execute(listenerBunch)
        }
    }

    fun startNewLooper() {
        SERIAL_EXECUTOR.execute(this)
    }

    override fun taskStart(task: DownloadTask) {
        runningTask = task
    }

    @Synchronized
    override fun taskEnd(
        task: DownloadTask, cause: EndCause,
        realCause: Exception?
    ) {
        if (cause != EndCause.CANCELED && task === runningTask) {
            runningTask = null
        }
    }

    companion object {
        const val ID_INVALID = BreakpointStoreOnCache.FIRST_ID - 1
        private val SERIAL_EXECUTOR: Executor = ThreadPoolExecutor(0, Int.MAX_VALUE, 30, TimeUnit.SECONDS, SynchronousQueue(), Util.threadFactory("OkDownload DynamicSerial", false))
        private const val TAG = "AppDownloadSerialQueue"
    }
}

