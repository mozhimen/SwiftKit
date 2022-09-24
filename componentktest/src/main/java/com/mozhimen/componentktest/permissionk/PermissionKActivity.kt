package com.mozhimen.componentktest.permissionk

import android.Manifest
import android.os.Bundle
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.componentk.permissionk.PermissionK
import com.mozhimen.componentk.permissionk.annors.PermissionKAnnor
import com.mozhimen.componentktest.R
import com.mozhimen.componentktest.databinding.ActivityPermissionkBinding

@PermissionKAnnor(permissions = [Manifest.permission.INTERNET])
class PermissionKActivity : BaseKActivity<ActivityPermissionkBinding, BaseKViewModel>(R.layout.activity_permissionk) {
    override fun initData(savedInstanceState: Bundle?) {
        //方法一
        PermissionK.initPermissions(this) {
            if (it) {
                initView(savedInstanceState)
            } else {
                PermissionK.applySetting(this)
            }
        }

        //方法二
        PermissionK.initPermissions(this, arrayOf(Manifest.permission.INTERNET)) {
            if (it) {
                initView(savedInstanceState)
            } else {
                PermissionK.applySetting(this)
            }
        }
    }

    override fun initView(savedInstanceState: Bundle?) {

    }
}