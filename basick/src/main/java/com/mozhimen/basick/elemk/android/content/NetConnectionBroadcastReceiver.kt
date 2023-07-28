package com.mozhimen.basick.elemk.android.content

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import com.mozhimen.basick.elemk.android.content.bases.BaseBroadcastReceiver
import com.mozhimen.basick.elemk.android.content.commons.IBroadcastReceiver
import com.mozhimen.basick.elemk.commons.IConnectionListener
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.elemk.android.net.cons.ENetType
import com.mozhimen.basick.utilk.android.net.UtilKNetConn
import com.mozhimen.basick.utilk.android.util.wt


/**
 * @ClassName NetKConnReceiver
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/13 15:18
 * @Version 1.0
 */
@AManifestKRequire(
    CPermission.ACCESS_NETWORK_STATE,
    CPermission.ACCESS_WIFI_STATE,
    CPermission.INTERNET
)
class NetConnectionBroadcastReceiver : BaseBroadcastReceiver(), IBroadcastReceiver {
    companion object {
        @JvmStatic
        val instance = INSTANCE.holder
    }

    ///////////////////////////////////////////////////////////////////////////

    private val _listeners: ArrayList<IConnectionListener> = ArrayList()

    override fun registerReceiver(context: Context) {
        context.registerReceiver(INSTANCE.holder, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun unregisterReceiver(context: Context) {
        context.unregisterReceiver(INSTANCE.holder)
    }

    override fun onReceive(context: Context?, intent: Intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION == intent.action) {
            val netType: ENetType = UtilKNetConn.getNetType()
            "onReceive: eNetKType $netType".wt(TAG)
            notifyListeners(netType)
        }
    }

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
        val holder = NetConnectionBroadcastReceiver()
    }


}