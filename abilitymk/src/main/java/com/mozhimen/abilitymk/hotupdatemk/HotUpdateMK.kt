package com.mozhimen.abilitymk.hotupdatemk

import com.liulishuo.okdownload.DownloadListener
import com.liulishuo.okdownload.DownloadTask
import com.mozhimen.basicsmk.utilmk.UtilMKGlobal
import java.net.URL

/**
 * @ClassName HotUpdateMK
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/24 12:15
 * @Version 1.0
 */
object HotUpdateMK {
    private const val TAG = "HotUpdateMK>>>>>"
    private const val INSTALL_DIRECTORY = "/apk/"

    private fun downloadApp(urlStr: String, listener: DownloadListener) {
        val url = try {
            URL(urlStr)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            null
        }
        url?.let {
            val mTask = DownloadTask.Builder(
                urlStr,
                UtilMKGlobal.instance.getApp()!!.filesDir.absolutePath + INSTALL_DIRECTORY,
                System.currentTimeMillis().toString() + ".apk"
            ).build()
            mTask.enqueue(listener)
        }

    }

    private fun getAppName() = ""
}