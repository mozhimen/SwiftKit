package com.mozhimen.basick.utilk.kotlin

import android.animation.Animator
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.MotionEvent
import android.view.animation.Animation
import androidx.annotation.ColorInt
import com.mozhimen.basick.utilk.android.util.e
import com.mozhimen.basick.utilk.android.view.motionEvent2str
import com.mozhimen.basick.utilk.google.gson.UtilKGsonWrapper
import com.mozhimen.basick.utilk.java.io.byteArrayOutputStream2bytes_use
import com.mozhimen.basick.utilk.java.io.flushClose
import com.mozhimen.basick.utilk.kotlin.collections.list2str
import com.mozhimen.basick.utilk.kotlin.collections.map2str
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.ObjectOutputStream


/**
 * @ClassName UtilKAnyFormat
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/7 12:30
 * @Version 1.0
 */
fun Any.obj2jSONObject(): JSONObject =
    UtilKAnyFormat.obj2jSONObject(this)

fun Any.obj2intColor(): Int =
    UtilKAnyFormat.obj2intColor(this)

fun Any.obj2str(): String =
    UtilKAnyFormat.obj2str(this)

fun Any.obj2str_trim(): String =
    UtilKAnyFormat.obj2str_trim(this)

fun Any.obj2clazz(): Class<*> =
    UtilKAnyFormat.obj2clazz(this)

fun Any.obj2bytes(): ByteArray? =
    UtilKAnyFormat.obj2bytes(this)

//////////////////////////////////////////////////////////////////

object UtilKAnyFormat {
    @JvmStatic
    fun obj2jSONObject(obj: Any): JSONObject =
        if (obj is String)
            JSONObject(obj)
        else JSONObject(UtilKGsonWrapper.obj2strJson_ofGson(obj))

    @JvmStatic
    @ColorInt
    fun obj2intColor(obj: Any): Int =
        when (obj) {
            is String -> obj.strColor2intColor()
            is Int -> obj
            else -> Color.WHITE
        }

    @JvmStatic
    fun obj2str(obj: Any): String =
        when (obj) {
            is String -> obj
            is Throwable -> obj.throwable2str()
            is List<*> -> obj.list2str()
            is Map<*, *> -> obj.map2str()
            is MotionEvent -> obj.motionEvent2str()
            else -> obj.toString()
        }

    @JvmStatic
    fun obj2str_trim(obj: Any): String =
        obj.toString().trim()

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
    fun obj2bytes(obj: Any): ByteArray? {
        var objectOutputStream: ObjectOutputStream? = null
        try {
            val byteArrayOutputStream = ByteArrayOutputStream()
            objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
            objectOutputStream.writeObject(obj)
            return byteArrayOutputStream.byteArrayOutputStream2bytes_use()
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.e(UtilKAny.TAG)
        } finally {
            objectOutputStream?.flushClose()
        }
        return null
    }
}