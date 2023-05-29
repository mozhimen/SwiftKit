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
class CacheKSPDelegateDouble(
    private val _cacheKSPProvider: CacheKSPProvider,
    private val _key: String,
    private val _default: Double = 0.0
) : ReadWriteProperty<Any?, Double> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): Double {
        return _cacheKSPProvider.getDouble(_key, _default)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Double) {
        _cacheKSPProvider.putDouble(_key, value)
    }
}