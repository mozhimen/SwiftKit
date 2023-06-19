package com.mozhimen.basick.cachek.bases

import com.mozhimen.basick.cachek.commons.ICacheKProvider
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


/**
 * @ClassName CacheKSPDelegate
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/13 15:17
 * @Version 1.0
 */
open class BaseCacheKDelegateBoolean<T : ICacheKProvider>(
    private val _cacheKProvider: T,
    private val _key: String,
    private val _default: Boolean = false
) : ReadWriteProperty<Any?, Boolean> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): Boolean {
        return _cacheKProvider.getBoolean(_key, _default)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
        _cacheKProvider.putBoolean(_key, value)
    }
}