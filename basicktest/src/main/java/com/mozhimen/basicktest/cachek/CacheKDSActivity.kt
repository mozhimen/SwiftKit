package com.mozhimen.basicktest.cachek

import android.os.Bundle
import com.mozhimen.basick.cachek.datastore.CacheKDS
import com.mozhimen.basick.cachek.datastore.temps.CacheKDSVarPropertyString
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVB
import com.mozhimen.basick.utilk.android.widget.showToast
import com.mozhimen.basicktest.databinding.ActivityCachekDsBinding

class CacheKDSActivity : BaseActivityVB<ActivityCachekDsBinding>() {
    private val _dsTestPro by lazy { CacheKDS.instance.with("sp_test") }
    private var _editStr: String by CacheKDSVarPropertyString(_dsTestPro, "editStr", "")

    override fun initView(savedInstanceState: Bundle?) {
        vb.cachekDsEdit.setText(_editStr)
        vb.cachekDsBtn.setOnClickListener {
            _editStr = vb.cachekDsEdit.text.toString()
        }
        vb.cachekDsBtn1.setOnClickListener {
            "${_dsTestPro.contains<String>("editStr")}".showToast()
        }
    }
}
