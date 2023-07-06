package com.mozhimen.basick.elemk.delegate

import com.mozhimen.basick.elemk.commons.IAA_BListener
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
open class VarDeleg_Set<T>(
    default: T,
    private val _isOpenCheckEquals: Boolean = true,
    private val _isOpenCheckNull: Boolean = true,
    private val _onSetField: IAA_BListener<T, Boolean>
) : ReadWriteProperty<Any?, T> {
    @Volatile
    private var _field = default
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (_isOpenCheckEquals && _field == value) return
        if (_isOpenCheckNull && value == null) return
        if (_onSetField.invoke(_field, value)) {
            _field = value
        }
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return _field
    }
}