package com.mozhimen.basicktest.stackk

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.lintk.optin.OptInApiInit_InApplication
import com.mozhimen.basick.stackk.cb.StackKCb
import com.mozhimen.basick.stackk.commons.IStackKListener
import com.mozhimen.basick.utilk.android.widget.showToast
import com.mozhimen.basicktest.databinding.ActivityStackkCbBinding

class StackKCbActivity : BaseActivityVB<ActivityStackkCbBinding>(), IStackKListener {

    @OptIn(OptInApiInit_InApplication::class)
    @SuppressLint("SetTextI18n")
    override fun initView(savedInstanceState: Bundle?) {
        val stackTopActivity = StackKCb.instance.getStackTopActivity()
        val stackCount = StackKCb.instance.getStackCount()
        vb.stackkCbTitle.text = "StackTop: ${stackTopActivity?.javaClass?.simpleName ?: "Null"}, StackCount: $stackCount"

        StackKCb.instance.addFrontBackListener(this)
    }

    @OptIn(OptInApiInit_InApplication::class)
    override fun onDestroy() {
        StackKCb.instance.removeFrontBackListener(this)
        super.onDestroy()
    }

    override fun onChanged(isFront: Boolean) {
        "App is At Front ?: $isFront".showToast()
        Log.d(TAG, "App is At Front ?: $isFront")
    }
}