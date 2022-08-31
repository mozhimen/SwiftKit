package com.mozhimen.basick.utilk

import android.R
import android.util.Log

/**
 * @ClassName UtilKDataType
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/11 18:32
 * @Version 1.0
 */
object UtilKDataType {
    private const val TAG = "UtilKDataType>>>>>"

    /**
     * 判断数据类型是否是原始数据类型
     * @param type Any
     * @return Boolean
     */
    fun isPrimitive(
        type: Any
    ): Boolean {
        //String
        if (type.javaClass == String::class.java) {
            return true
        }
        try {
            //只适用于int byte short long boolean char double float
            val field = type.javaClass.getField("TYPE")
            val clazz = field[null] as Class<*>
            if (clazz.isPrimitive) {
                return true
            }
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 判断类型是否匹配
     * @param type Class<*>
     * @param matches Array<out Class<*>>
     * @return Boolean
     */
    fun isTypeMatch(
        type: Any,
        vararg matches: Class<*>
    ): Boolean {
        try {
            return matches.any { type.javaClass == it || type.javaClass.superclass == it }
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 获取类型名称
     * @param type Any
     * @return String
     */
    fun getTypeName(
        type: Any
    ): String =
        type.javaClass.simpleName
}