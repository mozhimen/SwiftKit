package com.mozhimen.uicorektest.tabk

import android.os.Bundle
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.basek.BaseKActivityVBVM
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.uicorek.tabk.bottom.helpers.TabKFragmentAdapter
import com.mozhimen.uicorek.tabk.bottom.mos.TabKBottomMo
import com.mozhimen.uicorek.tabk.commons.ITabKLayout
import com.mozhimen.uicorektest.R
import com.mozhimen.uicorektest.databinding.ActivityTabkBottomFragmentBinding
import com.mozhimen.uicorektest.tabk.fragments.HomeFragment
import com.mozhimen.uicorektest.tabk.fragments.MineFragment
import com.mozhimen.uicorektest.tabk.fragments.MoreFragment


/**
 * @ClassName TabBottomFragmentActivity
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/8/2 14:38
 * @Version 1.0
 */
class TabKBottomFragmentActivity : BaseKActivityVB<ActivityTabkBottomFragmentBinding>() {

    companion object {
        private const val SAVED_CURRENT_ID = "SAVED_CURRENT_ID"
    }

    private var _infoList = ArrayList<TabKBottomMo>()
    private var _currentItemIndex = 0

    override fun initData(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            _currentItemIndex = savedInstanceState.getInt(SAVED_CURRENT_ID)
        }

        initTabKBottom()
    }

    private fun initTabKBottom() {
        vb.tabkBottomFragmentContainer.setTabBottomAlpha(0.85f)
        val homeInfo = TabKBottomMo(
            "首页",
            "fonts/iconfont.ttf",
            getString(R.string.icon_home),
            getString(R.string.icon_home),
            UtilKRes.getColor(R.color.black),
            UtilKRes.getColor(R.color.blue_theme),
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
            UtilKRes.getColor(R.color.black),
            UtilKRes.getColor(R.color.blue_theme),
        )
        homeInfo.fragment = HomeFragment::class.java
        moreInfo.fragment = MoreFragment::class.java
        mineInfo.fragment = MineFragment::class.java
        _infoList.apply {
            add(homeInfo)
            add(moreInfo)
            add(mineInfo)
        }
        vb.tabkBottomFragmentContainer.inflateInfo(_infoList)
        val tabKFragmentAdapter = TabKFragmentAdapter(supportFragmentManager, _infoList)
        vb.tabkBottomFragmentView.setAdapter(tabKFragmentAdapter)
        vb.tabkBottomFragmentContainer.addTabSelectedChangeListener(object :
            ITabKLayout.TabSelectedListener<TabKBottomMo> {
            override fun onTabSelected(index: Int, prevMo: TabKBottomMo?, nextMo: TabKBottomMo) {
                vb.tabkBottomFragmentView.setCurrentItem(index)
                _currentItemIndex = index
            }
        })
        vb.tabkBottomFragmentContainer.defaultSelected(_infoList[_currentItemIndex])
        vb.tabkBottomFragmentContainer.findTab(_infoList[1])?.resetTabHeight(66f.dp2px()) //改变某个Tab的高度
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(SAVED_CURRENT_ID, _currentItemIndex)
        super.onSaveInstanceState(outState)
    }
}