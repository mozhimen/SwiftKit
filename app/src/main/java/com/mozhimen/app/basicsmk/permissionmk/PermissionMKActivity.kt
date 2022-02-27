package com.mozhimen.app.basicsmk.permissionmk

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.app.R
import com.mozhimen.basicsmk.permissionmk.PermissionAnnor
import com.mozhimen.basicsmk.permissionmk.PermissionMK
import com.mozhimen.basicsmk.permissionmk.PermissionMK.applySetts
import com.mozhimen.swiftmk.helper.toast.showToast
import java.lang.StringBuilder

@PermissionAnnor(permissions = [Manifest.permission.INTERNET])
class PermissionMKActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permissionmk)

        PermissionMK.initPermissions(this) { allGranted, deniedList ->
            if (allGranted) {
                initView()
            } else {
                val deniedStr = StringBuilder()
                deniedList.forEach {
                    deniedStr.append(it).append(",")
                }
                deniedStr.removeRange(deniedStr.length - 1, deniedStr.length).trim()
                "请在设置中打开${deniedStr}权限".showToast(this)

                applySetts(this)
            }
        }
    }

    private fun initView() {}
}