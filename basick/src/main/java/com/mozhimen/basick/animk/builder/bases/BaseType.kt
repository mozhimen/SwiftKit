package com.mozhimen.basick.animk.builder.bases

import android.view.animation.Interpolator
import androidx.annotation.FloatRange

/**
 * @ClassName BaseAnimationConfig
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/17 22:44
 * @Version 1.0
 */
abstract class BaseType<T> {

    protected var _interpolator: Interpolator? = null
    protected var _pivotX = 0f
    protected var _pivotY = 0f
    protected var _pivotX2 = 0f
    protected var _pivotY2 = 0f

    fun setInterpolator(interpolator: Interpolator): T {
        _interpolator = interpolator
        return this as T
    }

    fun setPivot(@FloatRange(from = 0.0, to = 1.0) x: Float, @FloatRange(from = 0.0, to = 1.0) y: Float): T {
        _pivotX = x
        _pivotY = y
        return this as T
    }

    fun setPivot2(@FloatRange(from = 0.0, to = 1.0) x: Float, @FloatRange(from = 0.0, to = 1.0) y: Float): T {
        _pivotX2 = x
        _pivotY2 = y
        return this as T
    }

    fun setPivotX(@FloatRange(from = 0.0, to = 1.0) x: Float): T {
        _pivotX = x
        return this as T
    }

    fun setPivotY(@FloatRange(from = 0.0, to = 1.0) y: Float): T {
        _pivotY = y
        return this as T
    }

    fun setPivotX2(@FloatRange(from = 0.0, to = 1.0) x: Float): T {
        _pivotX2 = x
        return this as T
    }

    fun setPivotY2(@FloatRange(from = 0.0, to = 1.0) y: Float): T {
        _pivotY2 = y
        return this as T
    }

    internal fun getKey(): Int {
        return this.javaClass.toString().hashCode()
    }
}