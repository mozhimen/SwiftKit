package com.mozhimen.basicktest.utilk

import android.os.Bundle
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.extsk.showToast
import com.mozhimen.basick.utilk.verify.UtilKVerifyUrl
import com.mozhimen.basicktest.databinding.ActivityUtilkVerifyUrlBinding

class UtilKVerifyUrlActivity : BaseKActivityVB<ActivityUtilkVerifyUrlBinding>() {
    override fun initData(savedInstanceState: Bundle?) {
        vb.utilkVerifyUrlBtnPort.setOnClickListener {
            "是否合法: ${UtilKVerifyUrl.isPortValid(vb.utilkVerifyUrlTxtPort.text.toString())}".showToast()
        }
    }
}