package com.mozhimen.uicorektest.toastk

import android.os.Bundle
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.lintk.optin.annors.ALintKOptIn_ApiDeprecated_Official_AfterV_30_11_R
import com.mozhimen.basick.utilk.android.widget.showToast
import com.mozhimen.uicorek.toastk.builder.ToastKBuilder
import com.mozhimen.uicorektest.databinding.ActivityToastkBinding

class ToastKActivity : BaseActivityVB<ActivityToastkBinding>() {
    @OptIn(ALintKOptIn_ApiDeprecated_Official_AfterV_30_11_R::class)
    override fun initView(savedInstanceState: Bundle?) {
        vb.toastkBtnNormal.setOnClickListener {
            "这是原生Toast".showToast()
        }
        vb.toastkBtnCustom.setOnClickListener {
            ToastKBuilder.makeText(this,"这是自定义Toast").show()
        }
    }
}