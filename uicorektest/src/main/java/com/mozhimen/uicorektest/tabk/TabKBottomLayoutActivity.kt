package com.mozhimen.uicorektest.tabk

import android.os.Bundle
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.basick.extsk.showToast
import com.mozhimen.uicorek.tabk.bottom.mos.TabKBottomMo
import com.mozhimen.uicorek.tabk.commons.ITabKLayout
import com.mozhimen.uicorektest.R
import com.mozhimen.uicorektest.databinding.ActivityTabkBottomLayoutBinding

class TabKBottomLayoutActivity : BaseKActivity<ActivityTabkBottomLayoutBinding, BaseKViewModel>(R.layout.activity_tabk_bottom_layout) {

    override fun initData(savedInstanceState: Bundle?) {
        initTabBottom()
    }

    private fun initTabBottom() {
        vb.tabkBottomLayout.setTabBottomAlpha(0.85f)
        val bottomMoList: MutableList<TabKBottomMo> = ArrayList()
        val homeInfo = TabKBottomMo(
            "首页",
            "fonts/iconfont.ttf",
            getString(R.string.icon_home),
            getString(R.string.icon_home),
            "#ff656667",
            "#ffd44949"
        )
        val moreInfo = TabKBottomMo(
            "更多",
            R.mipmap.tabk_bottom_layout_fire,
            R.mipmap.tabk_bottom_layout_fire
        )
        val mineInfo = TabKBottomMo(
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
        vb.tabkBottomLayout.inflateInfo(bottomMoList)
        vb.tabkBottomLayout.addTabSelectedChangeListener(object : ITabKLayout.TabSelectedListener<TabKBottomMo> {
            override fun onTabSelected(index: Int, prevMo: TabKBottomMo?, nextMo: TabKBottomMo) {
                nextMo.name!!.showToast()
            }
        })
        vb.tabkBottomLayout.defaultSelected(homeInfo)
        vb.tabkBottomLayout.findTab(bottomMoList[1])?.resetTabHeight(66f.dp2px()) //改变某个Tab的高度
    }
}