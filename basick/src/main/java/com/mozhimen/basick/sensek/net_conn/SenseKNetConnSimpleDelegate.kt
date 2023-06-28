package com.mozhimen.basick.sensek.net_conn

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.elemk.lifecycle.bases.BaseWakeBefDestroyLifecycleObserver
import com.mozhimen.basick.utilk.android.net.UtilKNetConn

/**
 * @ClassName NetKConnSimpleDelegate
 * @Description TODO
 * @Author Mozhimen & Kolin
 * @Date 2023/4/25 11:51
 * @Version 1.0
 */
class SenseKNetConnSimpleDelegate(private val _networkCallback: ConnectivityManager.NetworkCallback) : BaseWakeBefDestroyLifecycleObserver() {
    init {
        UtilKNetConn.getConnectivityManager().registerNetworkCallback(NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build(), _networkCallback)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        UtilKNetConn.getConnectivityManager().unregisterNetworkCallback(_networkCallback)
        super.onDestroy(owner)
    }
}