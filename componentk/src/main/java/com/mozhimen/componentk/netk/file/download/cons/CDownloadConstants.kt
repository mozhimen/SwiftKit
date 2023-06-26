package com.mozhimen.componentk.netk.file.download.cons

import android.app.DownloadManager
import com.mozhimen.basick.utilk.android.util.UtilKLog.dt
import com.mozhimen.basick.utilk.bases.BaseUtilK

object CDownloadConstants : BaseUtilK() {
    const val STATUS_PENDING = DownloadManager.STATUS_PENDING
    const val STATUS_PAUSED = DownloadManager.STATUS_PAUSED
    const val STATUS_RUNNING = DownloadManager.STATUS_RUNNING
    const val STATUS_SUCCESSFUL = DownloadManager.STATUS_SUCCESSFUL
    const val STATUS_FAILED = DownloadManager.STATUS_FAILED
    const val STATUS_UNKNOWN = -1

    /**
     * 仅在下载中展示通知，下载完成通知则会消失
     */
    const val NOTIFIER_VISIBLE = DownloadManager.Request.VISIBILITY_VISIBLE

    /**
     * 不展示通知栏.
     *
     * > 如果下载模式为 [DownloadEngine.DOWNLOAD_MANAGER], 需要添加权限 android.permission.DOWNLOAD_WITHOUT_NOTIFICATION
     */
    const val NOTIFIER_HIDDEN = DownloadManager.Request.VISIBILITY_HIDDEN

    /**
     * 下载中和下载完成都会显示通知
     */
    const val NOTIFIER_VISIBLE_NOTIFY_COMPLETED =
        DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED

    /**
     * 仅在下载完成时展示通知
     */
    const val NOTIFIER_VISIBLE_NOTIFY_ONLY_COMPLETION =
        DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION

    @JvmStatic
    fun printDownloadStatus(downloadId: Long, status: Int) {
        when (status) {
            STATUS_PENDING -> dt(TAG, "downloadId=$downloadId, status=STATUS_PENDING")
            STATUS_PAUSED -> dt(TAG, "downloadId=$downloadId, status=STATUS_PAUSED")
            STATUS_RUNNING -> dt(TAG, "downloadId=$downloadId, status=STATUS_RUNNING")
            STATUS_SUCCESSFUL -> dt(TAG, "downloadId=$downloadId, status=STATUS_SUCCESSFUL")
            STATUS_FAILED -> dt(TAG, "downloadId=$downloadId, status=STATUS_FAILED")
            STATUS_UNKNOWN -> dt(TAG, "downloadId=$downloadId, status=STATUS_UNKNOWN")
        }
    }
}
