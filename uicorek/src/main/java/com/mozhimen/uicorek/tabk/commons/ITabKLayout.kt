package com.mozhimen.uicorek.tabk.commons

import android.view.ViewGroup

/**
 * @ClassName ITabKLayout
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/15 15:07
 * @Version 1.0
 */
interface ITabKLayout<View : ViewGroup, Info> {
    fun findTab(info: Info): View?

    fun addTabSelectedChangeListener(listener: TabSelectedListener<Info>)

    fun defaultSelected(defaultInfo: Info)

    fun inflateInfo(infoList: List<Info>)

    interface TabSelectedListener<Info> {
        fun onTabSelected(index: Int, prevMo: Info?, nextMo: Info)
    }
}