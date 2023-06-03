package com.mozhimen.basicktest.cachek

import android.os.Bundle
import com.mozhimen.basick.cachek.shared_preferences.CacheKSP
import com.mozhimen.basick.cachek.shared_preferences.temps.CacheKSPDelegateString
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basicktest.databinding.ActivityCachekSpBinding

class CacheKSPActivity : BaseActivityVB<ActivityCachekSpBinding>() {

    private val _spTestPro by lazy { CacheKSP.instance.with("sp_test") }
    private var _editStr: String by CacheKSPDelegateString(_spTestPro, "editStr", "")

    override fun initView(savedInstanceState: Bundle?) {
        vb.cachekSpEdit.setText(_editStr)
        vb.cachekSpBtn.setOnClickListener {
            _editStr = vb.cachekSpEdit.text.toString()
        }
    }
}