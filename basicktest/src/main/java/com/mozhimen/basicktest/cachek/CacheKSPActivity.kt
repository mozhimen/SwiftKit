package com.mozhimen.basicktest.cachek

import android.os.Bundle
import com.mozhimen.cachek.sharedpreferences.CacheKSP
import com.mozhimen.cachek.sharedpreferences.temps.CacheKSPVarPropertyString
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVDB
import com.mozhimen.basick.utilk.android.widget.showToast
import com.mozhimen.basicktest.databinding.ActivityCachekSpBinding

class CacheKSPActivity : BaseActivityVDB<ActivityCachekSpBinding>() {

    private val _spTestPro by lazy { CacheKSP.instance.with("sp_test") }
    private var _editStr: String by CacheKSPVarPropertyString(_spTestPro, "editStr", "")

    override fun initView(savedInstanceState: Bundle?) {
        vdb.cachekSpEdit.setText(_editStr)
        vdb.cachekSpBtn.setOnClickListener {
            _editStr = vdb.cachekSpEdit.text.toString()
        }
        vdb.cachekSpBtn1.setOnClickListener {
            "${_spTestPro.contains("editStr")}".showToast()
        }
    }
}