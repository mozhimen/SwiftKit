package com.mozhimen.app.basicsk.stackk

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.app.databinding.ActivityStackkBinding
import com.mozhimen.basicsk.stackk.StackK
import com.mozhimen.basicsk.utilk.showToast

class StackKActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "StackKActivity>>>>>"
    }

    private val vb: ActivityStackkBinding by lazy { ActivityStackkBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        initView()
    }

    private fun initView() {
        StackK.instance.addFrontBackCallback(object : StackK.FrontBackCallback {
            override fun onChanged(isFront: Boolean) {
                "当前处于: $isFront".showToast()
                Log.i(TAG, "onChanged: 当前处于: $isFront")
            }
        })

        vb.stackkSkip.setOnClickListener {
            startActivity(Intent(this, StackKSecondActivity::class.java))
        }
    }
}