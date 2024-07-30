package com.mozhimen.basick.elemk.kotlin.properties

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @ClassName PropertyDelegate
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Version 1.0
 */
open class VarProperty<T>(default: T) : ReadWriteProperty<Any?, T> {
    private var _field = default

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        _field = value
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return _field
    }
}