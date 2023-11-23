package com.mozhimen.uicorek.adapterk.page

import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basick.utilk.android.view.applyDebounceClickListener
import com.mozhimen.uicorek.recyclerk.item.RecyclerKPageItem
import com.mozhimen.uicorek.vhk.VHKRecycler

/**
 * @ClassName AdapterKPageRecyclerMulti
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/11 11:44
 * @Version 1.0
 */
open class AdapterKPageRecyclerMulti<DATA>(itemCallback: ItemCallback<DATA>) : AdapterKPageRecycler<DATA>(0, itemCallback) {
    private val _recyclerKPageItems by lazy(LazyThreadSafetyMode.NONE) { SparseArray<RecyclerKPageItem<DATA>>() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHKRecycler {
        val recyclerKPageItem = getRecyclerKPageItem(viewType)
        checkNotNull(recyclerKPageItem) { "ViewType: $viewType no such provider found，please use addItemProvider() first!" }
        recyclerKPageItem.context = parent.context
        return recyclerKPageItem.onCreateViewHolder(parent, viewType).apply {
            recyclerKPageItem.onViewHolderCreated(this, viewType)
            onBindViewClickListener(this, viewType)
        }
    }

    override fun onBindViewHolder(holder: VHKRecycler, position: Int) {
        onBindViewHolder(holder, getItem(position))
    }

    override fun onBindViewClickListener(holder: VHKRecycler, viewType: Int) {
        super.onBindViewClickListener(holder, viewType)
        onBindViewClickListener(holder)
        onBindChildClickListener(holder, viewType)
    }

    override fun onViewAttachedToWindow(holder: VHKRecycler) {
        super.onViewAttachedToWindow(holder)
        getRecyclerKPageItem(holder.itemViewType)?.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: VHKRecycler) {
        super.onViewDetachedFromWindow(holder)
        getRecyclerKPageItem(holder.itemViewType)?.onViewDetachedFromWindow(holder)
    }

    /////////////////////////////////////////////////////////////////////////////////

    /**
     * 通过 ViewType 获取 BaseItemProvider
     * 例如：如果ViewType经过特殊处理，可以重写此方法，获取正确的Provider
     * （比如 ViewType 通过位运算进行的组合的）
     */
    fun getRecyclerKPageItem(viewType: Int): RecyclerKPageItem<DATA>? {
        return _recyclerKPageItems.get(viewType)
    }

    /**
     * 必须通过此方法，添加 provider
     */
    fun addRecyclerKPageItem(item: RecyclerKPageItem<DATA>) {
        item.setAdapter(this)
        _recyclerKPageItems.put(item.itemViewType, item)
    }

    fun onBindViewHolder(holder: VHKRecycler, item: DATA?) {
        getRecyclerKPageItem(holder.itemViewType)?.onBindViewHolder(holder, item)
    }

    fun onBindViewHolder(holder: VHKRecycler, item: DATA, payloads: List<Any>) {
        getRecyclerKPageItem(holder.itemViewType)?.onBindViewHolder(holder, item, payloads)
    }

    /////////////////////////////////////////////////////////////////////////////////

    protected open fun onBindViewClickListener(holder: VHKRecycler) {
        if (getOnItemClickListener() == null) {
            //如果没有设置点击监听，则回调给 itemProvider
            //Callback to itemProvider if no click listener is set
            holder.itemView.applyDebounceClickListener(1000) {
                val position = holder.adapterPosition
                if (position == RecyclerView.NO_POSITION)
                    return@applyDebounceClickListener
                val itemViewType = holder.itemViewType
                if (itemViewType == -0x201) //过滤掉暂无更多
                    return@applyDebounceClickListener
                val recyclerKPageItem = _recyclerKPageItems.get(itemViewType)
                recyclerKPageItem.onClick(holder, it, getItem(position), position)
            }
        }
    }

    protected open fun onBindChildClickListener(holder: VHKRecycler, viewType: Int) {
        if (getOnItemChildClickListener() == null) {
            val recyclerKPageItem = getRecyclerKPageItem(viewType) ?: return
            val childClickViewIds = recyclerKPageItem.getChildClickViewIds()
            childClickViewIds.forEach { id ->
                holder.itemView.findViewById<View>(id)?.let {
                    if (!it.isClickable)
                        it.isClickable = true
                    it.applyDebounceClickListener(1000) { v ->
                        val position: Int = holder.adapterPosition
                        if (position == RecyclerView.NO_POSITION)
                            return@applyDebounceClickListener
                        recyclerKPageItem.onChildClick(holder, v, getItem(position), position)
                    }
                }
            }
        }
    }
}