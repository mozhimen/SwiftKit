package com.mozhimen.componentk.netk.file.download

import com.liulishuo.okdownload.DownloadTask

/**
 * @ClassName INetKDownloadListener
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/1 21:58
 * @Version 1.0
 */
interface ISingleFileDownloadListener {
    fun onStart(task: DownloadTask)
    fun onProgress(task: DownloadTask, blockIndex: Int, increaseBytes: Long)
    fun onComplete(task: DownloadTask)
    fun onFail(task: DownloadTask, e: Exception?)
}