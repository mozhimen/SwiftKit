package com.mozhimen.abilityktest.transk

import android.os.Bundle
import com.mozhimen.abilityk.transk.TransKTTSProxy
import com.mozhimen.abilityktest.databinding.ActivityTranskBinding
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.lintk.optin.OptInApiInit_ByLazy
import com.mozhimen.basick.lintk.optin.OptInApiCall_BindLifecycle
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.permission.ManifestKPermission
import com.mozhimen.basick.manifestk.permission.annors.APermissionCheck
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion

@AManifestKRequire(CPermission.FOREGROUND_SERVICE)
@APermissionCheck(CPermission.FOREGROUND_SERVICE)
class TransKActivity : BaseActivityVB<ActivityTranskBinding>() {
    @OptIn(OptInApiInit_ByLazy::class, OptInApiCall_BindLifecycle::class)
    private val _transKTTSProxy by lazy {
        TransKTTSProxy(this)
    }

    override fun initData(savedInstanceState: Bundle?) {
        if (UtilKBuildVersion.isAfterV_28_9_P()) {
            ManifestKPermission.requestPermissions(this, arrayOf(CPermission.FOREGROUND_SERVICE)) {
                super.initData(savedInstanceState)
            }
        } else {
            super.initData(savedInstanceState)
        }
    }

    @OptIn(OptInApiInit_ByLazy::class, OptInApiCall_BindLifecycle::class)
    override fun initView(savedInstanceState: Bundle?) {
        _transKTTSProxy.bindLifecycle(this)
        vb.transkT2sBtn.setOnClickListener {
            _transKTTSProxy.play(vb.transkT2sEdt.text.toString())
        }
    }
}