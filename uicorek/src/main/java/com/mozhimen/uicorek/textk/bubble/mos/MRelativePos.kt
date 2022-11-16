package com.mozhimen.uicorek.textk.bubble.mos

import com.mozhimen.uicorek.drawablek.arrow.cons.EArrowDirection
import com.mozhimen.uicorek.textk.bubble.annors.ARelativeHorizontal
import com.mozhimen.uicorek.textk.bubble.annors.ARelativeVertical

/**
 * @ClassName MRelativePos
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/9/6 21:14
 * @Version 1.0
 */
class MRelativePos(@ARelativeHorizontal horizontalRelate: Int, @ARelativeVertical verticalRelate: Int) {
    private var _horizontalRelate = horizontalRelate
    private var _verticalRelate = verticalRelate

    companion object {
        const val CENTER_HORIZONTAL = 0
        const val TO_LEFT_OF = 1
        const val TO_RIGHT_OF = 2
        const val ALIGN_LEFT = 3
        const val ALIGN_RIGHT = 4

        const val CENTER_VERTICAL = 0
        const val ABOVE = 1
        const val BELOW = 2
        const val ALIGN_TOP = 3
        const val ALIGN_BOTTOM = 4
    }

    fun getHorizontalRelate(): Int {
        return _horizontalRelate
    }

    fun setHorizontalRelate(horizontalRelate: Int) {
        _horizontalRelate = horizontalRelate
    }

    fun getVerticalRelate(): Int {
        return _verticalRelate
    }

    fun setVerticalRelate(verticalRelate: Int) {
        _verticalRelate = verticalRelate
    }

    fun getArrowDirection(): EArrowDirection {
        if (isHorizontalToTargetOf() && !isVerticalToTargetOf()) {
            if (_horizontalRelate == TO_RIGHT_OF) {
                return EArrowDirection.Left
            } else if (_horizontalRelate == TO_LEFT_OF) {
                return EArrowDirection.Right
            }
        }
        if (!isHorizontalToTargetOf() && isVerticalToTargetOf()) {
            if (_verticalRelate == BELOW) {
                return EArrowDirection.Up
            } else if (_verticalRelate == ABOVE) {
                return EArrowDirection.Down
            }
        }
        return EArrowDirection.None
    }

    private fun isHorizontalToTargetOf(): Boolean {
        return _horizontalRelate == TO_LEFT_OF || _horizontalRelate == TO_RIGHT_OF
    }

    private fun isVerticalToTargetOf(): Boolean {
        return _verticalRelate == ABOVE || _verticalRelate == BELOW
    }
}