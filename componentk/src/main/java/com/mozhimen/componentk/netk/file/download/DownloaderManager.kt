package com.mozhimen.componentk.netk.file.download

import java.util.concurrent.ConcurrentHashMap

internal object DownloaderManager {

    private val _downloadingMap = ConcurrentHashMap<DownloadRequest, Downloader>()

    fun addIfAbsent(downloader: Downloader) {
        _downloadingMap[downloader.request] = downloader
    }

    fun remove(vararg requests: DownloadRequest) {
        for (request in requests) _downloadingMap.remove(request)
    }

    fun runningCount() = _downloadingMap.size

    fun isRunning(request: DownloadRequest) = _downloadingMap.containsKey(request)

    fun getDownloader(request: DownloadRequest): Downloader? = _downloadingMap[request]
}