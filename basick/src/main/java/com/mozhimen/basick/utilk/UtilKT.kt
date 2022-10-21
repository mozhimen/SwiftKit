package com.mozhimen.basick.utilk

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @ClassName UtilKT
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/13 17:53
 * @Version 1.0
 */
object UtilKT {
    @JvmStatic
    fun <T> getT(obj: Any, i: Int): Class<T>? {
        try {
            val superClass: Type? = obj.javaClass.genericSuperclass
            if (superClass != null && superClass is ParameterizedType) {
                val arguments: Array<Type> = superClass.actualTypeArguments
                if (arguments.isNotEmpty()) {
                    return arguments[i] as? Class<T>?
                }
            }
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
        return null
    }
}