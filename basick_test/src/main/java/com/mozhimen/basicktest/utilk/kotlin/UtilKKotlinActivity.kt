package com.mozhimen.basicktest.utilk.kotlin

import android.view.View
import com.mozhimen.kotlin.elemk.androidx.appcompat.bases.databinding.BaseActivityVDB
import com.mozhimen.kotlin.utilk.android.content.startContext
import com.mozhimen.basicktest.databinding.ActivityUtilkKotlinBinding

class UtilKKotlinActivity : BaseActivityVDB<ActivityUtilkKotlinBinding>() {

    fun goUtilKVerifyUrl(view: View) {
        startContext<UtilKVerifyUrlActivity>()
    }

    fun goUtilKMd5(view: View) {
        startContext<UtilKMd5Activity>()
    }

}