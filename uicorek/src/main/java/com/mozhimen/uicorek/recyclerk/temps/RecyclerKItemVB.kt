package com.mozhimen.uicorek.recyclerk.temps

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.mozhimen.uicorek.recyclerk.bases.BaseRecyclerKItem
import com.mozhimen.uicorek.vhk.VHKRecyclerVB


/**
 * @ClassName RecyclerKItemVB
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/4/4 14:32
 * @Version 1.0
 */
typealias IRecyclerKItemVBListener<DATA, VB> = (holder: VHKRecyclerVB<VB>, data: DATA, position: Int, currentSelectPos: Int) -> Unit

class RecyclerKItemVB<DATA, VB : ViewDataBinding>(
    val data: DATA,
    private val _brId: Int,
    private val _layoutId: Int,
    private val _selectItemPos: Int,
    private val _onBind: IRecyclerKItemVBListener<DATA, VB>? = null
) : BaseRecyclerKItem<VHKRecyclerVB<VB>>() {
    override fun onBindItem(holder: VHKRecyclerVB<VB>, position: Int) {
        super.onBindItem(holder, position)
        holder.VB.setVariable(_brId, data)
        _onBind?.invoke(holder, data, position, _selectItemPos)
        holder.VB.executePendingBindings()
    }

    override fun onCreateViewHolder(parent: ViewGroup): VHKRecyclerVB<VB> {
        return VHKRecyclerVB(LayoutInflater.from(parent.context).inflate(getItemLayoutId(), parent, false))
    }

    override fun getItemLayoutId(): Int {
        return _layoutId
    }
}