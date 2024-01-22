package com.mozhimen.componentk.pagingk.widgets

import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import androidx.core.util.forEach
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basick.utilk.android.view.applyDebounceClickListener
import com.mozhimen.uicorek.vhk.VHKRecycler

/**
 * @ClassName PagingKPagedListMultiAdapter
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/11 11:44
 * @Version 1.0
 */
open class PagingKPagedListMultiAdapter<DATA : Any>(itemCallback: ItemCallback<DATA>) : PagingKPagedListAdapter<DATA>(0, itemCallback) {
    private val _pagingKItems by lazy(LazyThreadSafetyMode.NONE) { SparseArray<PagingKItem<DATA>>() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHKRecycler {
        val recyclerKPageItem = getPagingKItem(viewType)
        checkNotNull(recyclerKPageItem) { "ViewType: $viewType no such provider found，please use addItemProvider() first!" }
        recyclerKPageItem.context = parent.context
        return recyclerKPageItem.onCreateViewHolder(parent, viewType).apply {
            recyclerKPageItem.onViewHolderCreated(this, viewType)
        }
    }

    override fun onBindViewHolder(holder: VHKRecycler, position: Int) {
        super.onBindViewHolder(holder, position)
        bindPagingKItem(holder, getItem(position), position)
    }

    override fun onBindViewHolder(holder: VHKRecycler, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        bindPagingKItem(holder, getItem(position), position, payloads)
    }

//    override fun bindViewClickListener(holder: VHKRecycler, viewType: Int, position: Int) {
//        super.bindViewClickListener(holder, viewType, position)
//        bindItemViewClickListener(holder, viewType, position)
//        bindItemChildViewClickListener(holder, viewType, position)
//    }

    /////////////////////////////////////////////////////////////////////////////////

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        _pagingKItems.forEach { _, value ->
            value.onAttachedToRecyclerView()
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        _pagingKItems.forEach { _, value ->
            value.onDetachedFromRecyclerView()
        }
        super.onDetachedFromRecyclerView(recyclerView)
    }

    /////////////////////////////////////////////////////////////////////////////////

    /**Fe
     * 通过 ViewType 获取 BaseItemProvider
     * 例如：如果ViewType经过特殊处理，可以重写此方法，获取正确的Provider
     * （比如 ViewType 通过位运算进行的组合的）
     */
    fun getPagingKItem(viewType: Int): PagingKItem<DATA>? =
        _pagingKItems.get(viewType)

    /**
     * 必须通过此方法，添加 provider
     */
    fun addPagingKItem(item: PagingKItem<DATA>) {
        item.setAdapter(this)
        _pagingKItems.put(item.itemViewType, item)
    }

    fun bindPagingKItem(holder: VHKRecycler, item: DATA?, position: Int) {
        getPagingKItem(holder.itemViewType)?.onBindViewHolder(holder, item, position)
    }

    fun bindPagingKItem(holder: VHKRecycler, item: DATA?, position: Int, payloads: List<Any>) {
        getPagingKItem(holder.itemViewType)?.onBindViewHolder(holder, item, position, payloads)
    }

    /////////////////////////////////////////////////////////////////////////////////

    protected open fun bindItemViewClickListener(holder: VHKRecycler, viewType: Int, position: Int) {
        if (getOnItemClickListener() == null) {
            //如果没有设置点击监听，则回调给 itemProvider
            //Callback to itemProvider if no click listener is set
            holder.itemView.applyDebounceClickListener(lifecycleScope, 1000) {
//                val position = holder.adapterPosition
                if (position == RecyclerView.NO_POSITION)
                    return@applyDebounceClickListener
//                val itemViewType = holder.itemViewType
//                if (itemViewType == -0x201) //过滤掉暂无更多
//                    return@applyDebounceClickListener
                val recyclerKPageItem = _pagingKItems.get(viewType)
                recyclerKPageItem.onClick(holder, it, getItem(position), position)
            }
        }
    }

    protected open fun bindItemChildViewClickListener(holder: VHKRecycler, viewType: Int, position: Int) {
        if (getOnItemChildClickListener() == null) {
            val recyclerKPageItem = getPagingKItem(viewType) ?: return
            val childClickViewIds = recyclerKPageItem.getChildClickViewIds()
            childClickViewIds.forEach { id ->
                holder.itemView.findViewById<View>(id)?.let { childView ->
                    if (!childView.isClickable)
                        childView.isClickable = true
                    childView.applyDebounceClickListener(1000) { clickView ->
//                        val position: Int = holder.adapterPosition
                        if (position == RecyclerView.NO_POSITION)
                            return@applyDebounceClickListener
                        recyclerKPageItem.onChildClick(holder, clickView, getItem(position), position)
                    }
                }
            }
        }
    }
}