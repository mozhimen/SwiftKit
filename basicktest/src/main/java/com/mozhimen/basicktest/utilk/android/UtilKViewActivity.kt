package com.mozhimen.basicktest.utilk.android

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.elemk.java.util.cons.CDateFormat
import com.mozhimen.basick.utilk.android.util.dt
import com.mozhimen.basick.utilk.android.view.applyDebounceClickListener
import com.mozhimen.basick.utilk.android.view.applySuspendDebounceClickListener
import com.mozhimen.basick.utilk.java.util.asDateStr
import com.mozhimen.basicktest.databinding.ActivityUtilkViewBinding
import kotlinx.coroutines.delay


class UtilKViewActivity : BaseActivityVB<ActivityUtilkViewBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        vb.utilkViewBtnDebounce.applyDebounceClickListener(lifecycleScope) {
            "点击${System.currentTimeMillis().asDateStr(CDateFormat.yyyyMMddHHmmss)}".dt(TAG)
        }

        vb.utilkViewBtnDebounceSuspend.applySuspendDebounceClickListener(lifecycleScope) {
            "点击${System.currentTimeMillis().asDateStr(CDateFormat.yyyyMMddHHmmss)}".dt(TAG)
            delay(2000)
            "延迟${System.currentTimeMillis().asDateStr(CDateFormat.yyyyMMddHHmmss)}".dt(TAG)
        }
    }
}