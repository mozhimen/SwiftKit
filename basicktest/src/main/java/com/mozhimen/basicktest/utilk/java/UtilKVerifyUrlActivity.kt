package com.mozhimen.basicktest.utilk.java

import android.os.Bundle
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.exts.showToast
import com.mozhimen.basick.utilk.java.datatype.regular.UtilKVerifyUrl
import com.mozhimen.basicktest.databinding.ActivityUtilkVerifyUrlBinding

class UtilKVerifyUrlActivity : BaseActivityVB<ActivityUtilkVerifyUrlBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        VB.utilkVerifyUrlBtnPort.setOnClickListener {
            "是否合法: ${UtilKVerifyUrl.checkPort(VB.utilkVerifyUrlTxtPort.text.toString())}".showToast()
        }
    }
}