package com.mozhimen.componentk.netk.file.download

import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import com.mozhimen.componentk.netk.file.download.commons.IDownloadListener

/**
 *
 * @author by chiclaim@google.com
 */
internal class DownloadService : Service(), IDownloadListener {

    private val handler by lazy {
        Handler(Looper.getMainLooper())
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val c = super.onStartCommand(intent, flags, startId)
        val url = intent?.getStringExtra(CDownloadParameter.EXTRA_URL) ?: return c
        // 来自通知栏
        if (intent.getIntExtra(CDownloadParameter.EXTRA_FROM, -1) == CDownloadParameter.EXTRA_FROM_NOTIFIER) {
            DownloadRequest(applicationContext, url)
                .registerListener(this)
                .setFromNotifier(true)
                .download()
        } else {
            val downloader: Downloader? =
                DownloaderManager.getDownloader(DownloadRequest(applicationContext, url))
            downloader?.request?.registerListener(this)
            downloader?.download()
        }
        return c
    }

    override fun onDownloadStart() {
    }

    override fun onProgressUpdate(percent: Int) {
    }

    override fun onDownloadComplete(uri: Uri) {
        tryStopService()
    }

    override fun onDownloadFailed(e: Throwable) {
        tryStopService()
    }

    private fun tryStopService() {
        if (DownloaderManager.runningCount() == 0) {
            stopSelf()
        }
    }
}