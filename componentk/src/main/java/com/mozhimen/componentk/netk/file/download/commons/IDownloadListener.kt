package com.mozhimen.componentk.netk.file.download.commons

import android.net.Uri

/**
 * @author by chiclaim@google.com
 */
interface IDownloadListener {
    fun onDownloadStart()
    fun onDownloadComplete(uri: Uri)
    fun onDownloadFailed(e: Throwable)
    fun onProgressUpdate(percent: Int)
}