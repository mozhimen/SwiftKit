package com.mozhimen.uicorek.adapterk

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mozhimen.uicorek.recyclerk.RecyclerKItem
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
class AdapterKRecycler() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var dataSets = ArrayList<RecyclerKItem<*, out RecyclerView.ViewHolder>>()
    private var typeArrays = SparseArray<RecyclerKItem<*, out RecyclerView.ViewHolder>>()
    private var recyclerViewRef: WeakReference<RecyclerView>? = null

    /**
     * 在末尾添加item
     * @param item DataKItem<*, out ViewHolder>
     * @param notify Boolean
     */
    fun addItem(item: RecyclerKItem<*, out RecyclerView.ViewHolder>, notify: Boolean) {
        addItemAt(-1, item, notify)
    }


    /**
     * 在指定位上添加item
     */
    fun addItemAt(index: Int, item: RecyclerKItem<*, out RecyclerView.ViewHolder>, notify: Boolean) {
        if (index >= 0) {
            dataSets.add(index, item)
        } else {
            dataSets.add(item)
        }

        val notifyPos = if (index >= 0) index else dataSets.size - 1
        if (notify) {
            notifyItemInserted(notifyPos)
        }
    }

    /**
     * 添加items集合
     */
    fun addItems(items: List<RecyclerKItem<*, out RecyclerView.ViewHolder>>, notify: Boolean) {
        val start = dataSets.size
        items.forEach {
            dataSets.add(it)
        }
        if (notify) {
            notifyItemRangeInserted(start, items.size)
        }
    }

    /**
     * 从指定位上移除item
     */
    fun removeItemAt(index: Int): RecyclerKItem<*, out RecyclerView.ViewHolder>? {
        return if (index > 0 && index < dataSets.size) {
            val remove = dataSets.removeAt(index)
            notifyItemRemoved(index)
            remove
        } else {
            null
        }
    }

    /**
     * 删除所有的item
     */
    fun removeItemsAll() {
        notifyItemRangeRemoved(0, dataSets.size)
        dataSets.clear()
    }


    /**
     * 移除item
     */
    fun removeItem(dataMKItem: RecyclerKItem<*, out RecyclerView.ViewHolder>) {
        val indexOf = dataSets.indexOf(dataMKItem)
        removeItemAt(indexOf)
    }

    /**
     * 以每种item类型的class.hasCode为该item的viewType
     * 把type存储起来,为了onCreateViewHolder方法能够为不同类型的item创建不同的viewHolder
     */
    override fun getItemViewType(position: Int): Int {
        val dataMKItem = dataSets[position]
        val type = dataMKItem.javaClass.hashCode()
        //如果还没有包含这种类型的item,则添加进来
        if (typeArrays.indexOfKey(type) < 0) {
            typeArrays.put(type, dataMKItem)
        }
        return type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val dataMKItem = typeArrays.get(viewType)
        var view: View? = dataMKItem.getItemView(parent)
        if (view == null) {
            val layoutRes = dataMKItem.getItemLayoutRes()
            if (layoutRes < 0) {
                throw RuntimeException("dataItem ${dataMKItem.javaClass.name} must override getItemView or getItemLayoutRes")
            }
            view = mInflater!!.inflate(layoutRes, parent, false)
        }
        return createViewHolderInternal(dataMKItem.javaClass, view!!)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val dataMKItem = getItem(position)
        dataMKItem?.onBindData(holder, position)
    }

    private fun createViewHolderInternal(
        javaClass: Class<RecyclerKItem<*, out RecyclerView.ViewHolder>>,
        view: View
    ): RecyclerView.ViewHolder {
        //得到该item的父类型,即为RecyclerKItem.class. class也是type的一个子类
        //type的子类常见的有class. 泛类型, parameterizedType参数泛型, TypeVariable字段泛型
        //来进一步判断它是不是参数泛型
        val superClass = javaClass.genericSuperclass
        if (superClass is ParameterizedType) {
            //得到它携带的泛型参数的数组
            val arguments = superClass.actualTypeArguments
            //挨个遍历判单 是不是需要的RecyclerView.ViewHolder类型
            arguments.forEach {
                if (it is Class<*> && RecyclerView.ViewHolder::class.java.isAssignableFrom(
                        it
                    )
                ) {
                    try {
                        //如果是则使用反射 实例化类上标记的实际的泛型对象
                        //这里需要 try-catch, 如果直接在RecyclerKItem子类上标记 RecyclerView.ViewHolder. 抽象类是不允许反射的
                        return it.getConstructor(View::class.java)
                            .newInstance(view) as RecyclerView.ViewHolder
                    } catch (e: Throwable) {
                        e.printStackTrace()
                    }
                }
            }
        }
        return object : DataMKViewHolder(view) {}
    }

    override fun getItemCount() = dataSets.size

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerViewRef = WeakReference(recyclerView)
        //为列表上的item适配网格布局
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is GridLayoutManager) {
            val spanCount = layoutManager.spanCount
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    if (position < dataSets.size) {
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
        recyclerViewRef?.clear()
    }

    fun getAttachRecyclerView(): RecyclerView? {
        return recyclerViewRef?.get()
    }

    fun refreshItem(dataMKItem: RecyclerKItem<*, out RecyclerView.ViewHolder>) {
        val indexOf = dataSets.indexOf(dataMKItem)
        notifyItemChanged(indexOf)
    }

    fun getItem(position: Int): RecyclerKItem<*, RecyclerView.ViewHolder>? {
        if (position < 0 || position >= dataSets.size) {
            return null
        }
        return dataSets[position] as RecyclerKItem<*, RecyclerView.ViewHolder>
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        val recyclerView = getAttachRecyclerView()
        recyclerView?.let {
            //瀑布流的item占比
            val position = recyclerView.getChildAdapterPosition(holder.itemView)
            val dataMKItem = getItem(position) ?: return
            val layoutParams = holder.itemView.layoutParams
            if (layoutParams != null && layoutParams is StaggeredGridLayoutManager.LayoutParams) {
                val manager = recyclerView.layoutManager as StaggeredGridLayoutManager?
                val spanSize = dataMKItem.getSpanSize()
                if (spanSize == manager!!.spanCount) {
                    layoutParams.isFullSpan = true
                }
            }
            dataMKItem.onViewAttachedToWindow(holder)
        }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        val dataMKItem = getItem(holder.adapterPosition) ?: return
        dataMKItem.onViewDetachedFromWindow(holder)
    }
}