package com.mozhimen.uicorektest.viewk

import android.view.View
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.extsk.start
import com.mozhimen.uicorektest.R
import com.mozhimen.uicorektest.databinding.ActivityViewkBinding

class ViewKActivity : BaseKActivity<ActivityViewkBinding, BaseKViewModel>(R.layout.activity_viewk) {

    fun gotoViewKViews(view: View) {
        start<ViewKViewsActivity>()
    }

    fun gotoViewKSteps(view: View) {
        start<ViewKStepsActivity>()
    }
}