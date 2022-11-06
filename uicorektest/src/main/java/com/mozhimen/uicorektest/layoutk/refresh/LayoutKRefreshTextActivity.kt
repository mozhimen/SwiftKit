package com.mozhimen.uicorektest.layoutk.refresh

import android.os.Bundle
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.basick.extsk.postDelayed
import com.mozhimen.basick.prefabk.handler.PrefabKHandler
import com.mozhimen.uicorek.layoutk.refresh.commons.IRefreshListener
import com.mozhimen.uicorek.layoutk.refresh.temps.TextOverView
import com.mozhimen.uicorektest.databinding.ActivityLayoutkRefreshTextBinding

class LayoutKRefreshTextActivity : BaseKActivityVB<ActivityLayoutkRefreshTextBinding>() {

    override fun initData(savedInstanceState: Bundle?) {
        val textOverView = TextOverView(this)
        vb.layoutkRefreshTextContainer.setRefreshOverView(textOverView)
        vb.layoutkRefreshTextContainer.setRefreshParams(90f.dp2px(), 1.6f, null)
        vb.layoutkRefreshTextContainer.setRefreshListener(object : IRefreshListener {
            override fun onRefresh() {
                PrefabKHandler(this@LayoutKRefreshTextActivity).postDelayed(1000) { vb.layoutkRefreshTextContainer.refreshFinished() }
            }

            override fun enableRefresh(): Boolean {
                return true
            }
        })
    }
}