package com.mozhimen.uicorektest.layoutk.refresh

import android.os.Bundle
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.exts.dp2px
import com.mozhimen.basick.utilk.exts.postDelayed
import com.mozhimen.basick.elemk.handler.WakeBefPauseLifecycleHandler
import com.mozhimen.uicorek.layoutk.refresh.commons.IRefreshListener
import com.mozhimen.uicorek.layoutk.refresh.temps.TextOverView
import com.mozhimen.uicorektest.databinding.ActivityLayoutkRefreshTextBinding

class LayoutKRefreshTextActivity : BaseActivityVB<ActivityLayoutkRefreshTextBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        val textOverView = TextOverView(this)
        VB.layoutkRefreshTextContainer.setRefreshOverView(textOverView)
        VB.layoutkRefreshTextContainer.setRefreshParams(90f.dp2px().toInt(), 1.6f, null)
        VB.layoutkRefreshTextContainer.setRefreshListener(object : IRefreshListener {
            override fun onRefreshing() {
                WakeBefPauseLifecycleHandler(this@LayoutKRefreshTextActivity).postDelayed(1000) { VB.layoutkRefreshTextContainer.finishRefresh() }
            }

            override fun onEnableRefresh(): Boolean {
                return true
            }
        })
    }
}