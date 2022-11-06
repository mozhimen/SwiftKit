package com.mozhimen.uicorek.recyclerk

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * @ClassName DataItemK
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/8/31 15:56
 * @Version 1.0
 */
abstract class RecyclerKItem<DATA, VH : RecyclerView.ViewHolder>(val data: DATA? = null) {
    protected val TAG = "${this.javaClass.simpleName}>>>>>"

    private var _adapter: RecyclerKAdapter? = null

    /**
     * 绑定数据
     * @param holder VH
     * @param position Int
     */
    abstract fun onBindData(holder: VH, position: Int)

    /**
     * 返回该item的资源id
     * @return Int
     */
    open fun getItemLayoutRes(): Int = -1

    /**
     * 返回该item的视图view
     * @param parent ViewGroup
     * @return View?
     */
    open fun getItemView(parent: ViewGroup): View? = null

    /**
     * 获取item所占列数
     * @return Int
     */
    open fun getSpanSize() = 0

    /**
     * 该item被滑进屏幕
     * @param holder VH
     */
    open fun onViewAttachedToWindow(holder: VH) {}

    /**
     * 当item被划出屏幕
     * @param holder VH
     */
    open fun onViewDetachedFromWindow(holder: VH) {}

    /**
     * 创建ViewHolder
     * @param parent ViewGroup
     * @return VH?
     */
    open fun onCreateViewHolder(parent: ViewGroup): VH? {
        return null
    }

    /**
     * 设置adapter
     * @param adapter DataKAdapter
     */
    fun setAdapter(adapter: RecyclerKAdapter) {
        this._adapter = adapter
    }

    /**
     * 刷新列表
     */
    fun refreshItem() {
        _adapter?.refreshItem(this)
    }

    /**
     * 从列表上移除
     */
    fun removeItem() {
        _adapter?.removeItem(this)
    }
}