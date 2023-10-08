package com.mozhimen.basick.elemk.android.app.cons

import android.app.DownloadManager

/**
 * @ClassName CDownloadManager
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/9/18 14:57
 * @Version 1.0
 */
object CDownloadManager {
    const val STATUS_PENDING = DownloadManager.STATUS_PENDING
    const val STATUS_PAUSED = DownloadManager.STATUS_PAUSED
    const val STATUS_RUNNING = DownloadManager.STATUS_RUNNING
    const val STATUS_SUCCESSFUL = DownloadManager.STATUS_SUCCESSFUL
    const val STATUS_FAILED = DownloadManager.STATUS_FAILED
    const val STATUS_UNKNOWN = -1

    const val EXTRA_DOWNLOAD_ID = DownloadManager.EXTRA_DOWNLOAD_ID

    const val ACTION_DOWNLOAD_COMPLETE = DownloadManager.ACTION_DOWNLOAD_COMPLETE

    object Request {
        /**
         * 仅在下载中展示通知，下载完成通知则会消失
         */
        const val VISIBILITY_VISIBLE = DownloadManager.Request.VISIBILITY_VISIBLE

        /**
         * 不展示通知栏.
         * > 如果下载模式为 [DownloadEngine.DOWNLOAD_MANAGER], 需要添加权限 android.permission.DOWNLOAD_WITHOUT_NOTIFICATION
         */
        const val VISIBILITY_HIDDEN = DownloadManager.Request.VISIBILITY_HIDDEN

        /**
         * 下载中和下载完成都会显示通知
         */
        const val VISIBILITY_VISIBLE_NOTIFY_COMPLETED =
            DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED

        /**
         * 仅在下载完成时展示通知
         */
        const val VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION =
            DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION
    }
}