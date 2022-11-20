package com.mozhimen.basick.animk.builder.commons

import android.util.Log
import android.util.SparseArray
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import com.mozhimen.basick.animk.builder.temps.AlphaType
import com.mozhimen.basick.animk.builder.mos.AnimKConfig
import com.mozhimen.basick.animk.builder.temps.RotationType
import com.mozhimen.basick.animk.builder.temps.ScaleType
import com.mozhimen.basick.animk.builder.temps.TranslationType
import com.mozhimen.basick.utilk.UtilKRes

/**
 * @ClassName AnimationApi
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/17 22:50
 * @Version 1.0
 */
abstract class IAnimKBuilder<T> {
    companion object {
        private val TAG = "IAnimKBuilder>>>>>"

        val DEFAULT_FILLBEFORE = false
        val DEFAULT_FILLAFTER = true
        val DEFAULT_DURATION = UtilKRes.getInteger(android.R.integer.config_longAnimTime).toLong()
        val DEFAULT_INTERPOLATOR: Interpolator = AccelerateDecelerateInterpolator()
    }

    protected var _animKConfig = AnimKConfig(DEFAULT_FILLBEFORE, DEFAULT_FILLAFTER, DEFAULT_DURATION, DEFAULT_INTERPOLATOR)
    protected var _types: SparseArray<IAnimKType<*>> = SparseArray()


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

    fun asAlpha(type: AlphaType): T {
        appendConfigs(type)
        return this as T
    }

    fun asScale(type: ScaleType): T {
        appendConfigs(type)
        return this as T
    }

    fun asTranslation(type: TranslationType): T {
        appendConfigs(type)
        return this as T
    }

    fun asRotation(type: RotationType): T {
        appendConfigs(type)
        return this as T
    }

    private fun appendConfigs(type: IAnimKType<*>) {
        _types.delete(type.getKey())//同类型的只能作用一个
        _types.append(type.getKey(), type)
        Log.d(TAG, "appendConfigs: $_types")
    }
}