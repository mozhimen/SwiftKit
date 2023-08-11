package com.mozhimen.componentk.netk.connection

import android.app.Activity
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.lintk.optin.OptInApiInit_ByLazy
import com.mozhimen.basick.elemk.android.content.bases.BaseBroadcastReceiverProxy
import com.mozhimen.basick.lintk.optin.OptInApiCall_BindLifecycle
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.elemk.android.content.NetConnectionBroadcastReceiver
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
class NetKConnectionProxy<A>(
    _activity: A,
    private val _listener: IConnectionListener,
    private val _receiver: NetConnectionBroadcastReceiver = NetConnectionBroadcastReceiver(),
) : BaseBroadcastReceiverProxy<A>(_activity, _receiver, CConnectivityManager.CONNECTIVITY_ACTION) where A : Activity, A : LifecycleOwner {

    init {
        _receiver.registerListener(_listener)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        _receiver.unRegisterListener(_listener)
        super.onDestroy(owner)
    }
}