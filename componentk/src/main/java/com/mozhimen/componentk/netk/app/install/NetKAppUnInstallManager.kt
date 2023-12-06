package com.mozhimen.componentk.netk.app.install

import com.mozhimen.basick.lintk.optin.OptInApiInit_InApplication
import com.mozhimen.basick.utilk.java.io.UtilKFileDir
import com.mozhimen.basick.utilk.java.io.deleteFile
import com.mozhimen.basick.utilk.java.io.deleteFolder
import com.mozhimen.componentk.installk.manager.InstallKManager
import com.mozhimen.componentk.netk.app.NetKApp
import com.mozhimen.componentk.netk.app.cons.CNetKAppState
import com.mozhimen.componentk.netk.app.task.db.AppTask
import com.mozhimen.componentk.netk.app.task.db.AppTaskDaoManager
import java.io.File

/**
 * @ClassName NetKAppUnInstallManager
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/12/6 14:51
 * @Version 1.0
 */
internal object NetKAppUnInstallManager {
    @OptIn(OptInApiInit_InApplication::class)
    @JvmStatic
    fun onUninstallSuccess(apkPackageName: String) {
        AppTaskDaoManager.getByApkPackageName(apkPackageName)?.let {
            onUninstallSuccess(it)
        } ?: run {
            InstallKManager.onPackageRemoved(apkPackageName)
        }
    }

    @OptIn(OptInApiInit_InApplication::class)
    @JvmStatic
    fun onUninstallSuccess(appTask: AppTask) {
        InstallKManager.onPackageRemoved(appTask.apkPackageName)

        /**
         * [CNetKAppState.STATE_UNINSTALL_SUCCESS]
         */
        NetKApp.onUninstallSuccess(appTask)
    }

    ///////////////////////////////////////////////////////////////////

    /**
     * 删除Apk文件
     */
    @JvmStatic
    fun deleteFileApk(appTask: AppTask): Boolean {
        val externalFilesDir = UtilKFileDir.External.getFilesDownloadsDir() ?: return true
        File(externalFilesDir, appTask.apkFileName).deleteFile()
        if (appTask.apkFileName.endsWith(".npk")) {//如果是npk,删除解压的文件夹
            File(externalFilesDir, appTask.apkFileName.split(".npk")[0]).deleteFolder()
        }
        return true
    }
}