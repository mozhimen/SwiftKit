package com.mozhimen.basicktest.netk.conn

import android.annotation.SuppressLint
import android.os.Bundle
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.permission.ManifestKPermission
import com.mozhimen.basick.manifestk.permission.annors.APermissionCheck
import com.mozhimen.basick.netk.conn.NetKConnDelegate
import com.mozhimen.basick.netk.conn.commons.INetKConnListener
import com.mozhimen.basick.netk.conn.cons.ENetKType
import com.mozhimen.basick.utilk.net.UtilKNetConn
import com.mozhimen.basicktest.databinding.ActivityNetkConnBinding


/**
 * @ClassName NetKConnActivity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/13 15:36
 * @Version 1.0
 */
@AManifestKRequire(
    CPermission.ACCESS_NETWORK_STATE,
    CPermission.ACCESS_WIFI_STATE,
    CPermission.INTERNET
)
@APermissionCheck(
    CPermission.ACCESS_NETWORK_STATE,
    CPermission.ACCESS_WIFI_STATE,
    CPermission.INTERNET
)
class NetKConnActivity : BaseActivityVB<ActivityNetkConnBinding>() {
    private val _netKConnDelegate: NetKConnDelegate<NetKConnActivity> by lazy { NetKConnDelegate(this, _netKConnListener) }
    private val _netKConnListener = object : INetKConnListener {
        override fun onDisconnected() {
            vb.netkConnTxt.text = "断网了"
        }

        @SuppressLint("SetTextI18n")
        override fun onConnected(type: ENetKType) {
            vb.netkConnTxt.text = "联网了 type ${
                when (type) {
                    ENetKType.WIFI -> {
                        "WIFI risi ${UtilKNetConn.getWifiStrength()}"
                    }
                    ENetKType.M4G, ENetKType.M2G, ENetKType.M3G -> {
                        "移动网"
                    }
                    else -> "其他"
                }
            }"
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        ManifestKPermission.initPermissions(this, onSuccess = {
            super.initData(savedInstanceState)
        })
    }

    override fun initView(savedInstanceState: Bundle?) {
        _netKConnDelegate.bindLifecycle(this)
    }
}