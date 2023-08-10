package com.mozhimen.uicorek.layoutk

import android.content.Context
import android.content.res.Resources.Theme
import android.util.AttributeSet
import android.widget.ArrayAdapter
import com.mozhimen.basick.elemk.kotlin.cons.CSuppress
import com.mozhimen.uicorek.R

/**
 * @ClassName AdapterKSpinner
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/6 15:56
 * @Version 1.0
 */
class LayoutKSpinner<T> @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = android.R.attr.spinnerStyle, popupTheme: Theme? = null
) : androidx.appcompat.widget.AppCompatSpinner(context, attrs, defStyleAttr, MODE_DROPDOWN, popupTheme) {

    private var _spinnerItemLayout = R.layout.adapterk_spinner_item
    private var _items = arrayListOf<T>()
    private var _arrayAdapter: ArrayAdapter<T>? = null

    init {
        dropDownWidth = LayoutParams.MATCH_PARENT
    }

    fun setLayout(layoutId: Int): LayoutKSpinner<T> {
        _arrayAdapter = ArrayAdapter(context, layoutId.also { _spinnerItemLayout = it }, _items).also { this.adapter = it }
        return this
    }

    fun setEntries(list: List<T>): LayoutKSpinner<T> {
        _items.clear()
        _items.addAll(list)
        if (_arrayAdapter != null) {
            _arrayAdapter!!.apply {
                clear()
                addAll(_items)
                notifyDataSetChanged()
            }
        } else {
            _arrayAdapter = ArrayAdapter(context, _spinnerItemLayout, _items).also { this.adapter = it }
        }
        return this
    }

    fun setSelectItem(obj: T) {
        setSelectItem(_items.indexOf(obj))
    }

    fun setSelectItem(position: Int) {
        if (position in 0 until _items.size) {
            this.setSelection(position)
        }
    }

    @Suppress(CSuppress.UNCHECKED_CAST)
    fun getSelectItem(): T? {
        return if (_items.isNotEmpty()) this.selectedItem as? T? else null
    }
}