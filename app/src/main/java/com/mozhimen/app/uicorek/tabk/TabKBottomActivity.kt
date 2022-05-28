package com.mozhimen.app.uicorek.tabk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityTabkBottomBinding
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.uicorek.tabk.bottom.mos.TabKBottomMo

/**
 * @ClassName TabBottomActivity
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/8/2 14:39
 * @Version 1.0
 */
class TabKBottomActivity : AppCompatActivity() {
    private val vb by lazy { ActivityTabkBottomBinding.inflate(layoutInflater) }

    private val homeInfo by lazy {
        TabKBottomMo(
            "首页",
            "fonts/iconfont.ttf",
            getString(R.string.icon_home),
            getString(R.string.icon_more),
            "#ff000000",//UtilKRes.getColor(android.R.color.black),
            "#ff287FF1"//UtilKRes.getColor(R.color.blue_normal)
        )
    }

    private val moreInfo by lazy {
        TabKBottomMo(
            "更多",
            R.mipmap.tabk_bottom_layout_fire,
            R.mipmap.tabk_bottom_layout_fire
        )
    }

    private val mineInfo by lazy {
        TabKBottomMo(
            "我的",
            R.mipmap.tabk_bottom_layout_fire,
            R.mipmap.tabk_bottom_layout_fire,
            UtilKRes.getColor(android.R.color.black),
            UtilKRes.getColor(R.color.blue_normal)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        vb.tabkBottomHome.setTabInfo(homeInfo)
        vb.tabkBottomMore.setTabInfo(moreInfo)
        vb.tabkBottomMine.setTabInfo(mineInfo)
    }
}