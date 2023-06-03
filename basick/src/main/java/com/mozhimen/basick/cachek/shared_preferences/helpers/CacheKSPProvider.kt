package com.mozhimen.basick.cachek.shared_preferences.helpers

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.mozhimen.basick.utilk.java.io.encrypt.UtilKAES
import com.mozhimen.basick.utilk.content.UtilKApplication
import java.lang.IllegalArgumentException

/**
 * @ClassName CacheKSPProvider
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/25 16:35
 * @Version 1.0
 */
class CacheKSPProvider(spName: String) {
    companion object {
        private const val CACHEK_SP_ENCRYPT_ALIAS = "5rfj4FVG&Td#$*Jd"
    }

    private var _sharedPreferences: SharedPreferences

    init {
        _sharedPreferences = UtilKApplication.instance.applicationContext.getSharedPreferences(spName, Context.MODE_PRIVATE)
    }

    /////////////////////////////////////////////////////////////////////

    fun getEditor(): Editor =
        _sharedPreferences.edit()

    fun getPutEditor(key: String, value: Any) =
        when (value) {
            is String -> getEditor().putString(key, value)
            is Boolean -> getEditor().putBoolean(key, value)
            is Int -> getEditor().putInt(key, value)
            is Long -> getEditor().putLong(key, value)
            is Float -> getEditor().putFloat(key, value)
            else -> throw IllegalArgumentException("Unknown Type.")
        }

    /////////////////////////////////////////////////////////////////////

    fun putObj(key: String, value: Any, sync: Boolean = false) {
        if (sync) getPutEditor(key, value).commit() else getPutEditor(key, value).apply()
    }

    /////////////////////////////////////////////////////////////////////

    fun putString(key: String, value: String, sync: Boolean = false) {
        putObj(key, value, sync)
    }

    fun getString(key: String, defaultValue: String = ""): String? =
        _sharedPreferences.getString(key, defaultValue)

    /////////////////////////////////////////////////////////////////////

    fun putStringEncrypt(key: String, value: String, sync: Boolean = false) {
        if (value.isEmpty()) return
        putObj(key, UtilKAES.with(secretKey = CACHEK_SP_ENCRYPT_ALIAS).encryptWithBase64(value), sync)
    }

    fun getStringDecrypt(key: String, defaultValue: String = ""): String {
        val valueDecrypted = _sharedPreferences.getString(key, null) ?: return defaultValue
        return UtilKAES.with(secretKey = CACHEK_SP_ENCRYPT_ALIAS).decryptWithBase64(valueDecrypted)
    }

    /////////////////////////////////////////////////////////////////////

    fun putBoolean(key: String, value: Boolean, sync: Boolean = false) {
        putObj(key, value, sync)
    }

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean =
        _sharedPreferences.getBoolean(key, defaultValue)

    /////////////////////////////////////////////////////////////////////

    fun putInt(key: String, value: Int, sync: Boolean = false) {
        putObj(key, value, sync)
    }

    fun getInt(key: String, defaultValue: Int = 0): Int =
        _sharedPreferences.getInt(key, defaultValue)

    /////////////////////////////////////////////////////////////////////

    fun putLong(key: String, value: Long, sync: Boolean = false) {
        putObj(key, value, sync)
    }

    fun getLong(key: String, defaultValue: Long = 0): Long =
        _sharedPreferences.getLong(key, defaultValue)

    /////////////////////////////////////////////////////////////////////

    fun putFloat(key: String, value: Float, sync: Boolean = false) {
        putObj(key, value, sync)
    }

    fun getFloat(key: String, defaultValue: Float = 0F): Float =
        _sharedPreferences.getFloat(key, defaultValue)

    /////////////////////////////////////////////////////////////////////

    fun putDouble(key: String, value: Double, sync: Boolean = false) {
        putObj(key, java.lang.Double.doubleToRawLongBits(value), sync)
    }

    fun getDouble(key: String, defaultValue: Double = 0.0): Double =
        java.lang.Double.longBitsToDouble(_sharedPreferences.getLong(key, defaultValue.toLong()))

    /////////////////////////////////////////////////////////////////////

    fun putStringSet(key: String, value: Set<String>, sync: Boolean = false) {
        if (sync) getEditor().putStringSet(key, value).commit() else getEditor().putStringSet(key, value).apply()
    }

    /////////////////////////////////////////////////////////////////////

    fun getAll(): MutableMap<String, *> =
        _sharedPreferences.all

    /////////////////////////////////////////////////////////////////////

    fun contains(key: String): Boolean =
        _sharedPreferences.contains(key)

    /////////////////////////////////////////////////////////////////////

    fun remove(key: String) {
        getEditor().remove(key).apply()
    }

    fun clear() {
        getEditor().clear().apply()
    }
}

