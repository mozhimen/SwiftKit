package com.mozhimen.abilityktest.transk

import android.Manifest
import android.os.Build
import android.os.Bundle
import com.mozhimen.abilityk.transk.TransKText2Speech
import com.mozhimen.abilityktest.databinding.ActivityTranskBinding
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.permissionk.PermissionK

class TransKActivity : BaseKActivityVB<ActivityTranskBinding>() {
    private val _transKText2Speech by lazy {
        TransKText2Speech(this)
    }

    override fun initData(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            PermissionK.initPermissions(this, arrayOf(Manifest.permission.FOREGROUND_SERVICE)) {
                initView(savedInstanceState)
            }
        } else {
            initView(savedInstanceState)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        vb.transkT2sBtn.setOnClickListener {
            _transKText2Speech.play(vb.transkT2sEdt.text.toString())
        }
    }
}