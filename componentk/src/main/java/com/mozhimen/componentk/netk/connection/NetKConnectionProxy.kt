package com.mozhimen.componentk.netk.connection

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.lintk.optin.OptInApiInit_ByLazy
import com.mozhimen.basick.elemk.android.content.bases.BaseBroadcastReceiverProxy
import com.mozhimen.basick.lintk.optin.OptInApiCall_BindLifecycle
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.elemk.android.content.bases.BaseConnectivityBroadcastReceiver
import com.mozhimen.basick.elemk.android.net.cons.CConnectivityManager
import com.mozhimen.basick.elemk.commons.IConnectionListener


/**
 * @ClassName SenseKNetConnProxy
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/13 15:41
 * @Version 1.0
 */
@OptInApiCall_BindLifecycle
@OptInApiInit_ByLazy
@AManifestKRequire(
    CPermission.ACCESS_NETWORK_STATE,
    CPermission.ACCESS_WIFI_STATE,
    CPermission.ACCESS_FINE_LOCATION,
    CPermission.INTERNET
)
class NetKConnectionProxy<C>(
    context: C,
    private val _listener: IConnectionListener,
    receiver: BaseConnectivityBroadcastReceiver = BaseConnectivityBroadcastReceiver(),
) : BaseBroadcastReceiverProxy<C>(context, receiver, CConnectivityManager.CONNECTIVITY_ACTION) where C : Context, C : LifecycleOwner {

    init {
        (_receiver as BaseConnectivityBroadcastReceiver).registerListener(_listener)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        (_receiver as BaseConnectivityBroadcastReceiver).unRegisterListener(_listener)
        super.onDestroy(owner)
    }
}