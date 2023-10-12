package com.mozhimen.componentk.pagingk.commons

/**
 * @ClassName IDataLoadingListener
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/11 16:24
 * @Version 1.0
 */
interface IPagingKDataSourceLoadListener {
    /**
     * 首次加载开始
     */
    fun onFirstLoadStart()

    /**
     * 首次加载完成
     * @param isEmpty 是否为空
     */
    fun onFirstLoadCompleted(isEmpty: Boolean)
}