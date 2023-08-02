package com.mozhimen.basick.utilk.kotlin

/**
 * @ClassName UtilKT
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/2 15:13
 * @Version 1.0
 */
object UtilKT {

    /**
     * 泛型是否为空
     * @return Boolean
     */
    inline fun <reified T> isTNullable(): Boolean {
        return kotlin.reflect.typeOf<T>().isMarkedNullable
    }

    /**
     * 判断数据类型是否是原始数据类型
     * @return Boolean
     */
    @JvmStatic
    inline fun <reified T> isTPrimitive(): Boolean {
        return UtilKAny.isObjPrimitive(T::class.java)
    }

    ////////////////////////////////////////////////////
    @JvmStatic
    fun <T> t2bytes(obj: T): ByteArray? =
            UtilKAny.obj2bytes(obj!!)
}