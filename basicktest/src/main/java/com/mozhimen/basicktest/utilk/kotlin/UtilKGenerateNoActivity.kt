package com.mozhimen.basicktest.utilk.kotlin

import android.os.Bundle
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVB
import com.mozhimen.basick.utilk.kotlin.UtilKStrSerialNo
import com.mozhimen.basicktest.databinding.ActivityUtilkGenerateNoBinding

class UtilKGenerateNoActivity : BaseActivityVB<ActivityUtilkGenerateNoBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        vb.utilkGenerateNoBtn.setOnClickListener {
            vb.utilkGenerateNoTxt.text = UtilKStrSerialNo.get()
        }
    }
}