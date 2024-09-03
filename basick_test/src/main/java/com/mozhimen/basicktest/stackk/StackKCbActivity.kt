package com.mozhimen.basicktest.stackk

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper
import com.mozhimen.kotlin.elemk.androidx.appcompat.bases.databinding.BaseActivityVDB
import com.mozhimen.kotlin.lintk.optins.OApiInit_InApplication
import com.mozhimen.stackk.cb.StackKCb
import com.mozhimen.stackk.commons.IStackKListener
import com.mozhimen.kotlin.utilk.android.widget.showToast
import com.mozhimen.basicktest.databinding.ActivityStackkCbBinding
import java.lang.ref.WeakReference

class StackKCbActivity : BaseActivityVDB<ActivityStackkCbBinding>(), com.mozhimen.stackk.commons.IStackKListener {

    @OptIn(OApiInit_InApplication::class)
    @SuppressLint("SetTextI18n")
    override fun initView(savedInstanceState: Bundle?) {
        val stackTopActivity = com.mozhimen.stackk.cb.StackKCb.instance.getStackTopActivity()
        val stackCount = com.mozhimen.stackk.cb.StackKCb.instance.getStackCount()
        vdb.stackkCbTitle.text = "StackTop: ${stackTopActivity?.javaClass?.simpleName ?: "Null"}, StackCount: $stackCount"

        com.mozhimen.stackk.cb.StackKCb.instance.addFrontBackListener(this)
    }

    @OptIn(OApiInit_InApplication::class)
    override fun onDestroy() {
        com.mozhimen.stackk.cb.StackKCb.instance.removeFrontBackListener(this)
        super.onDestroy()
    }

    override fun onChanged(isFront: Boolean, activity: Activity) {
        "App is At Front ?: $isFront".showToast()
        UtilKLogWrapper.d(TAG, "App is At Front ?: $isFront")
    }
}