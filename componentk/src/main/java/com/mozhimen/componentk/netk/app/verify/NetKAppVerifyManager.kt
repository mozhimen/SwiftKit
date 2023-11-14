package com.mozhimen.componentk.netk.app.verify

import android.text.TextUtils
import android.util.Log
import com.mozhimen.basick.lintk.optin.OptInApiInit_InApplication
import com.mozhimen.basick.utilk.bases.IUtilK
import com.mozhimen.basick.utilk.java.io.UtilKFileDir
import com.mozhimen.basick.utilk.java.io.deleteFile
import com.mozhimen.basick.utilk.java.io.file2strMd5
import com.mozhimen.componentk.netk.app.NetKApp
import com.mozhimen.componentk.netk.app.cons.CNetKAppErrorCode
import com.mozhimen.componentk.netk.app.cons.CNetKAppState
import com.mozhimen.componentk.netk.app.download.mos.AppDownloadException
import com.mozhimen.componentk.netk.app.download.mos.int2appDownloadException
import com.mozhimen.componentk.netk.app.task.db.AppTask
import com.mozhimen.componentk.netk.app.unzip.NetKAppUnzipManager
import java.io.File

/**
 * @ClassName AppVerifyManager
 * @Description TODO
 * @Author Mozhimen
 * @Date 2023/11/8 17:01
 * @Version 1.0
 */
@OptInApiInit_InApplication
object NetKAppVerifyManager : IUtilK {
    @JvmStatic
    fun verify(appTask: AppTask) {
        /**
         * [CNetKAppState.STATE_VERIFYING]
         */
        NetKApp.onVerifying(appTask)

        if (appTask.apkName.endsWith(".npk"))//如果文件以.npk结尾则先解压
            verifyAndUnzipNpk(appTask)
        else
            verifyApk(appTask)
    }

    /**
     * 判断是否需要校验MD5值
     * 1、NPK不需要校验MD5值
     * 2、如果是使用站内地址下载，不用校验MD5值
     * 3、如果使用站外地址，且没有站内地址，且第一次校验失败，则第二次时不用校验
     */
    @JvmStatic
    fun isNeedVerify(appTask: AppTask): Boolean {
        if (appTask.apkName.endsWith(".npk"))
            return false
        if (appTask.downloadUrlCurrent == appTask.downloadUrl) {//如果是使用站内地址下载，不用校验MD5值
            return false
        }
        return appTask.apkVerifyNeed
    }

    /**
     * 安装.npk文件
     */
    private fun verifyAndUnzipNpk(appTask: AppTask) {
        if (NetKAppUnzipManager.isUnziping(appTask)) {
            Log.d(TAG, "verifyAndUnzipNpk: isUnziping")
            return//正在解压中，不进行操作
        }

        val externalFilesDir = UtilKFileDir.External.getFilesDownloadsDir()
        if (externalFilesDir == null) {
            /**
             * [CNetKAppState.STATE_VERIFY_FAIL]
             */
            NetKApp.onVerifyFail(appTask, CNetKAppErrorCode.CODE_VERIFY_DOWNLOAD_DIR_NULL.int2appDownloadException())
            Log.d(TAG, "verifyAndUnzipNpk: getFilesDownloadsDir is null")
            return
        }
        val fileApk = File(externalFilesDir, appTask.apkName)
        if (!fileApk.exists()) {
            /**
             * [CNetKAppState.STATE_VERIFY_FAIL]
             */
            NetKApp.onVerifyFail(appTask, CNetKAppErrorCode.CODE_VERIFY_DOWNLOAD_FILE_NOT_EXIST.int2appDownloadException())
            Log.d(TAG, "verifyAndUnzipNpk: download file fail")
            return
        }

        if (isNeedVerify(appTask)) {
            val apkFileMd5Remote = appTask.apkFileMd5
            if (apkFileMd5Remote.isNotEmpty() && "NONE" != apkFileMd5Remote) {
                val apkFileMd5Locale = fileApk.file2strMd5()//取文件的MD5值
                if (!TextUtils.equals(apkFileMd5Remote, apkFileMd5Locale)) {
                    /**
                     * [CNetKAppState.STATE_VERIFY_FAIL]
                     */
                    NetKApp.onVerifyFail(appTask, CNetKAppErrorCode.CODE_VERIFY_MD5_FAIL.int2appDownloadException())
                    Log.d(TAG, "verifyAndUnzipNpk: download file fail")
                    return
                }
            }
        }
        /**
         * [CNetKAppState.STATE_VERIFY_SUCCESS]
         */
        NetKApp.onVerifySuccess(appTask)

        NetKAppUnzipManager.unzip(appTask.apply {
            apkPathName = fileApk.absolutePath
        })
    }

    /**
     * 安装apk文件
     */
    private fun verifyApk(appTask: AppTask) {
        if (appTask.apkFileMd5.isEmpty() || "NONE" == appTask.apkFileMd5) {//如果文件没有MD5值或者为空，则不校验 直接去安装
            /**
             * [CNetKAppState.STATE_VERIFY_SUCCESS]
             */
            NetKApp.onVerifySuccess(appTask)

            NetKAppUnzipManager.unzip(appTask.apply {
                apkPathName = fileApk.absolutePath
            })

            /**
             * [CNetKAppState.STATE_INSTALL_CREATE]
             */
            NetKApp.onInstallCreate(appTask.apply {
                apkPathName = File(UtilKFileDir.External.getFilesDownloadsDir() ?: return, appTask.apkName).absolutePath
            })
            return
        }


        val externalFilesDir = UtilKFileDir.External.getFilesDownloadsDir() ?: kotlin.run {
            /**
             * [CNetKAppState.STATE_VERIFY_FAIL]
             */
            NetKApp.onVerifyFail(appTask)
            return
        }
        val fileApk = File(externalFilesDir, appTask.apkName)
        if (!fileApk.exists()) {
            /**
             * [CNetKAppState.STATE_VERIFY_FAIL]
             */
            NetKApp.onVerifyFail(appTask)
            return
        }

        if (isNeedVerify(appTask)) {//判断是否需要校验MD5值
            val apkFileMd5Remote = appTask.apkFileMd5//如果本地文件存在，且MD5值相等
            if (apkFileMd5Remote.isNotEmpty()) {
                val apkFileMd5Locale = (fileApk.file2strMd5() ?: "") /*+ "1"*///取文件的MD5值
                if (!TextUtils.equals(apkFileMd5Remote, apkFileMd5Locale)) {
                    /**
                     * [CNetKAppState.STATE_VERIFY_FAIL]
                     */
                    NetKApp.onVerifyFail(appTask)

                    fileApk.deleteFile()//删除本地文件
                    if (appTask.downloadUrlCurrent != appTask.downloadUrl) {//重新使用内部地址下载
                        if (appTask.downloadUrl.isNotEmpty()) {
                            appTask.downloadUrlCurrent = appTask.downloadUrl
                            NetKApp.taskStart(appTask)
                        } else {
                            appTask.apkVerifyNeed = false//重新下载，下次不校验MD5值
                            NetKApp.taskStart(appTask)
                        }
                    }
                    return
                }
            }
        }
        /**
         * [CNetKAppState.STATE_VERIFY_SUCCESS]
         */
        NetKApp.onVerifySuccess(appTask)//检测通过，去安装
        /**
         * [CNetKAppState.STATE_INSTALL_CREATE]
         */
        NetKApp.onInstallCreate(appTask.apply {
            apkPathName = fileApk.absolutePath
        })//调用安装的回调
    }
}