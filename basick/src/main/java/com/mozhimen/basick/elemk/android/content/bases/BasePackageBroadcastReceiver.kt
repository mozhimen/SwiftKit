package com.mozhimen.basick.elemk.android.content.bases

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.util.Log
import com.mozhimen.basick.elemk.android.content.cons.CIntent
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.lintk.optin.OptInApiTarget_AtV_25_71_N1
import com.mozhimen.basick.utilk.android.content.UtilKApp
import com.mozhimen.basick.utilk.android.content.UtilKContext

/**
 * @ClassName BaseInstallObserverReceiver
 * @Description if you use it, you must register in manifest first

 * 权限:
无

 * 继承:
class ElemKInstallObserverReceiver : BaseInstallObserverReceiver()

 * 静态注册
<receiver
android:name=".elemk.receiver.ElemKInstallObserverReceiver"
android:enabled="true"
android:exported="true">
<intent-filter android:priority="100">
<action android:name="android.intent.action.PACKAGE_ADDED" />
<action android:name="android.intent.action.PACKAGE_REPLACED" />
<action android:name="android.intent.action.PACKAGE_REMOVED" />
<data android:scheme="package" />
</intent-filter>
</receiver>

 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/6/13 12:04
 * @Version 1.0
 */
@OptInApiTarget_AtV_25_71_N1
@TargetApi(CVersCode.V_25_71_N1)
open class BasePackageBroadcastReceiver : BaseBroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val strPackageName = intent.dataString
        when (intent.action) {
            CIntent.ACTION_PACKAGE_REPLACED -> {
                Log.w(TAG, "onReceiveInstall: update one apk, restart program soon strPackageName $strPackageName // package:${UtilKContext.getPackageName(context)}")
                if (strPackageName == "package:${UtilKContext.getPackageName(context)}") {
                    UtilKApp.restartApp(isKillProcess = true, context = context)
                } else {
                    Log.w(TAG, "onReceiveInstall: strPackageName is different")
                }
            }

            CIntent.ACTION_PACKAGE_ADDED -> {
                Log.w(TAG, "onReceiveInstall: install one apk $strPackageName")
            }

            CIntent.ACTION_PACKAGE_REMOVED -> {
                Log.w(TAG, "onReceiveInstall: uninstall one apk $strPackageName")
            }
        }
    }
}