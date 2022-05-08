package com.mozhimen.app.basicsk.stackk

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.app.databinding.ActivityStackkBinding
import com.mozhimen.basicsk.logk.LogK
import com.mozhimen.basicsk.stackk.StackK
import com.mozhimen.basicsk.stackk.commons.IStackKListener
import com.mozhimen.basicsk.utilk.showToast

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
                LogK.it(TAG, "App is At Front ?: $isFront")
            }
        })
    }
}