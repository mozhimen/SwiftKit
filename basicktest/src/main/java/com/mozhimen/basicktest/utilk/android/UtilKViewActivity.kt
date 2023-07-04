package com.mozhimen.basicktest.utilk.android

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.elemk.cons.CDateFormat
import com.mozhimen.basick.utilk.android.util.dt
import com.mozhimen.basick.utilk.android.view.applyDebounceClickListener
import com.mozhimen.basick.utilk.java.util.toDateStr
import com.mozhimen.basicktest.databinding.ActivityUtilkViewBinding

class UtilKViewActivity : BaseActivityVB<ActivityUtilkViewBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        vb.utilkViewBtnDebounce.applyDebounceClickListener(lifecycleScope) {
            "点击${System.currentTimeMillis().toDateStr(CDateFormat.yyyyMMddHHmmss)}".dt(TAG)
        }
    }
}