package com.mozhimen.basick.animk.builder.bases

import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import android.util.SparseArray
import android.view.animation.Interpolator
import com.mozhimen.basick.animk.builder.commons.IAnimationType
import com.mozhimen.basick.elemk.kotlin.cons.CSuppress

/**
 * @ClassName AnimationApi
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/17 22:50
 * @Version 1.0
 */
@Suppress(CSuppress.UNCHECKED_CAST)
abstract class BaseAnimationBuilder<T> : BaseAnimKBuilder() {
    protected var _types: SparseArray<IAnimationType> = SparseArray()

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

    fun add(type: IAnimationType): T {
        appendConfigs(type)
        return this as T
    }

    private fun appendConfigs(type: IAnimationType) {
        _types.delete((type as BasePropertyType<*>).getKey())//同类型的只能作用一个
        _types.append(type.getKey(), type)
        UtilKLogWrapper.d(TAG, "appendConfigs: $_types")
    }
}