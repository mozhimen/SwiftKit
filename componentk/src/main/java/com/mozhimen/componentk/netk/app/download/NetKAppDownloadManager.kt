package com.mozhimen.componentk.netk.app.download

import android.content.Context
import android.util.Log
import android.util.SparseArray
import androidx.annotation.WorkerThread
import com.liulishuo.okdownload.DownloadTask
import com.liulishuo.okdownload.OkDownload
import com.liulishuo.okdownload.StatusUtil
import com.liulishuo.okdownload.core.cause.EndCause
import com.liulishuo.okdownload.core.cause.ResumeFailedCause
import com.liulishuo.okdownload.core.connection.DownloadOkHttp3Connection
import com.liulishuo.okdownload.core.listener.DownloadListener1
import com.liulishuo.okdownload.core.listener.assist.Listener1Assist
import com.mozhimen.basick.elemk.commons.IAB_Listener
import com.mozhimen.basick.elemk.javax.net.bases.BaseX509TrustManager
import com.mozhimen.basick.lintk.optin.OptInApiInit_InApplication
import com.mozhimen.basick.taskk.handler.TaskKHandler
import com.mozhimen.basick.utilk.bases.IUtilK
import com.mozhimen.basick.utilk.java.io.UtilKFileDir
import com.mozhimen.basick.utilk.javax.net.UtilKSSLSocketFactory
import com.mozhimen.componentk.netk.app.NetKApp
import com.mozhimen.componentk.netk.app.cons.CNetKAppErrorCode
import com.mozhimen.componentk.netk.app.cons.CNetKAppState
import com.mozhimen.componentk.netk.app.download.helpers.AppDownloadSerialQueue
import com.mozhimen.componentk.netk.app.download.mos.AppDownloadException
import com.mozhimen.componentk.netk.app.download.mos.MAppDownloadProgress
import com.mozhimen.componentk.netk.app.task.db.AppTask
import com.mozhimen.componentk.netk.app.task.db.AppTaskDaoManager
import okhttp3.OkHttpClient
import java.lang.Exception

/**
 * @ClassName AppDownloadManager
 * @Description TODO
 * @Author Mozhimen
 * @Date 2023/11/7 14:19
 * @Version 1.0
 */
@OptInApiInit_InApplication
object NetKAppDownloadManager : DownloadListener1(), IUtilK {
    private val _appTasks = SparseArray<AppTask>()
    private val _appDownloadSerialQueue: AppDownloadSerialQueue by lazy { AppDownloadSerialQueue(this) }

