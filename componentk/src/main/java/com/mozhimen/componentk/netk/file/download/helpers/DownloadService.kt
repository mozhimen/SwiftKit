package com.mozhimen.componentk.netk.file.download.helpers

import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.IBinder
import com.mozhimen.componentk.netk.file.download.CDownloadParameter
import com.mozhimen.componentk.netk.file.download.DownloadRequest
import com.mozhimen.componentk.netk.file.download.DownloaderManager
import com.mozhimen.componentk.netk.file.download.commons.IDownloadListener

/**
 *
 * @author by chiclaim@google.com
 */
internal class DownloadService : Service(), IDownloadListener {

//    private val handler by lazy { Handler(Looper.getMainLooper()) }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val command = super.onStartCommand(intent, flags, startId)
        val url = intent?.getStringExtra(CDownloadParameter.EXTRA_URL) ?: return command
        // 来自通知栏
        if (intent.getIntExtra(CDownloadParameter.EXTRA_FROM, -1) == CDownloadParameter.EXTRA_FROM_NOTIFIER) {
            DownloadRequest(applicationContext, url)
                .registerListener(this)
                .setFromNotifier(true)
                .download()
        } else {
            DownloaderManager.getDownloader(DownloadRequest(applicationContext, url))?.request
                ?.registerListener(this)
                ?.download()
        }
        return command
    }

    override fun onDownloadStart() {
    }

    override fun onDownloadComplete(uri: Uri) {
        tryStopService()
    }

    override fun onDownloadFailed(e: Throwable) {
        tryStopService()
    }

    override fun onProgressUpdate(percent: Int) {
    }

    private fun tryStopService() {
        if (DownloaderManager.runningCount() == 0)
            stopSelf()
    }
}