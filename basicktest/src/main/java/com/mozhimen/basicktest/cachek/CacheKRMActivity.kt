package com.mozhimen.basicktest.cachek

import android.os.Bundle
import com.mozhimen.basick.cachek.room.CacheKRM
import com.mozhimen.basick.cachek.room.temps.CacheKRMVarPropertyString
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVB
import com.mozhimen.basick.utilk.android.widget.showToast
import com.mozhimen.basicktest.databinding.ActivityCachekRmBinding

class CacheKRMActivity : BaseActivityVB<ActivityCachekRmBinding>() {
    private var _editStr: String by CacheKRMVarPropertyString(CacheKRM, "editStr", "")

    override fun initView(savedInstanceState: Bundle?) {
        vb.cachekRmEdit.setText(_editStr)
        vb.cachekRmBtn.setOnClickListener {
            _editStr = vb.cachekRmEdit.text.toString()
        }
        vb.cachekRmBtn1.setOnClickListener {
            "${CacheKRM.contains("editStr")}".showToast()
        }
    }
}