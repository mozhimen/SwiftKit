package com.mozhimen.basicktest.stackk

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.stackk.commons.IStackKListener
import com.mozhimen.basick.stackk.process.StackKProcess
import com.mozhimen.basick.utilk.android.widget.showToast
import com.mozhimen.basicktest.databinding.ActivityStackkProcessBinding

class StackKProcessActivity : BaseActivityVB<ActivityStackkProcessBinding>(), IStackKListener {

    @SuppressLint("SetTextI18n")
    override fun initView(savedInstanceState: Bundle?) {
        val stackTopActivity = StackKProcess.instance.getStackTopActivity()
        val stackCount = StackKProcess.instance.getStackCount()
        vb.stackkProcessTitle.text = "StackTop: ${stackTopActivity?.javaClass?.simpleName ?: "Null"}, StackCount: $stackCount"

        StackKProcess.instance.addFrontBackListener(this)
    }

    override fun onDestroy() {
        StackKProcess.instance.removeFrontBackListener(this)
        super.onDestroy()
    }

    override fun onChanged(isFront: Boolean) {
        "App is At Front ?: $isFront".showToast()
        Log.d(TAG, "App is At Front ?: $isFront")
    }
}