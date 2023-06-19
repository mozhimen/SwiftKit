package com.mozhimen.uicorek.adapterk.commons

import androidx.databinding.ViewDataBinding
import com.mozhimen.uicorek.adapterk.IAdapterKRecyclerVB2Listener


/**
 * @ClassName IAdapterKRecyclerVB
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/4/3 17:55
 * @Version 1.0
 */
interface IAdapterKRecyclerVB<DATA, VB : ViewDataBinding> {
    /**
     * Item改变
     * @param item BEAN
     */
    fun onItemRefresh(item: DATA, position: Int, notify: Boolean)


    /**
     * 刷新Item
     * @param items List<DATA>
     */
    fun onItemsRefresh(items: List<DATA>, notify: Boolean)

    /**
     * 在末尾添加item
     * @param item BEAN
     * @param notify Boolean
     */
    fun onItemAdd(item: DATA, notify: Boolean)

    /**
     * 在指定位上添加item
     * @param item BEAN
     * @param position Int
     * @param notify Boolean
     */
    fun onItemAddAtPosition(item: DATA, position: Int, notify: Boolean)

    /**
     * 添加items集合
     * @param items List<DATA>
     * @param notify Boolean
     */
    fun onItemsAdd(items: List<DATA>, notify: Boolean)

    /**
     * 移除item
     * @param item BEAN
     */
    fun onItemRemove(item: DATA, notify: Boolean)

    /**
     * 从指定位上移除item
     * @param position Int
     * @return BEAN
     */
    fun onItemRemoveAtPosition(position: Int, notify: Boolean): DATA?

    /**
     * 删除所有的item
     */
    fun onItemsRemoveAll(notify: Boolean)

    /**
     * 获取Item
     * @param position Int
     * @return BEAN
     */
    fun onItemGet(position: Int): DATA?

    /**
     * 获取Items
     * @return List<DATA>
     */
    fun onItemsGet(): List<DATA?>

    /**
     * 设置选择的Item的位置
     * @param position Int
     */
    fun onSelectItemPositionSet(position: Int, listener: IAdapterKRecyclerVB2Listener<DATA, VB>)

    /**
     * 获取当前选择的位置
     * @return Int
     */
    fun onSelectItemPositionGet(): Int
}