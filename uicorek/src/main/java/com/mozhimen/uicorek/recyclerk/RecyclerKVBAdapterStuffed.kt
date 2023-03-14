package com.mozhimen.uicorek.recyclerk

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basick.elemk.lifecycle.commons.IDefaultLifecycleObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @ClassName HeaderRecyclerAdapterK
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/7/7 14:48
 * @Version 1.0
 */
/**
 * 作用: 带Header的RecyclerView适配器
 * 用法: viewBinding.mainList.layoutManager = GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)
 * val adapter: RecyclerAdapterK<User> = object : RecyclerAdapterK<User>(viewModel.userList, R.layout.item_user, BR.item) {
 *  override fun addListener(view: View, itemData: User, position: Int) {
 *      (view.findViewById(R.id.user_pane) as LinearLayout).setOnClickListener {
 *          //逻辑
 * }}}
 * viewBinding.mainList.adapter=adapter
 */
typealias IRecyclerKVBAdapterStuffedListener<BEAN, VB> = (holder: RecyclerKVBAdapterStuffed.MultipleViewHolder<VB>, itemData: BEAN, position: Int, currentSelectPos: Int) -> Unit

open class RecyclerKVBAdapterStuffed<DATA, VB : ViewDataBinding>(
    private var _itemDatas: List<DATA>,
    private var _defaultLayout: Int,
    private var _headerLayout: Int?,
    private var _footerLayout: Int?,
    private var _brId: Int,
    private var _listener: IRecyclerKVBAdapterStuffedListener<DATA, VB>? = null
) : RecyclerView.Adapter<RecyclerKVBAdapterStuffed.MultipleViewHolder<VB>>(), IDefaultLifecycleObserver {

    private val TAG = "RecyclerKVBAdapterStuffed>>>>>"

    private var _selectItemPosition = 0
    private lateinit var _vb: VB

    @SuppressLint("NotifyDataSetChanged")
    fun onItemSelected(position: Int) {
        _selectItemPosition = position
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun onItemDataChanged(newItemDatas: List<DATA>) {
        _itemDatas = newItemDatas
        notifyDataSetChanged()
    }

    fun onItemRangeChanged(newItemDatas: List<DATA>, positionStart: Int, itemCount: Int) {
        _itemDatas = newItemDatas
        notifyItemChanged(positionStart, itemCount)
    }

    fun onItemRangeInserted(newItemDatas: List<DATA>, positionStart: Int, itemCount: Int) {
        _itemDatas = newItemDatas
        notifyItemRangeInserted(positionStart, itemCount)
    }

    fun onItemRangeRemoved(newItemDatas: List<DATA>, positionStart: Int, itemCount: Int) {
        _itemDatas = newItemDatas
        notifyItemRangeRemoved(positionStart, itemCount)
    }

    class MultipleViewHolder<VB : ViewDataBinding> : RecyclerView.ViewHolder {
        var binding: VB? = null

        constructor(itemView: View) : super(itemView)
        constructor(binding: VB) : super(binding.root) {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultipleViewHolder<VB> {
        return when (viewType) {
            _headerLayout -> {
                MultipleViewHolder(
                    LayoutInflater.from(parent.context).inflate(_headerLayout!!, parent, false)
                )
            }
            _footerLayout -> {
                MultipleViewHolder(
                    LayoutInflater.from(parent.context).inflate(_footerLayout!!, parent, false)
                )
            }
            else -> {
                val binding = DataBindingUtil.inflate<VB>(
                    LayoutInflater.from(parent.context),
                    viewType,
                    parent, false
                )
                MultipleViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (_headerLayout != null && _footerLayout != null) {
            if (_itemDatas.isEmpty()) 2 else _itemDatas.size + 2
        } else if (_headerLayout == null && _footerLayout != null) {
            if (_itemDatas.isEmpty()) 1 else _itemDatas.size + 1
        } else if (_headerLayout != null && _footerLayout == null) {
            if (_itemDatas.isEmpty()) 1 else _itemDatas.size + 1
        } else {
            if (_itemDatas.isEmpty()) 0 else _itemDatas.size
        }
    }

    override fun onBindViewHolder(holder: MultipleViewHolder<VB>, position: Int) {
        if (_headerLayout != null) {
            if (position != 0 && position <= _itemDatas.size) {
                holder.binding?.apply {
                    setVariable(_brId, _itemDatas[position - 1])
                    _listener?.invoke(holder, _itemDatas[position - 1], position - 1, _selectItemPosition)
                    executePendingBindings()
                }
            }
        } else {
            if (position < _itemDatas.size) {
                holder.binding?.apply {
                    setVariable(_brId, _itemDatas[position])
                    _listener?.invoke(holder, _itemDatas[position], position, _selectItemPosition)
                    executePendingBindings()
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (_headerLayout != null && _footerLayout != null) {
            when (position) {
                0 -> {
                    _headerLayout!!
                }
                itemCount - 1 -> {
                    _footerLayout!!
                }
                else -> {
                    _defaultLayout
                }
            }
        } else if (_headerLayout == null && _footerLayout != null) {
            if (position == itemCount - 1) {
                _footerLayout!!
            } else {
                _defaultLayout
            }
        } else if (_headerLayout != null && _footerLayout == null) {
            if (position == 0) {
                _headerLayout!!
            } else {
                _defaultLayout
            }
        } else {
            _defaultLayout
        }
    }

    override fun bindLifecycle(owner: LifecycleOwner) {
        owner.lifecycleScope.launch(Dispatchers.Main) {
            owner.lifecycle.removeObserver(this@RecyclerKVBAdapterStuffed)
            owner.lifecycle.addObserver(this@RecyclerKVBAdapterStuffed)
        }
    }

    override fun onPause(owner: LifecycleOwner) {
        if (this::_vb.isInitialized) _vb.unbind()
        owner.lifecycle.removeObserver(this)
    }
}