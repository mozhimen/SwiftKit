package com.mozhimen.basick.netk.conn

import android.app.Activity
import android.net.ConnectivityManager
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.elemk.receiver.BaseReceiverDelegate
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.netk.conn.commons.INetKConnListener
import com.mozhimen.basick.netk.conn.helpers.NetKConnReceiver


/**
 * @ClassName NetKConnDelegate
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/13 15:41
 * @Version 1.0
 */
@AManifestKRequire(
    CPermission.ACCESS_NETWORK_STATE,
    CPermission.ACCESS_WIFI_STATE,
    CPermission.INTERNET
)
class NetKConnDelegate<T>(
    private val _activity: T,
    private val _listener: INetKConnListener,
    private val _receiver: NetKConnReceiver = NetKConnReceiver(),
) : BaseReceiverDelegate<T>(_activity, _receiver, ConnectivityManager.CONNECTIVITY_ACTION) where T : Activity, T : LifecycleOwner {
    init {
        _receiver.registerListener(_listener)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        _receiver.unRegisterListener(_listener)
        super.onDestroy(owner)
    }
}