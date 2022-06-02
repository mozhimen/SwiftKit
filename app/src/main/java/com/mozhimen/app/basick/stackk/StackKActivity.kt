package com.mozhimen.app.basick.stackk

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.app.databinding.ActivityStackkBinding
import com.mozhimen.basick.stackk.StackK
import com.mozhimen.basick.stackk.commons.IStackKListener
import com.mozhimen.basick.utilk.showToast

class StackKActivity : AppCompatActivity() {

    private val vb: ActivityStackkBinding by lazy { ActivityStackkBinding.inflate(layoutInflater) }
    private val TAG = "StackKActivity>>>>>"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        initView()
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        val stackTopActivity = StackK.getStackTopActivity()
        val stackCount = StackK.getStackCount()
        vb.stackkTitle.text = "StackTop: ${stackTopActivity?.javaClass?.simpleName ?: "Null"}, StackCount: $stackCount"

        StackK.addFrontBackListener(object : IStackKListener {
            override fun onChanged(isFront: Boolean) {
                "App is At Front ?: $isFront".showToast()
                Log.d(TAG, "App is At Front ?: $isFront")
            }
        })
    }
}