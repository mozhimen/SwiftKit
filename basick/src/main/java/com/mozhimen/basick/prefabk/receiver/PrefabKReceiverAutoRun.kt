package com.mozhimen.basick.prefabk.receiver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

/**
 * @ClassName LoadKReceiverAutoRun
 * @Description
 * 权限:
 * <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
 *
 * 继承:
 * class AutoRunReceiver() : LoadKReceiverAutoRun(LoadKActivity::class.java) {
 * companion object {private val DELAY_TIME = 15//s}
 *
 * override fun onReceive(context: Context, intent: Intent) {
 *  runBlocking {
 *      delay(DELAY_TIME * 1000L)
 *      launch(context, intent)
 *  }
 * }}
 * 注册:
 * <receiver
 * android:name=".loadk.AutoRunReceiver"
 * android:enabled="true"
 * android:exported="true">
 *  <intent-filter android:priority="1000">
 *      <action android:name="android.intent.action.BOOT_COMPLETED" />
 *  </intent-filter>
 * </receiver>
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/6/13 11:55
 * @Version 1.0
 */
open class PrefabKReceiverAutoRun(private val _cls: Class<*>) : BroadcastReceiver() {
    private val TAG = "LoadKReceiverAutoRun>>>>>"

    override fun onReceive(context: Context, intent: Intent) {
        launch(context, intent)
    }

    @SuppressLint("LongLogTag")
    fun launch(context: Context, intent: Intent) {
        val action: String? = intent.action
        Log.d(TAG, "onReceive $action")
        if (action == Intent.ACTION_BOOT_COMPLETED) {
            val reIntent = Intent(context, _cls)
            reIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(reIntent)
        }
    }
}