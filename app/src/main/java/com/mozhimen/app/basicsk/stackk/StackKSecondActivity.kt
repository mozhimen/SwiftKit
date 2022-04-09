package com.mozhimen.app.basicsk.stackk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.app.databinding.ActivityStackkSecondBinding
import com.mozhimen.basicsk.stackk.StackK

class StackKSecondActivity : AppCompatActivity() {
    private val vb: ActivityStackkSecondBinding by lazy {
        ActivityStackkSecondBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        initView()
    }

    fun initView() {
        val topActivity = StackK.instance.getTopActivity()
        topActivity?.let {
            vb.stackkTopName.text = topActivity.localClassName
        }
    }
}