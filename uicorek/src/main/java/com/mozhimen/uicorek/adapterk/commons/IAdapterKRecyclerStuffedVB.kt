package com.mozhimen.uicorek.adapterk.commons

import android.view.View
import androidx.databinding.ViewDataBinding


/**
 * @ClassName IAdapterKRecyclerStuffed
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/4/3 16:38
 * @Version 1.0
 */
interface IAdapterKRecyclerStuffedVB<DATA, VB : ViewDataBinding> : IAdapterKRecyclerVB<DATA, VB> {
    /**
     * 添加Header
     * @param view View
     */
    fun onHeaderViewAdd(view: View)

    /**
     * 移除Header
     * @param view View
     */
    fun onHeaderViewRemove(view: View)

    /**
     * 添加Footer
     * @param view View
     */
    fun onFooterViewAdd(view: View)

    /**
     * 移除Footer
     * @param view View
     */
    fun onFooterViewRemove(view: View)

    /**
     * 获取Header数量
     * @return Int
     */
    fun onHeaderViewSizeGet(): Int

    /**
     * 获取Footer数量
     * @return Int
     */
    fun onFooterViewSizeGet(): Int

    /**
     * 获取item数量
     * @return Int
     */
    fun onNormalItemSizeGet(): Int
}