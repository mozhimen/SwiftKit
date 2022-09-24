package com.mozhimen.uicorektest.datak.mos

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mozhimen.uicorek.bindk.BindKViewHolder
import com.mozhimen.uicorek.datak.commons.DataKItem
import com.mozhimen.uicorektest.R
import com.mozhimen.uicorektest.databinding.ItemDatakTabBinding

/**
 * @ClassName DataKItemTab
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/9/2 16:03
 * @Version 1.0
 */
class DataKItemTab : DataKItem<Any, BindKViewHolder<ItemDatakTabBinding>>() {

    override fun onBindData(holder: BindKViewHolder<ItemDatakTabBinding>, position: Int) {
        //holder.binding.setVariable(BR.XXX, XXX)
        holder.binding.datakItemTabImg.setImageResource(R.mipmap.datak_item_tab)
    }

    override fun onCreateViewHolder(parent: ViewGroup): BindKViewHolder<ItemDatakTabBinding> {
        return BindKViewHolder(LayoutInflater.from(parent.context).inflate(getItemLayoutRes(), parent, false))
    }

    override fun getItemLayoutRes() = R.layout.item_datak_tab

}