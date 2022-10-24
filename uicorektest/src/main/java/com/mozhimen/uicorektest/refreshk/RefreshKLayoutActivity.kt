package com.mozhimen.uicorektest.refreshk

import android.os.Bundle
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.basek.BaseKActivityVBVM
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.basick.extsk.postDelayed
import com.mozhimen.basick.prefabk.handler.PrefabKHandler
import com.mozhimen.uicorek.refreshk.commons.IRefreshK
import com.mozhimen.uicorek.refreshk.customs.TextOverView
import com.mozhimen.uicorektest.databinding.ActivityRefreshkBinding

class RefreshKLayoutActivity : BaseKActivityVB<ActivityRefreshkBinding>() {

    override fun initData(savedInstanceState: Bundle?) {
        val textOverView = TextOverView(this)
        vb.refreshkContainer.setRefreshOverView(textOverView)
        vb.refreshkContainer.setRefreshParams(90f.dp2px(), 1.6f, null)
        vb.refreshkContainer.setRefreshListener(object : IRefreshK.IRefreshKListener {
            override fun onRefresh() {
                PrefabKHandler(this@RefreshKLayoutActivity).postDelayed(1000) { vb.refreshkContainer.refreshFinished() }
            }

            override fun enableRefresh(): Boolean {
                return true
            }
        })
    }
}