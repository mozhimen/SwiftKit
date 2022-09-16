package com.mozhimen.app.uicorek.viewk

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.app.databinding.ActivityViewkBinding
import com.mozhimen.basick.extsk.start

class ViewKActivity : AppCompatActivity() {

    private val vb by lazy { ActivityViewkBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
    }

    fun gotoViewKViews(view: View) {
        start<ViewKViewsActivity>()
    }

    fun gotoViewKSteps(view: View) {
        start<ViewKStepsActivity>()
    }
}