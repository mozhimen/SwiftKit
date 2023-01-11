package com.mozhimen.componentktest.permissionk

import android.os.Bundle
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.permissionk.cons.CPermission
import com.mozhimen.basick.permissionk.PermissionK
import com.mozhimen.basick.permissionk.annors.APermissionRequire
import com.mozhimen.basick.utilk.UtilKPermission
import com.mozhimen.componentktest.databinding.ActivityPermissionkBinding

@APermissionRequire(CPermission.INTERNET)
class PermissionKActivity : BaseActivityVB<ActivityPermissionkBinding>() {
    override fun initData(savedInstanceState: Bundle?) {
        //方法一,need APermissionK 注解
        PermissionK.initPermissions(this) {
            if (it) {
                super.initData(savedInstanceState)
            } else {
                UtilKPermission.openSettingSelf(this)
            }
        }

        //方法二,need APermissionK 注解
        PermissionK.initPermissions(this, onSuccess = {
            initView(savedInstanceState)
        }, onFail = {
            UtilKPermission.openSettingSelf(this)
        })

        //方法三
        PermissionK.initPermissions(this, arrayOf(CPermission.INTERNET)) {
            if (it) {
                initView(savedInstanceState)
            } else {
                UtilKPermission.openSettingSelf(this)
            }
        }
    }
}