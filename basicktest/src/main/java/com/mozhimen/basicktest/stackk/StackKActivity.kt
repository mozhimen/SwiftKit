package com.mozhimen.basicktest.stackk

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import com.mozhimen.basick.elemk.activity.commons.BaseActivityVB
import com.mozhimen.basick.extsk.showToast
import com.mozhimen.basick.stackk.StackK
import com.mozhimen.basick.stackk.commons.IStackKListener
import com.mozhimen.basicktest.databinding.ActivityStackkBinding

class StackKActivity : BaseActivityVB<ActivityStackkBinding>(), IStackKListener {

    @SuppressLint("SetTextI18n")
    override fun initView(savedInstanceState: Bundle?) {
        val stackTopActivity = StackK.getStackTopActivity()
        val stackCount = StackK.getStackCount()
        vb.stackkTitle.text = "StackTop: ${stackTopActivity?.javaClass?.simpleName ?: "Null"}, StackCount: $stackCount"

        StackK.addFrontBackListener(this)
    }

    override fun onDestroy() {
        StackK.removeFrontBackListener(this)
        super.onDestroy()
    }

    override fun onChanged(isFront: Boolean) {
        "App is At Front ?: $isFront".showToast()
        Log.d(TAG, "App is At Front ?: $isFront")
    }
}