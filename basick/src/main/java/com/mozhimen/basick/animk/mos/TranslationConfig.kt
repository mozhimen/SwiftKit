package com.mozhimen.basick.animk.mos

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import com.mozhimen.basick.animk.commons.IAnimKConfig
import com.mozhimen.basick.animk.cons.EDirection
import com.mozhimen.basick.animk.helpers.FloatPropertyCompat

/**
 * @ClassName TranslationConfig
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/17 23:03
 * @Version 1.0
 */
open class TranslationConfig : IAnimKConfig<TranslationConfig> {
    var fromX = 0f
    var toX = 0f
    var fromY = 0f
    var toY = 0f
    var isPercentageFromX = false
    var isPercentageToX = false
    var isPercentageFromY = false
    var isPercentageToY = false

    override fun resetInternal() {
        toY = 0f
        fromY = toY
        toX = fromY
        fromX = toX
        isPercentageToY = false
        isPercentageFromY = isPercentageToY
        isPercentageToX = isPercentageFromY
        isPercentageFromX = isPercentageToX
    }

    constructor() : super(false, false) {
        resetInternal()
    }

    constructor(resetParent: Boolean, resetInternal: Boolean) : super(resetParent, resetInternal) {
        resetInternal()
    }

    fun from(vararg directions: EDirection): TranslationConfig {
        if (directions.isNotEmpty()) {
            fromY = 0f
            fromX = fromY
            var flag = 0
            for (direction in directions) {
                flag = flag or direction.flag
            }
            if (EDirection.isDirectionFlag(EDirection.LEFT, flag)) {
                fromX(fromX - 1, true)
            }
            if (EDirection.isDirectionFlag(EDirection.RIGHT, flag)) {
                fromX(fromX + 1, true)
            }
            if (EDirection.isDirectionFlag(EDirection.CENTER_HORIZONTAL, flag)) {
                fromX(fromX + 0.5f, true)
            }
            if (EDirection.isDirectionFlag(EDirection.TOP, flag)) {
                fromY(fromY - 1, true)
            }
            if (EDirection.isDirectionFlag(EDirection.BOTTOM, flag)) {
                fromY(fromY + 1, true)
            }
            if (EDirection.isDirectionFlag(EDirection.CENTER_VERTICAL, flag)) {
                fromY(fromY + 0.5f, true)
            }
            isPercentageToY = true
            isPercentageToX = isPercentageToY
            isPercentageFromY = isPercentageToX
            isPercentageFromX = isPercentageFromY
        }
        return this
    }

    fun to(vararg directions: EDirection): TranslationConfig {
        if (directions.isNotEmpty()) {
            toY = 0f
            toX = toY
            var flag = 0
            for (direction in directions) {
                flag = flag or direction.flag
            }
            if (EDirection.isDirectionFlag(EDirection.LEFT, flag)) {
                toX += -1f
            }
            if (EDirection.isDirectionFlag(EDirection.RIGHT, flag)) {
                toX += 1f
            }
            if (EDirection.isDirectionFlag(EDirection.CENTER_HORIZONTAL, flag)) {
                toX += .5f
            }
            if (EDirection.isDirectionFlag(EDirection.TOP, flag)) {
                toY += -1f
            }
            if (EDirection.isDirectionFlag(EDirection.BOTTOM, flag)) {
                toY += 1f
            }
            if (EDirection.isDirectionFlag(EDirection.CENTER_VERTICAL, flag)) {
                toY += .5f
            }
            isPercentageToY = true
            isPercentageToX = isPercentageToY
            isPercentageFromY = isPercentageToX
            isPercentageFromX = isPercentageFromY
        }
        return this
    }

    fun fromX(fromX: Float): TranslationConfig {
        fromX(fromX, true)
        return this
    }

    fun toX(toX: Float): TranslationConfig {
        toX(toX, true)
        return this
    }

    fun fromY(fromY: Float): TranslationConfig {
        fromY(fromY, true)
        return this
    }

    fun toY(toY: Float): TranslationConfig {
        toY(toY, true)
        return this
    }

    fun fromX(fromX: Int): TranslationConfig {
        fromX(fromX.toFloat(), false)
        return this
    }

    fun toX(toX: Int): TranslationConfig {
        toX(toX.toFloat(), false)
        return this
    }

    fun fromY(fromY: Int): TranslationConfig {
        fromY(fromY.toFloat(), false)
        return this
    }

    fun toY(toY: Int): TranslationConfig {
        toY(toY.toFloat(), false)
        return this
    }

