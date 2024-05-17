package com.mozhimen.basick.utilk.kotlin

import com.mozhimen.basick.elemk.commons.IA_BListener

/**
 * @ClassName UtilKT
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/2 15:13
 * @Version 1.0
 */
inline fun <T> T?.filterNullable(predicate: IA_BListener<T, Boolean>): T? =
    UtilKT.filterNullable(this, predicate)

//////////////////////////////////////////////////////////////////////////////////

object UtilKT {

    //泛型是否为空
    @JvmStatic
    inline fun <reified T> isNullable(): Boolean {
        return kotlin.reflect.typeOf<T>().isMarkedNullable
    }

    //判断数据类型是否是原始数据类型
    @JvmStatic
    inline fun <reified T> isPrimitive(): Boolean {
        return UtilKAny.isObjPrimitive(T::class.java)
    }

    ////////////////////////////////////////////////////

    @JvmStatic
    fun <T> t2bytes(obj: T): ByteArray? =
        obj!!.obj2bytes()

    ////////////////////////////////////////////////////

    @JvmStatic
    inline fun <reified T : Any> newInstance(vararg params: Any): T =
        T::class.java.getDeclaredConstructor(*params.map { it::class.java }.toTypedArray()).apply { isAccessible = true }.newInstance(*params)

    inline fun <T> filterNullable(obj: T?, predicate: IA_BListener<T, Boolean>): T? =
        if (obj != null && predicate(obj)) obj else null
}