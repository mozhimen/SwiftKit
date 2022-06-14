package com.mozhimen.basick.loadk

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mozhimen.basick.logk.LogK
import com.mozhimen.basick.utilk.UtilKApp

/**
 * @ClassName LoadKReceiverInstall
 * @Description if you use it, you must register in manifest first
 * <receiver
 * android:name=".listener.UpdateReceiver" android:exported="false"
 * android:enabled="true">
 * <intent-filter android:priority="100">
 *     <action android:name="android.intent.action.PACKAGE_ADDED" />
 *     <action android:name="android.intent.action.PACKAGE_REPLACED" />
 *     <action android:name="android.intent.action.PACKAGE_REMOVED" />
 *     <data android:scheme="package" />
 * </intent-filter>
 * </receiver>
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/6/13 12:04
 * @Version 1.0
 */
open class LoadKReceiverInstall : BroadcastReceiver() {
    private val TAG = "LoadKReceiverInstall>>>>>"
    override fun onReceive(context: Context, intent: Intent) {
        val packageName = intent.dataString
        when (intent.action) {
            Intent.ACTION_PACKAGE_REPLACED -> {
                LogK.wt(TAG, "onReceive: update one pkg, restart program soon")
                UtilKApp.restartApp()
            }
            Intent.ACTION_PACKAGE_ADDED -> {
                LogK.wt(TAG, "onReceive: install one pkg $packageName")
            }
            Intent.ACTION_PACKAGE_REMOVED -> {
                LogK.wt(TAG, "onReceive: uninstall one pkg $packageName")
            }
        }
    }
}