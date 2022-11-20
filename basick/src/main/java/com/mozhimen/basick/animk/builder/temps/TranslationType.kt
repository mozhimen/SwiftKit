package com.mozhimen.basick.animk.builder.temps

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.util.Property
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import com.mozhimen.basick.animk.builder.commons.IAnimKType
import com.mozhimen.basick.animk.builder.cons.EDirection
import com.mozhimen.basick.animk.builder.mos.AnimKConfig

/**
 * @ClassName TranslationConfig
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/17 23:03
 * @Version 1.0
 */
open class TranslationType : IAnimKType<TranslationType>() {
    private var _fromX = 0f
    private var _toX = 0f
    private var _fromY = 0f
    private var _toY = 0f
    private var _isPercentageFromX = false
    private var _isPercentageToX = false
    private var _isPercentageFromY = false
    private var _isPercentageToY = false

    fun from(vararg directions: EDirection): TranslationType {
        if (directions.isNotEmpty()) {
            _fromY = 0f
            _fromX = _fromY
            var flag = 0
            for (direction in directions) {
                flag = flag or direction.flag
            }
            if (EDirection.isDirectionFlag(EDirection.LEFT, flag)) {
                fromX(_fromX - 1, true)
            }
            if (EDirection.isDirectionFlag(EDirection.RIGHT, flag)) {
                fromX(_fromX + 1, true)
            }
            if (EDirection.isDirectionFlag(EDirection.CENTER_HORIZONTAL, flag)) {
                fromX(_fromX + 0.5f, true)
            }
            if (EDirection.isDirectionFlag(EDirection.TOP, flag)) {
                fromY(_fromY - 1, true)
            }
            if (EDirection.isDirectionFlag(EDirection.BOTTOM, flag)) {
                fromY(_fromY + 1, true)
            }
            if (EDirection.isDirectionFlag(EDirection.CENTER_VERTICAL, flag)) {
                fromY(_fromY + 0.5f, true)
            }
            _isPercentageToY = true
            _isPercentageToX = _isPercentageToY
            _isPercentageFromY = _isPercentageToX
            _isPercentageFromX = _isPercentageFromY
        }
        return this
    }

    fun to(vararg directions: EDirection): TranslationType {
        if (directions.isNotEmpty()) {
            _toY = 0f
            _toX = _toY
            var flag = 0
            for (direction in directions) {
                flag = flag or direction.flag
            }
            if (EDirection.isDirectionFlag(EDirection.LEFT, flag)) {
                _toX += -1f
            }
            if (EDirection.isDirectionFlag(EDirection.RIGHT, flag)) {
                _toX += 1f
            }
            if (EDirection.isDirectionFlag(EDirection.CENTER_HORIZONTAL, flag)) {
                _toX += .5f
            }
            if (EDirection.isDirectionFlag(EDirection.TOP, flag)) {
                _toY += -1f
            }
            if (EDirection.isDirectionFlag(EDirection.BOTTOM, flag)) {
                _toY += 1f
            }
            if (EDirection.isDirectionFlag(EDirection.CENTER_VERTICAL, flag)) {
                _toY += .5f
            }
            _isPercentageToY = true
            _isPercentageToX = _isPercentageToY
            _isPercentageFromY = _isPercentageToX
            _isPercentageFromX = _isPercentageFromY
        }
        return this
    }

    fun fromX(fromX: Float): TranslationType {
        fromX(fromX, true)
        return this
    }

    fun toX(toX: Float): TranslationType {
        toX(toX, true)
        return this
    }

    fun fromY(fromY: Float): TranslationType {
        fromY(fromY, true)
        return this
    }

    fun toY(toY: Float): TranslationType {
        toY(toY, true)
        return this
    }

    fun fromX(fromX: Int): TranslationType {
        fromX(fromX.toFloat(), false)
        return this
    }

    fun toX(toX: Int): TranslationType {
        toX(toX.toFloat(), false)
        return this
    }

    fun fromY(fromY: Int): TranslationType {
        fromY(fromY.toFloat(), false)
        return this
    }

