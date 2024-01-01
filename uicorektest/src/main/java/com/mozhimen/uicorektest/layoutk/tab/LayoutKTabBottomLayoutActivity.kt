package com.mozhimen.uicorektest.layoutk.tab

import android.os.Bundle
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVB
import com.mozhimen.basick.utilk.android.util.dp2px
import com.mozhimen.basick.utilk.android.widget.showToast
import com.mozhimen.uicorek.layoutk.tab.bottom.mos.MTabBottom
import com.mozhimen.uicorek.layoutk.tab.commons.ITabSelectedListener
import com.mozhimen.uicorektest.R
import com.mozhimen.uicorektest.databinding.ActivityLayoutkTabBottomLayoutBinding

class LayoutKTabBottomLayoutActivity : BaseActivityVB<ActivityLayoutkTabBottomLayoutBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
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
        vb.layoutkTabBottomLayout.inflateTabItem(bottomMoList)
        vb.layoutkTabBottomLayout.addTabItemSelectedListener(object : ITabSelectedListener<MTabBottom> {
            override fun onTabItemSelected(index: Int, prevItem: MTabBottom?, currentItem: MTabBottom) {
                currentItem.name!!.showToast()
            }
        })
        vb.layoutkTabBottomLayout.defaultSelected(homeInfo)
        vb.layoutkTabBottomLayout.findTabItem(bottomMoList[1])?.resetTabHeight(66f.dp2px().toInt()) //改变某个Tab的高度
    }
}