package com.mozhimen.uicorektest.layoutk.refresh

import android.os.Bundle
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.exts.dp2px
import com.mozhimen.basick.utilk.exts.postDelayed
import com.mozhimen.basick.elemk.handler.LifecycleHandler
import com.mozhimen.uicorek.layoutk.refresh.commons.IRefreshListener
import com.mozhimen.uicorek.layoutk.refresh.temps.TextOverView
import com.mozhimen.uicorektest.databinding.ActivityLayoutkRefreshTextBinding

class LayoutKRefreshTextActivity : BaseActivityVB<ActivityLayoutkRefreshTextBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        val textOverView = TextOverView(this)
        vb.layoutkRefreshTextContainer.setRefreshOverView(textOverView)
        vb.layoutkRefreshTextContainer.setRefreshParams(90f.dp2px(), 1.6f, null)
        vb.layoutkRefreshTextContainer.setRefreshListener(object : IRefreshListener {
            override fun onRefresh() {
                LifecycleHandler(this@LayoutKRefreshTextActivity).postDelayed(1000) { vb.layoutkRefreshTextContainer.refreshFinished() }
            }

            override fun enableRefresh(): Boolean {
                return true
            }
        })
    }
}