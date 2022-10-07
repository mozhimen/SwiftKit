package com.mozhimen.uicorek.viewk

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basick.mok.MoKKey
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.bindk.BindKViewHolder
import com.mozhimen.uicorek.databinding.LayoutkRecyclerLinearItemBinding
import com.mozhimen.uicorek.datak.commons.DataKItem
import com.mozhimen.uicorek.datak.helpers.DataKAdapter

/**
 * @ClassName ViewKRecyclerLinear
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/21 15:04
 * @Version 1.0
 */
typealias IViewKRecyclerLinearListener = (pos: Int, MoKKey) -> Unit

class ViewKRecyclerLinear @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    RecyclerView(context, attrs, defStyleAttr) {

    private var _viewKRecyclerLinearListener: IViewKRecyclerLinearListener? = null
    private val _adapter = DataKAdapter(context)
    private val _keys = ArrayList<MoKKey>()

    init {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = DataKAdapter(context)
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    }

    /**
     * 设置监听器
     * @param listener Function2<[@kotlin.ParameterName] Int, MoKKey, Unit>
     */
    fun setOnItemClickListener(listener: IViewKRecyclerLinearListener) {
        _viewKRecyclerLinearListener = listener
    }

    /**
     * 绑定数据
     * @param keys List<MoKKey>
     * @param listener Function2<[@kotlin.ParameterName] Int, MoKKey, Unit>
     */
    fun bindKeys(keys: List<MoKKey>, listener: IViewKRecyclerLinearListener) {
        _keys.addAll(keys)
        val keyItems = mutableListOf<ViewKRecyclerLinearItem>()
        keys.forEach {
            keyItems.add(ViewKRecyclerLinearItem(it, listener))
        }
        _adapter.clearItems()
        _adapter.addItems(keyItems, true)
    }

    /**
     * 清除数据
     */
    fun clearKeys() {
        _keys.clear()
        _adapter.clearItems()
    }

    /**
     * 增加字段
     * @param key MoKKey
     */
    fun addKey(key: MoKKey) {
        _keys.add(key)
        _adapter.addItem(ViewKRecyclerLinearItem(key, _viewKRecyclerLinearListener), true)
    }

    /**
     * 删除字段
     * @param key MoKKey
     */
    fun removeKey(key: MoKKey) {
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
            _adapter.removeItemAt(index)
        }
    }

    private inner class ViewKRecyclerLinearItem(
        private val _dataKKey: MoKKey,
        private val _listener: IViewKRecyclerLinearListener?
    ) : DataKItem<MoKKey, BindKViewHolder<LayoutkRecyclerLinearItemBinding>>() {

        override fun onBindData(holder: BindKViewHolder<LayoutkRecyclerLinearItemBinding>, position: Int) {
            holder.binding.layoutkRecyclerLinearItemTxt.text = _dataKKey.key
            holder.itemView.setOnClickListener {
                _listener?.invoke(position, _dataKKey)
            }
        }

        override fun getItemLayoutRes(): Int {
            return R.layout.layoutk_recycler_linear_item
        }

        override fun onCreateViewHolder(parent: ViewGroup): BindKViewHolder<LayoutkRecyclerLinearItemBinding> {
            return BindKViewHolder(LayoutInflater.from(parent.context).inflate(getItemLayoutRes(), parent, false))
        }
    }
}