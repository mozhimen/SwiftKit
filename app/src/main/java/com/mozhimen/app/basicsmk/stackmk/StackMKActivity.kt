package com.mozhimen.app.basicsmk.stackmk

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.app.databinding.ActivityStackmkBinding
import com.mozhimen.basicsmk.stackmk.StackMKManager
import com.mozhimen.swiftmk.helper.toast.showToast

class StackMKActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "StackMKActivity>>>>>"
    }

    private val vb: ActivityStackmkBinding by lazy { ActivityStackmkBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        initView()
    }

    private fun initView() {
        StackMKManager.instance.addFrontBackCallback(object : StackMKManager.FrontBackCallback {
            override fun onChanged(isFront: Boolean) {
                "当前处于: $isFront".showToast(this@StackMKActivity)
                Log.i(TAG, "onChanged: 当前处于: $isFront")
            }
        })

        vb.stackmkSkip.setOnClickListener {
            startActivity(Intent(this, StackMKSecondActivity::class.java))
        }
    }
}