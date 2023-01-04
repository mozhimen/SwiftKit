package com.mozhimen.basick.elemk.receiver.bases

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.annotation.CallSuper
import com.mozhimen.basick.permissionk.annors.APermissionK
import com.mozhimen.basick.elemk.rxjava.commons.ObserverCallback
import com.mozhimen.basick.utilk.UtilKRxJavaTrans
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
@APermissionK(
    Manifest.permission.RECEIVE_BOOT_COMPLETED
)
open class BaseAutoRunReceiver(private val clazz: Class<*>, private val _delayTime: Long = 0L) : BroadcastReceiver() {

    @CallSuper
    override fun onReceive(context: Context, intent: Intent) {
        val action: String? = intent.action
        if (action?.isNotEmpty() == true && action == Intent.ACTION_BOOT_COMPLETED) {
            if (_delayTime != 0L) {
                Observable.just("").delay(_delayTime, TimeUnit.MILLISECONDS).compose(UtilKRxJavaTrans.io2mainObservable()).subscribe(object : ObserverCallback<String>() {
                    override fun onComplete() {
                        startActivity(context, clazz)
                    }
                })
            } else {
                startActivity(context, clazz)
            }
        }
    }

    private fun startActivity(context: Context, clazz: Class<*>) {
        val rebootIntent = Intent(context, clazz)
        rebootIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(rebootIntent)
    }
}