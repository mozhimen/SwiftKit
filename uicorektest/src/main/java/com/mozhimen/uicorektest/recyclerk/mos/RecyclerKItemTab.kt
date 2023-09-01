package com.mozhimen.uicorektest.recyclerk.mos

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mozhimen.uicorek.vhk.VHKRecyclerMultiVB
import com.mozhimen.uicorek.recyclerk.bases.BaseRecyclerKItem
import com.mozhimen.uicorek.vhk.VHKRecyclerVB
import com.mozhimen.uicorektest.R
import com.mozhimen.uicorektest.databinding.ItemRecyclerkTabBinding

/**
 * @ClassName DataKItemTab
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/9/2 16:03
 * @Version 1.0
 */
class RecyclerKItemTab : BaseRecyclerKItem<VHKRecyclerVB<ItemRecyclerkTabBinding>>() {

    override fun onBindItem(holder: VHKRecyclerVB<ItemRecyclerkTabBinding>, position: Int) {
        super.onBindItem(holder, position)
        //holder.binding.setVariable(BR.XXX, XXX)
        holder.vb.datakItemTabImg.setImageResource(R.mipmap.datak_item_tab)
    }

    override fun onCreateViewHolder(parent: ViewGroup): VHKRecyclerVB<ItemRecyclerkTabBinding> {
        return VHKRecyclerVB(LayoutInflater.from(parent.context).inflate(getItemLayoutId(), parent, false))
    }

    override fun getItemLayoutId() =
        R.layout.item_recyclerk_tab

    override fun getItemSpanSize(): Int {
        return 2
    }
}