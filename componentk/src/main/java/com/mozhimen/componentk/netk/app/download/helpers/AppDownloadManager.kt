package com.mozhimen.componentk.netk.app.download.helpers

import android.content.Context
import android.util.SparseArray
import androidx.annotation.WorkerThread
import com.liulishuo.okdownload.DownloadTask
import com.liulishuo.okdownload.OkDownload
import com.liulishuo.okdownload.StatusUtil
import com.mozhimen.basick.elemk.commons.IAB_Listener
import com.mozhimen.basick.lintk.optin.OptInApiInit_InApplication
import com.mozhimen.basick.taskk.handler.TaskKHandler
import com.mozhimen.basick.utilk.java.io.UtilKFileDir
import com.mozhimen.componentk.netk.app.download.cons.CAppDownloadErrorCode
import com.mozhimen.componentk.netk.app.download.db.AppDownloadTask
import com.mozhimen.componentk.netk.app.download.mos.AppDownloadException
import com.mozhimen.componentk.netk.app.download.mos.AppDownloadProgress

/**
 * @ClassName AppDownloadManager
 * @Description TODO
 * @Author Mozhimen
 * @Date 2023/11/7 14:19
 * @Version 1.0
 */
@OptInApiInit_InApplication
object AppDownloadManager {
    private val _appDownloadParams = SparseArray<AppDownloadTask>()

    fun pause(appDownloadTask: AppDownloadTask) {

    }

    fun waitCancel(appDownloadTask: AppDownloadTask) {
        val task = getTask(appDownloadTask)
        task ?: return
        //先从队列中移除
        mDownloadSerialQueue.remove(task)
        //然后取消任务
        task.cancel()
    }

    fun download(appDownloadTask: AppDownloadTask) {
        val externalFilesDir = UtilKFileDir.External.getFilesDownloadsDir()
        if (null == externalFilesDir) {
            //Toast.makeText(context, "   下载路径不存在", Toast.LENGTH_SHORT).show()
            throw AppDownloadException(CAppDownloadErrorCode.CODE_DOWNLOAD_PATH_NOT_EXIST)
            return
        }
        //先构建一个Task 框架可以保证Id唯一
        val downloadTask = DownloadTask.Builder(appDownloadTask.downloadUrlCurrent, externalFilesDir)
            .setFilename(appDownloadTask.apkSaveName)
            // 下载进度回调的间隔时间（毫秒）
            .setMinIntervalMillisCallbackProcess(1000)
            // 任务过去已完成是否要重新下载
            .setPassIfAlreadyCompleted(!appDownloadTask.apkIsInstalled)
            .build()
        //先根据Id去查找当前队列中有没有相同的任务，
        //如果有相同的任务，则不进行提交
        val appDownloadParams = _appDownloadParams[downloadTask.id]
        if (null != appDownloadParams) return

        when (StatusUtil.getStatus(downloadTask)) {
            StatusUtil.Status.PENDING -> {
                //等待中 不做处理
            }

            StatusUtil.Status.RUNNING -> {
                //下载中，不做处理
            }

            StatusUtil.Status.COMPLETED -> {
                //下载完成，去安装
                AppInstallManager.install(appDownloadTask)
                return
            }

            StatusUtil.Status.IDLE -> {

            }
            //StatusUtil.Status.UNKNOWN
            else -> {

            }
        }
        AppDownloadManager.onDownloadEnqueue(appDownloadTask)
        _appDownloadParams.put(downloadTask.id, appDownloadTask)
        mDownloadSerialQueue.enqueue(downloadTask)
    }

    fun init(context: Context) {

    }

    /**
     * 删除任务
     */
    @WorkerThread
    fun deleteOnBack(appDownloadTask: AppDownloadTask, onDelete: IAB_Listener<Boolean, Int>) {
        val task = getTask(appDownloadTask)
        if (null == task) {
            TaskKHandler.post {
                onDelete.invoke(false, "未找到下载任务！")
            }
            return
        }
        task.cancel()
        mTaskMap.delete(task.id)
        //先从队列中移除
        mDownloadSerialQueue.remove(task)
        OkDownload.with().breakpointStore().remove(task.id)
        task.file?.delete()
        AppFileParamsDaoManager.delete(appFileParams)
        HandlerHelper.post {
            onDelete.invoke(true, "")
            AppDownloadManager.onDeleteTask(appFileParams)
        }
    }

    /**
     * 查询下载状态
     */
    fun getAppDownloadProgress(appDownloadTask: AppDownloadTask): AppDownloadProgress {
        val task = getTask(appFileParams)
        task ?: return AppDownloadStatus().apply {
            appState = AppState.APP_STATE_NOT_DOWNLOADED
        }
        when (StatusUtil.getStatus(task)) {
            StatusUtil.Status.PENDING -> {
                //等待中 不做处理
                return AppDownloadStatus().apply {
                    appState = AppState.APP_STATE_PENDING
                }
            }
            StatusUtil.Status.RUNNING -> {
                //下载中，不做处理
                return AppDownloadStatus().apply {
                    appState = AppState.APP_STATE_DOWNLOAD_IN_PROGRESS
                    progress = StatusUtil.getCurrentInfo(task)?.let {
                        (it.totalOffset.toFloat() / it.totalLength * 100).toInt()
                    } ?: 0
                }
            }
            StatusUtil.Status.COMPLETED -> {
                //下载完成，去安装
                return AppDownloadStatus().apply {
                    appState = AppState.APP_STATE_DOWNLOAD_COMPLETED
                    progress = 100
                }
            }
            StatusUtil.Status.IDLE -> {
                return AppDownloadStatus().apply {
                    appState = AppState.APP_STATE_DOWNLOAD_PAUSED
                    progress = StatusUtil.getCurrentInfo(task)?.let {
                        (it.totalOffset.toFloat() / it.totalLength * 100).toInt()
                    } ?: 0
                }
            }
            //StatusUtil.Status.UNKNOWN
            else -> {
                return AppDownloadStatus().apply {
                    appState = AppState.APP_STATE_NOT_DOWNLOADED
                    progress = StatusUtil.getCurrentInfo(task)?.let {
                        (it.totalOffset.toFloat() / it.totalLength * 100).toInt()
                    } ?: 0
                }
            }
        }
    }
}