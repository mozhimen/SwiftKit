package com.mozhimen.basicktest.utilk.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.utilk.android.content.startContext
import com.mozhimen.basicktest.R
import com.mozhimen.basicktest.databinding.ActivityUtilkKotlinBinding

class UtilKKotlinActivity : BaseActivityVB<ActivityUtilkKotlinBinding>() {

    fun goUtilKVerifyUrl(view: View) {
        startContext<UtilKVerifyUrlActivity>()
    }
}