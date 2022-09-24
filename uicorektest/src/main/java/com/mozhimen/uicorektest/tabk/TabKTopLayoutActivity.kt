package com.mozhimen.uicorektest.tabk

import android.os.Bundle
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.extsk.showToast
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.uicorek.tabk.commons.ITabKLayout
import com.mozhimen.uicorek.tabk.top.mos.TabKTopMo
import com.mozhimen.uicorektest.R
import com.mozhimen.uicorektest.databinding.ActivityTabkTopLayoutBinding

/**
 * @ClassName TabTopLayoutActivity
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/8/4 17:17
 * @Version 1.0
 */
class TabKTopLayoutActivity : BaseKActivity<ActivityTabkTopLayoutBinding, BaseKViewModel>(R.layout.activity_tabk_top_layout) {

    override fun initData(savedInstanceState: Bundle?) {
        initTabKTopLayout()
    }

    private val _tabTop1 by lazy {
        TabKTopMo(
            "推荐",
            R.mipmap.tabk_bottom_layout_fire,
            R.mipmap.tabk_bottom_layout_fire,
            UtilKRes.getColor(R.color.blue_theme)
        )
    }

    private val _tabTop2 by lazy {
        TabKTopMo(
            "推荐1",
            R.mipmap.tabk_bottom_layout_fire,
            R.mipmap.tabk_bottom_layout_fire,
            UtilKRes.getColor(R.color.blue_theme),
            UtilKRes.getColor(R.color.blue_dark)
        )
    }

    private val _tabStr = arrayListOf(
        "数码", "鞋子", "零食", "家电", "汽车", "百货", "家居", "热门", "装修", "运动"
    )

    private fun initTabKTopLayout() {
        val infoList = ArrayList<TabKTopMo>()
        val colorDefault = UtilKRes.getColor(R.color.blue_theme)
        val colorSelected = UtilKRes.getColor(R.color.blue_dark)
        infoList.apply {
            add(_tabTop1)
            add(_tabTop2)
        }
        _tabStr.forEach {
            val info = TabKTopMo(it, colorDefault, colorSelected)
            infoList.add(info)
        }
        vb.tabkTopLayout.inflateInfo(infoList)
        vb.tabkTopLayout.addTabSelectedChangeListener(object : ITabKLayout.TabSelectedListener<TabKTopMo> {
            override fun onTabSelected(index: Int, prevMo: TabKTopMo?, nextMo: TabKTopMo) {
                nextMo.name!!.showToast()
            }
        })
        vb.tabkTopLayout.defaultSelected(infoList[0])
        vb.tabkTopLayout.setTabTopBackground(UtilKRes.getColor(R.color.blue_light))
    }
}
