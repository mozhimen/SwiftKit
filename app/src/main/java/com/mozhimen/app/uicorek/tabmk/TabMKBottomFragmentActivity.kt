package com.mozhimen.app.uicorek.tabk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityTabkBottomFragmentBinding
import com.mozhimen.app.uicorek.tabk.fragment.HomeFragment
import com.mozhimen.app.uicorek.tabk.fragment.MineFragment
import com.mozhimen.app.uicorek.tabk.fragment.MoreFragment
import com.mozhimen.uicorek.tabk.bottom.helpers.TabKViewAdapter
import com.mozhimen.uicorek.tabk.bottom.mos.TabKBottomInfo


/**
 * @ClassName TabBottomFragmentActivity
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/8/2 14:38
 * @Version 1.0
 */
class TabKBottomFragmentActivity : AppCompatActivity() {

    companion object {
        private const val SAVED_CURRENT_ID = "SAVED_CURRENT_ID"
    }

    private val vb by lazy { ActivityTabkBottomFragmentBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        savedInstanceState?.let {
            currentItemIndex = savedInstanceState.getInt(SAVED_CURRENT_ID)
        }

        vb.tabBottomFragment.setTabAlpha(0.85f)
        val defaultColor = ContextCompat.getColor(this, R.color.tabKBottomDefaultColor)
        val tintColor = ContextCompat.getColor(this, R.color.tabKBottomTintColor)
        val homeInfo = TabKBottomInfo(
            "首页",
            "fonts/iconfont.ttf",
            getString(R.string.icon_home),
            null,
            defaultColor,
            tintColor
        )
        val moreInfo = TabKBottomInfo(
            "更多",
            "fonts/iconfont.ttf",
            getString(R.string.icon_more),
            null,
            defaultColor,
            tintColor
        )
        val mineInfo = TabKBottomInfo(
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
        val tabKViewAdapter = TabKViewAdapter(supportFragmentManager, infoList)
        vb.tabBottomFragmentView.adapter = tabKViewAdapter
        vb.tabBottomFragment.addTabSelectedChangeListener { index, _, _ ->
            vb.tabBottomFragmentView.currentItem = index
            this.currentItemIndex = index
        }
        vb.tabBottomFragment.defaultSelected(infoList[currentItemIndex])
    }

    private var infoList = ArrayList<TabKBottomInfo<*>>()
    private var currentItemIndex = 0

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(SAVED_CURRENT_ID, currentItemIndex)
        super.onSaveInstanceState(outState)
    }
}