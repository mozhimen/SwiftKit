package com.mozhimen.uicorek.layoutk.tab.commons

import android.view.ViewGroup

/**
 * @ClassName ITabLayout
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/15 15:07
 * @Version 1.0
 */
interface ITabLayout<VIEW : ViewGroup, INFO> {
    fun findTab(info: INFO): VIEW?

    fun addTabSelectedChangeListener(listener: TabSelectedListener<INFO>)

    fun defaultSelected(defaultInfo: INFO)

    fun inflateInfo(infoList: List<INFO>)

    interface TabSelectedListener<Info> {
        fun onTabSelected(index: Int, prevMo: Info?, nextMo: Info)
    }
}