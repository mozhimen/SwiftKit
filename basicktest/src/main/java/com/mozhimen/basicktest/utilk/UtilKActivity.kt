package com.mozhimen.basicktest.utilk

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.extsk.drawable2Bitmap
import com.mozhimen.basick.extsk.showToast
import com.mozhimen.basick.extsk.start
import com.mozhimen.basick.utilk.*
import com.mozhimen.basicktest.R
import com.mozhimen.basicktest.databinding.ActivityUtilkBinding
import kotlinx.coroutines.launch

class UtilKActivity : BaseKActivity<ActivityUtilkBinding, BaseKViewModel>(R.layout.activity_utilk) {

    override fun initData(savedInstanceState: Bundle?) {
        UtilKDataBus.with<String>("stickyData").setStickyData("即时消息主界面")
    }

    fun goUtilKBitmap(view: View) {
        start<UtilKBitmapActivity>()
    }

    fun goUtilKDataBus(view: View) {
        start<UtilKDataBusActivity>()
    }

    fun goUtilKFile(view: View) {
        start<UtilKFileActivity>()
    }

    fun goUtilKGesture(view: View) {
        start<UtilKGestureActivity>()
    }

    fun goUtilKVerifyUrl(view: View) {
        start<UtilKVerifyUrlActivity>()
    }
}