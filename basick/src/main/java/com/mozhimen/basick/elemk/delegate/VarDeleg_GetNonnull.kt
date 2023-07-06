package com.mozhimen.basick.elemk.delegate

import com.mozhimen.basick.elemk.commons.I_AListener
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


/**
 * @ClassName VarNullableInitDelegate
 * @Description 替换此类写法
 * for example:

private var _fpsView: TextView? = null
get() {
if (field != null) return field
return (LayoutInflater.from(_context).inflate(R.layout.fpsk_view, null, false) as TextView).also { field = it }
}

 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/15 16:58
 * @Version 1.0
 */
typealias IOnFieldNull<T> = I_AListener<T>

open class VarDeleg_GetNonnull<T>(private val _onFieldNull: IOnFieldNull<T>) : ReadWriteProperty<Any?, T> {
    @Volatile
    private var _field: T? = null
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        _field = value
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        if (_field != null) return _field!!
        return _onFieldNull.invoke().also { _field = it }
    }
}