package com.mozhimen.componentk.netk.app.install

import androidx.annotation.MainThread
import androidx.annotation.UiThread
import com.mozhimen.basick.lintk.optin.OptInApiCall_BindLifecycle
import com.mozhimen.basick.lintk.optin.OptInApiInit_ByLazy
import com.mozhimen.basick.lintk.optin.OptInApiInit_InApplication
import com.mozhimen.basick.taskk.handler.TaskKHandler
import com.mozhimen.basick.utilk.android.content.UtilKAppInstall
import com.mozhimen.componentk.netk.app.NetKApp
import com.mozhimen.componentk.netk.app.task.db.AppTask
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
    @UiThread
    fun installApkOnUi(appTask: AppTask, fileApk: File) {
        NetKApp.netKAppInstallProxy.setAppTask(appTask)
        installApk(fileApk)
    }

    @JvmStatic
    fun installApk(fileApk: File) {
        UtilKAppInstall.installHand(fileApk)
    }
}