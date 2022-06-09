package com.mozhimen.app.abilityk.opencvk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mozhimen.app.R
import com.mozhimen.basick.extsk.start

class OpenCVKActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opencvk)
    }

    fun goOpenCVKContrast(view: View) {
        start<OpenCVKContrastActivity>()
    }

    fun goOpenCVKShape(view: View) {
        start<OpenCVKShapeActivity>()
    }
}