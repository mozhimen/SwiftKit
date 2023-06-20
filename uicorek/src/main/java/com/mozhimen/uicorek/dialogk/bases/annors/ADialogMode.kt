package com.mozhimen.uicorek.dialogk.bases.annors

import androidx.annotation.IntDef

/**
 * @ClassName DialogMode
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/24 22:33
 * @Version 1.0
 */
@Retention(AnnotationRetention.SOURCE)
@IntDef(ADialogMode.POSITIVE, ADialogMode.NEGATIVE, ADialogMode.BOTH, ADialogMode.NONE)
annotation class ADialogMode {
    companion object {
        const val POSITIVE = 0x10
        const val NEGATIVE = 0x11
        const val BOTH = 0x12
        const val NONE = 0x13
    }
}
