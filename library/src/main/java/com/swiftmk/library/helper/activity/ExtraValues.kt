package com.swiftmk.library.helper.activity

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.ArrayMap
import androidx.core.util.Preconditions
import java.io.Serializable
import java.util.*

/**
 * @ClassName ExtraValues
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/12 14:56
 * @Version 1.0
 */
class ExtraValues() : Parcelable {

    private var mMap: ArrayMap<String, Any>? = null

    init {
        mMap = ArrayMap()
    }

    @SuppressLint("RestrictedApi")
    constructor(size: Int) : this() {
        Preconditions.checkArgumentNonnegative(size)
        mMap = ArrayMap(size)
    }

    constructor(from: ExtraValues) : this() {
        Objects.requireNonNull(from)
        mMap = ArrayMap(from.mMap)
    }

    constructor(from: HashMap<String, Object>) : this() {
        mMap = ArrayMap()
        mMap?.putAll(from)
    }

    constructor(parcel: Parcel) : this() {
        mMap = ArrayMap<String, Any>(parcel.readInt())
        parcel.readArrayMap(mMap, null)
    }

    override fun equals(other: Any?): Boolean {
        return if (other !is ContentValues) {
            false
        } else mMap == (other as ExtraValues).mMap
    }

    fun getValues(): ArrayMap<String, Any>? {
        return mMap
    }

    override fun hashCode(): Int {
        return mMap.hashCode()
    }

    fun putAll(other: ExtraValues) {
        mMap?.putAll(other.mMap)
    }

    fun put(key: String, value: Bundle?) {
        mMap?.set(key, value)
    }

    fun put(key: String, value: Parcelable?) {
        mMap?.set(key, value)
    }

    fun put(key: String, value: Serializable?) {
        mMap?.set(key, value)
    }

    fun put(key: String, value: Array<Parcelable>?) {
        mMap?.set(key, value)
    }

    fun put(key: String, value: Array<CharSequence>?) {
        mMap?.set(key, value)
    }

    fun put(key: String, value: Array<String>?) {
        mMap?.set(key, value)
    }

    fun put(key: String, value: Boolean) {
        mMap!![key] = value
    }

    fun put(key: String, value: BooleanArray?) {
        mMap?.set(key, value)
    }

    fun put(key: String, value: Byte) {
        mMap!![key] = value
    }

    fun put(key: String, value: ByteArray?) {
        mMap?.set(key, value)
    }

    fun put(key: String, value: Char) {
        mMap!![key] = value
    }

    fun put(key: String, value: CharArray?) {
        mMap?.set(key, value)
    }

    fun put(key: String, value: CharSequence?) {
        mMap?.set(key, value)
    }

    fun put(key: String, value: Float) {
        mMap!![key] = value
    }

    fun put(key: String, value: FloatArray?) {
        mMap?.set(key, value)
    }

    fun put(key: String, value: Int) {
        mMap!![key] = value
    }

    fun put(key: String, value: IntArray?) {
        mMap?.set(key, value)
    }

    fun put(key: String, value: Long?) {
        mMap?.set(key, value)
    }

    fun put(key: String, value: LongArray?) {
        mMap?.set(key, value)
    }

    fun put(key: String, value: Short) {
        mMap!![key] = value
    }

    fun put(key: String, value: ShortArray?) {
        mMap?.set(key, value)
    }

    fun put(key: String, value: String?) {
        mMap?.set(key, value)
    }

    fun putNull(key: String?) {
        mMap!![key] = null
    }

    fun putObject(key: String, value: Any?) {
        when (value) {
            null -> {
                putNull(key)
            }
            is Bundle? -> {
                put(key, value)
            }
            is Parcelable? -> {
                put(key, value)
            }
            is Serializable? -> {
                put(key, value)
            }
            is Array<*>? -> {
                put(key, value)
            }
            is Boolean -> {
                put(key, value)
            }
            is BooleanArray? -> {
                put(key, value)
            }
            is Byte -> {
                put(key, value)
            }
            is ByteArray? -> {
                put(key, value)
            }
            is Char -> {
                put(key, value)
            }
            is CharArray? -> {
                put(key, value)
            }
            is CharSequence? -> {
                put(key, value)
            }
            is Double -> {
                put(key, value)
            }
            is DoubleArray? -> {
                put(key, value)
            }
            is Float -> {
                put(key, value)
            }
            is FloatArray? -> {
                put(key, value)
            }
            is Int -> {
                put(key, value)
            }
            is IntArray? -> {
                put(key, value)
            }
            is Long -> {
                put(key, value)
            }
            is LongArray? -> {
                put(key, value)
            }
            is Short -> {
                put(key, value)
            }
            is ShortArray? -> {
                put(key, value)
            }
            is String? -> {
                put(key, value)
            }
            else -> {
                throw IllegalArgumentException("Unsupported type ${value.javaClass}")
            }
        }
    }

    fun size(): Int {
        return mMap?.size ?: 0
    }

    fun isEmpty(): Boolean {
        return mMap!!.isEmpty()
    }

    fun remove(key: String?) {
        mMap!!.remove(key)
    }

    fun clear() {
        mMap!!.clear()
    }

    fun containsKey(key: String?): Boolean {
        return mMap!!.containsKey(key)
    }

    fun valueSet(): Set<Map.Entry<String?, Any?>?>? {
        return mMap!!.entries
    }

    fun keySet(): Set<String?>? {
        return mMap!!.keys
    }

    operator fun get(key: String?): Any? {
        return mMap!![key]
    }

    fun getAsString(key: String): String {

        val value = mMap?.get(key)
        return value?.toString()?:""
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let {
            it.writeInt(mMap!!.size)
            it.writeArrayMap(mMap)
        }
    }

    override fun toString(): String {
        val sb = StringBuilder()
        for (name in mMap!!.keys) {
            val value: String = getAsString(name)
            if (sb.isNotEmpty()) sb.append(" ")
            sb.append("$name=$value")
        }
        return sb.toString()
    }

    companion object CREATOR : Parcelable.Creator<ExtraValues> {
        override fun createFromParcel(parcel: Parcel): ExtraValues {
            return ExtraValues(parcel)
        }

        override fun newArray(size: Int): Array<ExtraValues?> {
            return arrayOfNulls(size)
        }
    }

    fun isSupportedValue(value: Any?): Boolean {
        return when (value) {
            null -> {
                false
            }
            is Bundle? -> {
                true
            }
            is Parcelable? -> {
                true
            }
            is Serializable? -> {
                true
            }
            is Array<*> -> {
                true
            }
            is Boolean -> {
                true
            }
            is BooleanArray? -> {
                true
            }
            is Byte -> {
                true
            }
            is ByteArray? -> {
                true
            }
            is Char -> {
                true
            }
            is CharArray? -> {
                true
            }
            is CharSequence? -> {
                true
            }
            is Double -> {
                true
            }
            is DoubleArray? -> {
                true
            }
            is Float -> {
                true
            }
            is FloatArray? -> {
                true
            }
            is Int -> {
                true
            }
            is IntArray? -> {
                true
            }
            is Long -> {
                true
            }
            is LongArray? -> {
                true
            }
            is Short -> {
                true
            }
            is ShortArray? -> {
                true
            }
            is String? -> {
                true
            }
            else -> {
                false
            }
        }
    }
}