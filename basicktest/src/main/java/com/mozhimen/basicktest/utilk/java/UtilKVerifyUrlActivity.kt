package com.mozhimen.basicktest.utilk.java

import android.os.Bundle
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.android.widget.showToast
import com.mozhimen.basick.utilk.kotlin.text.UtilKVerifyUrl
import com.mozhimen.basicktest.databinding.ActivityUtilkVerifyUrlBinding

class UtilKVerifyUrlActivity : BaseActivityVB<ActivityUtilkVerifyUrlBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        vb.utilkVerifyUrlBtnPort.setOnClickListener {
            "是否合法: ${UtilKVerifyUrl.checkPort(vb.utilkVerifyUrlTxtPort.text.toString())}".showToast()
        }
    }
}