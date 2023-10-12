package com.mozhimen.uicorek.adapterk.item

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mozhimen.uicorek.adapterk.commons.IAdapterKRecyclerStuffed
import java.lang.ref.WeakReference

/**
 * @ClassName DataKRecycler
 * @Description bugfix: DataKItem<*, out RecyclerView.ViewHolder>都被改成了这样。否则会有类型转换问题
 * @Author Kolin Zhao
 * @Date 2021/8/31 16:14
 * @Version 1.0
 */
open class AdapterKItemRecyclerStuffed : AdapterKItemRecycler(), IAdapterKRecyclerStuffed {

    private var _headers = SparseArray<View>()
    private var _footers = SparseArray<View>()
    private var ITEM_TYPE_HEADER = 1000000
    private var ITEM_TYPE_FOOTER = 2000000

    //region # IAdapterKRecyclerStuffed
    override fun addHeaderView(view: View) {
        //没有添加过
        if (_headers.indexOfValue(view) >= 0) return
        _headers.put(ITEM_TYPE_HEADER++, view)
        notifyItemInserted(_headers.size() - 1)
    }

    override fun removeHeaderView(view: View) {
        val indexOfValue = _headers.indexOfValue(view)
        if (indexOfValue < 0) return
        _headers.removeAt(indexOfValue)
        notifyItemRemoved(indexOfValue)
    }

    override fun addFooterView(view: View) {
        //说明这个footerView 没有添加过
        if (_footers.indexOfValue(view) >= 0) return
        _footers.put(ITEM_TYPE_FOOTER++, view)
        notifyItemInserted(itemCount)
    }

    override fun removeFooterView(view: View) {
        val indexOfValue = _footers.indexOfValue(view)
        if (indexOfValue < 0) return
        _footers.removeAt(indexOfValue)
        //position代表的是在列表中分位置
        notifyItemRemoved(indexOfValue + getHeaderViewSize() + getNormalItemSize())
    }

    override fun getHeaderViewSize(): Int = _headers.size()

    override fun getFooterViewSize(): Int = _footers.size()

    override fun getNormalItemSize(): Int = _items.size
    //endregion

    /////////////////////////////////////////////////////////////////////////////////

    //region # AdapterKRecycler
    /**
     * 以每种item类型的class.hasCode为该item的viewType
     * 把type存储起来,为了onCreateViewHolder方法能够为不同类型的item创建不同的viewHolder
     * @param position Int
     * @return Int
     */
    override fun getItemViewType(position: Int): Int {
        if (isHeaderPosition(position)) {
            return _headers.keyAt(position)
        }
        if (isFooterPosition(position)) {
            //footer的位置应该计算一下: position=6, headerCount=1, itemCount=5, footerSize=1
            val footerPosition = position - getHeaderViewSize() - getNormalItemSize()
            return _footers.keyAt(footerPosition)
        }

        val itemPosition = position - getHeaderViewSize()
        val item = _items[itemPosition]
        val type = item.javaClass.hashCode()

        //按照原来的写法相同的viewType仅仅只在第一次，会把viewType和dataItem关联
        _typePositions.put(type, position)
        return type
    }

    override fun getItemCount() = _items.size + getHeaderViewSize() + getFooterViewSize()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (_headers.indexOfKey(viewType) >= 0) {
            val view = _headers[viewType]
            return object : RecyclerView.ViewHolder(view) {}
        }
        if (_footers.indexOfKey(viewType) >= 0) {
            val view = _footers[viewType]
            return object : RecyclerView.ViewHolder(view) {}
        }

        //这会导致不同position，但viewType相同，获取到的dataItem始终是第一次关联的dataItem对象。
        //这就会导致通过getItemView创建的成员变量，只在第一个dataItem中，其它实例中无法生效

        //为了解决dataItem成员变量binding, 刷新之后无法被复用的问题
        val position = _typePositions.get(viewType)
        val item = _items[position]
        val viewHolder = item.onCreateViewHolder(parent)
        if (viewHolder != null) return viewHolder

        var view: View? = item.getItemView(parent)
        if (view == null) {
            val layoutId = item.getItemLayoutId()
            if (layoutId < 0) {
                throw RuntimeException("onCreateViewHolder item: ${item.javaClass.name} must override getItemView or getItemLayoutId")
            }
            view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        }

        return onCreateViewHolderInternal(item.javaClass, view!!)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (isHeaderPosition(position) || isFooterPosition(position)) return
        val itemPosition = position - getHeaderViewSize()
        val item = getItem(itemPosition)
        item?.onBindItem(holder, itemPosition)
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        val recyclerView = getAttachedRecyclerView()
        recyclerView?.let {
            //瀑布流的item占比
            val position = recyclerView.getChildAdapterPosition(holder.itemView)
            val isHeaderFooter = isHeaderPosition(position) || isFooterPosition(position)
            val itemPosition = position - getHeaderViewSize()

            val item = getItem(itemPosition) ?: return
            val layoutParams = holder.itemView.layoutParams
            if (layoutParams != null && layoutParams is StaggeredGridLayoutManager.LayoutParams) {
                val manager = recyclerView.layoutManager as StaggeredGridLayoutManager?
                if (isHeaderFooter) {
                    layoutParams.isFullSpan = true
                    return
                }
                val spanSize = item.getItemSpanSize()
                if (spanSize == manager!!.spanCount) {
                    layoutParams.isFullSpan = true
                }
            }
            item.onViewAttachedToWindow(holder)
        }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        val position = holder.adapterPosition
        if (isHeaderPosition(position) || isFooterPosition(position))
            return
        val itemPosition = position - getHeaderViewSize()
        val item = getItem(itemPosition) ?: return
        item.onViewDetachedFromWindow(holder)
    }
    //endregion

    override fun onAttachedToRecyclerViewInternal(recyclerView: RecyclerView) {
        _recyclerViewRef = WeakReference(recyclerView)
        //为列表上的item适配网格布局
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is GridLayoutManager) {
            val spanCount = layoutManager.spanCount
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    if (isHeaderPosition(position) || isFooterPosition(position)) {
                        return spanCount
                    }
                    val itemPosition = position - getHeaderViewSize()
                    if (itemPosition < _items.size) {
                        val dataItem = getItem(itemPosition)
                        dataItem?.let {
                            val spanSize = it.getItemSpanSize()
                            return if (spanSize <= 0) spanCount else spanSize
                        }
                    }
                    return spanCount
                }
            }
        }
    }

    /////////////////////////////////////////////////////////////////////////////////

    private fun isHeaderPosition(position: Int): Boolean = position < _headers.size() // 5 --> 4 3 2 1

    private fun isFooterPosition(position: Int): Boolean =
        position >= getHeaderViewSize() + getNormalItemSize() // 10->  4+ 4.
}