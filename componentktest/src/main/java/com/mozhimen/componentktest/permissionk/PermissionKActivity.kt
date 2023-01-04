package com.mozhimen.componentktest.permissionk

import android.Manifest
import android.os.Bundle
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.permissionk.PermissionK
import com.mozhimen.basick.permissionk.annors.APermissionK
import com.mozhimen.basick.utilk.UtilKPermission
import com.mozhimen.componentktest.databinding.ActivityPermissionkBinding

@APermissionK(Manifest.permission.INTERNET)
class PermissionKActivity : BaseActivityVB<ActivityPermissionkBinding>() {
    override fun initData(savedInstanceState: Bundle?) {
        //方法一,need APermissionK 注解
        PermissionK.initPermissions(this) {
            if (it) {
                super.initData(savedInstanceState)
            } else {
                UtilKPermission.openSettingSelf()
            }
        }

        //方法二,need APermissionK 注解
        PermissionK.initPermissions(this, onSuccess = {
            initView(savedInstanceState)
        }, onFail = {
            UtilKPermission.openSettingSelf()
        })

        //方法三
        PermissionK.initPermissions(this, arrayOf(Manifest.permission.INTERNET)) {
            if (it) {
                initView(savedInstanceState)
            } else {
                UtilKPermission.openSettingSelf()
            }
        }
    }
}