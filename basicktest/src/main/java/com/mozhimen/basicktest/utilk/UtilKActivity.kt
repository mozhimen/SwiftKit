package com.mozhimen.basicktest.utilk

import android.os.Bundle
import android.view.View
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.exts.startContext
import com.mozhimen.basick.utilk.jetpack.lifecycle.UtilKDataBus
import com.mozhimen.basicktest.databinding.ActivityUtilkBinding
import com.mozhimen.basicktest.utilk.app.UtilKApkActivity
import com.mozhimen.basicktest.utilk.content.UtilKContentActivity
import com.mozhimen.basicktest.utilk.graphics.UtilKBitmapActivity
import com.mozhimen.basicktest.utilk.java.UtilKEncryptActivity
import com.mozhimen.basicktest.utilk.java.UtilKFileActivity
import com.mozhimen.basicktest.utilk.java.UtilKVerifyUrlActivity
import com.mozhimen.basicktest.utilk.jetpack.UtilKDataBusActivity
import com.mozhimen.basicktest.utilk.res.UtilKAssetActivity
import com.mozhimen.basicktest.utilk.view.UtilKInputActivity
import com.mozhimen.basicktest.utilk.view.UtilKScreenActivity

class UtilKActivity : BaseActivityVB<ActivityUtilkBinding>() {
    override fun initData(savedInstanceState: Bundle?) {
        UtilKDataBus.with<String>("stickyData").setStickyData("即时消息主界面")
        super.initData(savedInstanceState)
    }

    fun goUtilKApk(view: View) {
        startContext<UtilKApkActivity>()
    }

    fun goUtilKContent(view: View) {
        startContext<UtilKContentActivity>()
    }

    fun goUtilKBitmap(view: View) {
        startContext<UtilKBitmapActivity>()
    }

    fun goUtilKEncrypt(view: View) {
        startContext<UtilKEncryptActivity>()
    }

    fun goUtilKFile(view: View) {
        startContext<UtilKFileActivity>()
    }

    fun goUtilKVerifyUrl(view: View) {
        startContext<UtilKVerifyUrlActivity>()
    }

    fun goUtilKDataBus(view: View) {
        startContext<UtilKDataBusActivity>()
    }

    fun goUtilKAsset(view: View) {
        startContext<UtilKAssetActivity>()
    }

    fun goUtilKInput(view: View) {
        startContext<UtilKInputActivity>()
    }

    fun goUtilKScreen(view: View) {
        startContext<UtilKScreenActivity>()
    }


}