    ///////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun init(context: Context) {
        try {
            val builder = OkDownload.Builder(context)
                .connectionFactory(
                    DownloadOkHttp3Connection.Factory().setBuilder(
                        OkHttpClient.Builder()
                            .sslSocketFactory(UtilKSSLSocketFactory.getTLS(), BaseX509TrustManager())
                            .hostnameVerifier { _, _ -> true }
                    )
                )
            OkDownload.setSingletonInstance(builder.build())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @JvmStatic
    fun download(appTask: AppTask) {
        val externalFilesDir = UtilKFileDir.External.getFilesDownloadsDir() ?: throw AppDownloadException(CNetKAppErrorCode.CODE_DOWNLOAD_PATH_NOT_EXIST)
        val downloadTask = DownloadTask.Builder(appTask.downloadUrlCurrent, externalFilesDir)//先构建一个Task 框架可以保证Id唯一
            .setFilename(appTask.apkName)
            .setMinIntervalMillisCallbackProcess(1000)// 下载进度回调的间隔时间（毫秒）
            .setPassIfAlreadyCompleted(!appTask.apkIsInstalled)// 任务过去已完成是否要重新下载
            .build()
        //先根据Id去查找当前队列中有没有相同的任务，
        //如果有相同的任务，则不进行提交
        val appFileParams1 = _appTasks[downloadTask.id]
        if (null != appFileParams1) return
        when (StatusUtil.getStatus(downloadTask)) {
            StatusUtil.Status.PENDING -> {
                //等待中 不做处理
            }

            StatusUtil.Status.RUNNING -> {
                //下载中，不做处理
            }

            StatusUtil.Status.COMPLETED -> {
                /**
                 * [CNetKAppState.STATE_DOWNLOAD_SUCCESS]
                 */
                NetKApp.onDownloadSuccess(appTask)//下载完成，去安装
                return
            }

            StatusUtil.Status.IDLE -> {

            }
            //StatusUtil.Status.UNKNOWN
            else -> {

            }
        }
        /**
         * [CNetKAppState.STATE_TASK_WAIT]
         */
        NetKApp.onTaskWait(appTask)
        _appTasks.put(downloadTask.id, appTask)
        _appDownloadSerialQueue.enqueue(downloadTask)
    }

    /**
     * 查询下载状态
     */
    fun getAppDownloadProgress(appTask: AppTask): MAppDownloadProgress {
        val task = getDownloadTask(appTask)
        task ?: return MAppDownloadProgress().apply {
            progressState = CNetKAppState.STATE_TASK_CREATE
        }
        when (StatusUtil.getStatus(task)) {
            StatusUtil.Status.PENDING -> {//等待中 不做处理
                return MAppDownloadProgress().apply {
                    progressState = CNetKAppState.STATE_TASK_WAIT
                }
            }

            StatusUtil.Status.RUNNING -> {//下载中，不做处理
                return MAppDownloadProgress().apply {
                    progressState = CNetKAppState.STATE_DOWNLOADING
                    progress = StatusUtil.getCurrentInfo(task)?.let {
                        (it.totalOffset.toFloat() / it.totalLength * 100).toInt()
                    } ?: 0
                }
            }

            StatusUtil.Status.COMPLETED -> {//下载完成，去安装
                return MAppDownloadProgress().apply {
                    progressState = CNetKAppState.STATE_DOWNLOAD_SUCCESS
                    progress = 100
                }
            }

            StatusUtil.Status.IDLE -> {
                return MAppDownloadProgress().apply {
                    progressState = CNetKAppState.STATE_DOWNLOAD_PAUSE
                    progress = StatusUtil.getCurrentInfo(task)?.let {
                        (it.totalOffset.toFloat() / it.totalLength * 100).toInt()
                    } ?: 0
                }
            }
            //StatusUtil.Status.UNKNOWN
            else -> {
                return MAppDownloadProgress().apply {
                    progressState = CNetKAppState.STATE_TASK_CREATE
                    progress = StatusUtil.getCurrentInfo(task)?.let {
                        (it.totalOffset.toFloat() / it.totalLength * 100).toInt()
                    } ?: 0
                }
            }
        }
    }

    fun waitCancel(appTask: AppTask) {
        val task = getDownloadTask(appTask) ?: return
        _appDownloadSerialQueue.remove(task)//先从队列中移除
        task.cancel()//然后取消任务
    }

    fun pause(appTask: AppTask) {
        val task = getDownloadTask(appTask) ?: return
        task.cancel()//取消任务
    }

    /**
     * 恢复任务
     */
    fun resume(appTask: AppTask) {
        val task = getDownloadTask(appTask) ?: return
        val status = StatusUtil.getStatus(task)
        if (status != StatusUtil.Status.RUNNING) {
            _appDownloadSerialQueue.enqueue(task)
        }
        _appTasks.put(task.id, appTask)
    }

    /**
     * 删除任务
     */
    @WorkerThread
    fun deleteOnBack(appTask: AppTask, onDelete: IAB_Listener<Boolean, Int>) {
        val task = getDownloadTask(appTask) ?: kotlin.run {
            TaskKHandler.post {
                onDelete.invoke(false, CNetKAppErrorCode.CODE_DOWNLOAD_CANT_FIND_TASK)
            }
            return
        }
        task.cancel()
        _appTasks.delete(task.id)//先从队列中移除
        _appDownloadSerialQueue.remove(task)
        OkDownload.with().breakpointStore().remove(task.id)
        task.file?.delete()
        AppTaskDaoManager.deleteOnBack(appTask)
        TaskKHandler.post {
            onDelete.invoke(true, -1)
            /**
             * [CNetKAppState.STATE_TASK_CANCEL]
             */
            NetKApp.onTaskCancel(appTask)
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////

    override fun taskStart(task: DownloadTask, model: Listener1Assist.Listener1Model) {
        Log.d(TAG, "taskStart: task $task")
        _appTasks[task.id]?.let { appTask ->
            /**
             * [CNetKAppState.STATE_DOWNLOAD_CREATE]
             */
            NetKApp.onDownloadCreate(appTask)
        }
    }

    override fun retry(task: DownloadTask, cause: ResumeFailedCause) {
        Log.d(TAG, "retry: task $task")
    }

    override fun connected(task: DownloadTask, blockCount: Int, currentOffset: Long, totalLength: Long) {
        Log.d(TAG, "connected: task $task")
    }

    override fun progress(task: DownloadTask, currentOffset: Long, totalLength: Long) {
        Log.d(TAG, "progress: task $task currentOffset $currentOffset  totalLength $totalLength")
        _appTasks[task.id]?.let { appTask ->
            val complete = (currentOffset.toFloat() / totalLength * 100).toInt()
            /**
             * [CNetKAppState.STATE_DOWNLOADING]
             */
            NetKApp.onDownloading(appTask, complete)
        }
    }

    override fun taskEnd(task: DownloadTask, cause: EndCause, realCause: Exception?, model: Listener1Assist.Listener1Model) {
        Log.d(TAG, "taskEnd: $task cause ${cause.name} realCause ${realCause.toString()}")
        _appTasks[task.id]?.let { appTask ->
            when (cause) {
                EndCause.COMPLETED -> {
                    /**
                     * [CNetKAppState.STATE_DOWNLOAD_SUCCESS]
                     */
                    NetKApp.onDownloadSuccess(appTask)
                }

                EndCause.CANCELED -> {
                    /**
                     * [CNetKAppState.STATE_DOWNLOAD_CANCEL]
                     */
                    NetKApp.onDownloadCancel(appTask)//下载取消
                }

                else -> {
                    /**
                     * [CNetKAppState.STATE_DOWNLOAD_FAIL]
                     */
                    NetKApp.onDownloadFail(appTask, realCause)
                }
            }
        }
        _appTasks.delete(task.id)//从队列里移除掉
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    private fun getDownloadTask(appTask: AppTask): DownloadTask? {
        val externalFilesDir = UtilKFileDir.External.getFilesDownloadsDir() ?: return null
        return DownloadTask.Builder(appTask.downloadUrlCurrent, externalFilesDir.absolutePath, appTask.apkName).build()
    }
}