package com.mozhimen.componentk.netk.file.download.annors

import androidx.annotation.IntDef
import com.mozhimen.basick.elemk.android.app.cons.CDownloadManager
import com.mozhimen.componentk.netk.file.download.*

/**
 *
 * @author by chiclaim@google.com
 */
@IntDef(
    value = [
        CDownloadManager.STATUS_PENDING,
        CDownloadManager.STATUS_PAUSED,
        CDownloadManager.STATUS_RUNNING,
        CDownloadManager.STATUS_SUCCESSFUL,
        CDownloadManager.STATUS_FAILED,
        CDownloadManager.STATUS_UNKNOWN
    ]
)
@Retention(AnnotationRetention.SOURCE)
annotation class ADownloadStatus
