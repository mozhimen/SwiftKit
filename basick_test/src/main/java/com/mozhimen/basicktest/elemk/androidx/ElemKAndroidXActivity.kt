package com.mozhimen.basicktest.elemk.androidx

import android.view.View
import com.mozhimen.mvvmk.bases.activity.databinding.BaseActivityVDB
import com.mozhimen.kotlin.utilk.android.content.startContext
import com.mozhimen.basicktest.databinding.ActivityElemkAndroidxBinding

class ElemKAndroidXActivity : BaseActivityVDB<ActivityElemkAndroidxBinding>() {
    fun goElemKBar(view: View) {
        startContext<ElemKBarActivity>()
    }

    fun goElemKVBVM(view: View) {
        startContext<ElemKVBVMActivity>()
    }
}