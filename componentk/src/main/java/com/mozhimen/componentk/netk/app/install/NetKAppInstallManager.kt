package com.mozhimen.componentk.netk.app.install

import android.util.Log
import com.mozhimen.basick.lintk.optin.OptInApiCall_BindLifecycle
import com.mozhimen.basick.lintk.optin.OptInApiInit_ByLazy
import com.mozhimen.basick.lintk.optin.OptInApiInit_InApplication
import com.mozhimen.basick.utilk.android.content.UtilKAppInstall
import com.mozhimen.basick.utilk.bases.IUtilK
import com.mozhimen.componentk.installk.manager.InstallKManager
import com.mozhimen.componentk.netk.app.NetKApp
import com.mozhimen.componentk.netk.app.cons.CNetKAppErrorCode
import com.mozhimen.componentk.netk.app.task.db.AppTask
import com.mozhimen.componentk.netk.app.task.db.AppTaskDaoManager
import com.mozhimen.componentk.netk.app.cons.CNetKAppState
import com.mozhimen.componentk.netk.app.download.mos.intAppErrorCode2appDownloadException
import com.mozhimen.componentk.netk.app.task.NetKAppTaskManager
import java.io.File

/**
 * @ClassName AppInstallManager
 * @Description TODO
 * @Author Mozhimen
 * @Date 2023/11/8 17:21
 * @Version 1.0
 */
@OptInApiInit_InApplication
internal object NetKAppInstallManager : IUtilK {
    @OptIn(OptInApiCall_BindLifecycle::class, OptInApiInit_ByLazy::class)
    @JvmStatic
    fun install(appTask: AppTask, fileApk: File) {
        if (!appTask.canInstall()) {
            Log.e(TAG, "install: the task hasn't unzip or verify success")
            /**
             * Net
             */
            NetKApp.onInstallFail(appTask, CNetKAppErrorCode.CODE_INSTALL_HAST_VERIFY_OR_UNZIP.intAppErrorCode2appDownloadException())
            return
        }
//        if (appTask.isTaskInstall()) {
//            Log.d(TAG, "install: the task already installing")
//            return
//        }
//        /**
//         * [CNetKAppState.STATE_INSTALLING]
//         */
//        NetKApp.onInstalling(appTask)

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

        if (NetKAppTaskManager.isDeleteApkFile) {
            NetKAppUnInstallManager.deleteFileApk(appTask)
        }

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

    /////////////////////////////////////////////////////

    @JvmStatic
    fun installCancel(appTask: AppTask) {
        NetKAppUnInstallManager.deleteFileApk(appTask)

        /**
         * [CNetKAppState.STATE_INzzzzzzSTALL_CANCEL]
         */
        NetKApp.onInstallCancel(appTask)
    }
}