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
class CacheKSPDelegateInt(
    private val _cacheKSPProvider: CacheKSPProvider,
    private val _key: String,
    private val _default: Int = 0
) : ReadWriteProperty<Any?, Int> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        return _cacheKSPProvider.getInt(_key, _default)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        _cacheKSPProvider.putInt(_key, value)
    }
}