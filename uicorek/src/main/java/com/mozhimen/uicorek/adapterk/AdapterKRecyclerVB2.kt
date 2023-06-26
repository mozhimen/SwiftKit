package com.mozhimen.uicorek.adapterk

import androidx.databinding.ViewDataBinding
import com.mozhimen.basick.utilk.kotlin.collections.joinElement2List
import com.mozhimen.basick.utilk.kotlin.collections.joinElement2ListIgnoreNull
import com.mozhimen.uicorek.adapterk.commons.IAdapterKRecyclerVB
import com.mozhimen.uicorek.recyclerk.temps.RecyclerKItemVB
import com.mozhimen.uicorek.vhk.VHKRecyclerVB

/**
 * @ClassName AdapterKRecyclerVB2
 * @Description 通用RecyclerView适配器
 *  *注意:
 * 在使用Fragment切换,挂起与恢复时, 要使recyclerView.adapter置null
 * 不然持有全局本类, 会引起内存的泄漏
 * @Author Kolin Zhao
 * @Date 2021/6/4 20:07
 * @Version 1.0
 */

typealias IAdapterKRecyclerVB2Listener<DATA, VB> = (holder: VHKRecyclerVB<VB>, data: DATA, position: Int, selectItemPos: Int) -> Unit

class AdapterKRecyclerVB2<DATA, VB : ViewDataBinding>(
    private val _datas: List<DATA>,
    private val _defaultLayoutId: Int,
    private val _brId: Int,
    private val _listener: IAdapterKRecyclerVB2Listener<DATA, VB>? = null
) : AdapterKRecycler(), IAdapterKRecyclerVB<DATA, VB> {

    private var _selectItemPosition = -1

    init {
        onItemsAdd(_datas, false)
    }

    override fun onItemRefresh(item: DATA, position: Int, notify: Boolean) {
        refreshItem(RecyclerKItemVB(item, _brId, _defaultLayoutId, _selectItemPosition, _listener), position, notify)
    }

    override fun onItemsRefresh(items: List<DATA>, notify: Boolean) {
        refreshItems(items.joinElement2List { RecyclerKItemVB(it, _brId, _defaultLayoutId, _selectItemPosition, _listener) }, notify)
    }

    override fun onItemAdd(item: DATA, notify: Boolean) {
        addItem(RecyclerKItemVB(item, _brId, _defaultLayoutId, _selectItemPosition, _listener), notify)
    }

    override fun onItemAddAtPosition(item: DATA, position: Int, notify: Boolean) {
        addItemAtPosition(RecyclerKItemVB(item, _brId, _defaultLayoutId, _selectItemPosition, _listener), position, notify)
    }

    override fun onItemsAdd(items: List<DATA>, notify: Boolean) {
        addItems(items.joinElement2List { RecyclerKItemVB(it, _brId, _defaultLayoutId, _selectItemPosition, _listener) }, notify)
    }

    override fun onItemRemove(item: DATA, notify: Boolean) {
        _items.find { (it as? RecyclerKItemVB<DATA, VB>?)?.data == item }?.let { removeItem(it, notify) }
    }

    override fun onItemRemoveAtPosition(position: Int, notify: Boolean): DATA? {
        return (removeItemAtPosition(position, notify) as? RecyclerKItemVB<DATA, VB>?)?.data
    }

    override fun onItemsRemoveAll(notify: Boolean) {
        removeItemsAll(notify)
    }

    override fun onItemGet(position: Int): DATA? {
        return (getItem(position) as? RecyclerKItemVB<DATA, VB>?)?.data
    }

    override fun onItemsGet(): List<DATA?> {
        return _items.joinElement2ListIgnoreNull { (it as? RecyclerKItemVB<DATA, VB>?)?.data }
    }

    override fun onSelectItemPositionSet(position: Int, listener: IAdapterKRecyclerVB2Listener<DATA, VB>) {
        if (position < 0 || position >= _items.size) return
        _selectItemPosition = position
        val item = getItem(_selectItemPosition) as RecyclerKItemVB<DATA, VB>
        listener.invoke(item.vh as VHKRecyclerVB<VB>, item.data, _selectItemPosition, _selectItemPosition)
    }

    override fun onSelectItemPositionGet(): Int =
        _selectItemPosition
}