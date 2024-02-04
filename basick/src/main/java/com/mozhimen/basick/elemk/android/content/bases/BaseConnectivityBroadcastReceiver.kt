package com.mozhimen.basick.elemk.android.content.bases

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.mozhimen.basick.elemk.android.content.commons.IBroadcastReceiver
import com.mozhimen.basick.elemk.android.net.cons.CConnectivityManager
import com.mozhimen.basick.elemk.commons.IConnectionListener
import com.mozhimen.basick.elemk.android.net.cons.ENetType
import com.mozhimen.basick.lintk.optins.permission.OPermission_ACCESS_NETWORK_STATE
import com.mozhimen.basick.lintk.optins.permission.OPermission_ACCESS_WIFI_STATE
import com.mozhimen.basick.lintk.optins.permission.OPermission_INTERNET
import com.mozhimen.basick.utilk.android.net.UtilKNetConn
import com.mozhimen.basick.utilk.android.util.wt


/**
 * @ClassName BaseConnectivityBroadcastReceiver
 * @Description

 * 权限:
CPermission.ACCESS_NETWORK_STATE,
CPermission.ACCESS_WIFI_STATE,
CPermission.INTERNET

 * 继承:
class ElemKNetConnectionReceiver : BaseNetConnectionBroadcastReceiver()

 * 静态注册(AndroidManifest.xml)一般动态注册:
<receiver
android:name=".elemk.receiver.ElemKNetConnectionReceiver"
android:enabled="true"
android:exported="true">
<intent-filter>
<action android:name="android.intent.action.CONNECTIVITY_ACTION" />
</intent-filter>
</receiver>

 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/13 15:18
 * @Version 1.0
 */
@OPermission_INTERNET
@OPermission_ACCESS_WIFI_STATE
@OPermission_ACCESS_NETWORK_STATE
open class BaseConnectivityBroadcastReceiver : BaseBroadcastReceiver(), IBroadcastReceiver {
    companion object {
        @JvmStatic
        val instance = INSTANCE.holder
    }

    ///////////////////////////////////////////////////////////////////////////

    private val _listeners: ArrayList<IConnectionListener> = ArrayList()
    protected var _netType = ENetType.NONE

    ///////////////////////////////////////////////////////////////////////////

    override fun onReceive(context: Context?, intent: Intent) {
        if (CConnectivityManager.CONNECTIVITY_ACTION != intent.action) return
        _netType = UtilKNetConn.getNetType().also { "onReceive: eNetKType $it".wt(TAG) }
        notifyListeners(_netType)
    }

    ///////////////////////////////////////////////////////////////////////////

    override fun registerReceiver(context: Context) {
        context.registerReceiver(INSTANCE.holder, IntentFilter(CConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun unregisterReceiver(context: Context) {
        context.unregisterReceiver(INSTANCE.holder)
    }

    ///////////////////////////////////////////////////////////////////////////

    fun registerListener(listener: IConnectionListener) {
        if (!_listeners.contains(listener)) _listeners.add(listener)
    }

    fun unRegisterListener(listener: IConnectionListener) {
        _listeners.remove(listener)
    }

    private fun notifyListeners(netType: ENetType) {
        if (netType == ENetType.NONE) {
            for (listener in _listeners) listener.onDisconnect()
        } else {
            for (listener in _listeners) listener.onConnect(netType)
        }
    }

    ///////////////////////////////////////////////////////////////////////////

    private object INSTANCE {
        val holder = BaseConnectivityBroadcastReceiver()
    }
}