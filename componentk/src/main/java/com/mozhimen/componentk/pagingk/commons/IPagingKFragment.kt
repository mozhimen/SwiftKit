package com.mozhimen.componentk.pagingk.commons

import android.content.Context
import android.graphics.Color
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mozhimen.componentk.pagingk.bases.BasePagingKViewModel

/**
 * @ClassName IPagingKFragment
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/17 17:13
 * @Version 1.0
 */
interface IPagingKFragment<DES, VM : BasePagingKViewModel<*, DES>> {
    fun getViewModel(): VM
    fun getPagedListAdapter(): PagedListAdapter<DES, *>
    fun getSwipeRefreshLayout(): SwipeRefreshLayout
    fun getSwipeRefreshLayoutColorScheme(): Int = Color.BLACK
    fun getRecyclerView(): RecyclerView
    fun getRecyclerViewLayoutManager(): LayoutManager
    fun onLoadStart()
    fun onLoadEmpty()
    fun onLoadComplete()
}