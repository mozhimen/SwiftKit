package com.mozhimen.basicktest.utilk.java

import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.view.View
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.utilk.android.content.startContext
import com.mozhimen.basicktest.databinding.ActivityUtilkJavaBinding
import java.lang.reflect.Method


class UtilKJavaActivity : BaseActivityVB<ActivityUtilkJavaBinding>() {
    fun goUtilKFile(view: View) {
        startContext<UtilKFileActivity>()
    }

    fun goManageAllStorage(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
            startActivity(intent)
        }
    }

    fun goManageAllStorageByReflect(view: View) {
        ManageAllStorageByReflect.reflect1()
    }
}