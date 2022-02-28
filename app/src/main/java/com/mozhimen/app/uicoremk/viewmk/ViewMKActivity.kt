package com.mozhimen.app.uicoremk.viewmk

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.app.databinding.ActivityViewmkBinding

class ViewMKActivity : AppCompatActivity() {

    private val vb by lazy { ActivityViewmkBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
    }

    fun gotoViewMKViews(view: View) {
        startActivity(Intent(this, ViewMKViewsActivity::class.java))
    }

    fun gotoViewMKSteps(view: View) {
        startActivity(Intent(this, ViewMKStepsActivity::class.java))
    }
}