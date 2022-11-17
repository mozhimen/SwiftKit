package com.mozhimen.basick.animk.helpers

import android.util.Property

/**
 * @ClassName FloatPropertyCompat
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/17 23:00
 * @Version 1.0
 */
abstract class FloatPropertyCompat<T>(name: String) : Property<T, Float>(Float::class.java, name) {
    /**
     * A type-specific variant of [.set] that is faster when dealing
     * with fields of type `float`.
     */
    abstract fun setValue(obj: T, value: Float)

    override operator fun set(obj: T, value: Float) {
        setValue(obj, value)
    }
}