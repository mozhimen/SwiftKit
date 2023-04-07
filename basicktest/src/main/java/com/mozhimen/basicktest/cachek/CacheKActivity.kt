package com.mozhimen.basicktest.cachek

import android.os.Bundle
import com.mozhimen.basick.cachek.cacheksp.CacheKSP
import com.mozhimen.basick.cachek.cacheksp.temps.CacheKSPDelegateString
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
    private val _spPro by lazy { CacheKSP.instance.with("cachek_sp_test") }
    private var _editText by CacheKSPDelegateString(_spPro, "key", "")
    override fun initView(savedInstanceState: Bundle?) {
        VB.cachekEdit.setText(_editText)
        VB.cachekBtn.setOnClickListener {
            _editText = VB.cachekEdit.text.toString()
        }
    }
}