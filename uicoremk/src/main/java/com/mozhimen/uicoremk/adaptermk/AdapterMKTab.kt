package com.mozhimen.uicoremk.adaptermk

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * @ClassName TabAdapterMK
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/7/5 13:12
 * @Version 1.0
 */
open class AdapterMKTab(private val tabList: List<Fragment>, fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager) {
    override fun getCount() = tabList.size

    override fun getItem(position: Int): Fragment = tabList[position]
}