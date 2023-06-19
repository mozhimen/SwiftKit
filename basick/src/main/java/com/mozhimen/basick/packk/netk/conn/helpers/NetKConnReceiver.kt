package com.mozhimen.basick.packk.netk.conn.helpers

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import com.mozhimen.basick.elemk.receiver.bases.BaseBroadcastReceiver
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.packk.netk.conn.commons.INetKConnListener
import com.mozhimen.basick.packk.netk.conn.cons.ENetKType


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
class NetKConnReceiver : BaseBroadcastReceiver() {
    companion object {
        @JvmStatic
        val instance = INSTANCE.holder
    }

    private val _listeners: ArrayList<INetKConnListener> = ArrayList()

    override fun onReceive(context: Context?, intent: Intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION == intent.action) {
            val eNetKType: ENetKType = NetKConnHelper.getENetKType()
            Log.d(TAG, "onReceive: eNetKType $eNetKType")
            notifyListeners(eNetKType)
        }
    }

    fun registerListener(listener: INetKConnListener) {
        if (!_listeners.contains(listener)) {
            _listeners.add(listener)
        }
    }

    fun unRegisterListener(listener: INetKConnListener) {
        _listeners.remove(listener)
    }

    private fun notifyListeners(eNetKType: ENetKType) {
        if (eNetKType == ENetKType.NONE) {
            for (listener in _listeners) {
                listener.onDisconnected()
            }
        } else {
            for (listener in _listeners) {
                listener.onConnected(eNetKType)
            }
        }
    }

    private object INSTANCE {
        val holder = NetKConnReceiver()
    }
}