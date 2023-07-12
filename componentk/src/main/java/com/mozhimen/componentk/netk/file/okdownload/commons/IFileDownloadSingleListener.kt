package com.mozhimen.componentk.netk.file.okdownload.commons

import com.liulishuo.okdownload.DownloadTask
import com.mozhimen.basick.lintk.optin.annors.ALintKOptIn_ApiDeprecated_ThirdParty

/**
 * @ClassName INetKDownloadListener
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/1 21:58
 * @Version 1.0
 */
@ALintKOptIn_ApiDeprecated_ThirdParty
@Deprecated("okdownload is deprecated")
interface IFileDownloadSingleListener {
    fun onStart(task: DownloadTask) {}
    fun onProgress(task: DownloadTask, totalIndex: Int, totalBytes: Long) {}
    fun onComplete(task: DownloadTask)
    fun onFail(task: DownloadTask, e: Exception?)
}