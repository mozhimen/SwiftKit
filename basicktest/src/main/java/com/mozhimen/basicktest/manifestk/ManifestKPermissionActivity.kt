package com.mozhimen.basicktest.manifestk

import android.os.Bundle
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVB
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.permission.ManifestKPermission
import com.mozhimen.basick.manifestk.permission.annors.APermissionCheck
import com.mozhimen.basick.utilk.android.app.UtilKLaunchActivity
import com.mozhimen.basicktest.databinding.ActivityManifestkPermissionBinding

@APermissionCheck(CPermission.WRITE_EXTERNAL_STORAGE)
class ManifestKPermissionActivity : BaseActivityVB<ActivityManifestkPermissionBinding>() {
    override fun initData(savedInstanceState: Bundle?) {
        //方法一,need APermissionCheck 注解
        ManifestKPermission.requestPermissions(this) {
            if (it) {
                super.initData(savedInstanceState)
            } else {
                UtilKLaunchActivity.startSettingAppDetails(this)
            }
        }

        //方法二,need APermissionCheck 注解
        ManifestKPermission.requestPermissions(this, onSuccess = {
            initView(savedInstanceState)
        }, onFail = {
            UtilKLaunchActivity.startSettingAppDetails(this)
        })

        //方法三
        ManifestKPermission.requestPermissions(this, arrayOf(CPermission.INTERNET)) {
            if (it) {
                initView(savedInstanceState)
            } else {
                UtilKLaunchActivity.startSettingAppDetails(this)
            }
        }
    }
}