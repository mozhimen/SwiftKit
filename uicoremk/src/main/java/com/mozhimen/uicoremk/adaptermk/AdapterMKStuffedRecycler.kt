package com.mozhimen.uicoremk.adaptermk

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * @ClassName HeaderRecyclerAdapterMK
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/7/7 14:48
 * @Version 1.0
 */
/**
 * 作用: 带Header的RecyclerView适配器
 * 用法: viewBinding.mainList.layoutManager = GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)
 * val adapter: RecyclerAdapterMK<User> = object : RecyclerAdapterMK<User>(viewModel.userList, R.layout.item_user, BR.item) {
 *  override fun addListener(view: View, itemData: User, position: Int) {
 *      (view.findViewById(R.id.user_pane) as LinearLayout).setOnClickListener {
 *          //逻辑
 * }}}
 * viewBinding.mainList.adapter=adapter
 */
open class AdapterMKStuffedRecycler<T>(
    private var itemDatas: List<T>,
    private var defaultLayout: Int,
    private var headerLayout: Int?,
    private var footerLayout: Int?,
    private var brId: Int
) : RecyclerView.Adapter<AdapterMKStuffedRecycler.BaseViewHolder>() {

    private val TAG = "AdapterMKStuffedRecycler>>>>>"

    class BaseViewHolder : RecyclerView.ViewHolder {
        var mBinding: ViewDataBinding? = null

        constructor(itemView: View) : super(itemView)
        constructor(binding: ViewDataBinding) : super(binding.root) {
            this.mBinding = binding
        }
    }

    //region #系统方法
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            headerLayout -> {
                BaseViewHolder(
                    LayoutInflater.from(parent.context).inflate(headerLayout!!, parent, false)
                )
            }
            footerLayout -> {
                BaseViewHolder(
                    LayoutInflater.from(parent.context).inflate(footerLayout!!, parent, false)
                )
            }
            else -> {
                val binding = DataBindingUtil.inflate<ViewDataBinding>(
                    LayoutInflater.from(parent.context),
                    viewType,
                    parent, false
                )
                BaseViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (headerLayout != null) {
            if (position != 0 && position <= itemDatas.size) {
                holder.mBinding?.apply {
                    setVariable(brId, itemDatas[position - 1])
                    addListener(root, itemDatas[position - 1], position - 1)
                    executePendingBindings()
                }
            }
        } else {
            if (position < itemDatas.size) {
                holder.mBinding?.apply {
                    setVariable(brId, itemDatas[position])
                    addListener(root, itemDatas[position], position)
                    executePendingBindings()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return if (headerLayout != null && footerLayout != null) {
            if (itemDatas.isNullOrEmpty()) 2 else itemDatas.size + 2
        } else if (headerLayout == null && footerLayout != null) {
            if (itemDatas.isNullOrEmpty()) 1 else itemDatas.size + 1
        } else if (headerLayout != null && footerLayout == null) {
            if (itemDatas.isNullOrEmpty()) 1 else itemDatas.size + 1
        } else {
            if (itemDatas.isNullOrEmpty()) 0 else itemDatas.size
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (headerLayout != null && footerLayout != null) {
            when (position) {
                0 -> {
                    headerLayout!!
                }
                itemCount - 1 -> {
                    footerLayout!!
                }
                else -> {
                    defaultLayout
                }
            }
        } else if (headerLayout == null && footerLayout != null) {
            if (position == itemCount - 1) {
                footerLayout!!
            } else {
                defaultLayout
            }
        } else if (headerLayout != null && footerLayout == null) {
            if (position == 0) {
                headerLayout!!
            } else {
                defaultLayout
            }
        } else {
            defaultLayout
        }
    }
    //endregion

    //region #自定义方法
    open fun addListener(view: View, itemData: T, position: Int) {}

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