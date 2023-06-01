package com.mozhimen.basicktest.cachek

import android.os.Bundle
import com.mozhimen.basick.cachek.shared_preferences.CacheKSP
import com.mozhimen.basick.cachek.shared_preferences.temps.CacheKSPDelegateString
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basicktest.databinding.ActivityCachekBinding


/**
 * @ClassName CacheKActivity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/13 15:56
 * @Version 1.0
 */
class CacheKActivity : BaseActivityVB<ActivityCachekBinding>() {
    private val _spTestPro by lazy { CacheKSP.instance.with("sp_test") }
    private var _editStr: String by CacheKSPDelegateString(_spTestPro, "editStr", "")

    override fun initView(savedInstanceState: Bundle?) {
        vb.cachekEdit.setText(_editStr)
        vb.cachekBtn.setOnClickListener {
            _editStr = vb.cachekEdit.text.toString()
        }
    }
}