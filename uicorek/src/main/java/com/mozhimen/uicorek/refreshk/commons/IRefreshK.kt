package com.mozhimen.uicorek.refreshk.commons

/**
 * @ClassName IRefreshK
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/17 23:31
 * @Version 1.0
 */
interface IRefreshK {

    interface IRefreshKListener {
        fun onRefresh()
        fun enableRefresh(): Boolean
    }

    /**
     * 设置下拉刷新的视图
     *
     * @param overView 下拉刷新的视图
     */
    fun setRefreshOverView(overView: RefreshKOverView)

    /**
     * 设置下拉参数
     * @param minPullRefreshHeight Int? 触发下拉刷新需要的最小高度
     * @param minDamp Float? 最小阻尼
     * @param maxDamp Float? 最大阻尼
     */
    fun setRefreshParams(
        minPullRefreshHeight: Int?,
        minDamp: Float?,
        maxDamp: Float?
    )

    /**
     * 设置下拉刷新的监听器
     *
     * @param listener 刷新的监听器
     */
    fun setRefreshListener(listener: IRefreshKListener)

    /**
     * 刷新时是否禁止滚动
     *
     * @param disableRefreshScroll 否禁止滚动
     */
    fun setDisableRefreshScroll(disableRefreshScroll: Boolean)

    /**
     * 刷新完成
     */
    fun refreshFinished()
}