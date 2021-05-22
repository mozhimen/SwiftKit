package com.mozhimen.swiftmk.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

/**
 * @ClassName BasePreferences
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/20 18:56
 * @Version 1.0
 */
open class BasePreferences(preferencesName: String, application: BaseApplication) {
    @SuppressLint("SharedPreferences")
    private val preferences: SharedPreferences =
        application.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)

    fun setString(entry: String, value: String) =
        preferences.edit().putString(entry, value).commit()

    fun setBoolean(entry: String, value: Boolean) =
        preferences.edit().putBoolean(entry, value).commit()

    fun setInt(entry: String, value: Int) = preferences.edit().putInt(entry, value).commit()

    fun setLong(entry: String, value: Long) = preferences.edit().putLong(entry, value).commit()

    fun setFloat(entry: String, value: Float) = preferences.edit().putFloat(entry, value)
        .commit()

    fun getAll(): MutableMap<String, *> = preferences.all

    fun getString(entry: String, defaultValue: String = "") =
        preferences.getString(entry, defaultValue)

    fun getInt(entry: String, defaultValue: Int = 0) = preferences.getInt(entry, defaultValue)

    fun getFloat(entry: String, defaultValue: Float = 0F) =
        preferences.getFloat(entry, defaultValue)

    fun getLong(entry: String, defaultValue: Long = 0) = preferences.getLong(entry, defaultValue)

    fun getBoolean(entry: String, defaultValue: Boolean) =
        preferences.getBoolean(entry, defaultValue)

    fun contains(entry: String) = preferences.contains(entry)

    fun clear(entry: String) = preferences.edit().remove(entry).commit()

    fun clearAll() = preferences.edit().clear().commit()
}