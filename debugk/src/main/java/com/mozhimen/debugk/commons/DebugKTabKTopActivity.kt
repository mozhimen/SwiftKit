package com.mozhimen.debugk.commons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.exts.dp2px
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.componentk.statusbark.StatusBarK
import com.mozhimen.debugk.databinding.DebugkActivityTabkTopBinding
import com.mozhimen.uicorek.layoutk.tab.commons.ITabSelectedListener
import com.mozhimen.uicorek.layoutk.tab.top.mos.MTabTop


/**
 * @ClassName DebugUIKActivity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/16 15:25
 * @Version 1.0
 */
abstract class DebugKTabKTopActivity : BaseActivityVB<DebugkActivityTabkTopBinding>() {
    private val _tabList: ArrayList<DebugKUITabTopItem> by lazy { getTabList() }

    abstract fun getTabList(): ArrayList<DebugKUITabTopItem>

    override fun initFlag() {
        StatusBarK.initStatusBar(this)
    }

    override fun initView(savedInstanceState: Bundle?) {
        vb.debugkUiTabTop.setTabTopHeight(20f.dp2px())
        vb.debugkUiTabTop.inflateTabItem(_tabList)
        vb.debugkUiTabTop.addTabItemSelectedListener(object : ITabSelectedListener<MTabTop> {
            override fun onTabItemSelected(index: Int, prevItem: MTabTop?, currentItem: MTabTop) {
                showLayoutView((currentItem as DebugKUITabTopItem).viewId)
            }
        })
        vb.debugkUiTabTop.defaultSelected(_tabList[0])
    }

    private fun showLayoutView(viewId: Int) {
        vb.debugkUiLayoutContainer.removeAllViews()
        val view = LayoutInflater.from(this).inflate(viewId, null)
        vb.debugkUiLayoutContainer.addView(view, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
    }

    data class DebugKUITabTopItem(
        val tabName: String,
        val viewId: Int
    ) : MTabTop(tabName, UtilKRes.getColor(com.mozhimen.uicorek.R.color.blue_normal), UtilKRes.getColor(com.mozhimen.uicorek.R.color.blue_dark))
}