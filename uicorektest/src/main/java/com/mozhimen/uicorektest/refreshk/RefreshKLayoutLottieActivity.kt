package com.mozhimen.uicorektest.refreshk

import android.os.Bundle
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.basek.BaseKActivityVBVM
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.basick.extsk.postDelayed
import com.mozhimen.basick.prefabk.handler.PrefabKHandler
import com.mozhimen.uicorek.refreshk.commons.IRefreshK
import com.mozhimen.uicorek.refreshk.customs.LottieOverView
import com.mozhimen.uicorektest.databinding.ActivityRefreshkLottieBinding

class RefreshKLayoutLottieActivity : BaseKActivityVB<ActivityRefreshkLottieBinding>() {
    override fun initData(savedInstanceState: Bundle?) {
        val lottieOverView = LottieOverView(this)
        vb.refreshkContainerLottie.setRefreshOverView(lottieOverView)
        vb.refreshkContainerLottie.setRefreshParams(90f.dp2px(), null, null)
        vb.refreshkContainerLottie.setRefreshListener(object : IRefreshK.IRefreshKListener {
            override fun onRefresh() {
                PrefabKHandler(this@RefreshKLayoutLottieActivity).postDelayed(1000) { vb.refreshkContainerLottie.refreshFinished() }
            }

            override fun enableRefresh(): Boolean {
                return true
            }
        })
    }
}