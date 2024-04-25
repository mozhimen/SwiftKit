package com.mozhimen.basick.animk.builder.impls

import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import com.mozhimen.basick.animk.builder.bases.BaseAnimationType
import com.mozhimen.basick.animk.builder.cons.EDirection
import com.mozhimen.basick.animk.builder.mos.MAnimKConfig

/**
 * @ClassName TranslationConfig
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/17 23:03
 * @Version 1.0
 */
open class AnimationTranslationType() : BaseAnimationType<AnimationTranslationType>() {
    private var _fromX = 0f
    private var _toX = 0f
    private var _fromY = 0f
    private var _toY = 0f
    private var _isPercentageFromX = false
    private var _isPercentageToX = false
    private var _isPercentageFromY = false
    private var _isPercentageToY = false

    fun from(vararg directions: EDirection): AnimationTranslationType {
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

    fun to(vararg directions: EDirection): AnimationTranslationType {
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

    fun fromX(fromX: Float): AnimationTranslationType {
        fromX(fromX, true)
        return this
    }

    fun toX(toX: Float): AnimationTranslationType {
        toX(toX, true)
        return this
    }

    fun fromY(fromY: Float): AnimationTranslationType {
        fromY(fromY, true)
        return this
    }

    fun toY(toY: Float): AnimationTranslationType {
        toY(toY, true)
        return this
    }

    fun fromX(fromX: Int): AnimationTranslationType {
        fromX(fromX.toFloat(), false)
        return this
    }

    fun toX(toX: Int): AnimationTranslationType {
        toX(toX.toFloat(), false)
        return this
    }

    fun fromY(fromY: Int): AnimationTranslationType {
        fromY(fromY.toFloat(), false)
        return this
    }

    fun toY(toY: Int): AnimationTranslationType {
        toY(toY.toFloat(), false)
        return this
    }

    fun fromX(fromX: Float, percentage: Boolean): AnimationTranslationType {
        _isPercentageFromX = percentage
        this._fromX = fromX
        return this
    }

    fun toX(toX: Float, percentage: Boolean): AnimationTranslationType {
        _isPercentageToX = percentage
        this._toX = toX
        return this
    }

    fun fromY(fromY: Float, percentage: Boolean): AnimationTranslationType {
        _isPercentageFromY = percentage
        this._fromY = fromY
        return this
    }

    fun toY(toY: Float, percentage: Boolean): AnimationTranslationType {
        _isPercentageToY = percentage
        this._toY = toY
        return this
    }

    override fun build(animKConfig: MAnimKConfig): Animation {
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
        format(animKConfig, animation)
        return animation
    }

    ////////////////////////////////////////////////////////////////////////////

    companion object {
        val FROM_LEFT_SHOW: AnimationTranslationType = AnimationTranslationType().apply {
            from(EDirection.LEFT)
        }
        val FROM_TOP_SHOW: AnimationTranslationType = AnimationTranslationType().apply {
            from(EDirection.TOP)
        }
        val FROM_RIGHT_SHOW: AnimationTranslationType = AnimationTranslationType().apply {
            from(EDirection.RIGHT)
        }
        val FROM_BOTTOM_SHOW: AnimationTranslationType = AnimationTranslationType().apply {
            from(EDirection.BOTTOM)
        }
        val TO_LEFT_HIDE: AnimationTranslationType = AnimationTranslationType().apply {
            to(EDirection.LEFT)
        }
        val TO_TOP_HIDE: AnimationTranslationType = AnimationTranslationType().apply {
            to(EDirection.TOP)
        }
        val TO_RIGHT_HIDE: AnimationTranslationType = AnimationTranslationType().apply {
            to(EDirection.RIGHT)
        }
        val TO_BOTTOM_HIDE: AnimationTranslationType = AnimationTranslationType().apply {
            to(EDirection.BOTTOM)
        }
    }
}
