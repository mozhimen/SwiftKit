package com.mozhimen.componentk.netk.app.install.helpers

import android.content.Context
import android.content.Intent
import android.util.Log
import com.mozhimen.basick.elemk.android.content.bases.BaseBroadcastReceiver
import com.mozhimen.basick.elemk.android.content.cons.CIntent
import com.mozhimen.basick.lintk.optin.OptInApiInit_InApplication
import com.mozhimen.componentk.installk.manager.InstallKManager
import com.mozhimen.componentk.netk.app.NetKApp
import com.mozhimen.componentk.netk.app.cons.CNetKAppState
import com.mozhimen.componentk.netk.app.install.NetKAppInstallManager
import com.mozhimen.componentk.netk.app.task.db.AppTask

/**
 * @ClassName InstallKFlymeReceiver
 * @Description TODO
 * @Author Mozhimen
 * @Date 2023/11/9 9:30
 * @Version 1.0
 */
class NetKAppInstallReceiver : BaseBroadcastReceiver() {
    @OptIn(OptInApiInit_InApplication::class)
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            intent?.let { intent ->
                intent.dataString?.let { packName ->
                    val apkPackName = packName.split(":")[1]
                    if (apkPackName.isEmpty()) return
                    Log.i(TAG, "onReceive: action ${intent.action} apkPackName $apkPackName")
                    when (intent.action) {
                        CIntent.ACTION_PACKAGE_REMOVED -> {//需要主动移除掉保存的应用
                            NetKAppInstallManager.onUninstallSuccess(apkPackName)
                        }

                        CIntent.ACTION_PACKAGE_ADDED, CIntent.ACTION_PACKAGE_REPLACED -> {//有应用发生变化，强制刷新应用
                            NetKAppInstallManager.onInstallSuccess(apkPackName)
                        }
                    }
                }
            }
        }
    }
}