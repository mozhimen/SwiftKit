package com.mozhimen.uicorektest.viewk

import android.os.Bundle
import android.view.View
import com.mozhimen.basick.elemk.activity.commons.BaseActivityVB
import com.mozhimen.basick.extsk.start
import com.mozhimen.uicorektest.databinding.ActivityViewkBinding

class ViewKActivity : BaseActivityVB<ActivityViewkBinding>() {

    fun goViewKSteps(view: View) {
        start<ViewKStepsActivity>()
    }

    fun goViewKViews(view: View) {
        start<ViewKViewsActivity>()
    }
}