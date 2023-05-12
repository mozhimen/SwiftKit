package com.mozhimen.basicktest.netk.conn

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.permission.ManifestKPermission
import com.mozhimen.basick.manifestk.permission.annors.APermissionCheck
import com.mozhimen.basick.packk.netk.conn.NetKConnDelegate
import com.mozhimen.basick.packk.netk.conn.commons.INetKConnListener
import com.mozhimen.basick.packk.netk.conn.cons.ENetKType
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
    CPermission.ACCESS_FINE_LOCATION,
    CPermission.INTERNET
)
@APermissionCheck(
    CPermission.ACCESS_NETWORK_STATE,
    CPermission.ACCESS_WIFI_STATE,
    CPermission.ACCESS_FINE_LOCATION,
    CPermission.INTERNET
)
class NetKConnActivity : BaseActivityVB<ActivityNetkConnBinding>() {
    private val _netKConnDelegate: NetKConnDelegate<NetKConnActivity> by lazy { NetKConnDelegate(this, _netKConnListener).apply { bindLifecycle(this@NetKConnActivity) } }
    private val _netKConnListener = object : INetKConnListener {
        override fun onDisconnected() {
            VB.netkConnTxt.text = "断网了"
        }

        @SuppressLint("SetTextI18n")
        override fun onConnected(type: ENetKType) {
            val str =
                when (type) {
                    ENetKType.WIFI -> {
                        if (ActivityCompat.checkSelfPermission(this@NetKConnActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            ""
                        } else
                            "WIFI risi ${UtilKNetConn.getWifiStrength()}"
                    }

                    ENetKType.M4G, ENetKType.M2G, ENetKType.M3G -> {
                        "移动网"
                    }

                    else -> "其他"
                }
            VB.netkConnTxt.text = "联网了 type $str"
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        _netKConnDelegate.bindLifecycle(this)
    }

    override fun initData(savedInstanceState: Bundle?) {
        ManifestKPermission.initPermissions(this, onSuccess = {
            super.initData(savedInstanceState)
        })
    }


}