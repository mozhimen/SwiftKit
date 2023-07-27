package com.mozhimen.componentk.camerak.camerax.annors

import androidx.annotation.IntDef

/**
 * @ClassName ACameraXKRotation
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/12/12 0:48
 * @Version 1.0
 */

@IntDef(value = [ACameraKXRotation.ROTATION_0, ACameraKXRotation.ROTATION_90, ACameraKXRotation.ROTATION_180, ACameraKXRotation.ROTATION_270])
annotation class ACameraKXRotation {
    companion object {
        const val ROTATION_0 = 0
        const val ROTATION_90 = 1
        const val ROTATION_180 = 2
        const val ROTATION_270 = 3
    }
}