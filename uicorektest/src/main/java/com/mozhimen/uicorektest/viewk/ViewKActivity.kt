package com.mozhimen.uicorektest.viewk

import android.view.View
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.exts.startContext
import com.mozhimen.uicorektest.databinding.ActivityViewkBinding

class ViewKActivity : BaseActivityVB<ActivityViewkBinding>() {

    fun goViewKViews(view: View) {
        startContext<ViewKViewsActivity>()
    }

    fun goViewKWheel(view: View) {
        startContext<ViewKWheelActivity>()
    }
}