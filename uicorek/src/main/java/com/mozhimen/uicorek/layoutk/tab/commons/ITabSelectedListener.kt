package com.mozhimen.uicorek.layoutk.tab.commons


/**
 * @ClassName ITabSelectedListener
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/16 11:39
 * @Version 1.0
 */
interface ITabSelectedListener<ITEM> {
    fun onTabItemSelected(index: Int, prevItem: ITEM?, currentItem: ITEM)
}