package com.mozhimen.basicktest.utilk.kotlin

import android.os.Bundle
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.utilk.kotlin.text.UtilKGenerateNo
import com.mozhimen.basicktest.databinding.ActivityUtilkGenerateNoBinding

class UtilKGenerateNoActivity : BaseActivityVB<ActivityUtilkGenerateNoBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        vb.utilkGenerateNoBtn.setOnClickListener {
            vb.utilkGenerateNoTxt.setText(UtilKGenerateNo.generateSerialNo())
        }
    }
}