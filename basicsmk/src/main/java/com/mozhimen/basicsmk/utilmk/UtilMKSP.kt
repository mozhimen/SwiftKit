package com.mozhimen.basicsmk.utilmk

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import java.util.concurrent.ConcurrentHashMap

/**
 * @ClassName UtilMKSharedPreferences
 * @Description UtilMKSP.INSTANCE.with("123").getAll()
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/28 14:09
 * @Version 1.0
 */
@SuppressLint("CommitPrefEdits")
class UtilMKSP {

    companion object {
        val instance = UtilMKSPProvider.holder
    }

    private object UtilMKSPProvider {
        val holder = UtilMKSP()
    }

    private val spMap = ConcurrentHashMap<String, SharedPreferences>()
    private lateinit var _preferences: SharedPreferences

    fun with(preferencesName: String): UtilMKSP {
        _preferences = if (spMap[preferencesName] != null && spMap.containsKey(preferencesName)) {
            spMap[preferencesName]!!
        } else {
            val preferences = UtilMKGlobal.instance.getApp()!!
                .getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
            spMap[preferencesName] = preferences
            preferences
        }
        return this
    }

    fun setString(entry: String, value: String) =
        _preferences.edit().putString(entry, value).commit()

    fun setBoolean(entry: String, value: Boolean) =
        _preferences.edit().putBoolean(entry, value).commit()

    fun setInt(entry: String, value: Int) =
        _preferences.edit().putInt(entry, value).commit()

    fun setLong(entry: String, value: Long) =
        _preferences.edit().putLong(entry, value).commit()

    fun setFloat(entry: String, value: Float) =
        _preferences.edit().putFloat(entry, value).commit()

    fun getAll(): MutableMap<String, *> =
        _preferences.all

    fun getString(entry: String, defaultValue: String = "") =
        _preferences.getString(entry, defaultValue)

    fun getInt(entry: String, defaultValue: Int = 0) =
        _preferences.getInt(entry, defaultValue)

    fun getFloat(entry: String, defaultValue: Float = 0F) =
        _preferences.getFloat(entry, defaultValue)

    fun getLong(entry: String, defaultValue: Long = 0) =
        _preferences.getLong(entry, defaultValue)

    fun getBoolean(entry: String, defaultValue: Boolean) =
        _preferences.getBoolean(entry, defaultValue)

    fun contains(entry: String) =
        _preferences.contains(entry)

    fun clear(entry: String) =
        _preferences.edit().remove(entry).commit()

    fun clearAll() =
        _preferences.edit().clear().commit()
}