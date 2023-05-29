package com.mozhimen.basick.cachek.shared_preferences.temps

import com.mozhimen.basick.cachek.shared_preferences.helpers.CacheKSPProvider
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


/**
 * @ClassName CacheKSPDelegateString
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/13 15:17
 * @Version 1.0
 */
class CacheKSPDelegateString(
    private val _cacheKSPProvider: CacheKSPProvider,
    private val _key: String,
    private val _default: String = ""
) : ReadWriteProperty<Any?, String> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return _cacheKSPProvider.getString(_key, _default)!!
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        _cacheKSPProvider.putString(_key, value)
    }
}