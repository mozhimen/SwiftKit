package com.mozhimen.basick.cachek.bases

import com.mozhimen.basick.cachek.commons.ICacheKProvider
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


/**
 * @ClassName BaseCacheKDelegateInt
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/13 15:17
 * @Version 1.0
 */
open class BaseCacheKVarPropertyInt<P : ICacheKProvider>(
    private val _cacheKProvider: P,
    private val _key: String,
    private val _default: Int = 0
) : ReadWriteProperty<Any?, Int> {
    @Volatile
    private var _field = _cacheKProvider.getInt(_key, _default)

    override fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        return _field
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        _field = value
        _cacheKProvider.putInt(_key, value)
    }
}