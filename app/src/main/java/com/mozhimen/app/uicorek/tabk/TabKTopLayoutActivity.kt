package com.mozhimen.app.uicorek.tabk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityTabkTopLayoutBinding
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.basick.utilk.showToast
import com.mozhimen.uicorek.tabk.commons.ITabKLayout
import com.mozhimen.uicorek.tabk.top.mos.TabKTopMo

/**
 * @ClassName TabTopLayoutActivity
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/8/4 17:17
 * @Version 1.0
 */
class TabKTopLayoutActivity : AppCompatActivity() {
    private val vb by lazy { ActivityTabkTopLayoutBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        initTabKTopLayout()
    }

    private val tabTop1 by lazy {
        TabKTopMo(
            "推荐",
            R.mipmap.tabk_bottom_layout_fire,
            R.mipmap.tabk_bottom_layout_fire,
            UtilKRes.getColor(R.color.blue_theme)
        )
    }

    private val tabTop2 by lazy {
        TabKTopMo(
            "推荐1",
            R.mipmap.tabk_bottom_layout_fire,
            R.mipmap.tabk_bottom_layout_fire,
            UtilKRes.getColor(R.color.blue_theme),
            UtilKRes.getColor(R.color.blue_dark)
        )
    }

    private val tabStr = arrayListOf(
        "数码", "鞋子", "零食", "家电", "汽车", "百货", "家居", "热门", "装修", "运动"
    )

    private fun initTabKTopLayout() {
        val infoList = ArrayList<TabKTopMo>()
        val colorDefault = UtilKRes.getColor(R.color.blue_theme)
        val colorSelected = UtilKRes.getColor(R.color.blue_dark)
        infoList.apply {
            add(tabTop1)
            add(tabTop2)
        }
        tabStr.forEach {
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
