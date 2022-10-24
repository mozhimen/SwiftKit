package com.mozhimen.abilityktest.opencvk

import android.os.Bundle
import android.view.View
import com.mozhimen.abilityktest.databinding.ActivityOpencvkBinding
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.extsk.start

class OpenCVKActivity : BaseKActivityVB<ActivityOpencvkBinding>() {

    fun goOpenCVKContrast(view: View) {
        start<OpenCVKContrastActivity>()
    }

    fun goOpenCVKShape(view: View) {
        start<OpenCVKShapeActivity>()
    }

    fun goOpenCVKMatch(view: View) {
        start<OpenCVKMatchActivity>()
    }

    override fun initData(savedInstanceState: Bundle?) {

    }
}