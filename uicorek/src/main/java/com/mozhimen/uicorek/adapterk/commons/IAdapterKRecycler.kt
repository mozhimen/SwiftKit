package com.mozhimen.uicorek.adapterk.commons

import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basick.utilk.bases.IUtilK
import com.mozhimen.uicorek.recyclerk.bases.BaseRecyclerKItem

/**
 * @ClassName IAdapterKRecycler
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/4/1 11:10
 * @Version 1.0
 */
interface IAdapterKRecycler : IUtilK {
    /**
     * 刷新Item
     * @param item BaseRecyclerKItem<DATA, ViewHolder>
     */
    fun refreshItem(item: BaseRecyclerKItem<out RecyclerView.ViewHolder>, position: Int, notify: Boolean)

    /**
     * 刷新Item
     */
    fun refreshItems(notify: Boolean)

    /**
     * 刷新Item
     * @param items List<BaseRecyclerKItem<DATA, ViewHolder>>
     */
    fun refreshItems(items: List<BaseRecyclerKItem<out RecyclerView.ViewHolder>>, notify: Boolean)

    /**
     * 在末尾添加item
     * @param item DataKItem<DATA, ViewHolder>
     * @param notify Boolean
     */
    fun addItem(item: BaseRecyclerKItem<out RecyclerView.ViewHolder>, notify: Boolean)

    /**
     * 在指定位上添加item
     * @param item BaseRecyclerKItem<DATA, ViewHolder>
     * @param position Int
     * @param notify Boolean
     */
    fun addItemAtPosition(item: BaseRecyclerKItem<out RecyclerView.ViewHolder>, position: Int, notify: Boolean)

    /**
     * 添加items集合
     * @param items List<BaseRecyclerKItem<DATA, ViewHolder>>
     * @param notify Boolean
     */
    fun addItems(items: List<BaseRecyclerKItem<out RecyclerView.ViewHolder>>, notify: Boolean)

    /**
     * 移除item
     * @param item BaseRecyclerKItem<DATA, ViewHolder>
     */
    fun removeItem(item: BaseRecyclerKItem<out RecyclerView.ViewHolder>, notify: Boolean)

    /**
     * 从指定位上移除item
     * @param position Int
     * @return BaseRecyclerKItem<DATA, VH>?
     */
    fun removeItemAtPosition(position: Int, notify: Boolean): BaseRecyclerKItem<in RecyclerView.ViewHolder>?

    /**
     * 删除所有的item
     */
    fun removeItemsAll(notify: Boolean)

    /**
     * 获取Item
     * @param position Int
     * @return BaseRecyclerKItem<DATA, VH>?
     */
    fun getItem(position: Int): BaseRecyclerKItem<RecyclerView.ViewHolder>?

    /**
     * 获取Items
     * @return BaseRecyclerKItem<DATA, VH>?
     */
    fun getItems(): List<BaseRecyclerKItem<in RecyclerView.ViewHolder>>

    /**
     * 获取RecyclerView
     * @return RecyclerView?
     */
    fun getAttachedRecyclerView(): RecyclerView?
}