package com.mozhimen.uicorek.textk.bubble.annors

import androidx.annotation.IntDef

/**
 * @ClassName ARelativeVertical
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/16 23:21
 * @Version 1.0
 */

@IntDef(value = [ARelativeVertical.CENTER_VERTICAL, ARelativeVertical.ABOVE, ARelativeVertical.BELOW, ARelativeVertical.ALIGN_TOP, ARelativeVertical.ALIGN_BOTTOM])
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class ARelativeVertical {
    companion object {
        const val CENTER_VERTICAL = 0
        const val ABOVE = 1
        const val BELOW = 2
        const val ALIGN_TOP = 3
        const val ALIGN_BOTTOM = 4
    }
}

