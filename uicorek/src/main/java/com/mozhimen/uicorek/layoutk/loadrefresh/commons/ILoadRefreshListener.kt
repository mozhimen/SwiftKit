package com.mozhimen.uicorek.layoutk.loadrefresh.commons

/**
 * @ClassName ILoadRefreshListener
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/6 19:46
 * @Version 1.0
 */
interface ILoadRefreshListener {
    fun onRefreshOrLoad(isSuccess: Boolean)
}