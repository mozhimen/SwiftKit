package com.mozhimen.componentk.netk.file.download.helpers

import android.content.Context
import android.content.Intent
import com.mozhimen.basick.elemk.android.app.cons.CDownloadManager
import com.mozhimen.basick.elemk.android.content.bases.BaseBroadcastReceiver
import com.mozhimen.basick.utilk.android.app.UtilKDownloadManager

/**
 * @author by chiclaim@google.com
 */
class SystemDownloadBroadcastReceiver : BaseBroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        require(intent != null && context != null)
        if (CDownloadManager.ACTION_DOWNLOAD_COMPLETE == intent.action) {
            val downloadApkId = intent.getLongExtra(CDownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (downloadApkId != -1L)
                installApk(context, downloadApkId)
        }// else if (DownloadManager.ACTION_NOTIFICATION_CLICKED == intent.action) {
        //如果还未完成下载，用户点击 Notification
        //val viewDownloadIntent = Intent(DownloadManager.ACTION_VIEW_DOWNLOADS)
        //viewDownloadIntent.addFlags(CIntent.FLAG_ACTIVITY_NEW_TASK)
        //context.startActivity(viewDownloadIntent)
        // }
    }

    private fun installApk(context: Context, downloadApkId: Long) {
        UtilKDownloadManager.getUriForDownloadedFile(context, downloadApkId)?.let {
            //TODO
            //startInstall(context, it)
        }
    }
}