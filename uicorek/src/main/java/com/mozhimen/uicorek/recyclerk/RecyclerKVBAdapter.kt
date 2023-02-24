package com.mozhimen.uicorek.recyclerk

import android.annotation.SuppressLint
import android.view.LayoutInflater
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
 * @ClassName RecyclerAdapterK
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/6/4 20:07
 * @Version 1.0
 */
/**
 * 作用: 通用RecyclerView适配器
 * 用法: viewBinding.mainList.layoutManager = GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)
 * val adapter: RecyclerAdapterK<User> = object : RecyclerAdapterK<User>(viewModel.userList, R.layout.item_user, BR.item) {
 *  override fun addListener(view: View, itemData: User, position: Int) {
 *      (view.findViewById(R.id.user_pane) as LinearLayout).setOnClickListener {
 *          //逻辑
 * }}}
 * viewBinding.mainList.adapter=adapter
 * 注意:
 * 在使用Fragment切换,挂起与恢复时, 要使recyclerView.adapter置null
 * 不然持有全局本类, 会引起内存的泄漏
 */
typealias IRecyclerKVBAdapterListener<BEAN, VB> = (holder: RecyclerKVBViewHolder<VB>, item: BEAN, position: Int, currentSelectPos: Int) -> Unit

open class RecyclerKVBAdapter<BEAN, VB : ViewDataBinding>(
    private var _itemDatas: List<BEAN>,
    private val _defaultLayout: Int,
    private val _brId: Int,
    private val _listener: IRecyclerKVBAdapterListener<BEAN, VB>? = null /* = (com.mozhimen.uicorek.recyclerk.datak.BindKViewHolder<androidx.databinding.ViewDataBinding>, T, kotlin.Int) -> kotlin.Unit */
) : RecyclerView.Adapter<RecyclerKVBViewHolder<VB>>(), IDefaultLifecycleObserver {

    private var _selectItemPosition = 0
    private lateinit var _vb: VB

    @SuppressLint("NotifyDataSetChanged")
    fun onItemSelected(position: Int) {
        _selectItemPosition = position
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun onItemDataChanged(newItemDatas: List<BEAN>) {
        _itemDatas = newItemDatas
        notifyDataSetChanged()
    }

    fun onItemRangeChanged(newItemDatas: List<BEAN>, positionStart: Int, itemCount: Int) {
        _itemDatas = newItemDatas
        notifyItemChanged(positionStart, itemCount)
    }

    fun onItemRangeInserted(newItemDatas: List<BEAN>, positionStart: Int, itemCount: Int) {
        _itemDatas = newItemDatas
        notifyItemRangeInserted(positionStart, itemCount)
    }

    fun onItemRangeRemoved(newItemDatas: List<BEAN>, positionStart: Int, itemCount: Int) {
        _itemDatas = newItemDatas
        notifyItemRangeRemoved(positionStart, itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerKVBViewHolder<VB> {
        val binding = DataBindingUtil.inflate<VB>(
            LayoutInflater.from(parent.context),
            viewType,
            parent,
            false
        )
        return RecyclerKVBViewHolder(binding.root, binding)
    }

    override fun getItemCount() = if (_itemDatas.isEmpty()) 0 else _itemDatas.size

    override fun onBindViewHolder(holder: RecyclerKVBViewHolder<VB>, position: Int) {
        holder.vb.setVariable(_brId, _itemDatas[position]).also { _vb = holder.vb }
        _listener?.invoke(holder, _itemDatas[position], position, _selectItemPosition)
        holder.vb.executePendingBindings()
    }

    override fun bindLifecycle(owner: LifecycleOwner) {
        owner.lifecycleScope.launch(Dispatchers.Main) {
            owner.lifecycle.removeObserver(this@RecyclerKVBAdapter)
            owner.lifecycle.addObserver(this@RecyclerKVBAdapter)
        }
    }

    override fun onPause(owner: LifecycleOwner) {
        if (this::_vb.isInitialized) _vb.unbind()
        owner.lifecycle.removeObserver(this)
    }

    override fun getItemViewType(position: Int) = _defaultLayout
}