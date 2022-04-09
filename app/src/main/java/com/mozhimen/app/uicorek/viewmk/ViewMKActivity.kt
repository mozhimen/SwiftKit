package com.mozhimen.app.uicorek.viewk

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.app.databinding.ActivityViewkBinding

class ViewKActivity : AppCompatActivity() {

    private val vb by lazy { ActivityViewkBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
    }

    fun gotoViewKViews(view: View) {
        startActivity(Intent(this, ViewKViewsActivity::class.java))
    }

    fun gotoViewKSteps(view: View) {
        startActivity(Intent(this, ViewKStepsActivity::class.java))
    }
}