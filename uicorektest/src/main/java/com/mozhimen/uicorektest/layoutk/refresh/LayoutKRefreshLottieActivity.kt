package com.mozhimen.uicorektest.layoutk.refresh

import android.os.Bundle
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.exts.dp2px
import com.mozhimen.basick.utilk.exts.postDelayed
import com.mozhimen.basick.elemk.handler.WakeBefPauseLifecycleHandler
import com.mozhimen.uicorek.layoutk.refresh.commons.IRefreshListener
import com.mozhimen.uicorek.layoutk.refresh.temps.LottieOverView
import com.mozhimen.uicorektest.databinding.ActivityLayoutkRefreshLottieBinding

class LayoutKRefreshLottieActivity : BaseActivityVB<ActivityLayoutkRefreshLottieBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        val lottieOverView = LottieOverView(this)
        VB.layoutkRefreshContainerLottie.setRefreshOverView(lottieOverView)
        VB.layoutkRefreshContainerLottie.setRefreshParams(90f.dp2px().toInt(), null, null)
        VB.layoutkRefreshContainerLottie.setRefreshListener(object : IRefreshListener {
            override fun onRefreshing() {
                WakeBefPauseLifecycleHandler(this@LayoutKRefreshLottieActivity).postDelayed(1000) {
                    VB.layoutkRefreshContainerLottie.finishRefresh()
                }
            }

            override fun onEnableRefresh(): Boolean {
                return true
            }
        })
    }
}