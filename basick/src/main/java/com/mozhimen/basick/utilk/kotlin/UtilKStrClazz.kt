package com.mozhimen.basick.utilk.kotlin

/**
 * @ClassName UtilKStrClazz
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/12/20
 * @Version 1.0
 */
fun String.isStrClassPackageExists() =
    UtilKStrClazz.isStrClassPackageExists(this)

////////////////////////////////////////////////////////////////////////////

object UtilKStrClazz {
    @JvmStatic
    fun isStrClassPackageExists(strClazzPackage: String): Boolean =
        try {
            Class.forName(strClazzPackage)
            true
        } catch (e: ClassNotFoundException) {
            false
        }
}