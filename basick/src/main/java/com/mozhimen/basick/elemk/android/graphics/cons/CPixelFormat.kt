package com.mozhimen.basick.elemk.android.graphics.cons

import android.graphics.PixelFormat
import android.os.Build
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode

/**
 * @ClassName CPixelFormat
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/9/9 0:05
 * @Version 1.0
 */
object CPixelFormat {
    const val UNKNOWN = PixelFormat.UNKNOWN
    const val TRANSLUCENT = PixelFormat.TRANSLUCENT
    const val TRANSPARENT = PixelFormat.TRANSPARENT
    const val OPAQUE = PixelFormat.OPAQUE
    const val RGBA_8888 = PixelFormat.RGBA_8888
    const val RGBX_8888 = PixelFormat.RGBX_8888
    const val RGB_888 = PixelFormat.RGB_888
    const val RGB_565 = PixelFormat.RGB_565
    const val RGBA_5551 = PixelFormat.RGBA_5551
    const val RGBA_4444 = PixelFormat.RGBA_4444
    const val A_8 = PixelFormat.A_8
    const val L_8 = PixelFormat.L_8
    const val LA_88 = PixelFormat.LA_88
    const val RGB_332 = PixelFormat.RGB_332
    const val YCbCr_422_SP = PixelFormat.YCbCr_422_SP
    const val YCbCr_420_SP = PixelFormat.YCbCr_420_SP
    const val YCbCr_422_I = PixelFormat.YCbCr_422_I

    @RequiresApi(CVersCode.V_26_8_O)
    const val RGBA_F16 = PixelFormat.RGBA_F16

    @RequiresApi(CVersCode.V_26_8_O)
    const val RGBA_1010102 = PixelFormat.RGBA_1010102
    const val HSV_888 = 0x37
    const val R_8 = 0x38
    const val JPEG = PixelFormat.JPEG
}