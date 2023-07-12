package com.mozhimen.uicorektest.adaptk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.utilk.android.content.startContext
import com.mozhimen.uicorektest.R
import com.mozhimen.uicorektest.databinding.ActivityAdaptkBinding

class AdaptKActivity : BaseActivityVB<ActivityAdaptkBinding>() {
    fun goSystemBar(view: View) {
        startContext<AdaptKSystemBarActivity>()
    }
}