package com.mozhimen.basick.animk.builder.bases

import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import com.mozhimen.basick.animk.builder.mos.AnimKConfig
import com.mozhimen.basick.utilk.android.content.UtilKRes
import com.mozhimen.basick.utilk.bases.IUtilK

/**
 * @ClassName IBaseAnimKBuilder
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/26 18:48
 * @Version 1.0
 */
open class BaseAnimKBuilder : IUtilK {
    companion object {
        val DEFAULT_FILLBEFORE = false
        val DEFAULT_FILLAFTER = true
        val DEFAULT_DURATION = UtilKRes.getInteger(android.R.integer.config_longAnimTime).toLong()
        val DEFAULT_INTERPOLATOR: Interpolator = AccelerateDecelerateInterpolator()
    }

    protected var _animKConfig = AnimKConfig(DEFAULT_FILLBEFORE, DEFAULT_FILLAFTER, DEFAULT_DURATION, DEFAULT_INTERPOLATOR)
}