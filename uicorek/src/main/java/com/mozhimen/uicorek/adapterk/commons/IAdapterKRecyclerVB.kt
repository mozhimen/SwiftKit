package com.mozhimen.uicorek.adapterk.commons

import androidx.databinding.ViewDataBinding


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
     * @param data BEAN
     */
    fun refreshData(data: DATA, position: Int, notify: Boolean)

    /**
     * 刷新Item
     */
    fun refreshDatas(notify: Boolean)

    /**
     * 刷新Item
     * @param datas List<DATA>
     */
    fun refreshDatas(datas: List<DATA>)

    /**
     * 刷新Item
     * @param datas List<DATA>
     */
    fun refreshDatas(datas: List<DATA>, notify: Boolean)

    /**
     * 在末尾添加item
     * @param data BEAN
     * @param notify Boolean
     */
    fun addData(data: DATA, notify: Boolean)

    /**
     * 在指定位上添加item
     * @param data BEAN
     * @param position Int
     * @param notify Boolean
     */
    fun addDataAtPosition(data: DATA, position: Int, notify: Boolean)

    /**
     * 添加items集合
     * @param datas List<DATA>
     * @param notify Boolean
     */
    fun addDatas(datas: List<DATA>, notify: Boolean)

    /**
     * 从指定位上移除item
     * @param position Int
     * @return BEAN
     */
    fun removeDataAtPosition(position: Int, notify: Boolean): DATA?

    /**
     * 移除item
     * @param data BEAN
     */
    fun removeData(data: DATA, notify: Boolean)

    /**
     * 删除所有的item
     */
    fun removeDatasAll(notify: Boolean)

    /**
     * 获取Item
     * @param position Int
     * @return BEAN
     */
    fun getData(position: Int): DATA?

    /**
     * 获取Items
     * @return List<DATA>
     */
    fun getDatas(): List<DATA?>

    /**
     * 设置选择的Item的位置
     * @param position Int
     */
    fun onDataSelected(position: Int)

    /**
     * 获取当前选择的位置
     * @return Int
     */
    fun getCurrentSelectPosition(): Int
}