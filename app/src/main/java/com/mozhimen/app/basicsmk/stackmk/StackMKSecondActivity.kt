package com.mozhimen.app.basicsmk.stackmk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.app.databinding.ActivityStackmkSecondBinding
import com.mozhimen.basicsmk.stackmk.StackMKManager

class StackMKSecondActivity : AppCompatActivity() {
    private val vb: ActivityStackmkSecondBinding by lazy {
        ActivityStackmkSecondBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        initView()
    }

    fun initView() {
        val topActivity = StackMKManager.instance.getTopActivity()
        topActivity?.let {
            vb.stackmkTopName.text = topActivity.localClassName
        }
    }
}