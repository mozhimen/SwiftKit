package com.mozhimen.uicorektest.tabk

import android.os.Bundle
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.uicorek.tabk.top.mos.TabKTopMo
import com.mozhimen.uicorektest.R
import com.mozhimen.uicorektest.databinding.ActivityTabkTopBinding

class TabKTopActivity : BaseKActivity<ActivityTabkTopBinding, BaseKViewModel>(R.layout.activity_tabk_top) {
    override fun initData(savedInstanceState: Bundle?) {
        initTabKTop()
    }

    private val _tabInfo1 by lazy {
        TabKTopMo(
            "home",
            UtilKRes.getColor(R.color.white),
            UtilKRes.getColor(R.color.blue_light)
        )
    }

    private val _tabInfo2 by lazy {
        TabKTopMo(
            "more",
            R.mipmap.tabk_bottom_layout_fire,
            R.mipmap.tabk_bottom_layout_fire,
            UtilKRes.getColor(R.color.blue_theme)
        )
    }

    private val _tabInfo3 by lazy {
        TabKTopMo(
            "mine",
            R.mipmap.tabk_bottom_layout_fire,
            R.mipmap.tabk_bottom_layout_fire,
            UtilKRes.getColor(R.color.white),
            UtilKRes.getColor(R.color.blue_light)
        )
    }

    private fun initTabKTop() {
        vb.tabkTop1.setTabInfo(_tabInfo1)
        vb.tabkTop2.setTabInfo(_tabInfo2)
        vb.tabkTop3.setTabInfo(_tabInfo3)
    }
}