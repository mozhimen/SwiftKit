package com.mozhimen.uicorek.tabk.commons

import androidx.annotation.Px

/**
 * @ClassName ITabK
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/15 15:07
 * @Version 1.0
 */
interface ITabK<Info> : ITabKLayout.TabSelectedListener<Info> {
    fun setTabInfo(tabMo: Info)

    /**
     * 动态修改某个item的大小
     * @param height Int
     */
    fun resetTabHeight(@Px height: Int)
}