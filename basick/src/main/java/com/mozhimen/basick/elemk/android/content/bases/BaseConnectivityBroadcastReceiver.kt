package com.mozhimen.basick.elemk.android.content.bases

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.content.commons.IBroadcastReceiver
import com.mozhimen.basick.elemk.android.net.cons.CConnectivityManager
import com.mozhimen.basick.elemk.commons.IConnectionListener
import com.mozhimen.basick.elemk.android.net.cons.ENetType
import com.mozhimen.basick.lintk.optins.permission.OPermission_ACCESS_NETWORK_STATE
import com.mozhimen.basick.lintk.optins.permission.OPermission_INTERNET
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.net.UtilKNet
import com.mozhimen.basick.utilk.android.util.w


/**
 * @ClassName BaseConnectivityBroadcastReceiver
 * @Description

 * 权限:
CPermission.ACCESS_NETWORK_STATE,
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
@OPermission_ACCESS_NETWORK_STATE
@OPermission_INTERNET
open class BaseConnectivityBroadcastReceiver : BaseBroadcastReceiver, IBroadcastReceiver {
    companion object {
        @JvmStatic
        val instance = INSTANCE.holder
    }

    ///////////////////////////////////////////////////////////////////////////

    @RequiresPermission(allOf = [CPermission.ACCESS_NETWORK_STATE, CPermission.INTERNET])
    constructor() : super()

    ///////////////////////////////////////////////////////////////////////////

    private val _listeners: ArrayList<IConnectionListener> = ArrayList()
    protected var netTypes = setOf(ENetType.NONE)

    ///////////////////////////////////////////////////////////////////////////

    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    override fun onReceive(context: Context?, intent: Intent) {
        if (CConnectivityManager.CONNECTIVITY_ACTION != intent.action) return
        val netTypes = UtilKNet.getNetTypes_ofActive()
        if (this.netTypes != netTypes) {
            "onReceive: eNetKType $netTypes".w(TAG)
            notifyListeners(netTypes)
        }
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

    private fun notifyListeners(netTypes: Set<ENetType>) {
        if (netTypes == setOf(ENetType.NONE) || netTypes.isEmpty()) {
            for (listener in _listeners) listener.onDisconnect()
        } else {
            for (listener in _listeners) listener.onConnect(netTypes)
        }
    }

    ///////////////////////////////////////////////////////////////////////////

    private object INSTANCE {
        val holder = BaseConnectivityBroadcastReceiver()
    }
}