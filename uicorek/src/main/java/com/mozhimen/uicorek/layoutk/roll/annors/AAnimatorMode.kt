package com.mozhimen.uicorek.layoutk.roll.annors

import androidx.annotation.IntDef

/**
 * @ClassName AAnimatorMode
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/9/12 15:30
 * @Version 1.0
 */
@IntDef(AAnimatorMode.UP, AAnimatorMode.DOWN)
annotation class AAnimatorMode {
    companion object {
        const val UP = 1
        const val DOWN = 2
    }
}
