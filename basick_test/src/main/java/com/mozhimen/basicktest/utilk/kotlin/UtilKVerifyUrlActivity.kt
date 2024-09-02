package com.mozhimen.basicktest.utilk.kotlin

import android.os.Bundle
import com.mozhimen.kotlin.elemk.androidx.appcompat.bases.databinding.BaseActivityVDB
import com.mozhimen.kotlin.utilk.android.widget.showToast
import com.mozhimen.kotlin.utilk.kotlin.text.UtilKRegexMatch
import com.mozhimen.basicktest.databinding.ActivityUtilkVerifyUrlBinding

class UtilKVerifyUrlActivity : BaseActivityVDB<ActivityUtilkVerifyUrlBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        vdb.utilkVerifyUrlBtnPort.setOnClickListener {
            "是否合法: ${UtilKRegexMatch.matches_ofStrPort(vdb.utilkVerifyUrlTxtPort.text.toString())}".showToast()
        }
    }
}