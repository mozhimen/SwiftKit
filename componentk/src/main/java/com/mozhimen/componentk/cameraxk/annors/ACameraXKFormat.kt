package com.mozhimen.componentk.cameraxk.annors

import androidx.annotation.IntDef

/**
 * @ClassName CameraXKFormat
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/7/18 17:40
 * @Version 1.0
 */
@IntDef(value = [ACameraXKFormat.RGBA_8888, ACameraXKFormat.YUV_420_888])
annotation class ACameraXKFormat {
    companion object {
        const val RGBA_8888 = 0
        const val YUV_420_888 = 1
    }
}
