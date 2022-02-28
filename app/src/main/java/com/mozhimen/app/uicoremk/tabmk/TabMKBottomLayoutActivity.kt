package com.mozhimen.app.uicoremk.tabmk

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityTabmkBottomLayoutBinding
import com.mozhimen.basicsmk.utilmk.dp2px
import com.mozhimen.uicoremk.tabmk.bottom.mos.TabMKBottomInfo
import java.util.*

class TabMKBottomLayoutActivity : AppCompatActivity() {

    private val vb by lazy { ActivityTabmkBottomLayoutBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        initTabBottom()
    }

    private fun initTabBottom() {
        vb.tabmkBottomLayout.setTabAlpha(0.85f)
        val bottomInfoList: MutableList<TabMKBottomInfo<*>> = ArrayList()
        val homeInfo = TabMKBottomInfo(
            "首页",
            "fonts/iconfont.ttf",
            getString(R.string.icon_home),
            null,
            "#ff656667",
            "#ffd44949"
        )
        /*val moreInfo = TabMKBottomInfo(
            "更多",
            "fonts/iconfont.ttf",
            getString(R.string.icon_more),
            null,
            "#ff656667",
            "#ffd44949"
        )*/
        val bitmap =
            BitmapFactory.decodeResource(resources, R.mipmap.tabmk_bottom_layout_fire, null)
        val moreInfo = TabMKBottomInfo<String>(
            "更多",
            bitmap,
            bitmap
        )
        val mineInfo = TabMKBottomInfo(
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
        vb.tabmkBottomLayout.inflateInfo(bottomInfoList)
        vb.tabmkBottomLayout.addTabSelectedChangeListener { _, _, nextInfo ->
            Toast.makeText(this, nextInfo.name, Toast.LENGTH_SHORT).show()
        }
        vb.tabmkBottomLayout.defaultSelected(homeInfo)
        //改变某个Tab的高度
        val bottomTab = vb.tabmkBottomLayout.findTab(bottomInfoList[1])
        bottomTab.apply {
            resetHeight(66f.dp2px())
        }
    }
}