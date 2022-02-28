package com.mozhimen.app.tabmk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityTabmkBottomFragmentBinding
import com.mozhimen.app.uicoremk.tabmk.fragment.HomeFragment
import com.mozhimen.app.tabmk.fragment.MineFragment
import com.mozhimen.app.tabmk.fragment.MoreFragment
import com.mozhimen.uicoremk.tabmk.bottom.helpers.TabMKViewAdapter
import com.mozhimen.uicoremk.tabmk.bottom.mos.TabMKBottomInfo


/**
 * @ClassName TabBottomFragmentActivity
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/8/2 14:38
 * @Version 1.0
 */
class TabMKBottomFragmentActivity : AppCompatActivity() {

    companion object {
        private const val SAVED_CURRENT_ID = "SAVED_CURRENT_ID"
    }

    private val vb by lazy { ActivityTabmkBottomFragmentBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        savedInstanceState?.let {
            currentItemIndex = savedInstanceState.getInt(SAVED_CURRENT_ID)
        }

        vb.tabBottomFragment.setTabAlpha(0.85f)
        val defaultColor = ContextCompat.getColor(this, R.color.tabBottomDefaultColor)
        val tintColor = ContextCompat.getColor(this, R.color.tabBottomTintColor)
        val homeInfo = TabMKBottomInfo(
            "首页",
            "fonts/iconfont.ttf",
            getString(R.string.icon_home),
            null,
            defaultColor,
            tintColor
        )
        val moreInfo = TabMKBottomInfo(
            "更多",
            "fonts/iconfont.ttf",
            getString(R.string.icon_more),
            null,
            defaultColor,
            tintColor
        )
        val mineInfo = TabMKBottomInfo(
            "首页",
            "fonts/iconfont.ttf",
            getString(R.string.icon_mine),
            null,
            defaultColor,
            tintColor
        )
        homeInfo.fragment = HomeFragment::class.java
        moreInfo.fragment = MoreFragment::class.java
        mineInfo.fragment = MineFragment::class.java
        infoList.add(homeInfo)
        infoList.add(moreInfo)
        infoList.add(mineInfo)
        vb.tabBottomFragment.inflateInfo(infoList)
        val tabMKViewAdapter = TabMKViewAdapter(supportFragmentManager, infoList)
        vb.tabBottomFragmentView.adapter = tabMKViewAdapter
        vb.tabBottomFragment.addTabSelectedChangeListener { index, _, _ ->
            vb.tabBottomFragmentView.currentItem = index
            this.currentItemIndex = index
        }
        vb.tabBottomFragment.defaultSelected(infoList[currentItemIndex])
    }

    private var infoList = ArrayList<TabMKBottomInfo<*>>()
    private var currentItemIndex = 0

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(SAVED_CURRENT_ID, currentItemIndex)
        super.onSaveInstanceState(outState)
    }
}