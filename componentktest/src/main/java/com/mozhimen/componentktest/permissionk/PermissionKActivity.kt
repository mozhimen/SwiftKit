package com.mozhimen.componentktest.permissionk

import android.Manifest
import android.os.Bundle
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.permissionk.PermissionK
import com.mozhimen.basick.permissionk.annors.APermissionK
import com.mozhimen.componentktest.databinding.ActivityPermissionkBinding

@APermissionK(permissions = [Manifest.permission.INTERNET])
class PermissionKActivity : BaseKActivityVB<ActivityPermissionkBinding>() {
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