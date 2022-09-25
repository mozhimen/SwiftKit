package com.mozhimen.abilityktest.opencvk

import android.view.View
import com.mozhimen.abilityktest.R
import com.mozhimen.abilityktest.databinding.ActivityOpencvkBinding
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.extsk.start

class OpenCVKActivity : BaseKActivity<ActivityOpencvkBinding, BaseKViewModel>(R.layout.activity_opencvk) {

    fun goOpenCVKContrast(view: View) {
        start<OpenCVKContrastActivity>()
    }

    fun goOpenCVKShape(view: View) {
        start<OpenCVKShapeActivity>()
    }

    fun goOpenCVKMatch(view: View) {
        start<OpenCVKMatchActivity>()
    }
}