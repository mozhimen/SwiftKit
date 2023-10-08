package com.mozhimen.componentk.netk.file.download.bases

import android.content.Intent
import com.mozhimen.componentk.netk.file.download.CDownloadParameter
import com.mozhimen.componentk.netk.file.download.DownloadRequest
import com.mozhimen.componentk.netk.file.download.DownloaderManager
import com.mozhimen.componentk.netk.file.download.commons.IDownloader

abstract class BaseDownloader(internal val request: DownloadRequest) : IDownloader {

    override fun download() {
        DownloaderManager.addIfAbsent(this)
        request.onStart()
    }

    override fun startDownload() {
        DownloaderManager.addIfAbsent(this)
        val intent = Intent("${request.context.packageName}.DownloadService")
        intent.setPackage(request.context.packageName)
        intent.putExtra(CDownloadParameter.EXTRA_URL, request.url)
        request.context.startService(intent)
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BaseDownloader) return false
        return request.url == other.request.url
    }

    override fun hashCode(): Int {
        return request.url.hashCode()
    }
}