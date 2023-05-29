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
class CacheKSPDelegateFloat(
    private val _cacheKSPProvider: CacheKSPProvider,
    private val _key: String,
    private val _default: Float = 0f
) : ReadWriteProperty<Any?, Float> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): Float {
        return _cacheKSPProvider.getFloat(_key, _default)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Float) {
        _cacheKSPProvider.putFloat(_key, value)
    }
}