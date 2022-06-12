package com.mozhimen.app.uicorek.tabk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityTabkBottomLayoutBinding
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.basick.extsk.showToast
import com.mozhimen.uicorek.tabk.bottom.mos.TabKBottomMo
import com.mozhimen.uicorek.tabk.commons.ITabKLayout
import java.util.*

class TabKBottomLayoutActivity : AppCompatActivity() {

    private val vb by lazy { ActivityTabkBottomLayoutBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

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