package com.mozhimen.basick.utilk.kotlin

/**
 * @ClassName UtilKStrPackage
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/2 15:24
 * @Version 1.0
 */
@Throws(ClassNotFoundException::class)
fun String.strPackage2clazz(): Class<*> =
    UtilKStrPackage.strPackage2clazz(this)

object UtilKStrPackage {
    @JvmStatic
    @Throws(ClassNotFoundException::class)
    fun strPackage2clazz(strPackage: String): Class<*> =
        Class.forName(strPackage)
}