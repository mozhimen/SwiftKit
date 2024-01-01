package com.mozhimen.basicktest.utilk.androidx

import android.view.View
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVB
import com.mozhimen.basick.utilk.android.content.startContext
import com.mozhimen.basicktest.databinding.ActivityUtilkAndroidxBinding

class UtilKAndroidXActivity : BaseActivityVB<ActivityUtilkAndroidxBinding>() {
    fun goUtilKActionBar(view: View) {
        startContext<UtilKActionBarActivity>()
    }
}