package com.mozhimen.uicorek.adapterk.commons

import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.uicorek.recyclerk.RecyclerKItem

/**
 * @ClassName IAdapterKRecycler
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/4/1 11:10
 * @Version 1.0
 */
interface IAdapterKRecycler {
    val TAG get() = "${this.javaClass.simpleName}>>>>>"

    /**
     * 刷新Item
     * @param item RecyclerKItem<*, out ViewHolder>
     */
    fun refreshItem(item: RecyclerKItem<*, out RecyclerView.ViewHolder>)

    /**
     * 在末尾添加item
     * @param item DataKItem<*, out ViewHolder>
     * @param notify Boolean
     */
    fun addItem(item: RecyclerKItem<*, out RecyclerView.ViewHolder>, notify: Boolean)

    /**
     * 在指定位上添加item
     * @param index Int
     * @param item RecyclerKItem<*, out ViewHolder>
     * @param notify Boolean
     */
    fun addItemAt(index: Int, item: RecyclerKItem<*, out RecyclerView.ViewHolder>, notify: Boolean)

    /**
     * 添加items集合
     * @param items List<RecyclerKItem<*, out ViewHolder>>
     * @param notify Boolean
     */
    fun addItems(items: List<RecyclerKItem<*, out RecyclerView.ViewHolder>>, notify: Boolean)

    /**
     * 移除item
     * @param item RecyclerKItem<*, out ViewHolder>
     */
    fun removeItem(item: RecyclerKItem<*, out RecyclerView.ViewHolder>)

    /**
     * 从指定位上移除item
     * @param index Int
     * @return RecyclerKItem<*, out RecyclerView.ViewHolder>?
     */
    fun removeItemAt(index: Int): RecyclerKItem<*, out RecyclerView.ViewHolder>?

    /**
     * 删除所有的item
     */
    fun removeItemsAll()

    /**
     * 获取Item
     * @param position Int
     * @return RecyclerKItem<*, RecyclerView.ViewHolder>?
     */
    fun getItem(position: Int): RecyclerKItem<*, RecyclerView.ViewHolder>?

    /**
     * 获取RecyclerView
     * @return RecyclerView?
     */
    fun getAttachedRecyclerView(): RecyclerView?
}