package com.mozhimen.basick.cachek.shared_preferences.temps

import com.mozhimen.basick.cachek.shared_preferences.helpers.CacheKSPProvider
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


/**
 * @ClassName CacheKSPDelegate
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/13 15:17
 * @Version 1.0
 */
class CacheKSPDelegateBoolean(
    private val _cacheKSPProvider: CacheKSPProvider,
    private val _key: String,
    private val _default: Boolean = false
) : ReadWriteProperty<Any?, Boolean> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): Boolean {
        return _cacheKSPProvider.getBoolean(_key, _default)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
        _cacheKSPProvider.putBoolean(_key, value)
    }
}