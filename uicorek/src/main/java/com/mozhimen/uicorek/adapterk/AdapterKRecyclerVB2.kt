package com.mozhimen.uicorek.adapterk

import androidx.databinding.ViewDataBinding
import com.mozhimen.basick.utilk.kotlin.collections.joinT2list
import com.mozhimen.basick.utilk.kotlin.collections.joinT2listIgnoreNull
import com.mozhimen.uicorek.adapterk.commons.IAdapterKRecyclerVB
import com.mozhimen.uicorek.adapterk.commons.IAdapterKRecyclerVBListener
import com.mozhimen.uicorek.recyclerk.temps.RecyclerKItemVB

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

class AdapterKRecyclerVB2<DATA, VB : ViewDataBinding>(
    private val _datas: List<DATA>,
    private val _defaultLayoutId: Int,
    private val _brId: Int,
    private val _listener: IAdapterKRecyclerVBListener<DATA, VB>? = null
) : AdapterKRecycler(), IAdapterKRecyclerVB<DATA, VB> {

    private var _selectItemPosition = -1

    init {
        addDatas(_datas, true)
    }

    ///////////////////////////////////////////////////////////////////////////////////

    override fun refreshData(data: DATA, position: Int, notify: Boolean) {
        refreshItem(RecyclerKItemVB(data, _brId, _defaultLayoutId, _selectItemPosition, _listener), position, notify)
    }

    override fun refreshDatas(notify: Boolean) {
        refreshItems(notify)
    }

    override fun refreshDatas(datas: List<DATA>) {
        refreshDatas(datas,true)
    }

    override fun refreshDatas(datas: List<DATA>, notify: Boolean) {
        refreshItems(datas.joinT2list { RecyclerKItemVB(it, _brId, _defaultLayoutId, _selectItemPosition, _listener) }, notify)
    }

    override fun addData(data: DATA, notify: Boolean) {
        addItem(RecyclerKItemVB(data, _brId, _defaultLayoutId, _selectItemPosition, _listener), notify)
    }

    override fun addDataAtPosition(data: DATA, position: Int, notify: Boolean) {
        addItemAtPosition(RecyclerKItemVB(data, _brId, _defaultLayoutId, _selectItemPosition, _listener), position, notify)
    }

    override fun addDatas(datas: List<DATA>, notify: Boolean) {
        addItems(datas.joinT2list { RecyclerKItemVB(it, _brId, _defaultLayoutId, _selectItemPosition, _listener) }, notify)
    }

    override fun removeData(data: DATA, notify: Boolean) {
        _items.find { (it as? RecyclerKItemVB<DATA, VB>?)?.data == data }?.let { removeItem(it, notify) }
    }

    override fun removeDataAtPosition(position: Int, notify: Boolean): DATA? {
        return (removeDataAtPosition(position, notify) as? RecyclerKItemVB<DATA, VB>?)?.data
    }

    override fun removeDatasAll(notify: Boolean) {
        removeDatasAll(notify)
    }

    override fun getData(position: Int): DATA? {
        return (getData(position) as? RecyclerKItemVB<DATA, VB>?)?.data
    }

    override fun getDatas(): List<DATA?> {
        return _items.joinT2listIgnoreNull { (it as? RecyclerKItemVB<DATA, VB>?)?.data }
    }

    override fun onDataSelected(position: Int) {
        if (position < 0 || position >= _items.size) return
        _selectItemPosition = position
        refreshItems(true)
//        val item = getData(_selectItemPosition) as RecyclerKItemVB<DATA, VB>
//        listener.invoke(item.vh as VHKRecyclerVB<VB>, item.data, _selectItemPosition, _selectItemPosition)
    }

    override fun getCurrentSelectPosition(): Int =
        _selectItemPosition
}