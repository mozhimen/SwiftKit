package com.mozhimen.abilityktest.hotfixk

import android.Manifest
import android.os.Bundle
import com.mozhimen.abilityktest.databinding.ActivityHotfixkBinding
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.extsk.showToast
import com.mozhimen.componentk.permissionk.PermissionK
import com.mozhimen.componentk.permissionk.annors.PermissionKAnnor

@PermissionKAnnor(permissions = [Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE])
class HotFixKActivity : BaseKActivityVB<ActivityHotfixkBinding>() {

    override fun initData(savedInstanceState: Bundle?) {
        PermissionK.initPermissions(this) {
            if (it) {
                initView(savedInstanceState)
            } else {
                PermissionK.applySetting(this)
            }
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        vb.hotfixkBtnToast.setOnClickListener {
            HotFixKTest.test().showToast()
        }
    }

    /**
     * 示例:
     * dex打包命令: dx --dex --no-strict --output=hotfixk_2.dex F:\GitHub\SwiftKit\abilityktest\build\tmp\kotlin-classes\debug\com\mozhimen\abilityktest\hotfixk\HotFixKTest.class
     * dex本地推送命令: adb push hotfixk_2.dex /sdcard/hotfixk_2.dex
     * dex远程推送方法: 后端接口
     */
}