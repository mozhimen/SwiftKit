package com.mozhimen.basick.elemk.google.material.commons

import com.google.android.material.tabs.TabLayout

/**
 * @ClassName IOnTabSelectedListener
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/9/28 16:37
 * @Version 1.0
 */
interface IOnTabSelectedListener : TabLayout.OnTabSelectedListener {
    override fun onTabReselected(tab: TabLayout.Tab) {}
    override fun onTabSelected(tab: TabLayout.Tab) {}
    override fun onTabUnselected(tab: TabLayout.Tab) {}
}