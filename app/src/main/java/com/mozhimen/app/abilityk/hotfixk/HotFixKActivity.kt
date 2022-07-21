package com.mozhimen.app.abilityk.hotfixk

import android.Manifest
import android.os.Bundle
import com.mozhimen.abilityk.hotfixk.HotFixMgr
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityHotfixkBinding
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.cachek.CacheKSP
import com.mozhimen.basick.executork.ExecutorK
import com.mozhimen.basick.extsk.showToast
import com.mozhimen.componentk.permissionk.PermissionK
import com.mozhimen.componentk.permissionk.annors.PermissionKAnnor

@PermissionKAnnor(permissions = [Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE])
class HotFixKActivity : BaseKActivity<ActivityHotfixkBinding, BaseKViewModel>(R.layout.activity_hotfixk) {

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

        vb.hotfixkBtnHotfix.setOnClickListener {
            ExecutorK.execute(TAG, 0) {
                HotFixMgr.instance.startFix(3, dexName = "hotfixk_1.dex")
            }
        }
    }

    /**
     * dex打包命令: dx --dex --no-strict --output=hotfixk_1.dex app/build/tmp/kotlin-classes/debug/com/mozhimen/app/abilityk/hotfixk/HotFixKTest.class
     * dex本地推送命令: adb push hotfixk_1.dex /sdcard/hotfixk_1.dex
     * dex远程推送方法: 后端接口
     */
}