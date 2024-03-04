package com.mozhimen.basicktest.utilk

import android.view.View
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVDB
import com.mozhimen.basick.utilk.android.content.startContext
import com.mozhimen.basicktest.databinding.ActivityUtilkBinding
import com.mozhimen.basicktest.utilk.android.UtilKApkActivity
import com.mozhimen.basicktest.utilk.android.UtilKAndroidActivity
import com.mozhimen.basicktest.utilk.android.UtilKBitmapActivity
import com.mozhimen.basicktest.utilk.kotlin.UtilKVerifyUrlActivity
import com.mozhimen.basicktest.utilk.android.UtilKAssetActivity
import com.mozhimen.basicktest.utilk.android.UtilKInputActivity
import com.mozhimen.basicktest.utilk.android.UtilKScreenActivity
import com.mozhimen.basicktest.utilk.androidx.UtilKAndroidXActivity
import com.mozhimen.basicktest.utilk.java.UtilKJavaActivity
import com.mozhimen.basicktest.utilk.javax.UtilKJavaXActivity
import com.mozhimen.basicktest.utilk.kotlin.UtilKKotlinActivity

class UtilKActivity : BaseActivityVDB<ActivityUtilkBinding>() {

    fun goUtilKAndroid(view: View) {
        startContext<UtilKAndroidActivity>()
    }

    fun goUtilKAndroidX(view: View) {
        startContext<UtilKAndroidXActivity>()
    }

    fun goUtilKJava(view: View) {
        startContext<UtilKJavaActivity>()
    }

    fun goUtilKJavaX(view: View) {
        startContext<UtilKJavaXActivity>()
    }

    fun goUtilKKotlin(view: View) {
        startContext<UtilKKotlinActivity>()
    }
}