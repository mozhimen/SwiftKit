package com.mozhimen.basick.utilk.java.datatype

import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.log.et

/**
 * @ClassName UtilKDataType
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/11 18:32
 * @Version 1.0
 */
object UtilKDataType : BaseUtilK() {

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
        return isObjPrimitive(T::class.java)
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
            val field = obj.javaClass.getField("TYPE")
            val clazz = field[null] as Class<*>
            if (clazz.isPrimitive) {
                return true
            }
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
    fun isTypeMatch(obj: Any, vararg matches: Class<*>): Boolean {
        try {
            return matches.any { obj.javaClass == it || obj.javaClass.superclass == it }
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 获取类型名称
     * @param obj Any
     * @return String
     */
    @JvmStatic
    fun getTypeName(obj: Any): String {
        return obj.javaClass.simpleName
    }
}