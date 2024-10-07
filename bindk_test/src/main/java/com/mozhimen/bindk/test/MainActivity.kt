package com.mozhimen.bindk.test

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mozhimen.bindk.bases.viewbinding.activity.BaseActivityVB
import com.mozhimen.bindk.test.databinding.ActivityMainBinding
import com.mozhimen.bindk.utils.viewBinding

class MainActivity : AppCompatActivity() {
    private val vb :ActivityMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
    }
}