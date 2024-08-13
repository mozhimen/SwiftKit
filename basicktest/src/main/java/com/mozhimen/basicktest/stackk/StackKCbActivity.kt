package com.mozhimen.basicktest.stackk

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVDB
import com.mozhimen.basick.lintk.optins.OApiInit_InApplication
import com.mozhimen.basick.stackk.cb.StackKCb
import com.mozhimen.basick.stackk.commons.IStackKListener
import com.mozhimen.basick.utilk.android.widget.showToast
import com.mozhimen.basicktest.databinding.ActivityStackkCbBinding
import java.lang.ref.WeakReference

class StackKCbActivity : BaseActivityVDB<ActivityStackkCbBinding>(), IStackKListener {

    @OptIn(OApiInit_InApplication::class)
    @SuppressLint("SetTextI18n")
    override fun initView(savedInstanceState: Bundle?) {
        val stackTopActivity = StackKCb.instance.getStackTopActivity()
        val stackCount = StackKCb.instance.getStackCount()
        vdb.stackkCbTitle.text = "StackTop: ${stackTopActivity?.javaClass?.simpleName ?: "Null"}, StackCount: $stackCount"

        StackKCb.instance.addFrontBackListener(this)
    }

    @OptIn(OApiInit_InApplication::class)
    override fun onDestroy() {
        StackKCb.instance.removeFrontBackListener(this)
        super.onDestroy()
    }

    override fun onChanged(isFront: Boolean, activity: Activity) {
        "App is At Front ?: $isFront".showToast()
        UtilKLogWrapper.d(TAG, "App is At Front ?: $isFront")
    }
}