package com.mozhimen.basicktest.sensek

import android.view.View
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.utilk.android.content.startContext
import com.mozhimen.basicktest.databinding.ActivitySensekBinding

class SenseKActivity : BaseActivityVB<ActivitySensekBinding>() {
    fun goSystemBar(view: View){
        startContext<SystemBarActivity>()
    }

    fun goNetConn(view: View) {
        startContext<NetConnActivity>()
    }
}