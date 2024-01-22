package com.mozhimen.componentk.pagingk.widgets.commons

import android.view.View
import com.mozhimen.componentk.pagingk.widgets.PagingKPagedListAdapter

/**
 * @ClassName
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/11
 * @Version 1.0
 */
/**
 * Callback method to be invoked when an item in this RecyclerView has
 * been clicked.
 *
 * @param adapter  the adapter
 * @param itemView     The itemView within the RecyclerView that was clicked (this
 * will be a view provided by the adapter)
 * @param position The position of the view in the adapter.
 */
typealias IOnPageItemClickListener<DATA> = (adapter: PagingKPagedListAdapter<DATA>, itemView: View, viewType: Int, position: Int) -> Unit
/**
 * callback method to be invoked when an item child in this view has been click
 *
 * @param adapter  BaseQuickAdapter
 * @param childView     The view whihin the ItemView that was clicked
 * @param position The position of the view int the adapter
 */
typealias IOnPageItemChildClickListener<DATA> = IOnPageItemClickListener<DATA>
/**
 * callback method to be invoked when an item child in this view has been click
 *
 * @param adapter  BaseQuickAdapter
 * @param itemView     The view whihin the ItemView that was clicked
 * @param position The position of the view int the adapter
 */
typealias IOnPageItemLongClickListener<DATA> = IOnPageItemClickListener<DATA>