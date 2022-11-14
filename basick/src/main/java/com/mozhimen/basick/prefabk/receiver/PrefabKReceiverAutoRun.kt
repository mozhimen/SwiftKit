package com.mozhimen.basick.prefabk.receiver

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mozhimen.basick.permissionk.annors.APermissionK
import com.mozhimen.basick.taskk.rxjava.commons.ObserverCallback
import com.mozhimen.basick.taskk.rxjava.helpers.RxJavaScheduler
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * @ClassName PrefabKReceiverAutoRun
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
@APermissionK(permissions = [Manifest.permission.RECEIVE_BOOT_COMPLETED])
open class PrefabKReceiverAutoRun(private val _delayTime: Long, private val _cls: Class<*>) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val action: String? = intent.action
        if (action?.isNotEmpty() == true && action == Intent.ACTION_BOOT_COMPLETED) {
            Observable.just("").delay(_delayTime, TimeUnit.MILLISECONDS).compose(RxJavaScheduler.io2mainObservable()).subscribe(object : ObserverCallback<String>() {
                override fun onComplete() {
                    val rebootIntent = Intent(context, _cls)
                    rebootIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(rebootIntent)
                }
            })
        }
    }
}