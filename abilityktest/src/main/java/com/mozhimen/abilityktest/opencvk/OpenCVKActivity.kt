package com.mozhimen.abilityktest.opencvk

import android.view.View
import com.mozhimen.abilityktest.databinding.ActivityOpencvkBinding
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.exts.start

class OpenCVKActivity : BaseActivityVB<ActivityOpencvkBinding>() {

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