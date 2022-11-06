package com.mozhimen.uicorektest.layoutk.tab

import android.os.Bundle
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.extsk.showToast
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.uicorek.layoutk.tab.commons.ITabLayout
import com.mozhimen.uicorek.layoutk.tab.top.mos.MTabTop
import com.mozhimen.uicorektest.R
import com.mozhimen.uicorektest.databinding.ActivityLayoutkTabTopLayoutBinding

/**
 * @ClassName TabTopLayoutActivity
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/8/4 17:17
 * @Version 1.0
 */
class LayoutKTabTopLayoutActivity : BaseKActivityVB<ActivityLayoutkTabTopLayoutBinding>() {

    override fun initData(savedInstanceState: Bundle?) {
        initTabTopLayout()
    }

    private val _tabTop1 by lazy {
        MTabTop(
            "推荐",
            R.mipmap.layoutk_tab_bottom_layout_fire,
            R.mipmap.layoutk_tab_bottom_layout_fire,
            UtilKRes.getColor(R.color.blue_theme)
        )
    }

    private val _tabTop2 by lazy {
        MTabTop(
            "推荐1",
            R.mipmap.layoutk_tab_bottom_layout_fire,
            R.mipmap.layoutk_tab_bottom_layout_fire,
            UtilKRes.getColor(R.color.blue_theme),
            UtilKRes.getColor(R.color.blue_dark)
        )
    }

    private val _tabStr = arrayListOf(
        "数码", "鞋子", "零食", "家电", "汽车", "百货", "家居", "热门", "装修", "运动"
    )

    private fun initTabTopLayout() {
        val infoList = ArrayList<MTabTop>()
        val colorDefault = UtilKRes.getColor(R.color.blue_theme)
        val colorSelected = UtilKRes.getColor(R.color.blue_dark)
        infoList.apply {
            add(_tabTop1)
            add(_tabTop2)
        }
        _tabStr.forEach {
            val info = MTabTop(it, colorDefault, colorSelected)
            infoList.add(info)
        }
        vb.layoutkTabTopLayout.inflateInfo(infoList)
        vb.layoutkTabTopLayout.addTabSelectedChangeListener(object : ITabLayout.TabSelectedListener<MTabTop> {
            override fun onTabSelected(index: Int, prevMo: MTabTop?, nextMo: MTabTop) {
                nextMo.name!!.showToast()
            }
        })
        vb.layoutkTabTopLayout.defaultSelected(infoList[0])
        vb.layoutkTabTopLayout.setTabTopBackground(UtilKRes.getColor(R.color.blue_light))
    }
}
