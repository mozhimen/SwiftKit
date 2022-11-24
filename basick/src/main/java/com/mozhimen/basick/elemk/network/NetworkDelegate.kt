package com.mozhimen.basick.elemk.network

import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.elemk.lifecycle.bases.BaseDelegateLifecycleObserver
import com.mozhimen.basick.elemk.network.commons.INetworkListener


/**
 * @ClassName NetworkDelegate
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/23 14:09
 * @Version 1.0
 */
class NetworkDelegate(private val _owner: LifecycleOwner) : BaseDelegateLifecycleObserver(_owner) {
    private var _networkListener: INetworkListener? = null

    private val _netWorkCallback = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
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

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
    }
}