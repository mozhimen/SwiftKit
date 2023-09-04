package com.mozhimen.basicktest.elemk.androidx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.utilk.android.content.startContext
import com.mozhimen.basicktest.R
import com.mozhimen.basicktest.databinding.ActivityElemkAndroidBinding
import com.mozhimen.basicktest.databinding.ActivityElemkAndroidxBinding

class ElemKAndroidXActivity : BaseActivityVB<ActivityElemkAndroidxBinding>() {
    fun goElemKVBVM(view: View) {
        startContext<ElemKVBVMActivity>()
    }
}