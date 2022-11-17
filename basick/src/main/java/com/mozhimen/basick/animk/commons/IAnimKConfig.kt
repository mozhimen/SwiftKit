package com.mozhimen.basick.animk.commons

import android.R
import android.animation.Animator
import android.content.res.Resources
import android.util.Log
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.Interpolator
import androidx.annotation.FloatRange

/**
 * @ClassName BaseAnimationConfig
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/17 22:44
 * @Version 1.0
 */
abstract class IAnimKConfig<T>(resetParent: Boolean, resetInternal: Boolean) {
    protected var TAG = this.javaClass.simpleName
    val DEFAULT_DURATION = Resources.getSystem().getInteger(R.integer.config_mediumAnimTime).toLong()
    val DEFAULT_INTERPOLATOR: Interpolator = AccelerateDecelerateInterpolator()
    var interpolator: Interpolator? = DEFAULT_INTERPOLATOR
    var duration = DEFAULT_DURATION
    var pivotX = 0f
    var pivotY = 0f
    var pivotX2 = 0f
    var pivotY2 = 0f
    var fillBefore = false
    var fillAfter = true
    var mResetParent = resetParent
    var mResetInternal = resetInternal

    open fun reset() {
        duration = DEFAULT_DURATION
        interpolator = DEFAULT_INTERPOLATOR
        pivotY2 = 0f
        pivotY = pivotY2
        pivotX = pivotY
        fillBefore = false
        fillAfter = true
    }

    open fun resetInternal() {}

    open fun interpolator(interpolator: Interpolator?): T {
        this.interpolator = interpolator
        return this as T
    }

    open fun duration(duration: Long): T {
        this.duration = duration
        return this as T
    }

    open fun pivot(@FloatRange(from = 0.0, to = 1.0) x: Float, @FloatRange(from = 0.0, to = 1.0) y: Float): T {
        pivotX = x
        pivotY = y
        return this as T
    }

    open fun pivot2(@FloatRange(from = 0.0, to = 1.0) x: Float, @FloatRange(from = 0.0, to = 1.0) y: Float): T {
        pivotX2 = x
        pivotY2 = y
        return this as T
    }


    open fun pivotX(@FloatRange(from = 0.0, to = 1.0) x: Float): T {
        pivotX = x
        return this as T
    }

    open fun pivotY(@FloatRange(from = 0.0, to = 1.0) y: Float): T {
        pivotY = y
        return this as T
    }

    open fun fillBefore(fillBefore: Boolean): T {
        this.fillBefore = fillBefore
        return this as T
    }

    open fun fillAfter(fillAfter: Boolean): T {
        this.fillAfter = fillAfter
        return this as T
    }


    open fun deploy(animation: Animation?) {
        if (animation == null) return
        animation.fillBefore = fillBefore
        animation.fillAfter = fillAfter
        animation.duration = duration
        animation.interpolator = interpolator
    }

    open fun deploy(animator: Animator?) {
        if (animator == null) return
        animator.duration = duration
        animator.interpolator = interpolator
    }

    open fun log() {
//        if (PopupLog.isOpenLog()) {
//            PopupLog.i(TAG, $toString(), this.toString());
//        }
        Log.i(TAG, "log: " + `$toString`() + this)
    }

    open fun `$toString`(): String {
        return "BaseConfig{" +
                "interpolator=" + (if (interpolator == null) "null" else interpolator!!.javaClass.simpleName) +
                ", duration=" + duration +
                ", pivotX=" + pivotX +
                ", pivotY=" + pivotY +
                ", fillBefore=" + fillBefore +
                ", fillAfter=" + fillAfter +
                '}'
    }

    fun `$buildAnimation`(isRevert: Boolean): Animation {
        log()
        val animation = buildAnimation(isRevert)
        if (mResetParent) {
            reset()
        }
        if (mResetInternal) {
            resetInternal()
        }
        return animation
    }

    fun `$buildAnimator`(isRevert: Boolean): Animator {
        log()
        val animator = buildAnimator(isRevert)
        if (mResetParent) {
            reset()
        }
        if (mResetInternal) {
            resetInternal()
        }
        return animator
    }

    protected abstract fun buildAnimation(isRevert: Boolean): Animation

    protected abstract fun buildAnimator(isRevert: Boolean): Animator

    open fun key(): Int {
        return this.javaClass.toString().hashCode()
    }
}