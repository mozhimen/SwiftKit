package com.mozhimen.basick.utilk.kotlin

import android.animation.Animator
import android.graphics.drawable.Drawable
import android.view.animation.Animation

/**
 * @ClassName UtilKClazz
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/26 23:54
 * @Version 1.0
 */
fun String.packageStr2Clazz(): Class<*> =
    UtilKClazz.packageStr2Clazz(this)

fun Class<*>.getPackageStr(): String =
    UtilKClazz.getPackageStr(this)


object UtilKClazz {
    @JvmStatic
    fun <T : Annotation> getAnnotation(clazz: Class<*>, annotationClazz: Class<T>): T? =
        clazz.getAnnotation(annotationClazz)

    @JvmStatic
    fun packageStr2Clazz(packageStr: String): Class<*> =
        Class.forName(packageStr)

    @JvmStatic
    fun getPackageStr(clazz: Class<*>): String =
        clazz.name

    @JvmStatic
    fun clazz2Log(clazz: Class<*>, lineNumber: Int): String =
        ".(" + clazz.simpleName + ".java:" + lineNumber + ")"

    @JvmStatic
    fun getSuperClazz(clazz: Class<*>): Class<*> =
        clazz.superclass

    @JvmStatic
    fun obj2Clazz(obj: Any): Class<*> =
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
}