package com.mozhimen.basick.cachek.bases

import com.mozhimen.basick.cachek.commons.ICacheKProvider
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


/**
 * @ClassName BaseCacheKDelegateBoolean
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/13 15:17
 * @Version 1.0
 */
open class BaseCacheKVarPropertyBoolean<P : ICacheKProvider>(
    private val _cacheKProvider: P,
    private val _key: String,
    private val _default: Boolean = false
) : ReadWriteProperty<Any?, Boolean> {
    @Volatile
    private var _field = _cacheKProvider.getBoolean(_key, _default)

    override fun getValue(thisRef: Any?, property: KProperty<*>): Boolean {
        return _field
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
        _field = value
        _cacheKProvider.putBoolean(_key, value)
    }
}