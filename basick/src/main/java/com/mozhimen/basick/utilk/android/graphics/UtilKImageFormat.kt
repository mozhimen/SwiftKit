package com.mozhimen.basick.utilk.android.graphics

import com.mozhimen.basick.elemk.android.graphics.cons.CImageFormat
import com.mozhimen.basick.elemk.android.os.cons.CBuild

/**
 * @ClassName UtilKImageFormat
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/9/8 22:16
 * @Version 1.0
 */
fun Int.imageFormat2str(): String =
    UtilKImageFormat.imageFormat2str(this)

object UtilKImageFormat {
    @JvmStatic
    fun imageFormat2str(imageFormat: Int): String =
        when (imageFormat) {
            CImageFormat.RGB_565 -> "RGB_565"
            CImageFormat.YV12 -> "YV12"
            CImageFormat.Y8 -> "Y8"
            CImageFormat.Y16 -> "Y16"
            CImageFormat.NV16 -> "NV16"
            CImageFormat.NV21 -> "NV21"
            CImageFormat.YUY2 -> "YUY2"
            CImageFormat.JPEG -> "JPEG"
            CImageFormat.DEPTH_JPEG -> "DEPTH_JPEG"
            CImageFormat.YUV_420_888 -> "YUV_420_888"
            CImageFormat.YUV_422_888 -> "YUV_422_888"
            CImageFormat.YUV_444_888 -> "YUV_444_888"
            CImageFormat.FLEX_RGB_888 -> "FLEX_RGB_888"
            CImageFormat.FLEX_RGBA_8888 -> "FLEX_RGBA_8888"
            CImageFormat.RAW_SENSOR -> "RAW_SENSOR"
            CImageFormat.RAW_PRIVATE -> "RAW_PRIVATE"
            CImageFormat.RAW10 -> "RAW10"
            CImageFormat.RAW12 -> "RAW12"
            CImageFormat.DEPTH16 -> "DEPTH16"
            CImageFormat.DEPTH_POINT_CLOUD -> "DEPTH_POINT_CLOUD"
            CImageFormat.RAW_DEPTH -> "RAW_DEPTH"
            CImageFormat.RAW_DEPTH10 -> "RAW_DEPTH10"
            CImageFormat.PRIVATE -> "PRIVATE"
            CImageFormat.HEIC -> "HEIC"
            else -> CBuild.UNKNOWN
        }
}