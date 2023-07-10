package com.mozhimen.abilityktest.transk

import android.os.Bundle
import com.mozhimen.abilityk.transk.TransKTTS
import com.mozhimen.abilityktest.databinding.ActivityTranskBinding
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.lintk.optin.annors.AOptInInitByLazy
import com.mozhimen.basick.lintk.optin.annors.AOptInNeedCallBindLifecycle
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.permission.ManifestKPermission
import com.mozhimen.basick.manifestk.permission.annors.APermissionCheck
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.utilk.android.os.UtilKBuildVers

@AManifestKRequire(CPermission.FOREGROUND_SERVICE)
@APermissionCheck(CPermission.FOREGROUND_SERVICE)
class TransKActivity : BaseActivityVB<ActivityTranskBinding>() {
    @OptIn(AOptInInitByLazy::class, AOptInNeedCallBindLifecycle::class)
    private val _transKTTS by lazy {
        TransKTTS(this)
    }

    override fun initData(savedInstanceState: Bundle?) {
        if (UtilKBuildVers.isAfterV_28_9_P()) {
            ManifestKPermission.initPermissions(this, arrayOf(CPermission.FOREGROUND_SERVICE)) {
                super.initData(savedInstanceState)
            }
        } else {
            super.initData(savedInstanceState)
        }
    }

    @OptIn(AOptInInitByLazy::class, AOptInNeedCallBindLifecycle::class)
    override fun initView(savedInstanceState: Bundle?) {
        _transKTTS.bindLifecycle(this)
        vb.transkT2sBtn.setOnClickListener {
            _transKTTS.play(vb.transkT2sEdt.text.toString())
        }
    }
}