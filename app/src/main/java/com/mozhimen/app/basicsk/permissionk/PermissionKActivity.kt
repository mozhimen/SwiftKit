package com.mozhimen.app.basicsk.permissionk

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.app.R
import com.mozhimen.basicsk.permissionk.annors.PermissionKAnnor
import com.mozhimen.basicsk.permissionk.PermissionK

@PermissionKAnnor(permissions = [Manifest.permission.INTERNET])
class PermissionKActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permissionk)

        //方法一
        PermissionK.initPermissions(this) {
            if (it) {
                initView()
            } else {
                PermissionK.applySetting(this)
            }
        }

        //方法二
        PermissionK.initPermissions(this, arrayOf(Manifest.permission.INTERNET)){
            if (it) {
                initView()
            } else {
                PermissionK.applySetting(this)
            }
        }
    }

    private fun initView() {}
}