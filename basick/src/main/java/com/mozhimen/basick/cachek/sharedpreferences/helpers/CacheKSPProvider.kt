package com.mozhimen.basick.cachek.sharedpreferences.helpers

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.mozhimen.basick.cachek.commons.ICacheKProvider
import com.mozhimen.basick.elemk.kotlin.cons.CSuppress
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.postk.crypto.PostKCryptoAES
import com.mozhimen.basick.postk.crypto.mos.MCryptoAESConfig
import java.lang.IllegalArgumentException

/**
 * @ClassName CacheKSPProvider
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/25 16:35
 * @Version 1.0
 */
class CacheKSPProvider(spName: String) : ICacheKProvider, BaseUtilK() {

    private val _sharedPreferences: SharedPreferences by lazy { _context.getSharedPreferences(spName, Context.MODE_PRIVATE) }

    /////////////////////////////////////////////////////////////////////

    fun getEditor(): Editor =
        _sharedPreferences.edit()

    fun <T> getPutEditor(key: String, value: T): Editor =
        when (value) {
            is String -> getEditor().putString(key, value)
            is Boolean -> getEditor().putBoolean(key, value)
            is Int -> getEditor().putInt(key, value)
            is Long -> getEditor().putLong(key, value)
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

    fun putStringEncryptSync(key: String, value: String) {
        if (value.isEmpty()) return
        putObjSync(key, PostKCryptoAES.with(MCryptoAESConfig(secretKey = "5rfj4FVG&Td#$*Jd")).encryptWithBase64(value))
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
        putObj(key, java.lang.Double.doubleToRawLongBits(value))
    }

    fun putStringSet(key: String, value: Set<String>) {
        getEditor().putStringSet(key, value).apply()
    }

    fun putStringEncrypt(key: String, value: String) {
        if (value.isEmpty()) return
        putObj(key, PostKCryptoAES.with(MCryptoAESConfig(secretKey = "5rfj4FVG&Td#$*Jd")).encryptWithBase64(value))
    }

    /////////////////////////////////////////////////////////////////////

    @Suppress(CSuppress.UNCHECKED_CAST)
    override fun <T> getObj(key: String, default: T): T =
        when (default) {
            is Int -> getInt(key, default)
            is Long -> getLong(key, default)
            is String -> getString(key, default)
            is Boolean -> getBoolean(key, default)
            is Float -> getFloat(key, default)
            is Double -> getDouble(key, default)
            else -> throw IllegalArgumentException("Unknown Type.")
        } as T

    override fun getInt(key: String): Int =
        getInt(key, 0)

    override fun getInt(key: String, defaultValue: Int): Int =
        _sharedPreferences.getInt(key, defaultValue)

    override fun getLong(key: String): Long =
        getLong(key, 0L)

    override fun getLong(key: String, defaultValue: Long): Long =
        _sharedPreferences.getLong(key, defaultValue)

    override fun getString(key: String): String =
        getString(key, "")

    override fun getString(key: String, defaultValue: String): String =
        _sharedPreferences.getString(key, defaultValue) ?: defaultValue

    override fun getBoolean(key: String): Boolean =
        getBoolean(key, false)

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean =
        _sharedPreferences.getBoolean(key, defaultValue)

    override fun getFloat(key: String): Float =
        getFloat(key, 0F)

    override fun getFloat(key: String, defaultValue: Float): Float =
        _sharedPreferences.getFloat(key, defaultValue)

    override fun getDouble(key: String): Double =
        getDouble(key, 0.0)

    override fun getDouble(key: String, defaultValue: Double): Double =
        java.lang.Double.longBitsToDouble(_sharedPreferences.getLong(key, defaultValue.toLong()))

    fun getStringSet(key: String): Set<String>? =
        getStringSet(key, null)

    fun getStringSet(key: String, defaultValue: Set<String>?): Set<String>? =
        _sharedPreferences.getStringSet(key, defaultValue)

    fun getStringDecrypt(key: String, defaultValue: String = ""): String {
        val valueDecrypted = _sharedPreferences.getString(key, null) ?: return defaultValue
        return PostKCryptoAES.with(MCryptoAESConfig(secretKey = "5rfj4FVG&Td#$*Jd")).decryptWithBase64(valueDecrypted)
    }

    fun getAll(): MutableMap<String, *> =
        _sharedPreferences.all

    /////////////////////////////////////////////////////////////////////

    fun contains(key: String): Boolean =
        _sharedPreferences.contains(key)

    fun remove(key: String) {
        if (contains(key))
            getEditor().remove(key).apply()
    }

    override fun clear() {
        getEditor().clear().apply()
    }
}

