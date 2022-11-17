package com.mozhimen.basick.animk.cons

import android.view.Gravity

/**
 * @ClassName Direction
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/17 22:59
 * @Version 1.0
 */
enum class EDirection(val flag: Int) {
    IDLE(Gravity.NO_GRAVITY),
    LEFT(Gravity.LEFT),
    TOP(Gravity.TOP),
    RIGHT(Gravity.RIGHT),
    BOTTOM(Gravity.BOTTOM),
    CENTER(Gravity.CENTER),
    CENTER_HORIZONTAL(Gravity.CENTER_HORIZONTAL),
    CENTER_VERTICAL(Gravity.CENTER_VERTICAL);

    companion object {
        fun isDirectionFlag(direction: EDirection, flag: Int): Boolean {
            return flag and Gravity.HORIZONTAL_GRAVITY_MASK == direction.flag ||
                    flag and Gravity.VERTICAL_GRAVITY_MASK == direction.flag
        }
    }
}