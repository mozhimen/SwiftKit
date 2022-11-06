package com.mozhimen.uicorektest.layoutk.refresh

import android.os.Bundle
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.basick.extsk.postDelayed
import com.mozhimen.basick.prefabk.handler.PrefabKHandler
import com.mozhimen.uicorek.layoutk.refresh.commons.IRefreshListener
import com.mozhimen.uicorek.layoutk.refresh.temps.LottieOverView
import com.mozhimen.uicorektest.databinding.ActivityLayoutkRefreshLottieBinding

class LayoutKRefreshLottieActivity : BaseKActivityVB<ActivityLayoutkRefreshLottieBinding>() {
    override fun initData(savedInstanceState: Bundle?) {
        val lottieOverView = LottieOverView(this)
        vb.layoutkRefreshContainerLottie.setRefreshOverView(lottieOverView)
        vb.layoutkRefreshContainerLottie.setRefreshParams(90f.dp2px(), null, null)
        vb.layoutkRefreshContainerLottie.setRefreshListener(object : IRefreshListener {
            override fun onRefresh() {
                PrefabKHandler(this@LayoutKRefreshLottieActivity).postDelayed(1000) { vb.layoutkRefreshContainerLottie.refreshFinished() }
            }

            override fun enableRefresh(): Boolean {
                return true
            }
        })
    }
}