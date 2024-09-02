package com.mozhimen.basicktest.manifestk

import android.os.Bundle
import com.mozhimen.kotlin.elemk.androidx.appcompat.bases.databinding.BaseActivityVDB
import com.mozhimen.kotlin.elemk.android.cons.CPermission
import com.mozhimen.manifestk.permission.ManifestKPermission
import com.mozhimen.manifestk.permission.annors.APermissionCheck
import com.mozhimen.kotlin.utilk.android.app.UtilKActivityStart
import com.mozhimen.basicktest.databinding.ActivityManifestkPermissionBinding

@APermissionCheck(CPermission.WRITE_EXTERNAL_STORAGE)
class ManifestKPermissionActivity : BaseActivityVDB<ActivityManifestkPermissionBinding>() {
    override fun initData(savedInstanceState: Bundle?) {
        //方法一,need APermissionCheck 注解
        ManifestKPermission.requestPermissions(this) {
            if (it) {
                super.initData(savedInstanceState)
            } else {
                UtilKActivityStart.startSettingApplicationDetailsSettings(this)
            }
        }

        //方法二,need APermissionCheck 注解
        ManifestKPermission.requestPermissions(this, onSuccess = {
            initView(savedInstanceState)
        }, onFail = {
            UtilKActivityStart.startSettingApplicationDetailsSettings(this)
        })

        //方法三
        ManifestKPermission.requestPermissions(this, arrayOf(CPermission.INTERNET)) {
            if (it) {
                initView(savedInstanceState)
            } else {
                UtilKActivityStart.startSettingApplicationDetailsSettings(this)
            }
        }
    }
}