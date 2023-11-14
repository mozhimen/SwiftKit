package com.mozhimen.componentk.netk.app.install

import com.mozhimen.basick.lintk.optin.OptInApiCall_BindLifecycle
import com.mozhimen.basick.lintk.optin.OptInApiInit_ByLazy
import com.mozhimen.basick.lintk.optin.OptInApiInit_InApplication
import com.mozhimen.basick.utilk.android.content.UtilKAppInstall
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
object NetKAppInstallManager {
    @OptIn(OptInApiCall_BindLifecycle::class, OptInApiInit_ByLazy::class)
    @JvmStatic
    fun installApk(appTask: AppTask, fileApk: File) {
        NetKApp.netKAppInstallProxy.setAppTask(appTask)
        UtilKAppInstall.installHand(fileApk)
    }

    @JvmStatic
    fun installApkSuccess(appTask: AppTask) {
        AppTaskDaoManager.removeAppTaskForDatabase(appTask)

        //将安装状态发给后端
        /*            GlobalScope.launch(Dispatchers.IO) {
                        ApplicationService.install(appDownloadParam0.appId)
                    }*/
        /*            //TODO 如果设置自动删除安装包，安装成功后删除安装包
                    if (AutoDeleteApkSettingHelper.isAutoDelete()) {
                        if (deleteApkFile(appDownloadParam0)) {
                            HandlerHelper.post {
                                AlertTools.showToast("文件已经删除！")
                            }
                        }
                    }*/

        /**
         * [CNetKAppState.STATE_INSTALL_SUCCESS]
         */
        NetKApp.onInstallSuccess(appTask.apply { apkIsInstalled = true })
    }
}