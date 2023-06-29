package com.mozhimen.basick.sensek.systembar.cons

/**
 * @ClassName CSystemBarProperty
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/29 19:09
 * @Version 1.0
 */
object CSystemBarProperty {
    object Mode {
        const val NORMAL = 0b0000000000
        const val IMMERSED = 0b1000000000
        const val IMMERSED_HARD = 0b1100000000
        const val IMMERSED_HARD_STICKY = 0b1110000000
    }
}