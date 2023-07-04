package com.mozhimen.basick.elemk.delegate

import com.mozhimen.basick.elemk.commons.IAA_BListener
import com.mozhimen.basick.elemk.commons.IAA_Listener
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @ClassName VarInvokeDelegate
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/5/19 17:47
 * @Version 1.0
 */
/**
 * true 则赋值, 否则不赋值
 */
typealias IVarDelegate_SetFun_R_Invoke<T> = IAA_BListener<T, Boolean>/*(field: T, value: T) -> Boolean*/

open class VarDelegate_SetFun_R_SameNonnull<T>(default: T, private val _onSet: IVarDelegate_SetFun_R_Invoke<T>) : ReadWriteProperty<Any?, T> {
    @Volatile
    private var _field = default
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (value == null) return
        if (_onSet.invoke(_field, value)) {
            _field = value
        }
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return _field
    }
}