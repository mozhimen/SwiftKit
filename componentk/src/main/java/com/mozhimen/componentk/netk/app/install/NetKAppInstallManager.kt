package com.mozhimen.componentk.netk.app.install

import android.util.Log
import com.mozhimen.basick.lintk.optin.OptInApiCall_BindLifecycle
import com.mozhimen.basick.lintk.optin.OptInApiInit_ByLazy
import com.mozhimen.basick.lintk.optin.OptInApiInit_InApplication
import com.mozhimen.basick.utilk.android.content.UtilKAppInstall
import com.mozhimen.basick.utilk.bases.IUtilK
import com.mozhimen.componentk.installk.manager.InstallKManager
import com.mozhimen.componentk.netk.app.NetKApp
import com.mozhimen.componentk.netk.app.task.db.AppTask
import com.mozhimen.componentk.netk.app.task.db.AppTaskDaoManager
import com.mozhimen.componentk.netk.app.cons.CNetKAppState
import java.io.File

/**
 * @ClassName AppInstallManager
 * @Description TODO
 * @Author Mozhimen
 * @Date 2023/11/8 17:21
 * @Version 1.0
 */
@OptInApiInit_InApplication
object NetKAppInstallManager : IUtilK {
    @OptIn(OptInApiCall_BindLifecycle::class, OptInApiInit_ByLazy::class)
    @JvmStatic
    fun install(appTask: AppTask, fileApk: File) {
        if (appTask.isTaskInstall()) {
            Log.d(TAG, "install: the task already install")
            return
        }
        /**
         * [CNetKAppState.STATE_INSTALLING]
         */
        NetKApp.onInstalling(appTask)

        NetKApp.netKAppInstallProxy.setAppTask(appTask)

        UtilKAppInstall.installHand(fileApk)
    }

    @JvmStatic
    fun onInstallSuccess(apkPackageName: String) {
        AppTaskDaoManager.getByApkPackageName(apkPackageName)?.let {
            onInstallSuccess(it)
        } ?: run {
            InstallKManager.onPackageAdded(apkPackageName)
        }
    }

    @JvmStatic
    fun onInstallSuccess(appTask: AppTask) {
        InstallKManager.onPackageAdded(appTask.apkPackageName)

        AppTaskDaoManager.removeAppTaskForDatabase(appTask)

        //将安装状态发给后端
        /*            GlobalScope.launch(Dispatchers.IO) {
                        ApplicationService.install(appDownloadParam0.appId)
                    }*/
        //如果设置自动删除安装包，安装成功后删除安装包
        /*if (AutoDeleteApkSettingHelper.isAutoDelete()) {
                        if (deleteApkFile(appDownloadParam0)) {
                            HandlerHelper.post {
                                AlertTools.showToast("文件已经删除！")
                            }
                        }
                    }*/

        /**
         * [CNetKAppState.STATE_INSTALL_SUCCESS]
         */
        NetKApp.onInstallSuccess(appTask)
    }

    @JvmStatic
    fun onUninstallSuccess(apkPackageName: String) {
        AppTaskDaoManager.getByApkPackageName(apkPackageName)?.let {
            onUninstallSuccess(it)
        } ?: run {
            InstallKManager.onPackageRemoved(apkPackageName)
        }
    }

    @JvmStatic
    fun onUninstallSuccess(appTask: AppTask) {
        InstallKManager.onPackageRemoved(appTask.apkPackageName)

        /**
         * [CNetKAppState.STATE_INSTALL_SUCCESS]
         */
        NetKApp.onUninstallSuccess(appTask)
    }
}