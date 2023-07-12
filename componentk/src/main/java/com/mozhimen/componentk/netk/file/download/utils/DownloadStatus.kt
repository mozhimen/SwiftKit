package com.mozhimen.componentk.netk.file.download.utils

import androidx.annotation.IntDef
import com.mozhimen.componentk.netk.file.download.*
import com.mozhimen.componentk.netk.file.download.cons.CDownloadConstants

/**
 *
 * @author by chiclaim@google.com
 */
@IntDef(value = [CDownloadConstants.STATUS_PENDING, CDownloadConstants.STATUS_PAUSED, CDownloadConstants.STATUS_RUNNING, CDownloadConstants.STATUS_SUCCESSFUL, CDownloadConstants.STATUS_FAILED, CDownloadConstants.STATUS_UNKNOWN])
@Retention(AnnotationRetention.SOURCE)
annotation class DownloadStatus
