package com.mozhimen.uicorek.textk.bubble.annors

import androidx.annotation.IntDef

/**
 * @ClassName ARelativeHorizontal
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/16 23:16
 * @Version 1.0
 */
@IntDef(value = [ARelativeHorizontal.CENTER_HORIZONTAL, ARelativeHorizontal.TO_LEFT_OF, ARelativeHorizontal.TO_RIGHT_OF, ARelativeHorizontal.ALIGN_LEFT, ARelativeHorizontal.ALIGN_RIGHT])
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class ARelativeHorizontal {
    companion object {
        const val CENTER_HORIZONTAL = 0
        const val TO_LEFT_OF = 1
        const val TO_RIGHT_OF = 2
        const val ALIGN_LEFT = 3
        const val ALIGN_RIGHT = 4
    }
}
