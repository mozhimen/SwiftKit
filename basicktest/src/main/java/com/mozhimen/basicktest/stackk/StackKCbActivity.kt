package com.mozhimen.basicktest.stackk

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.stackk.cb.StackKCb
import com.mozhimen.basick.stackk.commons.IStackKListener
import com.mozhimen.basick.utilk.view.bar.showToast
import com.mozhimen.basicktest.databinding.ActivityStackkCbBinding

class StackKCbActivity : BaseActivityVB<ActivityStackkCbBinding>(), IStackKListener {

    @SuppressLint("SetTextI18n")
    override fun initView(savedInstanceState: Bundle?) {
        val stackTopActivity = StackKCb.instance.getStackTopActivity()
        val stackCount = StackKCb.instance.getStackCount()
        vb.stackkCbTitle.text = "StackTop: ${stackTopActivity?.javaClass?.simpleName ?: "Null"}, StackCount: $stackCount"

        StackKCb.instance.addFrontBackListener(this)
    }

    override fun onDestroy() {
        StackKCb.instance.removeFrontBackListener(this)
        super.onDestroy()
    }

    override fun onChanged(isFront: Boolean) {
        "App is At Front ?: $isFront".showToast()
        Log.d(TAG, "App is At Front ?: $isFront")
    }
}