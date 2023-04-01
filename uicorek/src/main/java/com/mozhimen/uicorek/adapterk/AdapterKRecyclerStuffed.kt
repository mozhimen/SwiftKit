package com.mozhimen.uicorek.adapterk

import android.annotation.SuppressLint
import android.util.SparseArray
import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mozhimen.basick.utilk.exts.et
import com.mozhimen.uicorek.recyclerk.RecyclerKItem
import com.mozhimen.uicorek.vhk.VHKRecycler
import java.lang.ref.WeakReference
import java.lang.reflect.ParameterizedType
import java.util.ArrayList

/**
 * @ClassName DataKRecycler
 * @Description bugfix: DataKItem<*, out RecyclerView.ViewHolder>都被改成了这样。否则会有类型转换问题
 * @Author Kolin Zhao
 * @Date 2021/8/31 16:14
 * @Version 1.0
 */
open class AdapterKRecyclerStuffed : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    protected val TAG = "${this.javaClass.simpleName}>>>>>"

    private var _dataSets = ArrayList<RecyclerKItem<*, out RecyclerView.ViewHolder>>()
    private var _recyclerViewRef: WeakReference<RecyclerView>? = null
    private val _typePositions = SparseIntArray()
    private var _headers = SparseArray<View>()
    private var _footers = SparseArray<View>()
    private var BASE_ITEM_TYPE_HEADER = 1000000
    private var BASE_ITEM_TYPE_FOOTER = 2000000

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
     * @param index Int
     * @param item DataKItem<*, out ViewHolder>
     * @param notify Boolean
     */
    fun addItemAt(index: Int, item: RecyclerKItem<*, out RecyclerView.ViewHolder>, notify: Boolean) {
        if (index >= 0) {
            _dataSets.add(index, item)
        } else {
            _dataSets.add(item)
        }

        val notifyPos = if (index >= 0) index else _dataSets.size - 1
        if (notify) {
            notifyItemInserted(notifyPos)
        }

        item.setAdapter(this)
    }

    /**
     * 往现有集合的尾部逐年items集合
     * @param items List<DataKItem<*, out ViewHolder>>
     * @param notify Boolean
     */
    fun addItems(items: List<RecyclerKItem<*, out RecyclerView.ViewHolder>>, notify: Boolean) {
        val start = _dataSets.size
        items.forEach { item ->
            _dataSets.add(item)
            item.setAdapter(this)
        }
        if (notify) {
            notifyItemRangeInserted(start, items.size)
        }
    }

    /**
     * 从指定位上移除item
     * @param index Int
     * @return DataKItem<*, out RecyclerView.ViewHolder>?
     */
    fun removeItemAt(index: Int): RecyclerKItem<*, out RecyclerView.ViewHolder>? {
        return if (index >= 0 && index < _dataSets.size) {
            val remove = _dataSets.removeAt(index)
            notifyItemRemoved(index)
            remove
        } else {
            null
        }
    }

    /**
     * 移除指定item
     * @param dataItem DataKItem<*, out ViewHolder>
     */
    fun removeItem(dataItem: RecyclerKItem<*, out RecyclerView.ViewHolder>) {
        val index: Int = _dataSets.indexOf(dataItem)
        removeItemAt(index)
    }

    /**
     * 清出items
     */
    @SuppressLint("NotifyDataSetChanged")
    fun clearItems() {
        _dataSets.clear()
        notifyDataSetChanged()
    }

    /**
     * 获取item
     * @param position Int
     * @return DataKItem<*, RecyclerView.ViewHolder>?
     */
    fun getItem(position: Int): RecyclerKItem<*, RecyclerView.ViewHolder>? {
        if (position < 0 || position >= _dataSets.size) {
            return null
        }
        return _dataSets[position] as RecyclerKItem<*, RecyclerView.ViewHolder>
    }

    /**
     * 指定刷新某个item的数据
     * @param dataItem DataKItem<*, out ViewHolder>
     */
    fun refreshItem(dataItem: RecyclerKItem<*, out RecyclerView.ViewHolder>) {
        val indexOf = _dataSets.indexOf(dataItem)
        notifyItemChanged(indexOf)
    }

    /**
     * 添加Header
     * @param view View
     */
    fun addHeaderView(view: View) {
        //没有添加过
        if (_headers.indexOfValue(view) < 0) {
            _headers.put(BASE_ITEM_TYPE_HEADER++, view)
            notifyItemInserted(_headers.size() - 1)
        }
    }

    /**
     * 移除Header
     * @param view View
     */
    fun removeHeaderView(view: View) {
        val indexOfValue = _headers.indexOfValue(view)
        if (indexOfValue < 0) return
        _headers.removeAt(indexOfValue)
        notifyItemRemoved(indexOfValue)
    }

    /**
     * 添加Footer
     * @param view View
     */
    fun addFooterView(view: View) {
        //说明这个footerView 没有添加过
        if (_footers.indexOfValue(view) < 0) {
            _footers.put(BASE_ITEM_TYPE_FOOTER++, view)
            notifyItemInserted(itemCount)
        }
    }

    /**
     * 移除Footer
     * @param view View
     */
    fun removeFooterView(view: View) {
        val indexOfValue = _footers.indexOfValue(view)
        if (indexOfValue < 0) return
        _footers.removeAt(indexOfValue)
        //position代表的是在列表中分位置
        notifyItemRemoved(indexOfValue + getHeaderSize() + getOriginalItemSize())
    }

    /**
     * 获取Header数量
     * @return Int
     */
    fun getHeaderSize(): Int = _headers.size()

    /**
     * 获取Footer数量
     * @return Int
     */
    fun getFooterSize(): Int = _footers.size()

    /**
     * 获取item数量
     * @return Int
     */
    fun getOriginalItemSize(): Int = _dataSets.size

    /**
     * 获取recyclerView
     * @return RecyclerView?
     */
    fun getAttachedRecyclerView(): RecyclerView? {
        return _recyclerViewRef?.get()
    }

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
            val footerPosition = position - getHeaderSize() - getOriginalItemSize()
            return _footers.keyAt(footerPosition)
        }

        val itemPosition = position - getHeaderSize()
        val dataItem = _dataSets[itemPosition]
        val type = dataItem.javaClass.hashCode()

        //按照原来的写法相同的viewType仅仅只在第一次，会把viewType和dataItem关联
        _typePositions.put(type, position)
        return type
    }

    override fun getItemCount() = _dataSets.size + getHeaderSize() + getFooterSize()

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
        val dataItem = _dataSets[position]
        val vh = dataItem.onCreateViewHolder(parent)
        if (vh != null) return vh

        var view: View? = dataItem.getItemView(parent)
        if (view == null) {
            val layoutRes = dataItem.getItemLayoutRes()
            if (layoutRes < 0) {
                throw RuntimeException("dataItem: ${dataItem.javaClass.name} must override getItemView or getItemLayoutRes")
            }
            view = LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
        }

        return createViewHolderInternal(dataItem.javaClass, view!!)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (isHeaderPosition(position) || isFooterPosition(position)) return

        val itemPosition = position - getHeaderSize()
        val dataKItem = getItem(itemPosition)
        dataKItem?.onBindData(holder, itemPosition)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
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
                    val itemPosition = position - getHeaderSize()
                    if (itemPosition < _dataSets.size) {
                        val dataItem = getItem(itemPosition)
                        dataItem?.let {
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
        _recyclerViewRef?.clear()
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        val recyclerView = getAttachedRecyclerView()
        recyclerView?.let {
            //瀑布流的item占比
            val position = recyclerView.getChildAdapterPosition(holder.itemView)
            val isHeaderFooter = isHeaderPosition(position) || isFooterPosition(position)
            val itemPosition = position - getHeaderSize()

            val dataItem = getItem(itemPosition) ?: return
            val layoutParams = holder.itemView.layoutParams
            if (layoutParams != null && layoutParams is StaggeredGridLayoutManager.LayoutParams) {
                val manager = recyclerView.layoutManager as StaggeredGridLayoutManager?
                if (isHeaderFooter) {
                    layoutParams.isFullSpan = true
                    return
                }
                val spanSize = dataItem.getSpanSize()
                if (spanSize == manager!!.spanCount) {
                    layoutParams.isFullSpan = true
                }
            }
            dataItem.onViewAttachedToWindow(holder)
        }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        val position = holder.adapterPosition
        if (isHeaderPosition(position) || isFooterPosition(position))
            return
        val itemPosition = position - getHeaderSize()
        val dataItem = getItem(itemPosition) ?: return
        dataItem.onViewDetachedFromWindow(holder)
    }

    private fun isHeaderPosition(position: Int): Boolean = position < _headers.size() // 5 --> 4 3 2 1

    private fun isFooterPosition(position: Int): Boolean =
        position >= getHeaderSize() + getOriginalItemSize() // 10->  4+ 4.

    private fun createViewHolderInternal(
        javaClass: Class<RecyclerKItem<*, out RecyclerView.ViewHolder>>,
        view: View
    ): RecyclerView.ViewHolder {
        //得到该Item的父类类型, 即为DataKItem.class, class也是type的一个子类
        //type的子类常见的有class, 类泛型, ParameterizedType参数泛型, TypeVariable字段泛型
        //所以进一步判断它是不是参数泛型
        val superClass = javaClass.genericSuperclass
        if (superClass is ParameterizedType) {
            //得到它携带的泛型参数的数组
            val arguments = superClass.actualTypeArguments
            //挨个遍历判单是不是需要的RecyclerView.ViewHolder类型
            for (argument in arguments) if (argument is Class<*> && RecyclerView.ViewHolder::class.java.isAssignableFrom(argument)) {
                try {
                    //如果是, 则使用反射实例化类上标记的实际的泛型对象
                    //这里需要try-catch一把, 如果咱们直接在DataKItem子类上标记RecyclerView.ViewHolder, 抽象类是不允许反射的
                    return argument.getConstructor(View::class.java).newInstance(view) as RecyclerView.ViewHolder
                } catch (e: Throwable) {
                    e.printStackTrace()
                    e.message?.et(TAG)
                }
            }
        }
        return object : VHKRecycler(view) {}
    }
}