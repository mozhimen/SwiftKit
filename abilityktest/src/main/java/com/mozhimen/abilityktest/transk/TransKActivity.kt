package com.mozhimen.abilityktest.transk

import android.os.Build
import android.os.Bundle
import com.mozhimen.abilityk.transk.TransKTTS
import com.mozhimen.abilityktest.databinding.ActivityTranskBinding
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.permissionk.cons.CPermission
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.permissionk.PermissionK
import com.mozhimen.basick.permissionk.annors.APermissionRequire

@APermissionRequire(CPermission.FOREGROUND_SERVICE)
class TransKActivity : BaseActivityVB<ActivityTranskBinding>() {
    private val _transKTTS by lazy {
        TransKTTS(this)
    }

    override fun initData(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= CVersionCode.V_28_9_P) {
            PermissionK.initPermissions(this, arrayOf(CPermission.FOREGROUND_SERVICE)) {
                super.initData(savedInstanceState)
            }
        } else {
            super.initData(savedInstanceState)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        vb.transkT2sBtn.setOnClickListener {
            _transKTTS.play(vb.transkT2sEdt.text.toString())
        }
    }
}