package com.mozhimen.basicktest.cachek

import android.os.Bundle
import com.mozhimen.basick.cachek.room.CacheKRM
import com.mozhimen.basick.cachek.room.temps.CacheKRMVarPropertyString
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVDB
import com.mozhimen.basick.utilk.android.widget.showToast
import com.mozhimen.basicktest.databinding.ActivityCachekRmBinding

class CacheKRMActivity : BaseActivityVDB<ActivityCachekRmBinding>() {
    private var _editStr: String by CacheKRMVarPropertyString(CacheKRM, "editStr", "")

    override fun initView(savedInstanceState: Bundle?) {
        vdb.cachekRmEdit.setText(_editStr)
        vdb.cachekRmBtn.setOnClickListener {
            _editStr = vdb.cachekRmEdit.text.toString()
        }
        vdb.cachekRmBtn1.setOnClickListener {
            "${CacheKRM.contains("editStr")}".showToast()
        }
    }
}