package com.mozhimen.basick.utilk.java.lang

import com.mozhimen.basick.elemk.java.lang.bases.BaseGeneric
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @ClassName UtilKGeneric
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/10/22 3:53
 * @Version 1.0
 */
object UtilKGeneric {
    /**
     * 获取当前<>里面的泛型实例
     * @param index Int
     * @return Type?
     */
    @JvmStatic
    fun <T> getGenericType(index: Int = 0): Type? =
        object : BaseGeneric<T>() {}::class.java
            .genericSuperclass
            .let { it as ParameterizedType }
            .actualTypeArguments.filterIsInstance<Class<*>>()
            .run {
                if (this.isNotEmpty() && index in this.indices)
                    this[index]
                else
                    null
            }

    /**
     * 获取当前<>里面的泛型实例
     * @param index Int
     * @return Class<*>?
     */
    @JvmStatic
    fun <T> getGenericTypeClazz(index: Int = 0): Class<*>? =
        getGenericType<T>(index) as? Class<*>?

    ////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 获取父类泛型类
     * @param clazz Class<*>
     * @param index Int
     * @return Class<*>?
     */
    @JvmStatic
    fun getParentGenericTypeClazz(clazz: Class<*>, index: Int = 0): Class<*>? =
        getParentGenericType(clazz, index) as? Class<*>?

    /**
     * 获取父类泛型type
     * @param clazz Class<*>
     * @param index Int
     * @return Type?
     */
    @JvmStatic
    fun getParentGenericType(clazz: Class<*>, index: Int = 0): Type? {
        val genericSuperclass = clazz.genericSuperclass
        if (genericSuperclass !is ParameterizedType) return if (clazz.superclass != null) getParentGenericType(clazz.superclass, index) else null//当我们继承多层BaseActivity时递归查找泛型
        genericSuperclass
            .actualTypeArguments.filterIsInstance<Class<*>>()
            .run {
                return if (this.isNotEmpty() && index in this.indices)
                    this[index]
                else
                    null
            }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 获取继承父类的泛型类
     * @param index Int
     * @return Class<*>?
     */
    @JvmStatic
    inline fun <reified T> getParentGenericTypeClazz(index: Int = 0): Class<*>? =
        getParentGenericType<T>(index) as? Class<*>?

    /**
     * 获取继承父类的泛型Type
     * @param index Int
     * @return Type?
     */
    @JvmStatic
    inline fun <reified T> getParentGenericType(index: Int = 0): Type? =
        getParentGenericType(T::class.java, index)
}