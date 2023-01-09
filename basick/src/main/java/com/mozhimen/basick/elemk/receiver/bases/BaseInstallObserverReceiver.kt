package com.mozhimen.basick.elemk.receiver.bases

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.annotation.CallSuper
import com.mozhimen.basick.elemk.annors.ADescription
import com.mozhimen.basick.elemk.receiver.commons.IReceiverInstallListener
import com.mozhimen.basick.utilk.app.UtilKApp

/**
 * @ClassName BaseInstallObserverReceiver
 * @Description if you use it, you must register in manifest first

 * 继承
class ElemKInstallObserverReceiver : BaseInstallObserverReceiver()

 * 静态注册
<receiver
android:name=".elemk.receiver.ElemKInstallObserverReceiver" android:exported="true"
android:enabled="true">
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
@ADescription(
    ">> you should set your target sdk as 25, because android 8 later all limited",
    ">> 你必须设置你的targetSDK:25, 因为android8后有限制"
)
open class BaseInstallObserverReceiver(private val _listener: IReceiverInstallListener? = null) : BroadcastReceiver() {
    companion object {
        private val TAG = "${this::class.java.simpleName}>>>>>"
    }

    override fun onReceive(context: Context, intent: Intent) {
        onReceiveInstall(context, intent)
    }

    @CallSuper
    fun onReceiveInstall(context: Context, intent: Intent) {
        val packageName = intent.dataString
        when (intent.action) {
            Intent.ACTION_PACKAGE_REPLACED -> {
                Log.w(TAG, "onReceiveInstall: update one pkg, restart program soon packageName $packageName")
                _listener?.onAppUpdate()
                if (packageName == "package:" + context.packageName) {
                    UtilKApp.restartApp(true)
                }
            }
            Intent.ACTION_PACKAGE_ADDED -> {
                Log.w(TAG, "onReceiveInstall: install one pkg $packageName")
                _listener?.onAppInstall()
            }
            Intent.ACTION_PACKAGE_REMOVED -> {
                Log.w(TAG, "onReceiveInstall: uninstall one pkg $packageName")
                _listener?.onAppUnInstall()
            }
        }
    }
}