    fun toY(toY: Int): TranslationType {
        toY(toY.toFloat(), false)
        return this
    }

    fun fromX(fromX: Float, percentage: Boolean): TranslationType {
        _isPercentageFromX = percentage
        this._fromX = fromX
        return this
    }

    fun toX(toX: Float, percentage: Boolean): TranslationType {
        _isPercentageToX = percentage
        this._toX = toX
        return this
    }

    fun fromY(fromY: Float, percentage: Boolean): TranslationType {
        _isPercentageFromY = percentage
        this._fromY = fromY
        return this
    }

    fun toY(toY: Float, percentage: Boolean): TranslationType {
        _isPercentageToY = percentage
        this._toY = toY
        return this
    }

    override fun buildAnimation(animKConfig: AnimKConfig): Animation {
        val animation: Animation = TranslateAnimation(
            if (_isPercentageFromX) Animation.RELATIVE_TO_SELF else Animation.ABSOLUTE,
            _fromX,
            if (_isPercentageToX) Animation.RELATIVE_TO_SELF else Animation.ABSOLUTE,
            _toX,
            if (_isPercentageFromY) Animation.RELATIVE_TO_SELF else Animation.ABSOLUTE,
            _fromY,
            if (_isPercentageToY) Animation.RELATIVE_TO_SELF else Animation.ABSOLUTE,
            _toY
        )
        formatAnimation(animKConfig, animation)
        return animation
    }

    override fun buildAnimator(animKConfig: AnimKConfig): Animator {
        val animatorSet = AnimatorSet()
        val translationXProperty = if (_isPercentageFromX && _isPercentageToY) object : FloatPropertyCompat<View>(
            View.TRANSLATION_X.name
        ) {
            override fun setValue(obj: View, value: Float) {
                obj.translationX = obj.width * value
            }

            override operator fun get(obj: View): Float {
                return obj.translationX
            }
        } else View.TRANSLATION_X
        val translationYProperty = if (_isPercentageFromY && _isPercentageToY) object : FloatPropertyCompat<View>(
            View.TRANSLATION_Y.name
        ) {
            override fun setValue(obj: View, value: Float) {
                obj.translationY = obj.height * value
            }

            override operator fun get(obj: View): Float {
                return obj.translationY
            }
        } else View.TRANSLATION_Y
        val translationX = ObjectAnimator.ofFloat(null, translationXProperty, _fromX, _toX)
        val translationY = ObjectAnimator.ofFloat(null, translationYProperty, _fromY, _toY)
        animatorSet.playTogether(translationX, translationY)
        formatAnimator(animKConfig, animatorSet)
        return animatorSet
    }

    abstract class FloatPropertyCompat<T>(name: String) : Property<T, Float>(Float::class.java, name) {
        /**
         * A type-specific variant of [.set] that is faster when dealing
         * with fields of type `float`.
         */
        abstract fun setValue(obj: T, value: Float)

        override operator fun set(obj: T, value: Float) {
            setValue(obj, value)
        }
    }

    companion object {
        val FROM_LEFT_SHOW: TranslationType = TranslationType().apply {
            from(EDirection.LEFT)
        }
        val FROM_TOP_SHOW: TranslationType = TranslationType().apply {
            from(EDirection.TOP)
        }
        val FROM_RIGHT_SHOW: TranslationType = TranslationType().apply {
            from(EDirection.RIGHT)
        }
        val FROM_BOTTOM_SHOW: TranslationType = TranslationType().apply {
            from(EDirection.BOTTOM)
        }
        val TO_LEFT_HIDE: TranslationType = TranslationType().apply {
            to(EDirection.LEFT)
        }
        val TO_TOP_HIDE: TranslationType = TranslationType().apply {
            to(EDirection.TOP)
        }
        val TO_RIGHT_HIDE: TranslationType = TranslationType().apply {
            to(EDirection.RIGHT)
        }
        val TO_BOTTOM_HIDE: TranslationType = TranslationType().apply {
            to(EDirection.BOTTOM)
        }
    }
}
