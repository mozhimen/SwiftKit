package com.mozhimen.basick.cachek.commons

/**
 * @ClassName ICacheKProvider
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/6/4 14:34
 * @Version 1.0
 */
interface ICacheKProvider {
    fun putString(key: String, value: String)
    fun putBoolean(key: String, value: Boolean)
    fun putInt(key: String, value: Int)
    fun putLong(key: String, value: Long)
    fun putFloat(key: String, value: Float)
    fun putDouble(key: String, value: Double)

    /////////////////////////////////////////////////////////////////////

    fun getInt(key: String): Int
    fun getLong(key: String): Long
    fun getString(key: String): String
    fun getBoolean(key: String): Boolean
    fun getFloat(key: String): Float
    fun getDouble(key: String): Double

    /////////////////////////////////////////////////////////////////////

    fun getInt(key: String, defaultValue: Int): Int
    fun getLong(key: String, defaultValue: Long): Long
    fun getString(key: String, defaultValue: String): String
    fun getBoolean(key: String, defaultValue: Boolean): Boolean
    fun getFloat(key: String, defaultValue: Float): Float
    fun getDouble(key: String, defaultValue: Double): Double
}