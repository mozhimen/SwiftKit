package com.mozhimen.uicorek.recyclerk

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basick.elemk.commons.IValue2Listener2
import com.mozhimen.basick.elemk.mos.MKey
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.adapterk.AdapterKRecyclerStuffed
import com.mozhimen.uicorek.databinding.RecyclerkLinearItemBinding
import com.mozhimen.uicorek.vhk.VHKRecyclerVB

/**
 * @ClassName ViewKRecyclerLinear
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/21 15:04
 * @Version 1.0
 */
typealias IRecyclerKLinearListener = IValue2Listener2<Int, MKey>//(position: Int, item: MKey) -> Unit

class RecyclerKLinear @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RecyclerView(context, attrs, defStyleAttr) {

    private var _viewKRecyclerLinearListener: IRecyclerKLinearListener? = null
    private val _adapterKRecyclerStuffed by lazy { AdapterKRecyclerStuffed() }
    private val _keys = ArrayList<MKey>()

    init {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = _adapterKRecyclerStuffed
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    }

    /**
     * 设置监听器
     * @param listener Function2<[@kotlin.ParameterName] Int, MoKKey, Unit>
     */
    fun setOnItemClickListener(listener: IRecyclerKLinearListener) {
        _viewKRecyclerLinearListener = listener
    }

    /**
     * 绑定数据
     * @param keys List<MoKKey>
     * @param listener Function2<[@kotlin.ParameterName] Int, MoKKey, Unit>
     */
    fun bindKeys(keys: List<MKey>, listener: IRecyclerKLinearListener) {
        _keys.addAll(keys)
        val keyItems = mutableListOf<RecyclerKLinearItem>()
        keys.forEach {
            keyItems.add(RecyclerKLinearItem(it, listener))
        }
        _adapterKRecyclerStuffed.clearItems()
        _adapterKRecyclerStuffed.addItems(keyItems, true)
    }

    /**
     * 清除数据
     */
    fun clearKeys() {
        _keys.clear()
        _adapterKRecyclerStuffed.clearItems()
    }

    /**
     * 增加字段
     * @param key MoKKey
     */
    fun addKey(key: MKey) {
        _keys.add(key)
        _adapterKRecyclerStuffed.addItem(RecyclerKLinearItem(key, _viewKRecyclerLinearListener), true)
    }

    /**
     * 删除字段
     * @param key MoKKey
     */
    fun removeKey(key: MKey) {
        val index = _keys.indexOf(key)
        if (index in 0 until _keys.size) removeKey(index)
    }

    /**
     * 删除字段
     * @param index Int
     */
    fun removeKey(index: Int) {
        if (index in 0 until _keys.size) {
            _keys.removeAt(index)
            _adapterKRecyclerStuffed.removeItemAt(index)
        }
    }

    private inner class RecyclerKLinearItem(
        private val _dataKKey: MKey,
        private val _listener: IRecyclerKLinearListener?
    ) : RecyclerKItem<MKey, VHKRecyclerVB<RecyclerkLinearItemBinding>>() {

        override fun onBindData(holder: VHKRecyclerVB<RecyclerkLinearItemBinding>, position: Int) {
            holder.vb.layoutkRecyclerLinearItemTxt.text = _dataKKey.name
            holder.itemView.setOnClickListener {
                _listener?.invoke(position, _dataKKey)
            }
        }

        override fun getItemLayoutRes(): Int {
            return R.layout.recyclerk_linear_item
        }

        override fun onCreateViewHolder(parent: ViewGroup): VHKRecyclerVB<RecyclerkLinearItemBinding> {
            return VHKRecyclerVB(LayoutInflater.from(parent.context).inflate(getItemLayoutRes(), parent, false))
        }
    }
}