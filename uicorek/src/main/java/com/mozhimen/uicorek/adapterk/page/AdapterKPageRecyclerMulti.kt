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
        convert(holder, getItem(position))
    }

    override fun onBindViewClickListener(holder: VHKRecycler, viewType: Int) {
        super.onBindViewClickListener(holder, viewType)
        onBindClick(holder)
        bindChildClick(holder, viewType)
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
     *
     * @param viewType Int
     * @return BaseItemProvider
     */
    fun getRecyclerKPageItem(viewType: Int): RecyclerKPageItem<DATA>? {
        return _recyclerKPageItems.get(viewType)
    }

    /**
     * 必须通过此方法，添加 provider
     * @param provider BaseItemProvider
     */
    fun addRecyclerKPageItem(provider: RecyclerKPageItem<DATA>) {
        provider.setAdapter(this)
        _recyclerKPageItems.put(provider.itemViewType, provider)
    }

    fun convert(holder: VHKRecycler, item: DATA?) {
        getRecyclerKPageItem(holder.itemViewType)!!.convert(holder, item)
    }

    fun convert(holder: VHKRecycler, item: DATA, payloads: List<Any>) {
        getRecyclerKPageItem(holder.itemViewType)!!.convert(holder, item, payloads)
    }

    /////////////////////////////////////////////////////////////////////////////////

    protected open fun onBindClick(viewHolder: VHKRecycler) {
        if (getOnItemClickListener() == null) {
            //如果没有设置点击监听，则回调给 itemProvider
            //Callback to itemProvider if no click listener is set
            viewHolder.itemView.applyDebounceClickListener(1000) {
                val position = viewHolder.adapterPosition
                if (position == RecyclerView.NO_POSITION) return@applyDebounceClickListener
                val itemViewType = viewHolder.itemViewType
                //过滤掉暂无更多
                if (itemViewType == -0x201) return@applyDebounceClickListener
                val recyclerKPageItem = _recyclerKPageItems.get(itemViewType)
                recyclerKPageItem.onClick(viewHolder, it, getItem(position), position)
            }
        }
    }

    protected open fun bindChildClick(viewHolder: VHKRecycler, viewType: Int) {
        if (getOnItemChildClickListener() == null) {
            val recyclerKPageItem = getRecyclerKPageItem(viewType) ?: return
            val ids = recyclerKPageItem.getChildClickViewIds()
            ids.forEach { id ->
                viewHolder.itemView.findViewById<View>(id)?.let {
                    if (!it.isClickable) it.isClickable = true
                    it.applyDebounceClickListener(1000) { v ->
                        val position: Int = viewHolder.adapterPosition
                        if (position == RecyclerView.NO_POSITION) return@applyDebounceClickListener
                        recyclerKPageItem.onChildClick(viewHolder, v, getItem(position), position)
                    }
                }
            }
        }
    }
}