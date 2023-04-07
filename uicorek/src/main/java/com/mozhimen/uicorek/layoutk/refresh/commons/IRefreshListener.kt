package com.mozhimen.uicorek.layoutk.refresh.commons

/**
 * @ClassName IRefreshListener
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/6 19:52
 * @Version 1.0
 */
interface IRefreshListener {
    fun onRefreshing()
    fun onEnableRefresh(): Boolean
}