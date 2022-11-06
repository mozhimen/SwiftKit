package com.mozhimen.uicorek.layoutk.tab.commons

import androidx.annotation.Px

/**
 * @ClassName ITabItem
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/15 15:07
 * @Version 1.0
 */
interface ITabItem<INFO> : ITabLayout.TabSelectedListener<INFO> {
    fun setTabInfo(tabMo: INFO)

    /**
     * 动态修改某个item的大小
     * @param height Int
     */
    fun resetTabHeight(@Px height: Int)
}