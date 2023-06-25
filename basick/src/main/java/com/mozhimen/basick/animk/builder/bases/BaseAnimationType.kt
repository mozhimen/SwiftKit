package com.mozhimen.basick.animk.builder.bases

import android.view.animation.Animation
import com.mozhimen.basick.animk.builder.commons.IAnimationType
import com.mozhimen.basick.animk.builder.mos.AnimKConfig

/**
 * @ClassName BaseAnimationType
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/26 17:46
 * @Version 1.0
 */
abstract class BaseAnimationType<T> : BasePropertyType<T>(), IAnimationType {
    override fun formatAnimation(animKConfig: AnimKConfig, animation: Animation) {
        super.formatAnimation(animKConfig, animation)
        animation.interpolator = _interpolator ?: animKConfig.interpolator
    }
}