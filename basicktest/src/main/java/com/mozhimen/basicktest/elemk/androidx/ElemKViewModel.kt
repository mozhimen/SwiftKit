package com.mozhimen.basicktest.elemk.androidx

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.mozhimen.basick.elemk.androidx.lifecycle.bases.BaseViewModel
import com.mozhimen.basick.elemk.kotlin.properties.VarProperty_Set

/**
 * @ClassName ElemKDemoViewModel
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/25 14:46
 * @Version 1.0
 */
class ElemKViewModel() : BaseViewModel() {
    companion object {
        const val KEY_NUM = "KEY_NUM"
    }

    private var _savedStateHandle: SavedStateHandle? = null

    constructor(savedStateHandle: SavedStateHandle) : this() {
        _savedStateHandle = savedStateHandle
        _savedStateHandle?.get<Int>(KEY_NUM)?.let { value ->
            _num = value
        }
    }

    /////////////////////////////////////////////////////////////////////

    private var _num: Int by VarProperty_Set(0) { _, value ->
        lv_num.value = value.toString()
        _savedStateHandle?.set(KEY_NUM, value)
        true
    }

    val lv_num = MutableLiveData(_num.toString())

    fun addNum() {
        _num++
    }
}