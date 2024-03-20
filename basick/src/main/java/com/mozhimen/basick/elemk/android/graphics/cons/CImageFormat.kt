package com.mozhimen.basick.elemk.android.graphics.cons

import android.graphics.ImageFormat
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode

/**
 * @ClassName CImageFormat
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/9/8 22:12
 * @Version 1.0
 */
object CImageFormat {
    const val RGB_565 = ImageFormat.RGB_565
    const val YV12 = ImageFormat.YV12

    @RequiresApi(CVersCode.V_29_10_Q)
    const val Y8 = ImageFormat.Y8
    const val Y16 = 0x20363159
    const val NV16 = ImageFormat.NV16
    const val NV21 = ImageFormat.NV21
    const val YUY2 = ImageFormat.YUY2
    const val JPEG = ImageFormat.JPEG

    @RequiresApi(CVersCode.V_29_10_Q)
    const val DEPTH_JPEG = ImageFormat.DEPTH_JPEG
    const val YUV_420_888 = ImageFormat.YUV_420_888

    @RequiresApi(CVersCode.V_23_6_M)
    const val YUV_422_888 = ImageFormat.YUV_422_888

    @RequiresApi(CVersCode.V_23_6_M)
    const val YUV_444_888 = ImageFormat.YUV_444_888

    @RequiresApi(CVersCode.V_23_6_M)
    const val FLEX_RGB_888 = ImageFormat.FLEX_RGB_888

    @RequiresApi(CVersCode.V_23_6_M)
    const val FLEX_RGBA_8888 = ImageFormat.FLEX_RGBA_8888

    @RequiresApi(CVersCode.V_21_5_L)
    const val RAW_SENSOR = ImageFormat.RAW_SENSOR

    @RequiresApi(CVersCode.V_24_7_N)
    const val RAW_PRIVATE = ImageFormat.RAW_PRIVATE

    @RequiresApi(CVersCode.V_21_5_L)
    const val RAW10 = ImageFormat.RAW10

    @RequiresApi(CVersCode.V_23_6_M)
    const val RAW12 = ImageFormat.RAW12

    @RequiresApi(CVersCode.V_23_6_M)
    const val DEPTH16 = ImageFormat.DEPTH16

    @RequiresApi(CVersCode.V_23_6_M)
    const val DEPTH_POINT_CLOUD = ImageFormat.DEPTH_POINT_CLOUD
    const val RAW_DEPTH = 0x1002
    const val RAW_DEPTH10 = 0x1003

    @RequiresApi(CVersCode.V_23_6_M)
    const val PRIVATE = ImageFormat.PRIVATE

    @RequiresApi(CVersCode.V_29_10_Q)
    const val HEIC = ImageFormat.HEIC
}