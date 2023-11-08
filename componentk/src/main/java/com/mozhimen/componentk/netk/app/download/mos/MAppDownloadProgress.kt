package com.mozhimen.componentk.netk.app.download.mos

import com.mozhimen.componentk.netk.app.download.cons.CAppDownloadState

/**
 * @ClassName AppDownloadProgress
 * @Description TODO
 * @Author Mozhimen
 * @Date 2023/11/8 13:56
 * @Version 1.0
 */
data class MAppDownloadProgress(
    val progressState: Int = CAppDownloadState.STATE_TASK_CREATE,
    val progress: Int = 0
) {
    fun isDownloading(): Boolean =
        CAppDownloadState.isDownloading(progressState)
}