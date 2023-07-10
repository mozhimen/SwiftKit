package com.mozhimen.basick.sensek.net_conn.helpers

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import com.mozhimen.basick.elemk.android.content.bases.BaseBroadcastReceiver
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.sensek.net_conn.commons.INetConnListener
import com.mozhimen.basick.sensek.net_conn.cons.ENetType


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
class NetConnReceiver : BaseBroadcastReceiver() {
    companion object {
        @JvmStatic
        val instance = INSTANCE.holder
    }

    private val _listeners: ArrayList<INetConnListener> = ArrayList()

    override fun onReceive(context: Context?, intent: Intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION == intent.action) {
            val netType: ENetType = NetConnHelper.getENetKType()
            Log.d(TAG, "onReceive: eNetKType $netType")
            notifyListeners(netType)
        }
    }

    fun registerListener(listener: INetConnListener) {
        if (!_listeners.contains(listener)) {
            _listeners.add(listener)
        }
    }

    fun unRegisterListener(listener: INetConnListener) {
        _listeners.remove(listener)
    }

    private fun notifyListeners(netType: ENetType) {
        if (netType == ENetType.NONE) {
            for (listener in _listeners) {
                listener.onDisconnected()
            }
        } else {
            for (listener in _listeners) {
                listener.onConnected(netType)
            }
        }
    }

    private object INSTANCE {
        val holder = NetConnReceiver()
    }
}