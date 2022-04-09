package com.mozhimen.abilityk.hotupdatek

import com.liulishuo.okdownload.DownloadListener
import com.liulishuo.okdownload.DownloadTask
import com.mozhimen.basicsk.utilk.UtilKGlobal
import java.net.URL

/**
 * @ClassName HotUpdateK
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/24 12:15
 * @Version 1.0
 */
object HotUpdateK {
    private const val TAG = "HotUpdateK>>>>>"
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
                UtilKGlobal.instance.getApp()!!.filesDir.absolutePath + INSTALL_DIRECTORY,
                System.currentTimeMillis().toString() + ".apk"
            ).build()
            mTask.enqueue(listener)
        }

    }

    private fun getAppName() = ""
}