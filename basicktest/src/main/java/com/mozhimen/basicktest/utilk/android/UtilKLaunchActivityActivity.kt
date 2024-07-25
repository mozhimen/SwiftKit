package com.mozhimen.basicktest.utilk.android

import android.view.View
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVDB
import com.mozhimen.basick.lintk.optins.permission.OPermission_MANAGE_EXTERNAL_STORAGE
import com.mozhimen.basick.utilk.android.app.UtilKActivityStart
import com.mozhimen.basicktest.databinding.ActivityUtilkLaunchActivityBinding

/**
 * @ClassName UtilKLaunchActivityActivity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/23 16:59
 * @Version 1.0
 */
class UtilKLaunchActivityActivity : BaseActivityVDB<ActivityUtilkLaunchActivityBinding>() {
    @OptIn(OPermission_MANAGE_EXTERNAL_STORAGE::class)
    fun startManageAllFilesAccess(view: View) {
        UtilKActivityStart.startSettingManageAllFilesAccessPermission(this)
    }
}