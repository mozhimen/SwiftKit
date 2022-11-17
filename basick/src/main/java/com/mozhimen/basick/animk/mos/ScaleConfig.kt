package com.mozhimen.basick.animk.mos

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import com.mozhimen.basick.animk.commons.IAnimKConfig
import com.mozhimen.basick.animk.cons.EDirection

/**
 * @ClassName ScaleConfig
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/17 23:02
 * @Version 1.0
 */
open class ScaleConfig : IAnimKConfig<ScaleConfig> {
    var scaleFromX = 0f
    var scaleFromY = 0f
    var scaleToX = 1f
    var scaleToY = 1f
    var changeFrom = false
    var changeTo = false
    override fun resetInternal() {
        scaleFromX = 0f
        scaleFromY = 0f
        scaleToX = 1f
        scaleToY = 1f
        changeFrom = false
        changeTo = false
        pivot(.5f, .5f)
        pivot2(.5f, .5f)
    }

    constructor() : super(false, false) {
        resetInternal()
    }

    constructor(resetParent: Boolean, resetInternal: Boolean) : super(resetParent, resetInternal) {
        resetInternal()
    }

    fun scale(from: Float, to: Float): ScaleConfig {
        scaleFromY = from
        scaleFromX = scaleFromY
        scaleToY = to
        scaleToX = scaleToY
        changeTo = true
        changeFrom = changeTo
        return this
    }

    fun from(vararg from: EDirection): ScaleConfig {
        if (from != null) {
            if (!changeFrom) {
                scaleFromY = 1f
                scaleFromX = scaleFromY
            }
            var flag = 0
            for (direction in from) {
                flag = flag or direction.flag
            }
            if (EDirection.isDirectionFlag(EDirection.LEFT, flag)) {
                pivotX = 0f
                scaleFromX = if (changeFrom) scaleFromX else 0F
            }
            if (EDirection.isDirectionFlag(EDirection.RIGHT, flag)) {
                pivotX = 1f
                scaleFromX = if (changeFrom) scaleFromX else 0f
            }
            if (EDirection.isDirectionFlag(EDirection.CENTER_HORIZONTAL, flag)) {
                pivotX = 0.5f
                scaleFromX = if (changeFrom) scaleFromX else 0f
            }
            if (EDirection.isDirectionFlag(EDirection.TOP, flag)) {
                pivotY = 0f
                scaleFromY = if (changeFrom) scaleFromY else 0f
            }
            if (EDirection.isDirectionFlag(EDirection.BOTTOM, flag)) {
                pivotY = 1f
                scaleFromY = if (changeFrom) scaleFromY else 0f
            }
            if (EDirection.isDirectionFlag(EDirection.CENTER_VERTICAL, flag)) {
                pivotY = 0.5f
                scaleFromY = if (changeFrom) scaleFromY else 0f
            }
        }
        return this
    }

    fun to(vararg to: EDirection): ScaleConfig {
        if (to != null) {
            if (!changeTo) {
                scaleToY = 1f
                scaleToX = scaleToY
            }
            var flag = 0
            for (direction in to) {
                flag = flag or direction.flag
            }
            if (EDirection.isDirectionFlag(EDirection.LEFT, flag)) {
                pivotX2 = 0f
            }
            if (EDirection.isDirectionFlag(EDirection.RIGHT, flag)) {
                pivotX2 = 1f
            }
            if (EDirection.isDirectionFlag(EDirection.CENTER_HORIZONTAL, flag)) {
                pivotX2 = 0.5f
            }
            if (EDirection.isDirectionFlag(EDirection.TOP, flag)) {
                pivotY2 = 0f
            }
            if (EDirection.isDirectionFlag(EDirection.BOTTOM, flag)) {
                pivotY2 = 1f
            }
            if (EDirection.isDirectionFlag(EDirection.CENTER_VERTICAL, flag)) {
                pivotY2 = 0.5f
            }
        }
        return this
    }

    fun scaleX(from: Float, to: Float): ScaleConfig {
        scaleFromX = from
        scaleToX = to
        changeFrom = true
        return this
    }

    fun sclaeY(from: Float, to: Float): ScaleConfig {
        scaleFromY = from
        scaleToY = to
        changeTo = true
        return this
    }

    override fun toString(): String {
        return "ScaleConfig{" +
                "scaleFromX=" + scaleFromX +
                ", scaleFromY=" + scaleFromY +
                ", scaleToX=" + scaleToX +
                ", scaleToY=" + scaleToY +
                '}'
    }

    /**
     * 0 = fromx
     * 1 = tox
     * 2 = fromy
     * 3 = toy
     * 4 = pivotx
     * 5 = pivoty
     */
    fun values(isRevert: Boolean): FloatArray {
        val result = FloatArray(6)
        result[0] = if (isRevert) scaleToX else scaleFromX
        result[1] = if (isRevert) scaleFromX else scaleToX
        result[2] = if (isRevert) scaleToY else scaleFromY
        result[3] = if (isRevert) scaleFromY else scaleToY
        result[4] = if (isRevert) pivotX2 else pivotX
        result[5] = if (isRevert) pivotY2 else pivotY
        return result
    }

    override fun buildAnimation(isRevert: Boolean): Animation {
        val values = values(isRevert)
        val animation: Animation = ScaleAnimation(
            values[0], values[1], values[2], values[3],
            Animation.RELATIVE_TO_SELF, values[4],
            Animation.RELATIVE_TO_SELF, values[5]
        )
        deploy(animation)
        return animation
    }

    override fun buildAnimator(isRevert: Boolean): Animator {
        val values = values(isRevert)
        val animatorSet = AnimatorSet()
        val scaleX: Animator = ObjectAnimator.ofFloat(null, View.SCALE_X, values[0], values[1])
        val scaleY: Animator = ObjectAnimator.ofFloat(null, View.SCALE_Y, values[2], values[3])
        scaleX.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                val target = (animation as ObjectAnimator).target
                if (target is View) {
                    target.pivotX = target.width * values[4]
                }
            }
        })
        scaleY.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                val target = (animation as ObjectAnimator).target
                if (target is View) {
                    target.pivotY = target.height * values[5]
                }
            }
        })
        animatorSet.playTogether(scaleX, scaleY)
        deploy(animatorSet)
        return animatorSet
    }

    companion object {
        val LEFT_TO_RIGHT: ScaleConfig = object : ScaleConfig(true, true) {
            override fun resetInternal() {
                super.resetInternal()
                from(EDirection.LEFT)
                to(EDirection.RIGHT)
            }
        }
        val RIGHT_TO_LEFT: ScaleConfig = object : ScaleConfig(true, true) {
            override fun resetInternal() {
                super.resetInternal()
                from(EDirection.RIGHT)
                to(EDirection.LEFT)
            }
        }
        val TOP_TO_BOTTOM: ScaleConfig = object : ScaleConfig(true, true) {
            override fun resetInternal() {
                super.resetInternal()
                from(EDirection.TOP)
                to(EDirection.BOTTOM)
            }
        }
        val BOTTOM_TO_TOP: ScaleConfig = object : ScaleConfig(true, true) {
            override fun resetInternal() {
                super.resetInternal()
                from(EDirection.BOTTOM)
                to(EDirection.TOP)
            }
        }
        val CENTER: ScaleConfig = object : ScaleConfig(true, true) {
            override fun resetInternal() {
                super.resetInternal()
                from(EDirection.CENTER)
                to(EDirection.CENTER)
            }
        }
    }
}

