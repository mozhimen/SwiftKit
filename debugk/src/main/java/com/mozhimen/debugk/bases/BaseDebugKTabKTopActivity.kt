package com.mozhimen.debugk.bases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.utilk.exts.dp2px
import com.mozhimen.basick.utilk.res.UtilKRes
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
@AManifestKRequire(CPermission.INTERNET)
abstract class BaseDebugKTabKTopActivity : BaseActivityVB<DebugkActivityTabkTopBinding>() {
    private val _tabList: ArrayList<DebugKUITabTopItem> by lazy { getTabList() }

    abstract fun getTabList(): ArrayList<DebugKUITabTopItem>

    override fun initFlag() {
        com.mozhimen.basick.statusbark.StatusBarK.initStatusBar(this)
    }

    override fun initView(savedInstanceState: Bundle?) {
        VB.debugkUiTabTop.setTabTopHeight(20f.dp2px())
        VB.debugkUiTabTop.inflateTabItem(_tabList)
        VB.debugkUiTabTop.addTabItemSelectedListener(object : ITabSelectedListener<MTabTop> {
            override fun onTabItemSelected(index: Int, prevItem: MTabTop?, currentItem: MTabTop) {
                showLayoutView((currentItem as DebugKUITabTopItem).viewId)
            }
        })
        VB.debugkUiTabTop.defaultSelected(_tabList[0])
    }

    private fun showLayoutView(viewId: Int) {
        VB.debugkUiLayoutContainer.removeAllViews()
        val view = LayoutInflater.from(this).inflate(viewId, null)
        VB.debugkUiLayoutContainer.addView(view, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
    }

    data class DebugKUITabTopItem(
        val tabName: String,
        val viewId: Int
    ) : MTabTop(tabName, UtilKRes.getColor(com.mozhimen.uicorek.R.color.blue_normal), UtilKRes.getColor(com.mozhimen.uicorek.R.color.blue_dark))
}