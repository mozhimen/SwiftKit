//package com.mozhimen.componentk.netk.app.download.helpers
//
//import com.liulishuo.okdownload.DownloadListener
//import com.liulishuo.okdownload.DownloadTask
//import com.liulishuo.okdownload.StatusUtil
//import com.liulishuo.okdownload.core.Util
//import com.liulishuo.okdownload.core.breakpoint.BreakpointStoreOnCache
//import com.liulishuo.okdownload.core.cause.EndCause
//import com.liulishuo.okdownload.core.listener.DownloadListener2
//import com.liulishuo.okdownload.core.listener.DownloadListenerBunch
//import com.mozhimen.basick.taskk.executor.TaskKExecutor
//import com.mozhimen.basick.utilk.bases.IUtilK
//import java.util.concurrent.Executor
//import java.util.concurrent.LinkedBlockingQueue
//import java.util.concurrent.ThreadPoolExecutor
//import java.util.concurrent.TimeUnit
//
///**
// * @ClassName AppDownloadParallelQueue
// * @Description TODO
// * @Author Mozhimen & Kolin Zhao
// * @Date 2023/12/4 18:11
// * @Version 1.0
// */
//class AppDownloadParallelQueue : DownloadListener2, Runnable, IUtilK {
//    companion object {
//        private const val ID_INVALID = BreakpointStoreOnCache.FIRST_ID - 1
//        private const val CAPACITY = 3
////        private val SERIAL_EXECUTOR: Executor = ThreadPoolExecutor(0, Int.MAX_VALUE, 30, TimeUnit.SECONDS, SynchronousQueue(), Util.threadFactory("OkDownload DynamicSerial", false))
//    }
//
//    //////////////////////////////////////////////////////////////////////////////////////////////
//
//    private val pauseTaskList = arrayListOf<DownloadTask>()
//    private val taskList: ArrayList<DownloadTask>
//    private val runningTaskQueue: LinkedBlockingQueue<DownloadTask> = LinkedBlockingQueue<DownloadTask>(CAPACITY)
//
//    @Volatile
//    var shutedDown = false
//
//    @Volatile
//    var looping = false
//
////    @Volatile
////    var paused = false
//
////    @Volatile
////    var runningTasks: ArrayList<DownloadTask> = ArrayList(CAPACITY)
//
//    var listenerBunch: DownloadListenerBunch
//
//    //////////////////////////////////////////////////////////////////////////////////////////////
//
//    constructor() : this(null)
//
//    constructor(listener: DownloadListener?) : this(listener, ArrayList<DownloadTask>())
//
//    constructor(listener: DownloadListener?, taskList: ArrayList<DownloadTask>) : super() {
//        listenerBunch = DownloadListenerBunch.Builder()
//            .append(this)
//            .append(listener).build()
//        this.taskList = taskList
//    }
//
//    //////////////////////////////////////////////////////////////////////////////////////////////
//
//    fun setListener(listener: DownloadListener?) {
//        listenerBunch = DownloadListenerBunch.Builder()
//            .append(this)
//            .append(listener).build()
//    }
//
//    /**
//     * 在串行队列的某个时间将给定的任务编入队列。如果“任务”在头部串行队列“任务”将自动启动。
//     */
//    @Synchronized
//    fun enqueue(task: DownloadTask) {
//        taskList.add(task)
//        taskList.sort()
//        if (/*!paused &&*/ !looping) {
//            looping = true
//            startNewLooper()
//        }
//    }
//
//    @Synchronized
//    fun remove(task: DownloadTask) {
//        if (taskList.contains(task)) {
//            val iterator = taskList.iterator()
//            while (iterator.hasNext()) {
//                val next = iterator.next()
//                if (next == task) {
//                    val status = StatusUtil.getStatus(next)
//                    if (status != StatusUtil.Status.RUNNING) {
//                        iterator.remove()
//                    }
//                    break
//                }
//            }
//        }
//        if (pauseTaskList.contains(task)) {
//            val iterator = pauseTaskList.iterator()
//            while (iterator.hasNext()) {
//                val next = iterator.next()
//                if (next == task) {
//                    val status = StatusUtil.getStatus(next)
//                    if (status != StatusUtil.Status.RUNNING) {
//                        iterator.remove()
//                    }
//                    break
//                }
//            }
//        }
//    }
//
////    /**
////     * 暂停队列所有。
////     */
////    @Synchronized
////    fun pauseAllTask() {
////        if (paused) {
////            Util.w(TAG, "require pause this queue(remain ${taskList.size}), but it has already been paused")
////            return
////        }
////        paused = true
////
////        if (runningTaskQueue.isNotEmpty()) {
////            for (runningTask in runningTaskQueue) {
////                runningTask.cancel()
////                pauseTaskList.add(0, runningTask)
////            }
////            runningTaskQueue.clear()
////        }
////    }
//
//    @Synchronized
//    fun pauseTaskOf(downloadTask: DownloadTask) {
////        if (paused) {
////            Util.w(TAG, "require pause ${downloadTask.url} this queue(remain ${taskList.size}), but it has already been paused")
////            return
////        }
//
//        if (runningTaskQueue.contains(downloadTask)) {
//            downloadTask.cancel()
//            pauseTaskList.add(downloadTask)
//            runningTaskQueue.remove(downloadTask)
//        }
//    }
//
//    /**
//     * 如果队列暂停，则恢复队列。
//     */
////    @Synchronized
////    fun resumeAllTask() {
////        if (!paused) {
////            Util.w(TAG, "require resume this queue(remain ${taskList.size}), but it is still running")
////            return
////        }
////        paused = false
////        if (taskList.isNotEmpty() && !looping) {
////            looping = true
////            startNewLooper()
////        }
////    }
//
//    @Synchronized
//    fun resumeTaskOf(downloadTask: DownloadTask) {
//        if (pauseTaskList.contains(downloadTask)) {
//            pauseTaskList.remove(downloadTask)
//            if (!taskList.contains(downloadTask)) {
//                taskList.add(downloadTask)
//            }
//        }
//        if (taskList.isNotEmpty() && !looping) {
//            looping = true
//            startNewLooper()
//        }
//    }
//
//    /**
//     * 返回正在工作的任务的标识，如果有任务正在工作，您将收到[.ID_INVALID]。@返回工作任务的标识
//     */
//    fun getWorkingTaskIds(): List<Int> {
//        return runningTaskQueue.map { it.id }
//    }
//
//    fun getWorkingTaskCount(): Int {
//        return runningTaskQueue.size
//    }
//
//    /**
//     * 获取正在此队列上等待的任务的计数。@返回此队列上等待任务的计数。
//     */
//    fun getWaitingTaskCount(): Int {
//        return taskList.size - runningTaskQueue.size
//    }
//
//    fun getPauseTaskCount(): Int {
//        return pauseTaskList.size
//    }
//
//    /**
//     * 尝试停止工作任务，停止等待任务的处理，并返回等待执行的任务列表。当从这个方法返回时，这些任务将从task:队列中抽取(移除)。
//     */
//    @Synchronized
//    fun shutdown(): Array<DownloadTask?> {
//        shutedDown = true
//        if (runningTaskQueue.isNotEmpty()) {
//            for (runningTask in runningTaskQueue) {
//                runningTask.cancel()
//            }
//            runningTaskQueue.clear()
//        }
//        val tasks = arrayOfNulls<DownloadTask>(taskList.size)
//        taskList.toArray(tasks)
//        taskList.clear()
//        return tasks
//    }
//
//    fun startNewLooper() {
//        TaskKExecutor.execute(TAG, this)
//    }
//
//    /////////////////////////////////////////////////////////////////////////////////////////////
//
//    override fun run() {
//        run loop@{
//            while (!shutedDown) {
//                val nextTask: DownloadTask
//                synchronized(this) {
//                    if (taskList.isEmpty() /*|| paused*/) {
//                        looping = false
//                        return@loop
//                    }
//                    nextTask = taskList[0]
//                }
//                nextTask.enqueue(listenerBunch)
//            }
//        }
//    }
//
//    /////////////////////////////////////////////////////////////////////////////////////////////
//
//    override fun taskStart(task: DownloadTask) {
//        runningTasks = task
//    }
//
//    @Synchronized
//    override fun taskEnd(task: DownloadTask, cause: EndCause, realCause: Exception?) {
//        if (cause != EndCause.CANCELED && task === runningTasks) {
//            runningTasks = null
//        }
//    }
//}
//
//
