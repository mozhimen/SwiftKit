package com.mozhimen.basicktest.utilk.android

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.elemk.android.view.HapticOnTouchCallback
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVB
import com.mozhimen.basick.elemk.java.util.cons.CDateFormat
import com.mozhimen.basick.utilk.android.util.dt
import com.mozhimen.basick.utilk.android.view.applyDebounceClickListener
import com.mozhimen.basick.utilk.android.view.applyPerformHapticFeedback
import com.mozhimen.basick.utilk.android.view.applySuspendDebounceClickListener
import com.mozhimen.basick.utilk.java.util.longDate2strDate
import com.mozhimen.basicktest.databinding.ActivityUtilkViewBinding
import kotlinx.coroutines.delay


class UtilKViewActivity : BaseActivityVB<ActivityUtilkViewBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        vb.utilkViewBtnDebounce.applyDebounceClickListener(lifecycleScope) {
            "点击${System.currentTimeMillis().longDate2strDate(CDateFormat.yyyyMMddHHmmss)}".dt(TAG)
        }

        vb.utilkViewBtnDebounceSuspend.applySuspendDebounceClickListener(lifecycleScope) {
            "点击${System.currentTimeMillis().longDate2strDate(CDateFormat.yyyyMMddHHmmss)}".dt(TAG)
            delay(2000)
            "延迟${System.currentTimeMillis().longDate2strDate(CDateFormat.yyyyMMddHHmmss)}".dt(TAG)
        }

        vb.utilkViewBtnHapticFeedback.apply {
            applyPerformHapticFeedback()
            setOnClickListener {
                Log.d(TAG, "initView: setOnClickListener")
            }
        }
    }
}