package com.mozhimen.uicorektest.viewk

import android.os.Bundle
import android.view.View
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.extsk.start
import com.mozhimen.uicorektest.databinding.ActivityViewkBinding

class ViewKActivity : BaseKActivityVB<ActivityViewkBinding>() {

    fun gotoViewKViews(view: View) {
        start<ViewKViewsActivity>()
    }

    fun gotoViewKSteps(view: View) {
        start<ViewKStepsActivity>()
    }

    override fun initData(savedInstanceState: Bundle?) {

    }
}