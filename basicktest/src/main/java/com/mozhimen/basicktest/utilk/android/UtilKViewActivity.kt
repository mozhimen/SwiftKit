package com.mozhimen.basicktest.utilk.android

import android.os.Bundle
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVDB
import com.mozhimen.basick.elemk.java.util.cons.CDateFormat
import com.mozhimen.basick.utilk.android.util.d
import com.mozhimen.basick.utilk.android.view.UtilKDecorView
import com.mozhimen.basick.utilk.android.view.UtilKViewGroup
import com.mozhimen.basick.utilk.android.view.UtilKViewGroupWrapper
import com.mozhimen.basick.utilk.android.view.applyDebounceClickListener
import com.mozhimen.basick.utilk.android.view.applyHapticOnTouchListener
import com.mozhimen.basick.utilk.android.view.applySuspendDebounceClickListener
import com.mozhimen.basick.utilk.java.util.longDate2strDate
import com.mozhimen.basicktest.databinding.ActivityUtilkViewBinding
import kotlinx.coroutines.delay


class UtilKViewActivity : BaseActivityVDB<ActivityUtilkViewBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        vdb.utilkViewBtnDebounce.applyDebounceClickListener(lifecycleScope) {
            "点击${System.currentTimeMillis().longDate2strDate(CDateFormat.yyyyMMddHHmmss)}".d(TAG)
        }

        vdb.utilkViewBtnDebounceSuspend.applySuspendDebounceClickListener(lifecycleScope) {
            "点击${System.currentTimeMillis().longDate2strDate(CDateFormat.yyyyMMddHHmmss)}".d(TAG)
            delay(2000)
            "延迟${System.currentTimeMillis().longDate2strDate(CDateFormat.yyyyMMddHHmmss)}".d(TAG)
        }

        vdb.utilkViewBtnHapticFeedback.apply {
            applyHapticOnTouchListener()
            setOnClickListener {
                UtilKLogWrapper.d(TAG, "initView: setOnClickListener")
            }
        }

        vdb.utilkViewBtnGetAllViews.setOnClickListener {
            UtilKLogWrapper.d(TAG, "initView: ${UtilKViewGroupWrapper.getChildViews(UtilKDecorView.getAsViewGroup(this)).map { it.javaClass.name + "\n" }}")
        }
    }
}