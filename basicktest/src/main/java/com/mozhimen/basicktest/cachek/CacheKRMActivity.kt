package com.mozhimen.basicktest.cachek

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mozhimen.basick.cachek.datastore.CacheKDS
import com.mozhimen.basick.cachek.datastore.temps.CacheKDSDelegateString
import com.mozhimen.basick.cachek.room.CacheKRM
import com.mozhimen.basick.cachek.room.temps.CacheKRMDelegateString
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.android.widget.showToast
import com.mozhimen.basicktest.R
import com.mozhimen.basicktest.databinding.ActivityCachekRmBinding

class CacheKRMActivity : BaseActivityVB<ActivityCachekRmBinding>() {
    private var _editStr: String by CacheKRMDelegateString(CacheKRM, "editStr", "")

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