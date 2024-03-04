package com.mozhimen.basicktest.utilk.kotlin

import android.os.Bundle
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVDB
import com.mozhimen.basick.utilk.kotlin.UtilKStrSerialNo
import com.mozhimen.basicktest.databinding.ActivityUtilkGenerateNoBinding

class UtilKGenerateNoActivity : BaseActivityVDB<ActivityUtilkGenerateNoBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        vdb.utilkGenerateNoBtn.setOnClickListener {
            vdb.utilkGenerateNoTxt.text = UtilKStrSerialNo.get()
        }
    }
}