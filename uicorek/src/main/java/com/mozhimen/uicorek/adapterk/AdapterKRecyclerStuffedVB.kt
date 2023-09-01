package com.mozhimen.uicorek.adapterk

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.uicorek.adapterk.commons.IAdapterKRecyclerMultiVBListener
import com.mozhimen.uicorek.adapterk.commons.IAdapterKRecyclerVB
import com.mozhimen.uicorek.adapterk.commons.IAdapterKRecyclerVBListener
import com.mozhimen.uicorek.vhk.VHKRecyclerMultiVB

/**
 * @ClassName AdapterKRecyclerStuffedVB
 * @Description 带Header的RecyclerView适配器
 * @Author Kolin Zhao
 * @Date 2021/7/7 14:48
 * @Version 1.0
 */

//typealias IAdapterKRecyclerStuffedVBListener<DATA, VB> = (holder: VHKRecyclerVB<VB>, itemData: DATA, position: Int, currentSelectPos: Int) -> Unit

open class AdapterKRecyclerStuffedVB<DATA, VB : ViewDataBinding>(
    private var _datas: MutableList<DATA>,
    private var _defaultLayout: Int,
    private var _headerLayout: Int?,
    private var _footerLayout: Int?,
    private var _brId: Int,
    private var _listener: IAdapterKRecyclerMultiVBListener<DATA, VB>? = null
) : RecyclerView.Adapter<VHKRecyclerMultiVB<VB>>(), IAdapterKRecyclerVB<DATA, VB> {

    private var _selectItemPosition = 0

    ////////////////////////////////////////////////////////////////////////////

    override fun refreshData(data: DATA, position: Int, notify: Boolean) {
        if (position < 0 || position >= _datas.size) return
        _datas[position] = data
        if (notify) notifyItemChanged(position)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun refreshDatas(notify: Boolean) {
        if (notify) notifyDataSetChanged()
    }

    override fun refreshDatas(datas: List<DATA>) {
        refreshDatas(datas, true)
    }

    override fun refreshDatas(datas: List<DATA>, notify: Boolean) {
        _datas.clear()
        _datas.addAll(datas)
        refreshDatas(notify)
    }

    override fun addData(data: DATA, notify: Boolean) {
        addDataAtPosition(data, -1, notify)
    }

    override fun addDataAtPosition(data: DATA, position: Int, notify: Boolean) {
        if (position >= 0) _datas.add(position, data)
        else _datas.add(data)

        val notifyPos = if (position >= 0) position else _datas.size - 1
        if (notify) notifyItemInserted(notifyPos)
    }

    override fun addDatas(datas: List<DATA>, notify: Boolean) {
        val start = _datas.size
        _datas.addAll(_datas)
        if (notify) notifyItemRangeInserted(start, _datas.size)
    }

    override fun removeData(data: DATA, notify: Boolean) {
        val position = _datas.indexOf(data)
        if (position != -1) removeDataAtPosition(position, notify)
    }

    override fun removeDataAtPosition(position: Int, notify: Boolean): DATA? {
        if (position < 0 || position >= _datas.size) return null
        val remove = _datas.removeAt(position)
        if (notify) notifyItemRemoved(position)
        return remove
    }

    override fun removeDatasAll(notify: Boolean) {
        _datas.clear()
        if (notify) notifyItemRangeRemoved(0, _datas.size)
    }

    override fun getData(position: Int): DATA? {
        if (position < 0 || position >= _datas.size) return null
        return _datas[position]
    }

    override fun getDatas(): List<DATA?> =
        _datas

    override fun onDataSelected(position: Int) {
        if (position < 0 || position >= _datas.size) return
        _selectItemPosition = position
        refreshDatas(true)
    }

    override fun getCurrentSelectPosition(): Int =
        _selectItemPosition

    ////////////////////////////////////////////////////////////////////////////

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHKRecyclerMultiVB<VB> {
        return when (viewType) {
            _headerLayout -> VHKRecyclerMultiVB(LayoutInflater.from(parent.context).inflate(_headerLayout!!, parent, false))

            _footerLayout -> VHKRecyclerMultiVB(LayoutInflater.from(parent.context).inflate(_footerLayout!!, parent, false))

            else -> {
                val binding = DataBindingUtil.inflate<VB>(
                    LayoutInflater.from(parent.context),
                    viewType,
                    parent, false
                )
                VHKRecyclerMultiVB(binding)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (_headerLayout != null && _footerLayout != null) {
            if (_datas.isEmpty()) 2 else _datas.size + 2
        } else if (_headerLayout == null && _footerLayout != null) {
            if (_datas.isEmpty()) 1 else _datas.size + 1
        } else if (_headerLayout != null && _footerLayout == null) {
            if (_datas.isEmpty()) 1 else _datas.size + 1
        } else {
            if (_datas.isEmpty()) 0 else _datas.size
        }
    }

    override fun onBindViewHolder(holder: VHKRecyclerMultiVB<VB>, position: Int) {
        if (_headerLayout != null) {
            if (position != 0 && position <= _datas.size) {
                holder.vb.apply {
                    setVariable(_brId, _datas[position - 1])
                    _listener?.invoke(holder, _datas[position - 1], position - 1, _selectItemPosition)
                    executePendingBindings()
                }
            }
        } else {
            if (position < _datas.size) {
                holder.vb.apply {
                    setVariable(_brId, _datas[position])
                    _listener?.invoke(holder, _datas[position], position, _selectItemPosition)
                    executePendingBindings()
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (_headerLayout != null && _footerLayout != null) {
            when (position) {
                0 -> _headerLayout!!
                itemCount - 1 -> _footerLayout!!
                else -> _defaultLayout
            }
        } else if (_headerLayout == null && _footerLayout != null) {
            if (position == itemCount - 1) _footerLayout!!
            else _defaultLayout
        } else if (_headerLayout != null && _footerLayout == null) {
            if (position == 0) _headerLayout!!
            else _defaultLayout
        } else _defaultLayout
    }


//    @SuppressLint("NotifyDataSetChanged")
//    fun onItemSelected(position: Int) {
//        _selectItemPosition = position
//        notifyDataSetChanged()
//    }
//
//    @SuppressLint("NotifyDataSetChanged")
//    fun onItemDataChanged(newItemDatas: List<DATA>) {
//        _datas = newItemDatas
//        notifyDataSetChanged()
//    }
//
//    fun onItemRangeChanged(newItemDatas: List<DATA>, positionStart: Int, itemCount: Int) {
//        _datas = newItemDatas
//        notifyItemChanged(positionStart, itemCount)
//    }
//
//    fun onItemRangeInserted(newItemDatas: List<DATA>, positionStart: Int, itemCount: Int) {
//        _datas = newItemDatas
//        notifyItemRangeInserted(positionStart, itemCount)
//    }
//
//    fun onItemRangeRemoved(newItemDatas: List<DATA>, positionStart: Int, itemCount: Int) {
//        _datas = newItemDatas
//        notifyItemRangeRemoved(positionStart, itemCount)
//    }
//
//    class MultipleViewHolder<VB : ViewDataBinding> : RecyclerView.ViewHolder {
//        var binding: VB? = null
//
//        constructor(itemView: View) : super(itemView)
//        constructor(binding: VB) : super(binding.root) {
//            this.binding = binding
//        }
//    }
}