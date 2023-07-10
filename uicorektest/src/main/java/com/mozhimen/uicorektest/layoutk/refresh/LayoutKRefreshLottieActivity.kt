package com.mozhimen.uicorektest.layoutk.refresh

import android.os.Bundle
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.utilk.android.util.dp2px
import com.mozhimen.basick.elemk.android.os.WakeBefPauseLifecycleHandler
import com.mozhimen.basick.utilk.android.os.applyPostDelayed
import com.mozhimen.uicorek.layoutk.refresh.commons.IRefreshListener
import com.mozhimen.uicorek.layoutk.refresh.temps.LottieOverView
import com.mozhimen.uicorektest.databinding.ActivityLayoutkRefreshLottieBinding

class LayoutKRefreshLottieActivity : BaseActivityVB<ActivityLayoutkRefreshLottieBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        val lottieOverView = LottieOverView(this)
        vb.layoutkRefreshContainerLottie.setRefreshOverView(lottieOverView)
        vb.layoutkRefreshContainerLottie.setRefreshParams(90f.dp2px().toInt(), null, null)
        vb.layoutkRefreshContainerLottie.setRefreshListener(object : IRefreshListener {
            override fun onRefreshing() {
                WakeBefPauseLifecycleHandler(this@LayoutKRefreshLottieActivity).applyPostDelayed(1000) {
                    vb.layoutkRefreshContainerLottie.finishRefresh()
                }
            }

            override fun onEnableRefresh(): Boolean {
                return true
            }
        })
    }
}