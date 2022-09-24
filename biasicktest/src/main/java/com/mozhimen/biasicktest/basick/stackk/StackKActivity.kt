package com.mozhimen.biasicktest.basick.stackk

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.extsk.showToast
import com.mozhimen.basick.stackk.StackK
import com.mozhimen.basick.stackk.commons.IStackKListener
import com.mozhimen.biasicktest.R
import com.mozhimen.biasicktest.databinding.ActivityStackkBinding

class StackKActivity : BaseKActivity<ActivityStackkBinding, BaseKViewModel>(R.layout.activity_stackk) {

    override fun initData(savedInstanceState: Bundle?) {
        initView(savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    override fun initView(savedInstanceState: Bundle?) {
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