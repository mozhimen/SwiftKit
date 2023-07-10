package com.mozhimen.basick.sensek.net_conn

import android.app.Activity
import android.net.ConnectivityManager
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.lintk.optin.annors.AOptInInitByLazy
import com.mozhimen.basick.elemk.android.content.bases.BaseReceiverProxy
import com.mozhimen.basick.lintk.optin.annors.AOptInNeedCallBindLifecycle
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.sensek.net_conn.commons.INetConnListener
import com.mozhimen.basick.sensek.net_conn.helpers.NetConnReceiver


/**
 * @ClassName SenseKNetConnProxy
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/13 15:41
 * @Version 1.0
 */
@AOptInNeedCallBindLifecycle
@AOptInInitByLazy
@AManifestKRequire(
    CPermission.ACCESS_NETWORK_STATE,
    CPermission.ACCESS_WIFI_STATE,
    CPermission.ACCESS_FINE_LOCATION,
    CPermission.INTERNET
)
class SenseKNetConnProxy<A>(
    _activity: A,
    private val _listener: INetConnListener,
    private val _receiver: NetConnReceiver = NetConnReceiver(),
) : BaseReceiverProxy<A>(_activity, _receiver, ConnectivityManager.CONNECTIVITY_ACTION) where A : Activity, A : LifecycleOwner {

    init {
        _receiver.registerListener(_listener)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        _receiver.unRegisterListener(_listener)
        super.onDestroy(owner)
    }
}