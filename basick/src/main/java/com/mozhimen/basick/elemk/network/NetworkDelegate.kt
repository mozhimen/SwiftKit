package com.mozhimen.basick.elemk.network

import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.elemk.lifecycle.bases.BaseDelegateLifecycleObserver
import com.mozhimen.basick.elemk.network.commons.INetworkListener
import com.mozhimen.basick.manifestk.annors.AManifestKRequire


/**
 * @ClassName NetworkDelegate
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/23 14:09
 * @Version 1.0
 */
@AManifestKRequire(CPermission.ACCESS_NETWORK_STATE, CPermission.ACCESS_WIFI_STATE)
class NetworkDelegate(private val _owner: LifecycleOwner) : BaseDelegateLifecycleObserver(_owner) {
    private var _networkListener: INetworkListener? = null

    private val _netWorkCallback = if (Build.VERSION.SDK_INT >= CVersionCode.V_21_5_L && Build.VERSION.SDK_INT <= CVersionCode.V_24_7_N) {
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                _networkListener?.onConnect()
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                _networkListener?.onLost()
            }
        }
    } else {
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                _networkListener?.onConnect()
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                _networkListener?.onLost()
            }
        }
    }

    fun setNetworkListener(listener: INetworkListener) {
        _networkListener = listener
    }
}