package com.mozhimen.componentk.netk.app.download.mos

import com.mozhimen.componentk.netk.app.cons.CNetKAppState

/**
 * @ClassName AppDownloadProgress
 * @Description TODO
 * @Author Mozhimen
 * @Date 2023/11/8 13:56
 * @Version 1.0
 */
data class MAppDownloadProgress(
    var progressState: Int = CNetKAppState.STATE_TASK_CREATE,
    var progress: Int = 0
) {
    fun isDownloading(): Boolean =
        CNetKAppState.isDownloading(progressState)
}