package com.mozhimen.app.uicorek.layoutk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mozhimen.app.databinding.ActivityLayoutkBinding

class LayoutKActivity : AppCompatActivity() {
    private val vb by lazy { ActivityLayoutkBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
    }
}