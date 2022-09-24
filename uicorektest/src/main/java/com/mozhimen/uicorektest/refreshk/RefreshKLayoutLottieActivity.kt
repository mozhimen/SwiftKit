package com.mozhimen.uicorektest.refreshk

import android.os.Bundle
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.eventk.EventKHandler
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.basick.extsk.postDelayed
import com.mozhimen.uicorek.refreshk.commons.IRefreshK
import com.mozhimen.uicorek.refreshk.customs.LottieOverView
import com.mozhimen.uicorektest.R
import com.mozhimen.uicorektest.databinding.ActivityRefreshkLottieBinding

class RefreshKLayoutLottieActivity : BaseKActivity<ActivityRefreshkLottieBinding, BaseKViewModel>(R.layout.activity_refreshk_lottie) {
    override fun initData(savedInstanceState: Bundle?) {
        val lottieOverView = LottieOverView(this)
        vb.refreshkContainerLottie.setRefreshOverView(lottieOverView)
        vb.refreshkContainerLottie.setRefreshParams(90f.dp2px(), null, null)
        vb.refreshkContainerLottie.setRefreshListener(object : IRefreshK.IRefreshKListener {
            override fun onRefresh() {
                EventKHandler(this@RefreshKLayoutLottieActivity).postDelayed(1000) { vb.refreshkContainerLottie.refreshFinished() }
            }

            override fun enableRefresh(): Boolean {
                return true
            }
        })
    }
}