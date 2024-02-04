package com.mozhimen.basick.elemk.android.net

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.lintk.optins.OApiInit_ByLazy
import com.mozhimen.basick.elemk.androidx.lifecycle.bases.BaseWakeBefDestroyLifecycleObserver
import com.mozhimen.basick.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.basick.utilk.android.net.UtilKNetConn

/**
 * @ClassName SenseKNetConnSimpleProxy
 * @Description TODO
 * @Author Mozhimen & Kolin
 * @Date 2023/4/25 11:51
 * @Version 1.0
 */
@OApiCall_BindLifecycle
@OApiInit_ByLazy
class NetworkCallbackProxy(private val _networkCallback: ConnectivityManager.NetworkCallback) : BaseWakeBefDestroyLifecycleObserver() {

    init {
        UtilKNetConn.registerNetworkCallback(NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build(), _networkCallback)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        UtilKNetConn.unregisterNetworkCallback(_networkCallback)
        super.onDestroy(owner)
    }
}