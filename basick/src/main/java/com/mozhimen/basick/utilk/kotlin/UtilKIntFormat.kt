package com.mozhimen.basick.utilk.kotlin

import com.mozhimen.basick.elemk.android.graphics.cons.CImageFormat
import com.mozhimen.basick.elemk.android.os.cons.CBuild
import com.mozhimen.basick.elemk.android.util.annors.ALog
import com.mozhimen.basick.elemk.android.util.cons.CLog
import com.mozhimen.basick.utilk.android.text.formatIpAddress
import com.mozhimen.basick.utilk.java.lang.UtilKCharacter

/**
 * @ClassName UtilKIntFormat
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/10/19 0:17
 * @Version 1.0
 */
fun Int.int2boolean(): Boolean =
    UtilKIntFormat.int2boolean(this)

fun Int.intIp2strIp(): String =
    UtilKIntFormat.intIp2strIp(this)

fun Int.intAscii2int(): Int =
    UtilKIntFormat.intAscii2int(this)

fun Int.intByte2strByte(): String =
    UtilKIntFormat.intByte2strByte(this)

fun Int.intImageFormat2strImageFormat(): String =
    UtilKIntFormat.intImageFormat2strImageFormat(this)

fun Int.intLogPriority2strLog_ofSimple(): String =
    UtilKIntFormat.intLogPriority2strLog_ofSimple(this)

fun Int.intLogPriority2strLog(): String =
    UtilKIntFormat.intLogPriority2strLog(this)

///////////////////////////////////////////////////////////////////////

object UtilKIntFormat {
    @JvmStatic
    fun int2boolean(int: Int) =
        int == 1

    @JvmStatic
    fun intIp2strIp(intIp: Int): String =
        intIp.formatIpAddress()

    //ASCII转整型 '5' ascci 是 53。 输入 int 53，输出 int 5
    @JvmStatic
    fun intAscii2int(intAscii: Int): Int =
        UtilKCharacter.getNumericValue(intAscii)

    @JvmStatic
    fun intByte2strByte(intByte: Int): String =
        Integer.toBinaryString(intByte)

    @JvmStatic
    fun intImageFormat2strImageFormat(imageFormat: Int): String =
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

    @JvmStatic
    fun intLogPriority2strLog_ofSimple(@ALog priority: Int): String =
        when (priority) {
            CLog.VERBOSE -> "V"
            CLog.DEBUG -> "D"
            CLog.INFO -> "I"
            CLog.WARN -> "W"
            CLog.ERROR -> "E"
            CLog.ASSERT -> "A"
            else -> "UNKNOWN"
        }

    @JvmStatic
    fun intLogPriority2strLog(@ALog priority: Int): String =
        when (priority) {
            CLog.VERBOSE -> "VERBOSE"
            CLog.DEBUG -> "DEBUG"
            CLog.INFO -> "INFO"
            CLog.WARN -> "WARN"
            CLog.ERROR -> "ERROR"
            CLog.ASSERT -> "ASSERT"
            else -> "UNKNOWN"
        }
}