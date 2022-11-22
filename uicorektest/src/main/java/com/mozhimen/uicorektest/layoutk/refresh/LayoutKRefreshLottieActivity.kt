package com.mozhimen.uicorektest.layoutk.refresh

import android.os.Bundle
import com.mozhimen.basick.elemk.activity.commons.BaseActivityVB
import com.mozhimen.basick.utilk.exts.dp2px
import com.mozhimen.basick.utilk.exts.postDelayed
import com.mozhimen.basick.elemk.handler.LifecycleHandler
import com.mozhimen.uicorek.layoutk.refresh.commons.IRefreshListener
import com.mozhimen.uicorek.layoutk.refresh.temps.LottieOverView
import com.mozhimen.uicorektest.databinding.ActivityLayoutkRefreshLottieBinding

class LayoutKRefreshLottieActivity : BaseActivityVB<ActivityLayoutkRefreshLottieBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        val lottieOverView = LottieOverView(this)
        vb.layoutkRefreshContainerLottie.setRefreshOverView(lottieOverView)
        vb.layoutkRefreshContainerLottie.setRefreshParams(90f.dp2px(), null, null)
        vb.layoutkRefreshContainerLottie.setRefreshListener(object : IRefreshListener {
            override fun onRefresh() {
                LifecycleHandler(this@LayoutKRefreshLottieActivity).postDelayed(1000) { vb.layoutkRefreshContainerLottie.refreshFinished() }
            }

            override fun enableRefresh(): Boolean {
                return true
            }
        })
    }
}