package com.mozhimen.uicorek.adapterk.page.commons

import android.view.View
import com.mozhimen.uicorek.adapterk.page.AdapterKPageRecycler

/**
 * @ClassName IAdapterKPageRecyclerListener
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/11 10:42
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
typealias IOnItemClickListener<DATA> = (adapter: AdapterKPageRecycler<DATA>, itemView: View, position: Int) -> Unit
/**
 * callback method to be invoked when an item child in this view has been click
 *
 * @param adapter  BaseQuickAdapter
 * @param childView     The view whihin the ItemView that was clicked
 * @param position The position of the view int the adapter
 */
typealias IOnItemChildClickListener<DATA> = IOnItemClickListener<DATA>
/**
 * callback method to be invoked when an item child in this view has been click
 *
 * @param adapter  BaseQuickAdapter
 * @param itemView     The view whihin the ItemView that was clicked
 * @param position The position of the view int the adapter
 */
typealias IOnItemLongClickListener<DATA> = IOnItemClickListener<DATA>