package com.mozhimen.componentk.camerak.camerax.annors

import android.view.Surface
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
        const val ROTATION_0 = Surface.ROTATION_0
        const val ROTATION_90 = Surface.ROTATION_90
        const val ROTATION_180 = Surface.ROTATION_180
        const val ROTATION_270 = Surface.ROTATION_270
    }
}