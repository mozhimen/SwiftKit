package com.mozhimen.basick.sensek.net_conn

import android.app.Activity
import android.net.ConnectivityManager
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.elemk.annors.ADescription
import com.mozhimen.basick.elemk.receiver.bases.BaseReceiverDelegate
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.sensek.net_conn.commons.INetConnListener
import com.mozhimen.basick.sensek.net_conn.helpers.NetConnReceiver


/**
 * @ClassName NetKConnDelegate
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/13 15:41
 * @Version 1.0
 */
@ADescription("init by lazy")
@AManifestKRequire(
    CPermission.ACCESS_NETWORK_STATE,
    CPermission.ACCESS_WIFI_STATE,
    CPermission.ACCESS_FINE_LOCATION,
    CPermission.INTERNET
)
class SenseKNetConnDelegate<A>(
    _activity: A,
    private val _listener: INetConnListener,
    private val _receiver: NetConnReceiver = NetConnReceiver(),
) : BaseReceiverDelegate<A>(_activity, _receiver, ConnectivityManager.CONNECTIVITY_ACTION) where A : Activity, A : LifecycleOwner {

    init {
        _receiver.registerListener(_listener)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        _receiver.unRegisterListener(_listener)
        super.onDestroy(owner)
    }
}