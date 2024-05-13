package com.mozhimen.basicktest.utilk.android

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVDB
import com.mozhimen.basick.elemk.java.util.cons.CDateFormat
import com.mozhimen.basick.utilk.android.util.d
import com.mozhimen.basick.utilk.android.view.UtilKDecorView
import com.mozhimen.basick.utilk.android.view.UtilKViewGroupWrapper
import com.mozhimen.basick.utilk.android.view.applyDebounceClickListener
import com.mozhimen.basick.utilk.android.view.applyHapticOnTouchListener
import com.mozhimen.basick.utilk.android.view.applySuspendDebounceClickListener
import com.mozhimen.basick.utilk.java.util.longDate2strDate
import com.mozhimen.basicktest.databinding.ActivityUtilkViewBinding
import kotlinx.coroutines.delay
import android.view.ViewGroup
import android.view.View
import android.app.Dialog
import com.mozhimen.basick.utilk.android.app.UtilKActivityWrapper
import com.mozhimen.basick.utilk.android.widget.showToast


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

            UtilKLogWrapper.d(TAG, "initView: ${UtilKViewGroupWrapper.getAllChildViews(UtilKDecorView.getAsViewGroup(this)).map { it.javaClass.name + "\n" }}")
            UtilKLogWrapper.d(TAG, "initView: ${UtilKViewGroupWrapper.getAllViews(UtilKDecorView.getAsViewGroup(this)).map { it.javaClass.name + "\n" }}")
            UtilKLogWrapper.d(TAG, "initView: hasFloatWindow_ofToken ${UtilKActivityWrapper.hasFloatWindow_ofToken(this)}")
            val alertDialog = AlertDialog.Builder(this).setCancelable(false).setPositiveButton("获取Window所有的View", null).setNegativeButton("关闭", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: android.content.DialogInterface?, which: kotlin.Int) {
                    dialog?.dismiss()
                }
            }).show()
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                UtilKLogWrapper.d(TAG, "initView: ${UtilKViewGroupWrapper.getAllChildViews(UtilKDecorView.get(this).rootView as ViewGroup).map { it.javaClass.name + "\n" }}")
                UtilKLogWrapper.d(TAG, "initView: ${UtilKViewGroupWrapper.getAllViews(UtilKDecorView.get(this@UtilKViewActivity).rootView as ViewGroup).map { it.javaClass.name + "\n" }}")
                UtilKLogWrapper.d(TAG, "initView: hasFloatWindow_ofToken ${UtilKActivityWrapper.hasFloatWindow_ofToken(this)}")
            }
        }
    }
}