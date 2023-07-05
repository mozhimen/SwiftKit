package com.mozhimen.basick.cachek.bases

import com.mozhimen.basick.cachek.commons.ICacheKProvider
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


/**
 * @ClassName CacheKSPDelegateString
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/13 15:17
 * @Version 1.0
 */
open class BaseCacheKDelegateString<P : ICacheKProvider>(
    private val _cacheKProvider: P,
    private val _key: String,
    private val _default: String = ""
) : ReadWriteProperty<Any?, String> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return _cacheKProvider.getString(_key, _default)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        _cacheKProvider.putString(_key, value)
    }
}