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
abstract class BaseAnimationType<T> : BaseProperty<T>(), IAnimationType {
    override fun formatAnim(animKConfig: MAnimKConfig, anim: Animation) {
        super.formatAnim(animKConfig, anim)
        anim.interpolator = _interpolator ?: animKConfig.interpolator
    }
}