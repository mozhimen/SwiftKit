package com.mozhimen.app.uicorek.layoutk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mozhimen.app.databinding.ActivityLayoutkBinding
import com.mozhimen.basick.extsk.start

class LayoutKActivity : AppCompatActivity() {
    private val vb by lazy { ActivityLayoutkBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
    }

    fun goLayoutKEmpty(view: View) {
        start<LayoutKEmptyActivity>()
    }
}