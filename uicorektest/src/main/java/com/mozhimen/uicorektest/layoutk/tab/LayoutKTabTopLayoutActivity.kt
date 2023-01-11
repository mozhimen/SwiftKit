package com.mozhimen.uicorektest.layoutk.tab

import android.os.Bundle
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.permissionk.cons.CPermission
import com.mozhimen.basick.permissionk.annors.APermissionRequire
import com.mozhimen.basick.utilk.exts.showToast
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.uicorek.layoutk.tab.commons.ITabSelectedListener
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
@APermissionRequire(CPermission.INTERNET)
class LayoutKTabTopLayoutActivity : BaseActivityVB<ActivityLayoutkTabTopLayoutBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
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
        vb.layoutkTabTopLayout.inflateTabItem(infoList)
        vb.layoutkTabTopLayout.addTabItemSelectedListener(object : ITabSelectedListener<MTabTop> {
            override fun onTabItemSelected(index: Int, prevItem: MTabTop?, currentItem: MTabTop) {
                currentItem.name!!.showToast()
            }
        })
        vb.layoutkTabTopLayout.defaultSelected(infoList[0])
        vb.layoutkTabTopLayout.setTabTopBackground(UtilKRes.getColor(R.color.blue_light))
    }
}
