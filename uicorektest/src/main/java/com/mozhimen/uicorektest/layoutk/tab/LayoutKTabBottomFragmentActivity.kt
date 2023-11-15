package com.mozhimen.uicorektest.layoutk.tab

import android.os.Bundle
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.utilk.android.util.dp2px
import com.mozhimen.basick.utilk.android.content.UtilKRes
import com.mozhimen.uicorek.layoutk.tab.bottom.helpers.TabBottomFragmentAdapter
import com.mozhimen.uicorek.layoutk.tab.bottom.mos.MTabBottom
import com.mozhimen.uicorek.layoutk.tab.commons.ITabSelectedListener
import com.mozhimen.uicorektest.R
import com.mozhimen.uicorektest.databinding.ActivityLayoutkTabBottomFragmentBinding
import com.mozhimen.uicorektest.layoutk.tab.fragments.HomeFragment
import com.mozhimen.uicorektest.layoutk.tab.fragments.MineFragment
import com.mozhimen.uicorektest.layoutk.tab.fragments.MoreFragment

/**
 * @ClassName TabBottomFragmentActivity
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/8/2 14:38
 * @Version 1.0
 */
class LayoutKTabBottomFragmentActivity : BaseActivityVB<ActivityLayoutkTabBottomFragmentBinding>() {

    private var _infoList = ArrayList<MTabBottom>()
    private var _currentItemIndex = 0

    override fun initView(savedInstanceState: Bundle?) {
        initTabBottom()
    }

    private fun initTabBottom() {
        vb.layoutkTabBottomFragmentContainer.setTabBottomAlpha(0.85f)
        val homeInfo = MTabBottom(
            "首页",
            "fonts/iconfont.ttf",
            getString(R.string.icon_home),
            getString(R.string.icon_home),
            UtilKRes.getColor(R.color.black),
            UtilKRes.getColor(com.mozhimen.uicorek.R.color.cok_blue_4785ef),
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
            UtilKRes.getColor(R.color.black),
            UtilKRes.getColor(com.mozhimen.uicorek.R.color.cok_blue_4785ef),
        )
        homeInfo.fragment = HomeFragment::class.java
        moreInfo.fragment = MoreFragment::class.java
        mineInfo.fragment = MineFragment::class.java
        _infoList.apply {
            add(homeInfo)
            add(moreInfo)
            add(mineInfo)
        }
        vb.layoutkTabBottomFragmentContainer.inflateTabItem(_infoList)
        val tabBottomFragmentAdapter = TabBottomFragmentAdapter(supportFragmentManager, _infoList)
        vb.layoutkTabBottomFragmentView.setAdapter(tabBottomFragmentAdapter)
        vb.layoutkTabBottomFragmentContainer.addTabItemSelectedListener(object : ITabSelectedListener<MTabBottom> {
            override fun onTabItemSelected(index: Int, prevItem: MTabBottom?, currentItem: MTabBottom) {
                vb.layoutkTabBottomFragmentView.setCurrentItem(index)
                _currentItemIndex = index
            }
        })
        vb.layoutkTabBottomFragmentContainer.defaultSelected(_infoList[_currentItemIndex])
        vb.layoutkTabBottomFragmentContainer.findTabItem(_infoList[1])?.resetTabHeight(66f.dp2px().toInt()) //改变某个Tab的高度
    }
}