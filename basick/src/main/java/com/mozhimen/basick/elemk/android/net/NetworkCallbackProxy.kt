package com.mozhimen.basick.elemk.android.net

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.lintk.optins.OApiInit_ByLazy
import com.mozhimen.basick.elemk.androidx.lifecycle.bases.BaseWakeBefDestroyLifecycleObserver
import com.mozhimen.basick.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.basick.lintk.optins.permission.OPermission_ACCESS_NETWORK_STATE
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.net.UtilKConnectivityManager
import com.mozhimen.basick.utilk.android.net.UtilKNet
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion

/**
 * @ClassName SenseKNetConnSimpleProxy
 * @Description TODO
 * @Author Mozhimen & Kolin
 * @Date 2023/4/25 11:51
 * @Version 1.0
 */
@OPermission_ACCESS_NETWORK_STATE
@OApiCall_BindLifecycle
@OApiInit_ByLazy
@RequiresApi(CVersCode.V_21_5_L)
class NetworkCallbackProxy : BaseWakeBefDestroyLifecycleObserver {

    private val _networkCallback: ConnectivityManager.NetworkCallback

    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    constructor(networkCallback: ConnectivityManager.NetworkCallback) : super() {
        _networkCallback = networkCallback
        if (UtilKBuildVersion.isAfterV_24_7_N())
            UtilKConnectivityManager.registerDefaultNetworkCallback(_context, _networkCallback)
        else
            UtilKConnectivityManager.registerNetworkCallback(_context, NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build(), _networkCallback)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        UtilKConnectivityManager.unregisterNetworkCallback(_context, _networkCallback)
        super.onDestroy(owner)
    }
}