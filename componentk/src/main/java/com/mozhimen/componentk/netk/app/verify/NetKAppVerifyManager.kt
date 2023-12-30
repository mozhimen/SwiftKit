package com.mozhimen.componentk.netk.app.verify

import android.text.TextUtils
import android.util.Log
import com.mozhimen.basick.lintk.optin.OptInApiInit_InApplication
import com.mozhimen.basick.utilk.bases.IUtilK
import com.mozhimen.basick.utilk.java.io.UtilKFileDir
import com.mozhimen.basick.utilk.java.io.file2strFilePath
import com.mozhimen.basick.utilk.java.io.file2strMd5
import com.mozhimen.componentk.netk.app.NetKApp
import com.mozhimen.componentk.netk.app.cons.CNetKAppErrorCode
import com.mozhimen.componentk.netk.app.cons.CNetKAppState
import com.mozhimen.componentk.netk.app.download.mos.intAppErrorCode2appDownloadException
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
internal object NetKAppVerifyManager : IUtilK {

    @JvmStatic
    fun verify(appTask: AppTask) {
        if (appTask.isTaskUnzip()) {
            Log.d(TAG, "verify: the task already verify")
            return
        }
        /**
         * [CNetKAppState.STATE_VERIFYING]
         */
        NetKApp.onVerifying(appTask)

        Log.d(TAG, "verify: apkFileName ${appTask.apkFileName}")

        if (appTask.apkFileName.endsWith(".apk") || appTask.apkFileName.endsWith(".npk")) {//如果文件以.npk结尾则先解压
            verifyApp(appTask)
        } else {
            /**
             * [CNetKAppState.STATE_VERIFY_FAIL]
             */
            NetKApp.onVerifyFail(appTask, CNetKAppErrorCode.CODE_VERIFY_FORMAT_INVALID.intAppErrorCode2appDownloadException())
            Log.d(TAG, "verifyAndUnzipNpk: getFilesDownloadsDir is null")
        }
    }

    /**
     * 安装.npk文件
     */
    private fun verifyApp(appTask: AppTask) {
        if (!isNeedVerify(appTask)) {//如果文件没有MD5值或者为空，则不校验 直接去安装
            onVerifySuccess(appTask, File(UtilKFileDir.External.getFilesDownloadsDir() ?: return, appTask.apkFileName))
            return
        }

        if (NetKAppUnzipManager.isUnziping(appTask)) {
            Log.d(TAG, "verifyAndUnzipNpk: isUnziping")
            return//正在解压中，不进行操作
        }

        val externalFilesDir = UtilKFileDir.External.getFilesDownloadsDir() ?: run {
            /**
             * [CNetKAppState.STATE_VERIFY_FAIL]
             */
            NetKApp.onVerifyFail(appTask, CNetKAppErrorCode.CODE_VERIFY_DIR_NULL.intAppErrorCode2appDownloadException())
            Log.e(TAG, "verifyAndUnzipNpk: getFilesDownloadsDir is null")
            return
        }
        val fileApk = File(externalFilesDir, appTask.apkFileName)
        if (!fileApk.exists()) {
            /**
             * [CNetKAppState.STATE_VERIFY_FAIL]
             */
            NetKApp.onVerifyFail(appTask, CNetKAppErrorCode.CODE_VERIFY_FILE_NOT_EXIST.intAppErrorCode2appDownloadException())
            Log.e(TAG, "verifyAndUnzipNpk: download file fail")
            return
        }

        if (isNeedVerify(appTask)) {
            val apkFileMd5Locale = fileApk.file2strMd5()//取文件的MD5值
            if (!TextUtils.equals(appTask.apkFileMd5, apkFileMd5Locale)) {
                /**
                 * [CNetKAppState.STATE_VERIFY_FAIL]
                 */
                NetKApp.onVerifyFail(appTask, CNetKAppErrorCode.CODE_VERIFY_MD5_FAIL.intAppErrorCode2appDownloadException())
                Log.e(TAG, "verifyAndUnzipNpk: download file fail")

                NetKApp.taskRetry(appTask.apply {
                    apkPathName = fileApk.file2strFilePath()
                })
                return
            }
        }

        onVerifySuccess(appTask, fileApk)
    }

    private fun onVerifySuccess(appTask: AppTask, fileApk: File) {
        /**
         * [CNetKAppState.STATE_VERIFY_SUCCESS]
         */
        NetKApp.onVerifySuccess(appTask)//检测通过，去安装

        NetKAppUnzipManager.unzip(appTask.apply {
            apkPathName = fileApk.file2strFilePath()
        })
    }

    //////////////////////////////////////////////////////////////////

    /**
     * 判断是否需要校验MD5值
     * 1、NPK不需要校验MD5值
     * 2、如果是使用站内地址下载，不用校验MD5值
     * 3、如果使用站外地址，且没有站内地址，且第一次校验失败，则第二次时不用校验
     */
    @JvmStatic
    private fun isNeedVerify(appTask: AppTask): Boolean {
//        if (appTask.apkName.endsWith(".npk"))
//            return false
//        if (appTask.downloadUrlCurrent == appTask.downloadUrl) {//如果是使用站内地址下载，不用校验MD5值
//            return false
//        }
        return appTask.apkVerifyNeed && appTask.apkFileMd5.isNotEmpty()
    }
}