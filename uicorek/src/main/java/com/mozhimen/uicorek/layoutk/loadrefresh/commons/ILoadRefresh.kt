package com.mozhimen.uicorek.layoutk.loadrefresh.commons

import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.uicorek.layoutk.refresh.commons.IRefreshListener
import com.mozhimen.uicorek.layoutk.refresh.commons.RefreshOverView
import com.mozhimen.uicorek.recyclerk.bases.BaseRecyclerKItem
import com.mozhimen.uicorek.recyclerk.load.RecyclerKLoad
import com.mozhimen.uicorek.recyclerk.load.commons.IRecyclerKLoadListener


/**
 * @ClassName ILoadRefresh
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/4/6 10:31
 * @Version 1.0
 */
interface ILoadRefresh {
    /**
     * 获取上拉加载RecyclerView
     * @return DataKRecyclerView
     */
    fun getRecyclerKLoad(): RecyclerKLoad

    /**
     * 绑定刷新动作
     * @param refreshOverView RefreshOverView 设置overview
     * @param minPullRefreshHeight Int? 触发下拉刷新需要的最小高度
     * @param minDamp Float? 最小阻尼
     * @param maxDamp Float? 最大阻尼
     * @param listener IRefreshListener
     */
    fun initRefreshParams(
        refreshOverView: RefreshOverView,
        minPullRefreshHeight: Int?,
        minDamp: Float?,
        maxDamp: Float?,
        listener: IRefreshListener?
    )

    /**
     * 绑定加载动作
     * @param prefetchSize Int 加载个数
     * @param items List<BaseRecyclerKItem<out ViewHolder>>
     * @param listener IRecyclerKLoadListener
     */
    fun initLoadParams(
        prefetchSize: Int,
        items: List<BaseRecyclerKItem<out RecyclerView.ViewHolder>>,
        listener: IRecyclerKLoadListener?
    )

    /**
     * 开始刷新
     * @param items List<BaseRecyclerKItem<out ViewHolder>>?
     * @param listener ILoadRefreshListener?
     */
    fun startRefresh(
        items: List<BaseRecyclerKItem<out RecyclerView.ViewHolder>>?,
        listener: ILoadRefreshListener?
    )

    /**
     * 开始加载
     * @param items List<BaseRecyclerKItem<ViewHolder>>?
     * @param listener ILoadRefreshListener?
     */
    fun startLoad(
        items: List<BaseRecyclerKItem<out RecyclerView.ViewHolder>>?,
        listener: ILoadRefreshListener?
    )
}