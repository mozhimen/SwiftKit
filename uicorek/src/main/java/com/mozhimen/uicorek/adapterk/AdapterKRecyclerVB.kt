package com.mozhimen.uicorek.adapterk

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basick.elemk.lifecycle.commons.IDefaultLifecycleObserver
import com.mozhimen.uicorek.vhk.VHKRecyclerVB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @ClassName AdapterKRecyclerVB
 * @Description  通用RecyclerView适配器
 * 注意:
 * 在使用Fragment切换,挂起与恢复时, 要使recyclerView.adapter置null
 * 不然持有全局本类, 会引起内存的泄漏
 * @Author Kolin Zhao
 * @Date 2021/6/4 20:07
 * @Version 1.0
 */

typealias IAdapterKRecyclerVBListener<DATA, VB> = (holder: VHKRecyclerVB<VB>, itemData: DATA, position: Int, currentSelectPos: Int) -> Unit

open class AdapterKRecyclerVB<DATA, VB : ViewDataBinding>(
    private var _datas: List<DATA>,
    private val _defaultLayout: Int,
    private val _brId: Int,
    private val _listener: IAdapterKRecyclerVBListener<DATA, VB>? = null /* = (com.mozhimen.uicorek.recyclerk.datak.BindKViewHolder<androidx.databinding.ViewDataBinding>, T, kotlin.Int) -> kotlin.Unit */
) : RecyclerView.Adapter<VHKRecyclerVB<VB>>() {

    private var _selectItemPosition = -1

    fun getSelectItemPosition(): Int = _selectItemPosition

    fun getItems(): List<DATA> = _datas

    @SuppressLint("NotifyDataSetChanged")
    fun onItemSelected(position: Int) {
        _selectItemPosition = position
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun onItemDataChanged(newItemDatas: List<DATA>) {
        _datas = newItemDatas
        notifyDataSetChanged()
    }

    fun onItemRangeChanged(newItemDatas: List<DATA>, positionStart: Int, itemCount: Int) {
        _datas = newItemDatas
        notifyItemChanged(positionStart, itemCount)
    }

    fun onItemRangeInserted(newItemDatas: List<DATA>, positionStart: Int, itemCount: Int) {
        _datas = newItemDatas
        notifyItemRangeInserted(positionStart, itemCount)
    }

    fun onItemRangeRemoved(newItemDatas: List<DATA>, positionStart: Int, itemCount: Int) {
        _datas = newItemDatas
        notifyItemRangeRemoved(positionStart, itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHKRecyclerVB<VB> {
        val binding = DataBindingUtil.inflate<VB>(
            LayoutInflater.from(parent.context),
            viewType,
            parent,
            false
        )
        return VHKRecyclerVB(binding.root, binding)
    }

    override fun getItemCount() = if (_datas.isEmpty()) 0 else _datas.size

    override fun onBindViewHolder(holder: VHKRecyclerVB<VB>, position: Int) {
        holder.vb.setVariable(_brId, _datas[position])
        _listener?.invoke(holder, _datas[position], position, _selectItemPosition)
        holder.vb.executePendingBindings()
    }

    override fun getItemViewType(position: Int) = _defaultLayout
}