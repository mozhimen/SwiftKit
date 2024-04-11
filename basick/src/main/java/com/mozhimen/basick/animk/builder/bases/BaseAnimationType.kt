package com.mozhimen.basick.animk.builder.bases

import android.view.animation.Animation
import com.mozhimen.basick.animk.builder.commons.IAnimationType
import com.mozhimen.basick.animk.builder.mos.MAnimKConfig

/**
 * @ClassName BaseAnimationType
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Version 1.0
 */
abstract class BaseAnimationType<T> : BasePropertyType<T>(), IAnimationType {
    override fun formatAnimation(animKConfig: MAnimKConfig, animation: Animation) {
        super.formatAnimation(animKConfig, animation)
        animation.interpolator = _interpolator ?: animKConfig.interpolator
    }
}