package com.mozhimen.basick.animk.builder.bases

import android.util.Log
import android.util.SparseArray
import android.view.animation.Interpolator
import com.mozhimen.basick.animk.builder.commons.IAnimationType
import com.mozhimen.basick.animk.builder.commons.IAnimatorType
import com.mozhimen.basick.animk.builder.temps.AlphaType
import com.mozhimen.basick.animk.builder.temps.RotationType
import com.mozhimen.basick.animk.builder.temps.ScaleType
import com.mozhimen.basick.animk.builder.temps.TranslationType

/**
 * @ClassName BaseAnimatorBuilder
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/26 18:46
 * @Version 1.0
 */
open class BaseAnimatorBuilder<T> : BaseAnimKBuilder() {
    protected var _types: SparseArray<IAnimatorType> = SparseArray()

    fun setFillBefore(fillBefore: Boolean = false): T {
        this._animKConfig.fillBefore = fillBefore
        return this as T
    }

    fun setFillAfter(fillAfter: Boolean = true): T {
        this._animKConfig.fillAfter = fillAfter
        return this as T
    }

    fun setDuration(duration: Long = DEFAULT_DURATION): T {
        this._animKConfig.duration = duration
        return this as T
    }

    fun setInterpolator(interpolator: Interpolator = DEFAULT_INTERPOLATOR): T {
        this._animKConfig.interpolator = interpolator
        return this as T
    }

    fun add(type: IAnimatorType): T {
        appendConfigs(type)
        return this as T
    }

    private fun appendConfigs(type: IAnimatorType) {
        _types.delete((type as BaseType<*>).getKey())//同类型的只能作用一个
        _types.append(type.getKey(), type)
        Log.d(TAG, "appendConfigs: $_types")
    }
}