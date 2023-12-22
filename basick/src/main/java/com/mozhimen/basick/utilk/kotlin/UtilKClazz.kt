package com.mozhimen.basick.utilk.kotlin

/**
 * @ClassName UtilKClazz
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/26 23:54
 * @Version 1.0
 */
fun Class<*>.getStrPackageName(): String =
    UtilKClazz.getStrPackageName(this)

fun Class<*>.getStrPackage(): String =
    UtilKClazz.getStrPackage(this)

object UtilKClazz {
    @JvmStatic
    fun <A : Annotation> getAnnotation(clazz: Class<*>, annotationClazz: Class<A>): A? =
        clazz.getAnnotation(annotationClazz)

    @JvmStatic
    fun getStrPackageName(clazz: Class<*>): String =
            clazz.name

    @JvmStatic
    fun getStrPackage(clazz: Class<*>): String {
        val clazzName = clazz.getStrPackageName()
        return clazzName.substring(0, clazzName.lastIndexOf("."))
    }

    @JvmStatic
    fun getSuperClazz(clazz: Class<*>): Class<*> =
            clazz.superclass

    ////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun clazz2strLog(clazz: Class<*>, lineNumber: Int): String =
        ".(" + clazz.simpleName + ".java:" + lineNumber + ")"
}