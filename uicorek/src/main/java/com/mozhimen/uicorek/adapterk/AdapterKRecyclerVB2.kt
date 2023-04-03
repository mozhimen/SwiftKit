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
import com.mozhimen.uicorek.adapterk.AdapterKRecycler
import com.mozhimen.uicorek.adapterk.commons.IAdapterKRecyclerVB
import com.mozhimen.uicorek.vhk.VHKRecyclerVB
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
typealias IAdapterKRecyclerVBListener<BEAN, VB> = (holder: VHKRecyclerVB<VB>, itemData: BEAN, position: Int, currentSelectPos: Int) -> Unit

open class AdapterKRecyclerVB<BEAN, VB : ViewDataBinding>(
    private var _itemDatas: List<BEAN>,
    private val _defaultLayout: Int,
    private val _brId: Int,
    private val _listener: IAdapterKRecyclerVBListener<BEAN, VB>? = null /* = (com.mozhimen.uicorek.recyclerk.datak.BindKViewHolder<androidx.databinding.ViewDataBinding>, T, kotlin.Int) -> kotlin.Unit */
) : AdapterKRecycler(), IAdapterKRecyclerVB/*RecyclerView.Adapter<VHKRecyclerVB<VB>>()*/ {

    private var _selectItemPosition = -1

    fun getSelectItemPosition(): Int = _selectItemPosition

    override fun setCurrentPosition(position: Int) {
        _selectItemPosition = position
    }

    override fun getCurrentPosition(): Int = _selectItemPosition

//    @SuppressLint("NotifyDataSetChanged")
//    fun onItemSelected(position: Int) {
//        _selectItemPosition = position
//        notifyDataSetChanged()
//    }
//
//    @SuppressLint("NotifyDataSetChanged")
//    fun onItemDataChanged(newItemDatas: List<BEAN>) {
//        _itemDatas = newItemDatas
//        notifyDataSetChanged()
//    }
//
//    fun onItemRangeChanged(newItemDatas: List<BEAN>, positionStart: Int, itemCount: Int) {
//        _itemDatas = newItemDatas
//        notifyItemChanged(positionStart, itemCount)
//    }
//
//    fun onItemRangeInserted(newItemDatas: List<BEAN>, positionStart: Int, itemCount: Int) {
//        _itemDatas = newItemDatas
//        notifyItemRangeInserted(positionStart, itemCount)
//    }
//
//    fun onItemRangeRemoved(newItemDatas: List<BEAN>, positionStart: Int, itemCount: Int) {
//        _itemDatas = newItemDatas
//        notifyItemRangeRemoved(positionStart, itemCount)
//    }

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHKRecyclerVB<VB> {
//        val binding = DataBindingUtil.inflate<VB>(LayoutInflater.from(parent.context), viewType, parent, false)
//        return VHKRecyclerVB(binding.root, binding)
//    }
//
//    override fun getItemCount() = if (_itemDatas.isEmpty()) 0 else _itemDatas.size
//
//    override fun onBindViewHolder(holder: VHKRecyclerVB<VB>, position: Int) {
//        holder.vb.setVariable(_brId, _itemDatas[position])
//        _listener?.invoke(holder, _itemDatas[position], position, _selectItemPosition)
//        holder.vb.executePendingBindings()
//    }
//
//    override fun getItemViewType(position: Int) = _defaultLayout

}