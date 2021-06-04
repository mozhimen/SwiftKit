package com.mozhimen.swiftmk.helper.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * @ClassName RecyclerAdapterMK
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/6/4 20:07
 * @Version 1.0
 */
/**
 * 作用: 通用RecyclerView适配器()
 */
class RecyclerAdapterMK<T>(
    private var itemDatas: List<T>,
    private val defaultLayout: Int,
    private val brId: Int
) : RecyclerView.Adapter<RecyclerAdapterMK.BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            viewType,
            parent,
            false
        )
        val position = BaseViewHolder(binding).adapterPosition
        addListener(binding.root, itemDatas[position], position)
        return BaseViewHolder(binding)
    }

    override fun getItemCount() = if (itemDatas.isNullOrEmpty()) 0 else itemDatas.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.binding.setVariable(brId, itemDatas[position])
        holder.binding.executePendingBindings()
    }

    override fun getItemViewType(position: Int) = getItemLayout(itemDatas[position])

    //region #自定义方法
    class BaseViewHolder(var binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun getItemLayout(itemData: T) = defaultLayout

    fun addListener(root: View, itemData: T, position: Int) {}

    fun onItemDataChanged(newItemDatas: List<T>) {
        itemDatas = newItemDatas
        notifyDataSetChanged()
    }

    fun onItemRangeChanged(newItemDatas: List<T>, positionStart: Int, itemCount: Int) {
        itemDatas = newItemDatas
        notifyItemChanged(positionStart, itemCount)
    }

    fun onItemRangeInserted(newItemDatas: List<T>, positionStart: Int, itemCount: Int) {
        itemDatas = newItemDatas
        notifyItemRangeInserted(positionStart, itemCount)
    }

    fun onItemRangeRemoved(newItemDatas: List<T>, positionStart: Int, itemCount: Int) {
        itemDatas = newItemDatas
        notifyItemRangeRemoved(positionStart, itemCount)
    }
    //endregion
}