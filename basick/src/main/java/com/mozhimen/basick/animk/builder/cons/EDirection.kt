package com.mozhimen.basick.animk.builder.cons

import com.mozhimen.basick.elemk.android.view.cons.CGravity

/**
 * @ClassName Direction
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/17 22:59
 * @Version 1.0
 */
enum class EDirection(val flag: Int) {
    IDLE(CGravity.NO_GRAVITY),
    LEFT(CGravity.LEFT),
    TOP(CGravity.TOP),
    RIGHT(CGravity.RIGHT),
    BOTTOM(CGravity.BOTTOM),
    CENTER(CGravity.CENTER),
    CENTER_HORIZONTAL(CGravity.CENTER_HORIZONTAL),
    CENTER_VERTICAL(CGravity.CENTER_VERTICAL);

    companion object {
        fun isDirectionFlag(direction: EDirection, flag: Int): Boolean =
            flag and CGravity.HORIZONTAL_GRAVITY_MASK == direction.flag || flag and CGravity.VERTICAL_GRAVITY_MASK == direction.flag
    }
}