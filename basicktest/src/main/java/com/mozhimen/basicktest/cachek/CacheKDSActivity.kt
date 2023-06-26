package com.mozhimen.basicktest.cachek

import android.content.Context
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.mozhimen.basick.cachek.datastore.CacheKDS
import com.mozhimen.basick.cachek.datastore.temps.CacheKDSDelegateString
import com.mozhimen.basick.cachek.shared_preferences.CacheKSP
import com.mozhimen.basick.cachek.shared_preferences.temps.CacheKSPDelegateString
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.android.widget.showToast
import com.mozhimen.basicktest.databinding.ActivityCachekDsBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CacheKDSActivity : BaseActivityVB<ActivityCachekDsBinding>() {
    private val _dsTestPro by lazy { CacheKDS.instance.with("sp_test") }
    private var _editStr: String by CacheKDSDelegateString(_dsTestPro, "editStr", "")

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
