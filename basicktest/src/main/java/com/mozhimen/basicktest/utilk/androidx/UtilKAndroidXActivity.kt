package com.mozhimen.basicktest.utilk.androidx

import android.view.View
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVDB
import com.mozhimen.basick.utilk.android.content.startContext
import com.mozhimen.basicktest.databinding.ActivityUtilkAndroidxBinding

class UtilKAndroidXActivity : BaseActivityVDB<ActivityUtilkAndroidxBinding>() {
    fun goUtilKActionBar(view: View) {
        startContext<UtilKActionBarActivity>()
    }
}