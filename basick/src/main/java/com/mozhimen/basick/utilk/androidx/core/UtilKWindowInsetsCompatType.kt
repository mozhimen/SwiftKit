package com.mozhimen.basick.utilk.androidx.core

import androidx.core.view.WindowInsetsCompat

/**
 * @ClassName UtilKWindowInsetsCompatType
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/3/26
 * @Version 1.0
 */
object UtilKWindowInsetsCompatType {
    @JvmStatic
    fun systemBars(): Int =
        WindowInsetsCompat.Type.systemBars()
}