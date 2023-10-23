package com.mozhimen.basicktest.utilk.android

import android.view.View
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.utilk.android.app.UtilKLaunchActivity
import com.mozhimen.basicktest.databinding.ActivityUtilkLaunchActivityBinding

/**
 * @ClassName UtilKLaunchActivityActivity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/23 16:59
 * @Version 1.0
 */
class UtilKLaunchActivityActivity : BaseActivityVB<ActivityUtilkLaunchActivityBinding>() {
    fun startManageAllFilesAccess(view: View) {
        UtilKLaunchActivity.startManageAllFilesAccess(this)
    }

    fun startManageAllFilesAccess2(view: View) {
        UtilKLaunchActivity.startManageAllFilesAccess2(this)
    }
}