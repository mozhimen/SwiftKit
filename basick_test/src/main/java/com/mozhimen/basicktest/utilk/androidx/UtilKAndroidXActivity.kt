package com.mozhimen.basicktest.utilk.androidx

import android.view.View
import com.mozhimen.mvvmk.bases.activity.databinding.BaseActivityVDB
import com.mozhimen.kotlin.utilk.android.content.startContext
import com.mozhimen.basicktest.databinding.ActivityUtilkAndroidxBinding

class UtilKAndroidXActivity : BaseActivityVDB<ActivityUtilkAndroidxBinding>() {
    fun goUtilKActionBar(view: View) {
        startContext<UtilKActionBarActivity>()
    }
}