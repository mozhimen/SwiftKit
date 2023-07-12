package com.mozhimen.componentk.cameraxk.annors

import androidx.annotation.IntDef
import com.mozhimen.componentk.cameraxk.cons.CCameraXKRotation

/**
 * @ClassName ACameraXKRotation
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/12/12 0:48
 * @Version 1.0
 */

@IntDef(value = [ACameraXKRotation.ROTATION_0, ACameraXKRotation.ROTATION_90, ACameraXKRotation.ROTATION_180, ACameraXKRotation.ROTATION_270])
annotation class ACameraXKRotation {
    companion object {
        const val ROTATION_0 = 0
        const val ROTATION_90 = 1
        const val ROTATION_180 = 2
        const val ROTATION_270 = 3
    }
}