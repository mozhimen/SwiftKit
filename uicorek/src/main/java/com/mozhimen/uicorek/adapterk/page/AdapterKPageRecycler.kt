package com.mozhimen.uicorek.adapterk.page

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basick.utilk.android.view.applyDebounceClickListener
import com.mozhimen.uicorek.adapterk.page.commons.IOnItemChildClickListener
import com.mozhimen.uicorek.adapterk.page.commons.IOnItemClickListener
import com.mozhimen.uicorek.adapterk.page.commons.IOnItemLongClickListener
import com.mozhimen.uicorek.vhk.VHKRecycler
import java.util.LinkedHashSet

/**
 * @ClassName AdapterPageRecycler
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/11 10:28
 * @Version 1.0
 */
open class AdapterKPageRecycler<DATA>(@LayoutRes private val _layoutId: Int, itemCallback: DiffUtil.ItemCallback<DATA>) : PagedListAdapter<DATA, VHKRecycler>(itemCallback) {
    private var _onItemClickListener: IOnItemClickListener<DATA>? = null
    private var _onItemChildClickListener: IOnItemChildClickListener<DATA>? = null
    private var _onItemLongClickListener: IOnItemLongClickListener<DATA>? = null
    private val _childClickViewIds = LinkedHashSet<Int>()//用于保存需要设置点击事件的 item

    //////////////////////////////////////////////////////////////////////////////

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHKRecycler {
        val inflate = LayoutInflater.from(parent.context).inflate(_layoutId, parent, false)
        val vhkRecycler = VHKRecycler(inflate)
        onBindViewClickListener(vhkRecycler, viewType)
        return vhkRecycler
    }

    override fun onBindViewHolder(holder: VHKRecycler, position: Int) {

    }

    public override fun getItem(position: Int): DATA? {
        return super.getItem(position)
    }

    //////////////////////////////////////////////////////////////////////////////

    /**
     * 绑定 item 点击事件
     */
    protected open fun onBindViewClickListener(holder: VHKRecycler, viewType: Int) {
        _onItemClickListener?.let {
            holder.itemView.applyDebounceClickListener(1000) { view ->
                val position = holder.adapterPosition
                if (position == RecyclerView.NO_POSITION) return@applyDebounceClickListener
                val itemViewType = holder.itemViewType
                //过滤掉暂无更多
                if (itemViewType == -0x201) return@applyDebounceClickListener
                it.invoke(this, view, position)
            }
        }
        _onItemChildClickListener?.let { block ->
            for (id in getChildClickViewIds()) {
                holder.itemView.findViewById<View>(id)?.let { view ->
                    if (!view.isClickable) view.isClickable = true
                    view.applyDebounceClickListener(1000) {
                        val position = holder.adapterPosition
                        if (position == RecyclerView.NO_POSITION) return@applyDebounceClickListener
                        block.invoke(this, view, position)
                    }
                }
            }
        }
        _onItemLongClickListener?.let {
            holder.itemView.setOnLongClickListener { view ->
                val position = holder.adapterPosition
                if (position == RecyclerView.NO_POSITION) return@setOnLongClickListener false
                it.invoke(this, view, position)
                return@setOnLongClickListener true
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////

    fun getChildClickViewIds(): LinkedHashSet<Int> {
        return _childClickViewIds
    }

    fun getOnItemClickListener(): IOnItemClickListener<DATA>? = _onItemClickListener

    fun getOnItemChildClickListener(): IOnItemChildClickListener<DATA>? = _onItemChildClickListener

    /**
     * 设置需要点击事件的子view
     */
    fun addChildClickViewIds(@IdRes vararg viewIds: Int) {
        for (viewId in viewIds) {
            _childClickViewIds.add(viewId)
        }
    }

    fun setOnItemClickListener(listener: IOnItemClickListener<DATA>) {
        _onItemClickListener = listener
    }

    fun setOnItemChildClickListener(listener: IOnItemChildClickListener<DATA>) {
        _onItemChildClickListener = listener
    }

    fun setOnItemLongClickListener(listener: IOnItemLongClickListener<DATA>) {
        _onItemLongClickListener = listener
    }
}