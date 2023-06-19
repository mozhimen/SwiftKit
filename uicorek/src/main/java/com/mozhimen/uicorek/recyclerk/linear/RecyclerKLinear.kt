package com.mozhimen.uicorek.recyclerk.linear

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basick.elemk.commons.IValue2T1T2Listener
import com.mozhimen.basick.elemk.mos.MKey
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.adapterk.AdapterKRecycler
import com.mozhimen.uicorek.databinding.RecyclerkLinearItemBinding
import com.mozhimen.uicorek.recyclerk.bases.BaseRecyclerKItem
import com.mozhimen.uicorek.recyclerk.linear.commons.IRecyclerKLinear
import com.mozhimen.uicorek.vhk.VHKRecyclerVB

/**
 * @ClassName ViewKRecyclerLinear
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/21 15:04
 * @Version 1.0
 */
typealias IRecyclerKLinearListener = IValue2T1T2Listener<Int, MKey>//(position: Int, item: MKey) -> Unit

class RecyclerKLinear @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RecyclerView(context, attrs, defStyleAttr), IRecyclerKLinear {

    private var _recyclerLinearListener: IRecyclerKLinearListener? = null
    private val _adapterKRecycler by lazy { AdapterKRecycler() }
    private val _keys = ArrayList<MKey>()

    init {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = _adapterKRecycler
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    }


    override fun setOnItemClickListener(listener: IRecyclerKLinearListener) {
        _recyclerLinearListener = listener
    }

    override fun bindKeys(keys: List<MKey>, listener: IRecyclerKLinearListener) {
        _keys.addAll(keys)
        val items = mutableListOf<RecyclerKLinearItem>()
        keys.forEach {
            items.add(RecyclerKLinearItem(it, listener))
        }
        _adapterKRecycler.removeItemsAll(true)
        _adapterKRecycler.addItems(items.toList(), true)
    }

    override fun clearKeys() {
        _keys.clear()
        _adapterKRecycler.removeItemsAll(true)
    }

    override fun addKey(key: MKey) {
        _keys.add(key)
        _adapterKRecycler.addItem(RecyclerKLinearItem(key, _recyclerLinearListener), true)
    }

    override fun removeKey(key: MKey) {
        val index = _keys.indexOf(key)
        if (index in 0 until _keys.size) removeKey(index)
    }

    override fun removeKey(index: Int) {
        if (index in 0 until _keys.size) {
            _keys.removeAt(index)
            _adapterKRecycler.removeItemAtPosition(index, true)
        }
    }

    private inner class RecyclerKLinearItem(
        private val _data: MKey,
        private val _listener: IRecyclerKLinearListener?
    ) : BaseRecyclerKItem<VHKRecyclerVB<RecyclerkLinearItemBinding>>() {

        override fun onBindItem(holder: VHKRecyclerVB<RecyclerkLinearItemBinding>, position: Int) {
            super.onBindItem(holder, position)
            holder.vb.layoutkRecyclerLinearItemTxt.text = _data.name
            holder.itemView.setOnClickListener {
                _listener?.invoke(position, _data)
            }
        }

        override fun getItemLayoutId(): Int {
            return R.layout.recyclerk_linear_item
        }

        override fun onCreateViewHolder(parent: ViewGroup): VHKRecyclerVB<RecyclerkLinearItemBinding> {
            return VHKRecyclerVB(LayoutInflater.from(parent.context).inflate(getItemLayoutId(), parent, false))
        }
    }
}