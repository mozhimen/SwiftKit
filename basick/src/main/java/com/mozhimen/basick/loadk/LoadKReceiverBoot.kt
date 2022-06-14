package com.mozhimen.basick.loadk

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

/**
 * @ClassName LoadKReceiverBoot
 * @Description
 * How to use : you must register in manifest, like:
 * <receiver
 * android:name=".receivers.BootReceiver"
 * android:enabled="true"
 * android:exported="true">
 * <intent-filter android:priority="1000">
 *     <action android:name="android.intent.action.BOOT_COMPLETED" />
 * </intent-filter>
 * </receiver>
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/6/13 11:55
 * @Version 1.0
 */
open class LoadKReceiverBoot(private val _cls: Class<*>) : BroadcastReceiver() {
    private val TAG = "LoadKReceiverBoot>>>>>"
    override fun onReceive(context: Context, intent: Intent) {
        val action: String? = intent.action
        Log.d(TAG, "onReceive $action")
        if (action == Intent.ACTION_BOOT_COMPLETED) {
            val reIntent = Intent(context, _cls)
            reIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(reIntent)
        }
    }
}