package com.mozhimen.basick.animk.builder.bases

import android.util.SparseArray
import android.view.animation.Interpolator
import com.mozhimen.basick.animk.builder.commons.IAnimCreateListener
import com.mozhimen.basick.animk.builder.commons.IAnimType
import com.mozhimen.basick.animk.builder.mos.MAnimKConfig
import com.mozhimen.basick.elemk.kotlin.cons.CSuppress
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.utilk.commons.IUtilK

/**
 * @ClassName IBaseAnimKBuilder
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/26 18:48
 * @Version 1.0
 */
@Suppress(CSuppress.UNCHECKED_CAST)
abstract class BaseAnimBuilder<BUILDER, ANIM, ANIMSET> : IUtilK {
    protected var _animKConfig = MAnimKConfig()
    protected var _types: SparseArray<IAnimType<ANIM>> = SparseArray()

    fun setFillBefore(fillBefore: Boolean): BUILDER {
        _animKConfig.fillBefore = fillBefore
        return this as BUILDER
    }

    fun setFillAfter(fillAfter: Boolean): BUILDER {
        _animKConfig.fillAfter = fillAfter
        return this as BUILDER
    }

    fun setDuration(duration: Long): BUILDER {
        _animKConfig.duration = duration
        return this as BUILDER
    }

    fun setInterpolator(interpolator: Interpolator): BUILDER {
        _animKConfig.interpolator = interpolator
        return this as BUILDER
    }

    fun combine(type: IAnimType<ANIM>): BUILDER {
        _types.delete((type as BaseProperty<*>).getKey())//同类型的只能作用一个
        _types.append(type.getKey(), type)
        UtilKLogWrapper.d(TAG, "add: type $type _types $_types")

        return this as BUILDER
    }

    ////////////////////////////////////////////////////

    abstract fun build(): ANIM

    abstract fun build(listener: IAnimCreateListener<ANIM, ANIMSET>?): ANIM
}