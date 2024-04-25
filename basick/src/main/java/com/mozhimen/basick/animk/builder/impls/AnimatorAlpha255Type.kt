package com.mozhimen.basick.animk.builder.impls

import androidx.annotation.FloatRange

/**
 * @ClassName DrawableAlphaType
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Version 1.0
 */
open class AnimatorAlpha255Type : AnimatorIntType() {

    fun setAlpha(@FloatRange(from = 0.0, to = 1.0) fromAlpha: Float, @FloatRange(from = 0.0, to = 1.0) toAlpha: Float): AnimatorAlpha255Type {
        setInt((fromAlpha * 255).toInt(), (fromAlpha * 255).toInt())
        return this
    }

    fun show(): AnimatorAlpha255Type {
        setAlpha(0f, 1f)
        return this
    }

    fun hide(): AnimatorAlpha255Type {
        setAlpha(1f, 0f)
        return this
    }

    //////////////////////////////////////////////////////

    companion object {
        val SHOW: AnimatorAlpha255Type = AnimatorAlpha255Type().show()

        val HIDE: AnimatorAlpha255Type = AnimatorAlpha255Type().hide()
    }
}