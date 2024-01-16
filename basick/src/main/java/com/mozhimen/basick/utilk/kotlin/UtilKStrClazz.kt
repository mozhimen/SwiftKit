package com.mozhimen.basick.utilk.kotlin

/**
 * @ClassName UtilKStrClazz
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/12/20
 * @Version 1.0
 */
@Throws(ClassNotFoundException::class)
fun String.strClazz2clazz(): Class<*> =
    UtilKStrClazz.strClazz2clazz(this)

fun String.isStrClassPackageExists() =
    UtilKStrClazz.isStrClassPackageExists(this)

////////////////////////////////////////////////////////////////////////////

object UtilKStrClazz {
    @JvmStatic
    @Throws(ClassNotFoundException::class)
    fun strClazz2clazz(strClazz: String): Class<*> =
        strClazz.strPackage2clazz()

    @JvmStatic
    fun isStrClassPackageExists(strClazzPackage: String): Boolean =
        try {
            strClazz2clazz(strClazzPackage)
            true
        } catch (e: ClassNotFoundException) {
            false
        }
}