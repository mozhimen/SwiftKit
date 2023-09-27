package com.mozhimen.basick.cachek.mmkv.helpers

import android.content.SharedPreferences
import android.os.Parcelable
import com.mozhimen.basick.cachek.commons.ICacheKProvider
import com.tencent.mmkv.MMKV
import java.lang.IllegalArgumentException

/**
 * @ClassName CacheKMMKVProvider
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/9/27 16:41
 * @Version 1.0
 */
class CacheKMMKVProvider(mmkvName: String, isMultiProcess: Boolean) : ICacheKProvider {

    private val _mmkv: MMKV by lazy { if (isMultiProcess) MMKV.mmkvWithID(mmkvName, MMKV.MULTI_PROCESS_MODE) else MMKV.mmkvWithID(mmkvName) }

    /////////////////////////////////////////////////////////////////////

    fun getEditor(): SharedPreferences.Editor =
        _mmkv.edit()

    fun <T> getPutEditor(key: String, value: T): SharedPreferences.Editor =
        when (value) {
            is Int -> getEditor().putInt(key, value)
            is Long -> getEditor().putLong(key, value)
            is String -> getEditor().putString(key, value)
            is Boolean -> getEditor().putBoolean(key, value)
            is Float -> getEditor().putFloat(key, value)
            else -> throw IllegalArgumentException("Unknown Type.")
        }

    /////////////////////////////////////////////////////////////////////

    fun <T> putObjSync(key: String, value: T) {
        getPutEditor(key, value).commit()
    }

    fun putIntSync(key: String, value: String) {
        putObjSync(key, value)
    }

    fun putLongSync(key: String, value: String) {
        putObjSync(key, value)
    }

    fun putStringSync(key: String, value: String) {
        putObjSync(key, value)
    }

    fun putBooleanSync(key: String, value: String) {
        putObjSync(key, value)
    }

    fun putFloatSync(key: String, value: String) {
        putObjSync(key, value)
    }

    fun putDoubleSync(key: String, value: String) {
        putObjSync(key, value)
    }

    fun putStringSetSync(key: String, value: Set<String>) {
        getEditor().putStringSet(key, value).commit()
    }

    /////////////////////////////////////////////////////////////////////

    override fun <T> putObj(key: String, obj: T) {
        getPutEditor(key, obj).apply()
    }

    override fun putInt(key: String, value: Int) {
        putObj(key, value)
    }

    override fun putLong(key: String, value: Long) {
        putObj(key, value)
    }

    override fun putString(key: String, value: String) {
        putObj(key, value)
    }

    override fun putBoolean(key: String, value: Boolean) {
        putObj(key, value)
    }

    override fun putFloat(key: String, value: Float) {
        putObj(key, value)
    }

    override fun putDouble(key: String, value: Double) {
        _mmkv.encode(key, value)
    }

    fun putByteArray(key: String, value: ByteArray) {
        _mmkv.encode(key, value)
    }

    fun putStringSet(key: String, value: Set<String>) {
        _mmkv.encode(key, value)
    }

    fun putParcelable(key: String, value: Parcelable) {
        _mmkv.encode(key, value)
    }

    /////////////////////////////////////////////////////////////////////

    override fun <T> getObj(key: String, default: T): T =
        when (default) {
            is Int -> getInt(key, default)
            is Long -> getLong(key, default)
            is String -> getString(key, default)
            is Boolean -> getBoolean(key, default)
            is Float -> getFloat(key, default)
            is Double -> getDouble(key, default)
            is ByteArray -> getByteArray(key, default)
            else -> throw IllegalArgumentException("Unknown Type.")
        } as T

    override fun getInt(key: String): Int =
        _mmkv.decodeInt(key)

    override fun getInt(key: String, defaultValue: Int): Int =
        _mmkv.decodeInt(key, defaultValue)

    override fun getLong(key: String): Long =
        _mmkv.decodeLong(key)

    override fun getLong(key: String, defaultValue: Long): Long =
        _mmkv.decodeLong(key, defaultValue)

    override fun getString(key: String): String =
        _mmkv.decodeString(key) ?: ""

    override fun getString(key: String, defaultValue: String): String =
        _mmkv.decodeString(key, defaultValue) ?: ""

    override fun getBoolean(key: String): Boolean =
        _mmkv.decodeBool(key)

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean =
        _mmkv.decodeBool(key, defaultValue)

    override fun getFloat(key: String): Float =
        _mmkv.decodeFloat(key)

    override fun getFloat(key: String, defaultValue: Float): Float =
        _mmkv.decodeFloat(key, defaultValue)

    override fun getDouble(key: String): Double =
        _mmkv.decodeDouble(key)

    override fun getDouble(key: String, defaultValue: Double): Double =
        _mmkv.decodeDouble(key, defaultValue)

    fun getByteArray(key: String): ByteArray? =
        _mmkv.decodeBytes(key)

    fun getByteArray(key: String, defaultValue: ByteArray): ByteArray? =
        _mmkv.decodeBytes(key, defaultValue)

    fun getStringSet(key: String): Set<String>? =
        _mmkv.decodeStringSet(key)

    fun getStringSet(key: String, defaultValue: Set<String>): Set<String>? =
        _mmkv.decodeStringSet(key, defaultValue)

    fun <T : Parcelable> getParcelable(key: String, clazz: Class<T>): T? =
        _mmkv.decodeParcelable(key, clazz)

    fun <T : Parcelable> getParcelable(key: String, clazz: Class<T>, defaultValue: T): T? =
        _mmkv.decodeParcelable(key, clazz, defaultValue)

    fun getAll(): MutableMap<String, *> =
        _mmkv.all

    /////////////////////////////////////////////////////////////////////

    fun contains(key: String): Boolean =
        _mmkv.containsKey(key)

    fun remove(key: String) {
        if (contains(key))
            getEditor().remove(key).apply()
    }

    fun removeAllOf(vararg key: String) {
        _mmkv.removeValuesForKeys(key)
    }

    override fun clear() {
        _mmkv.edit().clear().apply()
    }
}