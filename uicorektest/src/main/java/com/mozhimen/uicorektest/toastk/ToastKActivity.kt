package com.mozhimen.uicorektest.toastk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.lintk.optin.annors.ALintKOptIn_ApiDeprecated_Official_AfterV_30_11_R
import com.mozhimen.uicorek.toastk.builder.ToastKBuilder
import com.mozhimen.uicorektest.R
import com.mozhimen.uicorektest.databinding.ActivityToastkBinding

class ToastKActivity : BaseActivityVB<ActivityToastkBinding>() {
    @OptIn(ALintKOptIn_ApiDeprecated_Official_AfterV_30_11_R::class)
    override fun initView(savedInstanceState: Bundle?) {
        vb.toastkBtn.setOnClickListener {
            ToastKBuilder.makeText(this,"这是测试").show()
        }
    }
}