    fun fromX(fromX: Float, percentage: Boolean): TranslationConfig {
        isPercentageFromX = percentage
        this.fromX = fromX
        return this
    }

    fun toX(toX: Float, percentage: Boolean): TranslationConfig {
        isPercentageToX = percentage
        this.toX = toX
        return this
    }

    fun fromY(fromY: Float, percentage: Boolean): TranslationConfig {
        isPercentageFromY = percentage
        this.fromY = fromY
        return this
    }

    fun toY(toY: Float, percentage: Boolean): TranslationConfig {
        isPercentageToY = percentage
        this.toY = toY
        return this
    }

    override fun toString(): String {
        return "TranslationConfig{" +
                "fromX=" + fromX +
                ", toX=" + toX +
                ", fromY=" + fromY +
                ", toY=" + toY +
                ", isPercentageFromX=" + isPercentageFromX +
                ", isPercentageToX=" + isPercentageToX +
                ", isPercentageFromY=" + isPercentageFromY +
                ", isPercentageToY=" + isPercentageToY +
                '}'
    }

    override fun buildAnimation(isRevert: Boolean): Animation {
        val animation: Animation = TranslateAnimation(
            if (isPercentageFromX) Animation.RELATIVE_TO_SELF else Animation.ABSOLUTE,
            fromX,
            if (isPercentageToX) Animation.RELATIVE_TO_SELF else Animation.ABSOLUTE,
            toX,
            if (isPercentageFromY) Animation.RELATIVE_TO_SELF else Animation.ABSOLUTE,
            fromY,
            if (isPercentageToY) Animation.RELATIVE_TO_SELF else Animation.ABSOLUTE,
            toY
        )
        deploy(animation)
        return animation
    }

    override fun buildAnimator(isRevert: Boolean): Animator {
        val animatorSet = AnimatorSet()
        val TRANSLATION_X = if (isPercentageFromX && isPercentageToY) object : FloatPropertyCompat<View>(
            View.TRANSLATION_X.name
        ) {
            override fun setValue(obj: View, value: Float) {
                obj.translationX = obj.width * value
            }

            override operator fun get(obj: View): Float {
                return obj.translationX
            }
        } else View.TRANSLATION_X
        val TRANSLATION_Y = if (isPercentageFromY && isPercentageToY) object : FloatPropertyCompat<View>(
            View.TRANSLATION_Y.name
        ) {
            override fun setValue(obj: View, value: Float) {
                obj.translationY = obj.height * value
            }

            override operator fun get(obj: View): Float {
                return obj.translationY
            }
        } else View.TRANSLATION_Y
        val translationX = ObjectAnimator.ofFloat(null, TRANSLATION_X, fromX, toX)
        val translationY = ObjectAnimator.ofFloat(null, TRANSLATION_Y, fromY, toY)
        animatorSet.playTogether(translationX, translationY)
        deploy(animatorSet)
        return animatorSet
    }

    companion object {
        private const val TAG = "TranslationConfig"

        //------------------default
        val FROM_LEFT: TranslationConfig = object : TranslationConfig(true, true) {
            override fun resetInternal() {
                super.resetInternal()
                from(EDirection.LEFT)
            }
        }
        val FROM_TOP: TranslationConfig = object : TranslationConfig(true, true) {
            override fun resetInternal() {
                super.resetInternal()
                from(EDirection.TOP)
            }
        }
        val FROM_RIGHT: TranslationConfig = object : TranslationConfig(true, true) {
            override fun resetInternal() {
                super.resetInternal()
                from(EDirection.RIGHT)
            }
        }
        val FROM_BOTTOM: TranslationConfig = object : TranslationConfig(true, true) {
            override fun resetInternal() {
                super.resetInternal()
                from(EDirection.BOTTOM)
            }
        }
        val TO_LEFT: TranslationConfig = object : TranslationConfig(true, true) {
            override fun resetInternal() {
                super.resetInternal()
                to(EDirection.LEFT)
            }
        }
        val TO_TOP: TranslationConfig = object : TranslationConfig(true, true) {
            override fun resetInternal() {
                super.resetInternal()
                to(EDirection.TOP)
            }
        }
        val TO_RIGHT: TranslationConfig = object : TranslationConfig(true, true) {
            override fun resetInternal() {
                super.resetInternal()
                to(EDirection.RIGHT)
            }
        }
        val TO_BOTTOM: TranslationConfig = object : TranslationConfig(true, true) {
            override fun resetInternal() {
                super.resetInternal()
                to(EDirection.BOTTOM)
            }
        }
    }
}
