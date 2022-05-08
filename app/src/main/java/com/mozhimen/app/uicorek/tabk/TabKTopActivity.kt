package com.mozhimen.app.uicorek.tabk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityTabkTopBinding
import com.mozhimen.basicsk.utilk.UtilKRes
import com.mozhimen.uicorek.tabk.top.mos.TabKTopMo

class TabKTopActivity : AppCompatActivity() {
    private val vb: ActivityTabkTopBinding by lazy { ActivityTabkTopBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        initTabKTop()
    }

    private val tabInfo1 by lazy {
        TabKTopMo(
            "home",
            UtilKRes.getColor(R.color.white),
            UtilKRes.getColor(R.color.blue_light)
        )
    }

    private val tabInfo2 by lazy {
        TabKTopMo(
            "more",
            R.mipmap.tabk_bottom_layout_fire,
            R.mipmap.tabk_bottom_layout_fire,
            UtilKRes.getColor(R.color.blue_theme)
        )
    }

    private val tabInfo3 by lazy {
        TabKTopMo(
            "mine",
            R.mipmap.tabk_bottom_layout_fire,
            R.mipmap.tabk_bottom_layout_fire,
            UtilKRes.getColor(R.color.white),
            UtilKRes.getColor(R.color.blue_light)
        )
    }

    private fun initTabKTop() {
        vb.tabkTop1.setTabInfo(tabInfo1)
        vb.tabkTop2.setTabInfo(tabInfo2)
        vb.tabkTop3.setTabInfo(tabInfo3)
    }
}