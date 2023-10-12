package com.mozhimen.uicorek.recyclerk.linear

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basick.elemk.commons.IAB_Listener
import com.mozhimen.basick.elemk.mos.MKey
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.adapterk.item.AdapterKItemRecycler
import com.mozhimen.uicorek.databinding.RecyclerkLinearItemBinding
import com.mozhimen.uicorek.recyclerk.item.RecyclerKItem
import com.mozhimen.uicorek.recyclerk.linear.commons.IRecyclerKLinear
import com.mozhimen.uicorek.vhk.VHKRecyclerVB

/**
 * @ClassName ViewKRecyclerLinear
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/21 15:04
 * @Version 1.0
 */
typealias IRecyclerKLinearListener = IAB_Listener<Int, MKey>//(position: Int, item: MKey) -> Unit

class RecyclerKLinear @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RecyclerView(context, attrs, defStyleAttr), IRecyclerKLinear {

    private var _recyclerLinearListener: IRecyclerKLinearListener? = null
    private val _adapterKItemRecycler by lazy { AdapterKItemRecycler() }
    private val _keys = ArrayList<MKey>()

    init {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = _adapterKItemRecycler
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    }


    override fun setOnItemClickListener(listener: IRecyclerKLinearListener) {
        _recyclerLinearListener = listener
    }

    override fun bindKeys(keys: List<MKey>, listener: IRecyclerKLinearListener) {
        _keys.addAll(keys)
        val items = mutableListOf<RecyclerKLinearItem>()
        for (key in keys) items.add(RecyclerKLinearItem(key, listener))
        _adapterKItemRecycler.removeItemsAll(true)
        _adapterKItemRecycler.addItems(items.toList(), true)
    }

    override fun clearKeys() {
        _keys.clear()
        _adapterKItemRecycler.removeItemsAll(true)
    }

    override fun addKey(key: MKey) {
        _keys.add(key)
        _adapterKItemRecycler.addItem(RecyclerKLinearItem(key, _recyclerLinearListener), true)
    }

    override fun removeKey(key: MKey) {
        val index = _keys.indexOf(key)
        if (index in 0 until _keys.size) removeKey(index)
    }

    override fun removeKey(index: Int) {
        if (index in 0 until _keys.size) {
            _keys.removeAt(index)
            _adapterKItemRecycler.removeItemAtPosition(index, true)
        }
    }

    private inner class RecyclerKLinearItem(
        private val _data: MKey,
        private val _listener: IRecyclerKLinearListener?
    ) : RecyclerKItem<VHKRecyclerVB<RecyclerkLinearItemBinding>>() {

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