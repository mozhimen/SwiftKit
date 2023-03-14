package com.mozhimen.basick.elemk.delegate.bases

import com.mozhimen.basick.utilk.datatype.UtilKDataType
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @ClassName BaseDataDelegate
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/13 15:07
 * @Version 1.0
 */
/**
 * true 则赋值, 否则不赋值
 */
typealias IVarInvokeListener<T> = (field: T, value: T) -> Boolean

open class BaseVarInvokeDelegate<T>(default: T, private val onSet: IVarInvokeListener<T>) : ReadWriteProperty<Any?, T> {
    private var _field = default
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (_field == value) return
        if (onSet.invoke(_field, value)) {
            _field = value
        }
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return _field
    }
}