package com.mozhimen.basick.cachek.helpers

import android.content.Context
import android.content.SharedPreferences
import com.mozhimen.basick.utilk.encrypt.UtilKEncryptAES
import com.mozhimen.basick.utilk.context.UtilKApplication

/**
 * @ClassName CacheKSPProvider
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/25 16:35
 * @Version 1.0
 */
class CacheKSPProvider(spName: String) {
    private var _preferences: SharedPreferences
    private val CACHEK_SP_ENCRYPT_ALIAS = "5rfj4FVG&Td#$*Jd"

    init {
        _preferences = UtilKApplication.instance.get()
            .getSharedPreferences(spName, Context.MODE_PRIVATE)
    }

    fun putString(key: String, value: String) {
        _preferences.edit().putString(key, value).apply()
    }

    fun getString(key: String, defaultValue: String = ""): String? =
        _preferences.getString(key, defaultValue)

    fun putStringEncrypt(key: String, value: String) {
        if (value.isEmpty()) return
        val valueEncrypted = UtilKEncryptAES.with(secretKey = CACHEK_SP_ENCRYPT_ALIAS).encryptWithBase64(value)
        _preferences.edit().putString(key, valueEncrypted).apply()
    }

    fun getStringDecrypt(key: String, defaultValue: String = ""): String? {
        val valueDecrypted = _preferences.getString(key, null) ?: return null
        return UtilKEncryptAES.with(secretKey = CACHEK_SP_ENCRYPT_ALIAS).decryptWithBase64(valueDecrypted)
    }

    fun putBoolean(key: String, value: Boolean) {
        _preferences.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean =
        _preferences.getBoolean(key, defaultValue)

    fun putInt(key: String, value: Int) {
        _preferences.edit().putInt(key, value).apply()
    }

    fun getInt(key: String, defaultValue: Int = 0): Int =
        _preferences.getInt(key, defaultValue)

    fun putLong(key: String, value: Long) {
        _preferences.edit().putLong(key, value).apply()
    }

    fun getLong(key: String, defaultValue: Long = 0): Long =
        _preferences.getLong(key, defaultValue)

    fun putFloat(key: String, value: Float) {
        _preferences.edit().putFloat(key, value).apply()
    }

    fun getFloat(key: String, defaultValue: Float = 0F): Float =
        _preferences.getFloat(key, defaultValue)

    fun putStringSet(key: String, value: Set<String>) {
        _preferences.edit().putStringSet(key, value).apply()
    }

    fun getAll(): MutableMap<String, *> =
        _preferences.all

    fun contains(key: String): Boolean =
        _preferences.contains(key)

    fun remove(key: String) {
        _preferences.edit().remove(key).apply()
    }

    fun clear() {
        _preferences.edit().clear().apply()
    }
}

