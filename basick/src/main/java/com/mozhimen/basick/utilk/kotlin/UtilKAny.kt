package com.mozhimen.basick.utilk.kotlin

import android.animation.Animator
import android.graphics.drawable.Drawable
import android.util.SparseArray
import android.util.SparseBooleanArray
import android.util.SparseIntArray
import android.util.SparseLongArray
import android.view.animation.Animation
import androidx.collection.LongSparseArray
import androidx.collection.SimpleArrayMap
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.bases.IUtilK
import com.mozhimen.basick.utilk.java.lang.UtilKReflect
import java.io.ByteArrayOutputStream
import java.io.ObjectOutputStream
import java.lang.reflect.Array


/**
 * @ClassName UtilKAny
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/14 15:41
 * @Version 1.0
 */
fun Any.obj2stringTrim(): String =
        UtilKAny.obj2stringTrim(this)

fun Any.isObjPrimitive(): Boolean =
        UtilKAny.isObjPrimitive(this)

object UtilKAny : IUtilK {
    @JvmStatic
    fun obj2clazz(obj: Any): Class<*> =
            when (obj) {
                is Int -> Int::class.java
                is Boolean -> Boolean::class.java
                is Double -> Double::class.java
                is Float -> Float::class.java
                is Long -> Long::class.java
                is Animation -> Animation::class.java
                is Animator -> Animator::class.java
                is Drawable -> Drawable::class.java
                else -> obj.javaClass
            }

    @JvmStatic
    fun obj2stringTrim(obj: Any): String =
            obj.toString().trim()

    @JvmStatic
    fun obj2bytes(obj: Any): ByteArray? {
        var byteArrayOutputStream: ByteArrayOutputStream? = null
        var objectOutputStream: ObjectOutputStream? = null
        try {
            byteArrayOutputStream = ByteArrayOutputStream()
            objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
            objectOutputStream.writeObject(obj)
            objectOutputStream.flush()
            return byteArrayOutputStream.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(UtilKByteArray.TAG)
        } finally {
            byteArrayOutputStream?.close()
            objectOutputStream?.close()
        }
        return null
    }

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun isObjNullOrEmpty(obj: Any?): Boolean {
        if (obj == null) return true
        if (obj.javaClass.isArray && Array.getLength(obj) == 0) {
            return true
        }
        if (obj is CharSequence && obj.toString().isEmpty()) {
            return true
        }
        if (obj is Collection<*> && obj.isEmpty()) {
            return true
        }
        if (obj is Map<*, *> && obj.isEmpty()) {
            return true
        }
        if (obj is SimpleArrayMap<*, *> && obj.isEmpty) {
            return true
        }
        if (obj is SparseArray<*> && obj.size() == 0) {
            return true
        }
        if (obj is SparseBooleanArray && obj.size() == 0) {
            return true
        }
        if (obj is SparseIntArray && obj.size() == 0) {
            return true
        }
        if (UtilKBuildVersion.isAfterV_18_43_J2()) {
            if (obj is SparseLongArray && obj.size() == 0) {
                return true
            }
        }
        if (obj is LongSparseArray<*> && obj.size() == 0) {
            return true
        }
        if (UtilKBuildVersion.isAfterV_16_41_J()) {
            if (obj is android.util.LongSparseArray<*> && obj.size() == 0) {
                return true
            }
        }
        return false
    }

    /**
     * 判断数据类型是否是原始数据类型
     * @param obj Any
     * @return Boolean
     */
    @JvmStatic
    fun isObjPrimitive(obj: Any): Boolean {
        //String
        if (obj.javaClass == String::class.java) return true
        try {
            //只适用于int byte short long boolean char double float
            val field = UtilKReflect.getField(obj, "TYPE")
            val clazz = field[null] as Class<*>
            if (clazz.isPrimitive) return true
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
            e.message?.et(TAG)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
            e.message?.et(TAG)
        }
        return false
    }

    /**
     * 判断类型是否匹配
     * @param obj Class<*>
     * @param matches Array<out Class<*>>
     * @return Boolean
     */
    @JvmStatic
    fun isObjTypeMatch(obj: Any, vararg matches: Class<*>): Boolean {
        try {
            return matches.any { obj.javaClass == it || obj.javaClass.superclass == it }
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }
        return false
    }

    /////////////////////////////////////////////////////////////////////

    /**
     * 获取类型名称
     * @param obj Any
     * @return String
     */
    @JvmStatic
    fun getObjTypeName(obj: Any): String {
        return obj.javaClass.simpleName
    }
}