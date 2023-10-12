package com.mozhimen.uicorek.adapterk.item

import android.annotation.SuppressLint
import android.util.Log
import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mozhimen.basick.elemk.kotlin.cons.CSuppress
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.uicorek.adapterk.commons.IAdapterKRecycler
import com.mozhimen.uicorek.recyclerk.item.RecyclerKItem
import com.mozhimen.uicorek.vhk.VHKRecycler
import java.lang.ref.WeakReference
import java.lang.reflect.ParameterizedType
import java.util.ArrayList


/**
 * @ClassName AdapterKRecycler
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/31 18:38
 * @Version 1.0
 */
open class AdapterKItemRecycler : RecyclerView.Adapter<RecyclerView.ViewHolder>(), IAdapterKRecycler {
    protected var _items = ArrayList<RecyclerKItem<out RecyclerView.ViewHolder>>()
    protected val _typePositions = SparseIntArray()
    protected var _recyclerViewRef: WeakReference<RecyclerView>? = null

    ////////////////////////////////////////////////////////////////////////////////////

    //region # IAdapterKRecycler
    override fun refreshItem(item: RecyclerKItem<out RecyclerView.ViewHolder>, position: Int, notify: Boolean) {
        if (position < 0 || position >= _items.size) return
        _items[position] = item.apply { bindAdapter(this@AdapterKItemRecycler) }
        if (notify) notifyItemChanged(position)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun refreshItems(notify: Boolean) {
        if (notify) notifyDataSetChanged()
    }

    override fun refreshItems(items: List<RecyclerKItem<out RecyclerView.ViewHolder>>, notify: Boolean) {
        _items.clear()
        for (item in items) _items.add(item.apply { bindAdapter(this@AdapterKItemRecycler) })
        refreshItems(notify)
    }

    override fun addItem(item: RecyclerKItem<out RecyclerView.ViewHolder>, notify: Boolean) {
        addItemAtPosition(item, -1, notify)
    }

    override fun addItemAtPosition(item: RecyclerKItem<out RecyclerView.ViewHolder>, position: Int, notify: Boolean) {
        if (position >= 0)
            _items.add(position, item)
        else
            _items.add(item)

        val notifyPos = if (position >= 0) position else _items.size - 1
        if (notify) notifyItemInserted(notifyPos)

        item.bindAdapter(this)
    }

    override fun addItems(items: List<RecyclerKItem<out RecyclerView.ViewHolder>>, notify: Boolean) {
        val start = _items.size
        for (item in items)
            _items.add(item.apply { bindAdapter(this@AdapterKItemRecycler) })
        Log.d(TAG, "addItems: start $start items size ${items.size} _items size ${_items.size}")
        if (notify) notifyItemRangeInserted(start, items.size)
    }

    override fun removeItem(item: RecyclerKItem<out RecyclerView.ViewHolder>, notify: Boolean) {
        val position = _items.indexOf(item)
        if (position != -1) removeItemAtPosition(position, notify)
    }

    @Suppress(CSuppress.UNCHECKED_CAST)
    override fun removeItemAtPosition(position: Int, notify: Boolean): RecyclerKItem<in RecyclerView.ViewHolder>? {
        if (position < 0 || position >= _items.size) return null
        val remove = _items.removeAt(position)
        if (notify) notifyItemRemoved(position)
        return remove as RecyclerKItem<in RecyclerView.ViewHolder>
    }

    override fun removeItemsAll(notify: Boolean) {
        _items.clear()
        if (notify) notifyItemRangeRemoved(0, _items.size)
    }

    @Suppress(CSuppress.UNCHECKED_CAST)
    override fun getItem(position: Int): RecyclerKItem<RecyclerView.ViewHolder>? {
        if (position < 0 || position >= _items.size) return null
        return _items[position] as RecyclerKItem<RecyclerView.ViewHolder>
    }

    @Suppress(CSuppress.UNCHECKED_CAST)
    override fun getItems(): List<RecyclerKItem<in RecyclerView.ViewHolder>> {
        return _items.toList() as List<RecyclerKItem<in RecyclerView.ViewHolder>>
    }

    override fun getAttachedRecyclerView(): RecyclerView? {
        return _recyclerViewRef?.get()
    }
    //endregion

    ////////////////////////////////////////////////////////////////////////////////////

    //region # RecyclerView.Adapter
    /**
     * 以每种item类型的class.hasCode为该item的viewType
     * 把type存储起来,为了onCreateViewHolder方法能够为不同类型的item创建不同的viewHolder
     */
    override fun getItemViewType(position: Int): Int {
        val item = _items[position]
        val type = item.javaClass.hashCode()

        _typePositions.put(type, position)
        return type
    }

    override fun getItemCount() =
        _items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //为了解决dataItem成员变量binding, 刷新之后无法被复用的问题
        val position = _typePositions.get(viewType)
        val item = _items[position]
        val viewHolder = item.onCreateViewHolder(parent)
        if (viewHolder != null) return viewHolder

        var view: View? = item.getItemView(parent)
        if (view == null) {
            val layoutId = item.getItemLayoutId()
            if (layoutId < 0) {
                throw RuntimeException("dataItem ${item.javaClass.name} must override getItemView or getItemLayoutRes")
            }
            view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        }
        return onCreateViewHolderInternal(item.javaClass, view!!)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        item?.onBindItem(holder, position)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        onAttachedToRecyclerViewInternal(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        onDetachedFromRecyclerViewInternal(recyclerView)
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        val recyclerView = getAttachedRecyclerView()
        recyclerView?.let {
            //瀑布流的item占比
            val position = recyclerView.getChildAdapterPosition(holder.itemView)
            val item = getItem(position) ?: return
            val layoutParams = holder.itemView.layoutParams
            if (layoutParams != null && layoutParams is StaggeredGridLayoutManager.LayoutParams) {
                val manager = recyclerView.layoutManager as StaggeredGridLayoutManager?
                val spanSize = item.getItemSpanSize()
                if (spanSize == manager!!.spanCount) {
                    layoutParams.isFullSpan = true
                }
            }
            item.onViewAttachedToWindow(holder)
        }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        val item = getItem(holder.adapterPosition) ?: return
        item.onViewDetachedFromWindow(holder)
    }
    //endregion

    ////////////////////////////////////////////////////////////////////////////////////

    protected open fun onAttachedToRecyclerViewInternal(recyclerView: RecyclerView) {
        _recyclerViewRef = WeakReference(recyclerView)
        //为列表上的item适配网格布局
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is GridLayoutManager) {
            val spanCount = layoutManager.spanCount
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    if (position < _items.size) {
                        val item = getItem(position)
                        item?.let {
                            val spanSize = it.getItemSpanSize()
                            return if (spanSize <= 0) spanCount else spanSize
                        }
                    }
                    return spanCount
                }
            }
        }
    }

    protected open fun onDetachedFromRecyclerViewInternal(recyclerView: RecyclerView) {
        _recyclerViewRef?.clear()
    }

    protected open fun onCreateViewHolderInternal(clazz: Class<RecyclerKItem<out RecyclerView.ViewHolder>>, view: View): RecyclerView.ViewHolder {
        //得到该item的父类型,即为RecyclerKItem.class. class也是type的一个子类
        //type的子类常见的有class. 泛类型, parameterizedType参数泛型, TypeVariable字段泛型
        //来进一步判断它是不是参数泛型
        val superClass = clazz.genericSuperclass
        if (superClass is ParameterizedType) {
            //得到它携带的泛型参数的数组
            val arguments = superClass.actualTypeArguments
            //挨个遍历判单 是不是需要的RecyclerView.ViewHolder类型
            for (argument in arguments) {
                if (argument is Class<*> && RecyclerView.ViewHolder::class.java.isAssignableFrom(argument)) {
                    try {
                        //如果是则使用反射 实例化类上标记的实际的泛型对象
                        //这里需要 try-catch, 如果直接在RecyclerKItem子类上标记 RecyclerView.ViewHolder. 抽象类是不允许反射的
                        return (argument.getConstructor(View::class.java).newInstance(view) as RecyclerView.ViewHolder).also { Log.d(TAG, "onCreateViewHolderInternal: getViewHolder success") }
                    } catch (e: Throwable) {
                        e.printStackTrace()
                        e.message?.et(TAG)
                    }
                }
            }
        }
        return object : VHKRecycler(view) {}
    }
}