package com.mozhimen.basick.elemk.delegate.bases

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @ClassName BaseDataDelegate
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/13 15:07
 * @Version 1.0
 */
open class BaseDataDelegate<T>(default: T) : ReadWriteProperty<Any?, T> {
    private var _field = default
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (_field == value || value == null) return
        _field = value
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return _field
    }
}