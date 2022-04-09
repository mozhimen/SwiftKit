package com.mozhimen.app.uicorek.tabk

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityTabkBottomLayoutBinding
import com.mozhimen.basicsk.utilk.dp2px
import com.mozhimen.uicorek.tabk.bottom.mos.TabKBottomInfo
import java.util.*

class TabKBottomLayoutActivity : AppCompatActivity() {

    private val vb by lazy { ActivityTabkBottomLayoutBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        initTabBottom()
    }

    private fun initTabBottom() {
        vb.tabkBottomLayout.setTabAlpha(0.85f)
        val bottomInfoList: MutableList<TabKBottomInfo<*>> = ArrayList()
        val homeInfo = TabKBottomInfo(
            "首页",
            "fonts/iconfont.ttf",
            getString(R.string.icon_home),
            null,
            "#ff656667",
            "#ffd44949"
        )
        /*val moreInfo = TabKBottomInfo(
            "更多",
            "fonts/iconfont.ttf",
            getString(R.string.icon_more),
            null,
            "#ff656667",
            "#ffd44949"
        )*/
        val bitmap =
            BitmapFactory.decodeResource(resources, R.mipmap.tabk_bottom_layout_fire, null)
        val moreInfo = TabKBottomInfo<String>(
            "更多",
            bitmap,
            bitmap
        )
        val mineInfo = TabKBottomInfo(
            "我的",
            "fonts/iconfont.ttf",
            getString(R.string.icon_mine),
            null,
            "#ff656667",
            "#ffd44949"
        )
        bottomInfoList.add(homeInfo)
        bottomInfoList.add(moreInfo)
        bottomInfoList.add(mineInfo)
        vb.tabkBottomLayout.inflateInfo(bottomInfoList)
        vb.tabkBottomLayout.addTabSelectedChangeListener { _, _, nextInfo ->
            Toast.makeText(this, nextInfo.name, Toast.LENGTH_SHORT).show()
        }
        vb.tabkBottomLayout.defaultSelected(homeInfo)
        //改变某个Tab的高度
        val bottomTab = vb.tabkBottomLayout.findTab(bottomInfoList[1])
        bottomTab.apply {
            resetHeight(66f.dp2px())
        }
    }
}