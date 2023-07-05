package com.mozhimen.basick.elemk.delegate

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @ClassName BaseDataDelegate
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/13 15:07
 * @Version 1.0
 */

open class VarDeleg_SetFun_R_VaryNullable<T>(default: T, private val _onSet: IVarDelegate_SetFun_R_Invoke<T>) : ReadWriteProperty<Any?, T> {
    @Volatile
    private var _field = default
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (_field == value) return
        if (_onSet.invoke(_field, value)) {
            _field = value
        }
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return _field
    }
}