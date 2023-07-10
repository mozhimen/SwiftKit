package com.mozhimen.basicktest.cachek

import android.os.Bundle
import com.mozhimen.basick.cachek.shared_preferences.CacheKSP
import com.mozhimen.basick.cachek.shared_preferences.temps.CacheKSPVarPropertyString
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.utilk.android.widget.showToast
import com.mozhimen.basicktest.databinding.ActivityCachekSpBinding

class CacheKSPActivity : BaseActivityVB<ActivityCachekSpBinding>() {

    private val _spTestPro by lazy { CacheKSP.instance.with("sp_test") }
    private var _editStr: String by CacheKSPVarPropertyString(_spTestPro, "editStr", "")

    override fun initView(savedInstanceState: Bundle?) {
        vb.cachekSpEdit.setText(_editStr)
        vb.cachekSpBtn.setOnClickListener {
            _editStr = vb.cachekSpEdit.text.toString()
        }
        vb.cachekSpBtn1.setOnClickListener {
            "${_spTestPro.contains("editStr")}".showToast()
        }
    }
}