package com.mozhimen.uicorektest.layoutk.tab

import android.os.Bundle
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.manifestk.permission.annors.APermissionCheck
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.utilk.res.UtilKRes
import com.mozhimen.uicorek.layoutk.tab.top.mos.MTabTop
import com.mozhimen.uicorektest.R
import com.mozhimen.uicorektest.databinding.ActivityLayoutkTabTopBinding

@AManifestKRequire(CPermission.INTERNET)
@APermissionCheck(CPermission.INTERNET)
class LayoutKTabTopActivity : BaseActivityVB<ActivityLayoutkTabTopBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        initTabTop()
    }

    private val _tabInfo1 by lazy {
        MTabTop(
            "home",
            UtilKRes.getColor(R.color.white),
            UtilKRes.getColor(R.color.ui_blue_050)
        )
    }

    private val _tabInfo2 by lazy {
        MTabTop(
            "more",
            R.mipmap.layoutk_tab_bottom_layout_fire,
            R.mipmap.layoutk_tab_bottom_layout_fire,
            UtilKRes.getColor(R.color.ui_blue_633)
        )
    }

    private val _tabInfo3 by lazy {
        MTabTop(
            "mine",
            R.mipmap.layoutk_tab_bottom_layout_fire,
            R.mipmap.layoutk_tab_bottom_layout_fire,
            UtilKRes.getColor(R.color.white),
            UtilKRes.getColor(R.color.ui_blue_050)
        )
    }

    private fun initTabTop() {
        VB.layoutkTabTop.setTabItem(_tabInfo1)
        VB.layoutkTabTop2.setTabItem(_tabInfo2)
        VB.layoutkTabTop3.setTabItem(_tabInfo3)
    }
}