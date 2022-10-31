package com.mozhimen.abilityk.hotupdatek

import android.util.Log
import com.liulishuo.okdownload.DownloadListener
import com.liulishuo.okdownload.DownloadTask
import com.liulishuo.okdownload.core.cause.EndCause
import com.liulishuo.okdownload.core.listener.DownloadListener2
import com.mozhimen.abilityk.hotupdatek.commons.IHotupdateKListener
import com.mozhimen.basick.prefabk.receiver.PrefabKReceiverInstall
import com.mozhimen.underlayk.logk.LogK
import com.mozhimen.basick.utilk.UtilKFile
import com.mozhimen.basick.utilk.UtilKGlobal
import com.mozhimen.basick.utilk.UtilKPackage
import java.lang.StringBuilder
import java.net.URL
import kotlin.concurrent.thread

/**
 * @ClassName HotUpdateK
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/24 12:15
 * @Version 1.0
 */
object HotupdateK {
    private const val TAG = "HotUpdateK>>>>>"
    private val INSTALL_DIRECTORY = UtilKGlobal.instance.getApp()!!.filesDir.absolutePath + "/apk/"
    private val APK_NAME get() = System.currentTimeMillis().toString() + ".apk"

    fun updateApk(nowVersionCode: Int, apkUrl: String, receiver: Class<PrefabKReceiverInstall>, listener: IHotupdateKListener) {
        if (!isNeedUpdate(nowVersionCode)) return
        //delete all cache
        try {
            UtilKFile.deleteFolder(INSTALL_DIRECTORY)
        } catch (e: Exception) {
            LogK.et(TAG, "updateApk: Exception ${e.message}")
            e.printStackTrace()
        }
        //download new apk
        downloadApk(apkUrl, object : DownloadListener2() {
            override fun taskStart(task: DownloadTask) {
                Log.d(TAG, "taskStart: download start")
                val info = task.info
                info?.let {
                    val stringBuilder = StringBuilder()
                    stringBuilder.append("info ")
                    stringBuilder.append(info.url).append(" ")
                    stringBuilder.append(info.filename).append(" ")
                    Log.d(TAG, "taskStart: downloading... $stringBuilder")
                }
                listener.onStart(task)
            }

            override fun fetchProgress(task: DownloadTask, blockIndex: Int, increaseBytes: Long) {
                Log.d(TAG, "fetchProgress: downloading... blockIndex $blockIndex increaseBytes $increaseBytes")
                listener.onProgress(task, blockIndex, increaseBytes)
            }

            override fun taskEnd(
                task: DownloadTask,
                cause: EndCause,
                realCause: Exception?
            ) {
                if (cause == EndCause.COMPLETED) {
                    realCause?.printStackTrace()
                    Log.d(TAG, "taskEnd: download finish")
                    listener.onFinish(task, realCause)
                    val filePath = task.file?.absolutePath
                    if (filePath != null) {
                        Log.d(TAG, "taskEnd: install start")
                        installApk(filePath, receiver)
                    } else {
                        LogK.et(TAG, "taskEnd: lost path")
                    }
                }
            }
        })
    }

    fun isNeedUpdate(nowVersionCode: Int): Boolean =
        UtilKPackage.getPkgVersionCode() < nowVersionCode

    fun downloadApk(urlStr: String, listener: DownloadListener) {
        val url = try {
            URL(urlStr)
        } catch (e: Exception) {
            Log.e(TAG, "downloadApp: Exception ${e.message}")
            e.printStackTrace()
            null
        }
        url?.let {
            val downloadTask = DownloadTask.Builder(urlStr, INSTALL_DIRECTORY, APK_NAME).build()
            downloadTask.enqueue(listener)
        }
    }

    fun installApk(apkPath: String, receiver: Class<PrefabKReceiverInstall>) {
        thread {
            UtilKPackage.installSilence(apkPath, receiver)
        }.start()
    }
}