package com.mozhimen.uicorek.layoutk

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatCheckBox
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.mozhimen.basick.basek.BaseKLayoutLinear
import com.mozhimen.basick.datak.DataKKey
import com.mozhimen.uicorek.R

typealias ILayoutKChipGroupListener = (DataKKey) -> Unit

/**
 * @ClassName LayoutKChipGroup
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/20 16:33
 * @Version 1.0
 */
class LayoutKChipGroup @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) :
    BaseKLayoutLinear(context, attrs, defStyleAttr, defStyleRes) {

    private val _keys = ArrayList<DataKKey>()
    private lateinit var _chipGroup: ChipGroup

    init {
        initView()
    }

    fun bindData(keys: ArrayList<DataKKey>) {
        if (keys.isEmpty()) return
        _keys.clear()
        _keys.addAll(keys)

        for (index in 0 until _keys.size) {
            var chipItem: Chip
            val childCount = _chipGroup.childCount
            if (index < childCount) {
                chipItem = _chipGroup.getChildAt(index) as Chip
            } else {
                chipItem = createChipItem(index)
                _chipGroup.addView(chipItem)
            }
            chipItem.text = _keys[index].key
        }
    }

    fun clearData() {
        _keys.clear()
        _chipGroup.removeAllViews()
    }

    fun setOnCheckedListener(listener: ILayoutKChipGroupListener) {
        _chipGroup.setOnCheckedChangeListener { chipGroup, checkedId ->
            for (index in 0 until chipGroup.childCount) {
                val childAt = _chipGroup.getChildAt(index) as AppCompatCheckBox
                if (childAt.id == checkedId && childAt.isChecked) {
                    //chipItem 具有状态，被选中后，再次点击会被切换到未选中态，此时回调的checkedId=-1
                    //我们为了让 一个chipItem能够重复多次点击，所以选中后，主动清除chip_group的选择标记
                    listener(_keys[index])
                    _chipGroup.clearCheck()
                    break
                }
            }
        }
    }

    override fun initView() {
        val view = LayoutInflater.from(context).inflate(R.layout.layoutk_chip_group, this, true)
        _chipGroup = view.findViewById(R.id.layoutk_chip_group)
        orientation = VERTICAL
    }

    private fun createChipItem(id: Int): Chip {
        val chipItem = LayoutInflater.from(context).inflate(R.layout.layoutk_chip_group_item, _chipGroup, false) as Chip
        chipItem.isCheckable = true
        chipItem.isChecked = false
        chipItem.id = id
        return chipItem
    }
}