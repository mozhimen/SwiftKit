package com.mozhimen.basick.utilk.java.lang

import android.util.Log
import com.mozhimen.basick.elemk.java.lang.bases.BaseGeneric
import com.mozhimen.basick.utilk.bases.IUtilK
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @ClassName UtilKGeneric
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/10/22 3:53
 * @Version 1.0
 */
object UtilKReflectGenericKotlin : IUtilK {
    /**
     * 获取当前<>里面的泛型实例
     * @param index Int
     * @return Class<*>?
     */
    @JvmStatic
    inline fun <reified T> getGenericTypeClazz(index: Int = 0): Class<*>? =
        getGenericType<T>(index) as? Class<*>?

    /**
     * 获取当前<>里面的泛型实例
     * @param index Int
     * @return Type?
     */
    @JvmStatic
    inline fun <reified T> getGenericType(index: Int = 0): Type? =
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

    @JvmStatic
    fun getParentGenericTypeByTClazz(clazz: Class<*>, tClazz: Class<*>/*, index: Int = 0*/): Class<*>? =
        getParentGenericTypeByT(clazz, tClazz/*, index*/) as? Class<*>?

    @JvmStatic
    fun getParentGenericTypeByT(clazz: Class<*>, tClazz: Class<*>/*, index: Int = 0*/): Type? {
        val superClazz: Class<*>? = clazz.superclass
        val genericSuperclass: Type? = clazz.genericSuperclass
        if (genericSuperclass !is ParameterizedType) {//当继承类不是参数化类型,就从父类中寻找
            return if (superClazz != null) {
                getParentGenericTypeByT(superClazz, tClazz)//当我们继承多层BaseActivity时递归查找泛型
            } else
                null
        }
        genericSuperclass.actualTypeArguments.filterIsInstance<Class<*>>()
            .run {
//                Log.d(TAG, "getParentGenericTypeByT: ${this.map { it.simpleName }}")
                if (isNotEmpty()) {
                    for (clz in this) {
                        if (clz.isAssignableFrom(tClazz))
                            return clz
                    }
                }
                if (superClazz != null)
                    return getParentGenericTypeByT(superClazz, tClazz)
                else
                    return null
            }
    }

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
        if (genericSuperclass !is ParameterizedType) {//当继承类不是参数化类型,就从父类中寻找
            if (clazz.superclass != null) {
                return getParentGenericType(clazz.superclass, index)
            } else
                return null//当我们继承多层BaseActivity时递归查找泛型
        }
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