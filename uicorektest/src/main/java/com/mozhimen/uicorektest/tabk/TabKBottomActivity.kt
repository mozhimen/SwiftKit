package com.mozhimen.uicorektest.tabk

import android.os.Bundle
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.uicorek.tabk.bottom.mos.TabKBottomMo
import com.mozhimen.uicorektest.R
import com.mozhimen.uicorektest.databinding.ActivityTabkBottomBinding

/**
 * @ClassName TabBottomActivity
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/8/2 14:39
 * @Version 1.0
 */
class TabKBottomActivity : BaseKActivity<ActivityTabkBottomBinding, BaseKViewModel>(R.layout.activity_tabk_bottom) {
    override fun initData(savedInstanceState: Bundle?) {
        vb.tabkBottomHome.setTabInfo(_homeInfo)
        vb.tabkBottomMore.setTabInfo(_moreInfo)
        vb.tabkBottomMine.setTabInfo(_mineInfo)
    }

    private val _homeInfo by lazy {
        TabKBottomMo(
            "首页",
            "fonts/iconfont.ttf",
            getString(R.string.icon_home),
            getString(R.string.icon_more),
            "#ff000000",//UtilKRes.getColor(android.R.color.black),
            "#ff287FF1"//UtilKRes.getColor(R.color.blue_normal)
        )
    }

    private val _moreInfo by lazy {
        TabKBottomMo(
            "更多",
            R.mipmap.tabk_bottom_layout_fire,
            R.mipmap.tabk_bottom_layout_fire
        )
    }

    private val _mineInfo by lazy {
        TabKBottomMo(
            "我的",
            R.mipmap.tabk_bottom_layout_fire,
            R.mipmap.tabk_bottom_layout_fire,
            UtilKRes.getColor(android.R.color.black),
            UtilKRes.getColor(R.color.blue_normal)
        )
    }
}