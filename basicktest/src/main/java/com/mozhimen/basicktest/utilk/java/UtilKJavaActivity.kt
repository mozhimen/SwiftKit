package com.mozhimen.basicktest.utilk.java

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.utilk.android.content.startContext
import com.mozhimen.basick.utilk.androidx.lifecycle.UtilKDataBus
import com.mozhimen.basicktest.R
import com.mozhimen.basicktest.databinding.ActivityUtilkJavaBinding

class UtilKJavaActivity : BaseActivityVB<ActivityUtilkJavaBinding>() {
    fun goUtilKFile(view: View) {
        startContext<UtilKFileActivity>()
    }

}