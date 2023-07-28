package com.mozhimen.basick.elemk.android.net

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.lintk.optin.OptInApiInit_ByLazy
import com.mozhimen.basick.elemk.androidx.lifecycle.bases.BaseWakeBefDestroyLifecycleObserver
import com.mozhimen.basick.lintk.optin.OptInApiCall_BindLifecycle
import com.mozhimen.basick.utilk.android.net.UtilKNetConn

/**
 * @ClassName SenseKNetConnSimpleProxy
 * @Description TODO
 * @Author Mozhimen & Kolin
 * @Date 2023/4/25 11:51
 * @Version 1.0
 */
@OptInApiCall_BindLifecycle
@OptInApiInit_ByLazy
class NetworkCallbackProxy(context: Context, private val _networkCallback: ConnectivityManager.NetworkCallback) : BaseWakeBefDestroyLifecycleObserver() {

    init {
        UtilKNetConn.registerNetworkCallback(NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build(), _networkCallback)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        UtilKNetConn.unregisterNetworkCallback(_networkCallback)
        super.onDestroy(owner)
    }
}