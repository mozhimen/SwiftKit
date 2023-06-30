package com.mozhimen.basick.sensek.systembar.cons

/**
 * @ClassName CSystemBarProperty
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/29 19:09
 * @Version 1.0
 */
object CSystemBarProperty {
    const val NORMAL = 0b0
    const val IMMERSED = 0b1 shl 9//0b10000_00000
    const val IMMERSED_HARD = 0b11 shl 8//0b11000_00000
    const val IMMERSED_HARD_STICKY = 0b111 shl 7//0b11100_00000

}
