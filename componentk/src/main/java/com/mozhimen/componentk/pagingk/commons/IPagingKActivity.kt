package com.mozhimen.componentk.pagingk.commons

import android.graphics.Color
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
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
interface IPagingKActivity<DES : Any, VM : BasePagingKViewModel<*, DES>> {
    fun getViewModel(): VM
    fun getPagedListAdapter(): PagedListAdapter<DES, *>
    fun getSwipeRefreshLayout(): SwipeRefreshLayout?
    fun getSwipeRefreshLayoutColorScheme(): Int = 0
    fun getRecyclerView(): RecyclerView
    fun getRecyclerViewLayoutManager(): LayoutManager
    fun getRecyclerViewItemDecoration(): ItemDecoration? = null
    fun onLoadStart()
    fun onLoadEmpty()
    fun onLoadComplete()
}