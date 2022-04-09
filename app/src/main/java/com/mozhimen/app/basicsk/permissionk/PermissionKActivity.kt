package com.mozhimen.app.basicsk.permissionk

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.app.R
import com.mozhimen.basicsk.permissionk.PermissionKAnnor
import com.mozhimen.basicsk.permissionk.PermissionK
import com.mozhimen.basicsk.permissionk.PermissionK.applySetts
import com.mozhimen.basicsk.utilk.showToast
import java.lang.StringBuilder

@PermissionKAnnor(permissions = [Manifest.permission.INTERNET])
class PermissionKActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permissionk)

        PermissionK.initPermissions(this) { allGranted, deniedList ->
            if (allGranted) {
                initView()
            } else {
                val deniedStr = StringBuilder()
                deniedList.forEach {
                    deniedStr.append(it).append(",")
                }
                deniedStr.removeRange(deniedStr.length - 1, deniedStr.length).trim()
                "请在设置中打开${deniedStr}权限".showToast()

                applySetts(this)
            }
        }
    }

    private fun initView() {}
}