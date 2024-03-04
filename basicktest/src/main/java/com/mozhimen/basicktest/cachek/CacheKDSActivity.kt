package com.mozhimen.basicktest.cachek

import android.os.Bundle
import com.mozhimen.basick.cachek.datastore.CacheKDS
import com.mozhimen.basick.cachek.datastore.temps.CacheKDSVarPropertyString
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVDB
import com.mozhimen.basick.utilk.android.widget.showToast
import com.mozhimen.basicktest.databinding.ActivityCachekDsBinding

class CacheKDSActivity : BaseActivityVDB<ActivityCachekDsBinding>() {
    private val _dsTestPro by lazy { CacheKDS.instance.with("sp_test") }
    private var _editStr: String by CacheKDSVarPropertyString(_dsTestPro, "editStr", "")

    override fun initView(savedInstanceState: Bundle?) {
        vdb.cachekDsEdit.setText(_editStr)
        vdb.cachekDsBtn.setOnClickListener {
            _editStr = vdb.cachekDsEdit.text.toString()
        }
        vdb.cachekDsBtn1.setOnClickListener {
            "${_dsTestPro.contains<String>("editStr")}".showToast()
        }
    }
}
