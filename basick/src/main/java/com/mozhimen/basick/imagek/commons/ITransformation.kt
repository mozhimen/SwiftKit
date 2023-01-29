package com.mozhimen.basick.imagek.commons

import android.graphics.Bitmap

/**
 * @ClassName ITransformation
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/6 19:06
 * @Version 1.0
 */
internal interface ITransformation {
    val Bitmap.safeConfig: Bitmap.Config
        get() = config ?: Bitmap.Config.ARGB_8888
}