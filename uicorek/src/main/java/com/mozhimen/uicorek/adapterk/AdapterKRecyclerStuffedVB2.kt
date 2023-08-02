package com.mozhimen.uicorek.adapterk

import android.view.View
import androidx.databinding.ViewDataBinding
import com.mozhimen.basick.utilk.kotlin.collections.joinT2list
import com.mozhimen.basick.utilk.kotlin.collections.joinT2listIgnoreNull
import com.mozhimen.uicorek.adapterk.commons.IAdapterKRecyclerStuffedVB
import com.mozhimen.uicorek.recyclerk.temps.RecyclerKItemVB
import com.mozhimen.uicorek.vhk.VHKRecyclerVB

/**
 * @ClassName AdapterKRecyclerStuffedVB2
 * @Description 带Header的RecyclerView适配器
 * @Author Kolin Zhao
 * @Date 2021/7/7 14:48
 * @Version 1.0
 */

typealias IAdapterKRecyclerStuffedVB2Listener<DATA, VB> = (holder: VHKRecyclerVB<VB>, itemData: DATA, position: Int, currentSelectPos: Int) -> Unit

class AdapterKRecyclerStuffedVB2<DATA, VB : ViewDataBinding>(
    private var _datas: List<DATA>,
    private var _defaultLayoutId: Int,
    private var _brId: Int,
    private var _headerLayoutId: Int? = null,
    private var _footerLayoutId: Int? = null,
    private var _listener: IAdapterKRecyclerStuffedVB2Listener<DATA, VB>? = null
) : AdapterKRecyclerStuffed(), IAdapterKRecyclerStuffedVB<DATA, VB> {
    private var _selectItemPosition = -1

    init {
        onItemsAdd(_datas, false)
    }

    override fun onItemRefresh(item: DATA, position: Int, notify: Boolean) {
        refreshItem(RecyclerKItemVB(item, _brId, _defaultLayoutId, _selectItemPosition, _listener), position, notify)
    }

    override fun onItemsRefresh(items: List<DATA>, notify: Boolean) {
        refreshItems(items.joinT2list { RecyclerKItemVB(it, _brId, _defaultLayoutId, _selectItemPosition, _listener) }, notify)
    }

    override fun onItemAdd(item: DATA, notify: Boolean) {
        addItem(RecyclerKItemVB(item, _brId, _defaultLayoutId, _selectItemPosition, _listener), notify)
    }

    override fun onItemAddAtPosition(item: DATA, position: Int, notify: Boolean) {
        addItemAtPosition(RecyclerKItemVB(item, _brId, _defaultLayoutId, _selectItemPosition, _listener), position, notify)
    }

    override fun onItemsAdd(items: List<DATA>, notify: Boolean) {
        addItems(items.joinT2list { RecyclerKItemVB(it, _brId, _defaultLayoutId, _selectItemPosition, _listener) }, notify)
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
        return _items.joinT2listIgnoreNull { (it as? RecyclerKItemVB<DATA, VB>?)?.data }
    }

    override fun onSelectItemPositionSet(position: Int, listener: IAdapterKRecyclerVB2Listener<DATA, VB>) {
        if (position < 0 || position >= _items.size) return
        _selectItemPosition = position
        val item = getItem(_selectItemPosition) as RecyclerKItemVB<DATA, VB>
        listener.invoke(item.vh as VHKRecyclerVB<VB>, item.data, _selectItemPosition, _selectItemPosition)
    }

    override fun onSelectItemPositionGet(): Int {
        return _selectItemPosition
    }

    override fun onHeaderViewAdd(view: View) {
        addHeaderView(view)
    }

    override fun onHeaderViewRemove(view: View) {
        removeHeaderView(view)
    }

    override fun onFooterViewAdd(view: View) {
        addFooterView(view)
    }

    override fun onFooterViewRemove(view: View) {
        removeFooterView(view)
    }

    override fun onHeaderViewSizeGet(): Int {
        return getHeaderViewSize()
    }

    override fun onFooterViewSizeGet(): Int {
        return getFooterViewSize()
    }

    override fun onNormalItemSizeGet(): Int {
        return getNormalItemSize()
    }
}