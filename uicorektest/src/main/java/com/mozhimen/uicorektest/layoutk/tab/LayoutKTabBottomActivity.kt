package com.mozhimen.uicorektest.layoutk.tab

import android.os.Bundle
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.uicorek.layoutk.tab.bottom.mos.MTabBottom
import com.mozhimen.uicorektest.R
import com.mozhimen.uicorektest.databinding.ActivityLayoutkTabBottomBinding

/**
 * @ClassName TabBottomActivity
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/8/2 14:39
 * @Version 1.0
 */
class LayoutKTabBottomActivity : BaseKActivityVB<ActivityLayoutkTabBottomBinding>() {
    override fun initData(savedInstanceState: Bundle?) {
        vb.layoutkTabBottomHome.setTabItem(_homeInfo)
        vb.layoutkTabBottomMore.setTabItem(_moreInfo)
        vb.layoutkTabBottomMine.setTabItem(_mineInfo)
    }

    private val _homeInfo by lazy {
        MTabBottom(
            "首页",
            "fonts/iconfont.ttf",
            getString(R.string.icon_home),
            getString(R.string.icon_more),
            "#ff000000",//UtilKRes.getColor(android.R.color.black),
            "#ff287FF1"//UtilKRes.getColor(R.color.blue_normal)
        )
    }

    private val _moreInfo by lazy {
        MTabBottom(
            "更多",
            R.mipmap.layoutk_tab_bottom_layout_fire,
            R.mipmap.layoutk_tab_bottom_layout_fire
        )
    }

    private val _mineInfo by lazy {
        MTabBottom(
            "我的",
            R.mipmap.layoutk_tab_bottom_layout_fire,
            R.mipmap.layoutk_tab_bottom_layout_fire,
            UtilKRes.getColor(android.R.color.black),
            UtilKRes.getColor(R.color.blue_normal)
        )
    }
}