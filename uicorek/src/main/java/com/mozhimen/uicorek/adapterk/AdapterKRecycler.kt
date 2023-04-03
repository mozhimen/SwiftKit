package com.mozhimen.uicorek.adapterk

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mozhimen.basick.utilk.exts.et
import com.mozhimen.uicorek.adapterk.commons.IAdapterKRecycler
import com.mozhimen.uicorek.recyclerk.RecyclerKItem
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
open class AdapterKRecycler : RecyclerView.Adapter<RecyclerView.ViewHolder>(), IAdapterKRecycler {
    protected var _dataSets = ArrayList<RecyclerKItem<*, out RecyclerView.ViewHolder>>()
    protected var _typeArrays = SparseArray<RecyclerKItem<*, out RecyclerView.ViewHolder>>()
    protected var _recyclerViewRef: WeakReference<RecyclerView>? = null

    //region # IAdapterKRecycler
    override fun refreshItem(item: RecyclerKItem<*, out RecyclerView.ViewHolder>) {
        val position = _dataSets.indexOf(item)
        notifyItemChanged(position)
    }

    override fun addItem(item: RecyclerKItem<*, out RecyclerView.ViewHolder>, notify: Boolean) {
        addItemAt(-1, item, notify)
    }

    override fun addItemAt(position: Int, item: RecyclerKItem<*, out RecyclerView.ViewHolder>, notify: Boolean) {
        if (position >= 0) {
            _dataSets.add(position, item)
        } else {
            _dataSets.add(item)
        }

        val notifyPos = if (position >= 0) position else _dataSets.size - 1
        if (notify) {
            notifyItemInserted(notifyPos)
        }

        item.setAdapter(this)
    }

    override fun addItems(items: List<RecyclerKItem<*, out RecyclerView.ViewHolder>>, notify: Boolean) {
        val start = _dataSets.size
        items.forEach { item ->
            _dataSets.add(item)
            item.setAdapter(this)
        }
        if (notify) {
            notifyItemRangeInserted(start, items.size)
        }
    }

    override fun removeItem(item: RecyclerKItem<*, out RecyclerView.ViewHolder>) {
        val position = _dataSets.indexOf(item)
        removeItemAt(position)
    }

    override fun removeItemAt(position: Int): RecyclerKItem<*, out RecyclerView.ViewHolder>? {
        return if (position > 0 && position < _dataSets.size) {
            val remove = _dataSets.removeAt(position)
            notifyItemRemoved(position)
            remove
        } else {
            null
        }
    }

    override fun removeItemsAll() {
        _dataSets.clear()
        notifyItemRangeRemoved(0, _dataSets.size)
    }

    override fun getItem(position: Int): RecyclerKItem<*, RecyclerView.ViewHolder>? {
        if (position < 0 || position >= _dataSets.size) return null
        return _dataSets[position] as RecyclerKItem<*, RecyclerView.ViewHolder>
    }

    override fun getAttachedRecyclerView(): RecyclerView? {
        return _recyclerViewRef?.get()
    }
    //endregion

    /**
     * 以每种item类型的class.hasCode为该item的viewType
     * 把type存储起来,为了onCreateViewHolder方法能够为不同类型的item创建不同的viewHolder
     */
    override fun getItemViewType(position: Int): Int {
        val item = _dataSets[position]
        val type = item.javaClass.hashCode()
        //如果还没有包含这种类型的item,则添加进来
        if (_typeArrays.indexOfKey(type) < 0) {
            _typeArrays.put(type, item)
        }
        return type
    }

    override fun getItemCount() = _dataSets.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val item = _typeArrays.get(viewType)
        var view: View? = item.getItemView(parent)
        if (view == null) {
            val layoutRes = item.getItemLayoutRes()
            if (layoutRes < 0) {
                throw RuntimeException("dataItem ${item.javaClass.name} must override getItemView or getItemLayoutRes")
            }
            view = LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
        }
        return createViewHolderInternal(item.javaClass, view!!)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        item?.onBindData(holder, position)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        onAttachedToRecyclerViewInternal(recyclerView)
    }

    protected open fun onAttachedToRecyclerViewInternal(recyclerView: RecyclerView) {
        _recyclerViewRef = WeakReference(recyclerView)
        //为列表上的item适配网格布局
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is GridLayoutManager) {
            val spanCount = layoutManager.spanCount
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    if (position < _dataSets.size) {
                        val dataMKItem = getItem(position)
                        dataMKItem?.let {
                            val spanSize = it.getSpanSize()
                            return if (spanSize <= 0) spanCount else spanSize
                        }
                    }
                    return spanCount
                }
            }
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        onDetachedFromRecyclerViewInternal(recyclerView)
    }

    protected open fun onDetachedFromRecyclerViewInternal(recyclerView: RecyclerView) {
        _recyclerViewRef?.clear()
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
                val spanSize = item.getSpanSize()
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

    protected fun createViewHolderInternal(clazz: Class<RecyclerKItem<*, out RecyclerView.ViewHolder>>, view: View): RecyclerView.ViewHolder {
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
                        return argument.getConstructor(View::class.java)
                            .newInstance(view) as RecyclerView.ViewHolder
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