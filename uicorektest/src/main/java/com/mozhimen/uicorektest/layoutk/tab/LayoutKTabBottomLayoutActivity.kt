package com.mozhimen.uicorektest.layoutk.tab

import android.os.Bundle
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.basick.extsk.showToast
import com.mozhimen.uicorek.layoutk.tab.bottom.mos.MTabBottom
import com.mozhimen.uicorek.layoutk.tab.commons.ITabLayout
import com.mozhimen.uicorektest.R
import com.mozhimen.uicorektest.databinding.ActivityLayoutkTabBottomLayoutBinding

class LayoutKTabBottomLayoutActivity : BaseKActivityVB<ActivityLayoutkTabBottomLayoutBinding>() {

    override fun initData(savedInstanceState: Bundle?) {
        initTabBottom()
    }

    private fun initTabBottom() {
        vb.layoutkTabBottomLayout.setTabBottomAlpha(0.85f)
        val bottomMoList: MutableList<MTabBottom> = ArrayList()
        val homeInfo = MTabBottom(
            "首页",
            "fonts/iconfont.ttf",
            getString(R.string.icon_home),
            getString(R.string.icon_home),
            "#ff656667",
            "#ffd44949"
        )
        val moreInfo = MTabBottom(
            "更多",
            R.mipmap.layoutk_tab_bottom_layout_fire,
            R.mipmap.layoutk_tab_bottom_layout_fire
        )
        val mineInfo = MTabBottom(
            "我的",
            "fonts/iconfont.ttf",
            getString(R.string.icon_mine),
            getString(R.string.icon_mine),
            "#ff656667",
            "#ffd44949"
        )
        bottomMoList.apply {
            add(homeInfo)
            add(moreInfo)
            add(mineInfo)
        }
        vb.layoutkTabBottomLayout.inflateInfo(bottomMoList)
        vb.layoutkTabBottomLayout.addTabSelectedChangeListener(object : ITabLayout.TabSelectedListener<MTabBottom> {
            override fun onTabSelected(index: Int, prevMo: MTabBottom?, nextMo: MTabBottom) {
                nextMo.name!!.showToast()
            }
        })
        vb.layoutkTabBottomLayout.defaultSelected(homeInfo)
        vb.layoutkTabBottomLayout.findTab(bottomMoList[1])?.resetTabHeight(66f.dp2px()) //改变某个Tab的高度
    }
}