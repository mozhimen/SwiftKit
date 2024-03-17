package com.mozhimen.basicktest.utilk.java

import android.content.Intent
import android.provider.Settings
import android.view.View
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVDB
import com.mozhimen.basick.utilk.android.content.startContext
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basicktest.databinding.ActivityUtilkJavaBinding


class UtilKJavaActivity : BaseActivityVDB<ActivityUtilkJavaBinding>() {
    fun goUtilKFile(view: View) {
        startContext<UtilKFileActivity>()
    }

    fun goManageAllStorage(view: View) {
        if (UtilKBuildVersion.isAfterV_30_11_R()) {
            val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
            startActivity(intent)
        }
    }

    fun goManageAllStorageByReflect(view: View) {
//        ManageAllStorageByReflect.reflect1()
    }
}