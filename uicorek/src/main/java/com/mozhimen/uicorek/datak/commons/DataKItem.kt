package com.mozhimen.uicorek.datak.commons

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.uicorek.datak.helpers.DataKAdapter

/**
 * @ClassName DataItemK
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/8/31 15:56
 * @Version 1.0
 */
abstract class DataKItem<DATA, VH : RecyclerView.ViewHolder>(data: DATA? = null) {
    val TAG = "DataKItem>>>>>"

    private var mAdapter: DataKAdapter? = null
    var mData: DATA? = null

    init {
        this.mData = data
    }

    /**
     * 绑定数据
     */
    abstract fun onBindData(holder: VH, position: Int)

    /**
     * 返回该item的资源id
     */
    open fun getItemLayoutRes() = -1

    /**
     * 返回该item的视图view
     */
    open fun getItemView(parent: ViewGroup): View? = null

    fun setAdapter(adapter: DataKAdapter) {
        this.mAdapter = adapter
    }

    /**
     * 刷新列表
     */
    fun refreshItem() {
        mAdapter?.refreshItem(this)
    }

    /**
     * 从列表上移除
     */
    fun removeItem() {
        mAdapter?.removeItem(this)
    }

    /**
     * 获取item所占列数
     */
    open fun getSpanSize() = 0

    /**
     * 该item被滑进屏幕
     */
    open fun onViewAttachedToWindow(holder: VH) {}

    /**
     * 当item被划出屏幕
     */
    open fun onViewDetachedFromWindow(holder: VH) {}
}