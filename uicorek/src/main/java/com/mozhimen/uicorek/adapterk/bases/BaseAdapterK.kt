package com.mozhimen.uicorek.adapterk.bases

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.annotation.LayoutRes
import com.mozhimen.uicorek.vhk.bases.BaseVHK


/**
 * @ClassName BaseAdapterK
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/11/22 15:02
 * @Version 1.0
 */
abstract class BaseAdapterK<T>(
    private val _datas: MutableList<T>,
    @LayoutRes private val _layoutId: Int
) : BaseAdapter() {

    override fun getCount(): Int =
        _datas.size

    override fun getItem(position: Int): T =
        _datas[position]

    override fun getItemId(position: Int): Long =
        position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewHolder = BaseVHK.bindView(parent.context, convertView, parent, _layoutId, position)
        onBindView(viewHolder, getItem(position), position)
        return viewHolder.itemView
    }

    //////////////////////////////////////////////////////////////////////////

    abstract fun onBindView(holder: BaseVHK, data: T, position: Int)

    //////////////////////////////////////////////////////////////////////////

    //添加一个元素
    fun add(data: T) {
        _datas.add(data)
        notifyDataSetChanged()
    }

    //往特定位置，添加一个元素
    fun add(position: Int, data: T) {
        _datas.add(position, data)
        notifyDataSetChanged()
    }

    fun remove(data: T) {
        _datas.remove(data)
        notifyDataSetChanged()
    }

    fun remove(position: Int) {
        _datas.removeAt(position)
        notifyDataSetChanged()
    }

    fun clear() {
        _datas.clear()
        notifyDataSetChanged()
    }
}

