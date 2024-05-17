package com.mozhimen.basick.elemk.kotlinx.coroutines

import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @ClassName MutableStateProperty
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/5/16
 * @Version 1.0
 */
class VarPropertyMutableState<T>(
    private val mutableStateFlow: MutableStateFlow<T>
) : ReadWriteProperty<Any?, T> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): T = mutableStateFlow.value
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = run {
        mutableStateFlow.value = value
    }
}
