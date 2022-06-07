package com.mozhimen.app.abilityk.transk

import android.Manifest
import android.os.Build
import android.os.Bundle
import com.mozhimen.abilityk.transk.TransKText2Speech
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityTranskBinding
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.componentk.permissionk.PermissionK

class TransKActivity : BaseKActivity<ActivityTranskBinding, BaseKViewModel>(R.layout.activity_transk) {
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
            TransKText2Speech.getInstance(this).play(vb.transkT2sEdt.text.toString())
        }
    }
}