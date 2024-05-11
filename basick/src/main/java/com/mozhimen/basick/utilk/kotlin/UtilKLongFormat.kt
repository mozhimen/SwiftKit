package com.mozhimen.basick.utilk.kotlin

import com.mozhimen.basick.utilk.java.text.UtilKDecimalFormat
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.utilk.commons.IUtilK
import com.mozhimen.basick.utilk.kotlin.text.replaceDot
import java.util.Locale

/**
 * @ClassName UtilKLongFormat
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/20 16:51
 * @Version 1.0
 */
fun Long.long2strCrc32(): String =
    UtilKLongFormat.long2strCrc32(this)

fun Long.longFileSize2strFileSize(suffix: String = "B", bit: Int = 2, locale: Locale): String =
    UtilKLongFormat.longFileSize2strFileSize(this, suffix, bit, locale)

fun Long.longFileSize2strFileSize(suffix: String = "B", bit: Int = 2): String =
    UtilKLongFormat.longFileSize2strFileSize(this, suffix, bit)

fun Long.longFileSize2strFileSize_ofLong(suffix: String = "B"): String =
    UtilKLongFormat.longFileSize2strFileSize_ofLong(this, suffix)

////////////////////////////////////////////////////////////////////////

object UtilKLongFormat : IUtilK {
    @JvmStatic
    fun long2strCrc32(long: Long): String =
        "%08x".format(this).toUpperCase()

    @JvmStatic
    fun longFileSize2strFileSize(fileSize: Long, suffix: String = "B", bit: Int = 2): String =
        longFileSize2strFileSize(fileSize, suffix, bit, Locale.ENGLISH)

    @JvmStatic
    fun longFileSize2strFileSize(fileSize: Long, suffix: String = "B", bit: Int = 2, locale: Locale): String {
        val decimalFormat = UtilKDecimalFormat.get_ofBit(bit, locale)
        return (if (fileSize <= 0) "0B"
        else if (fileSize < 1024)
            "${fileSize}B"
        else if (fileSize < 1048576)
            decimalFormat.format(fileSize.toDouble() / 1024.0) + "K" + suffix
        else if (fileSize < 1073741824)
            decimalFormat.format(fileSize.toDouble() / 1048576.0) + "M" + suffix
        else
            decimalFormat.format(fileSize.toDouble() / 1073741824.0) + "G" + suffix).replaceDot().also { UtilKLogWrapper.d(TAG, "longFileSize2strFileSize: $it") }
    }

    @JvmStatic
    fun longFileSize2strFileSize_ofLong(longFileSize: Long, suffix: String = "B"): String {
        return if (longFileSize <= 0) "0B"
        else if (longFileSize < 1024)
            "${longFileSize}B"
        else if (longFileSize < 1048576)
            "${(longFileSize.toDouble() / 1024.0).toLong()}K${suffix}"
        else if (longFileSize < 1073741824)
            "${(longFileSize.toDouble() / 1048576.0).toLong()}M${suffix}"
        else
            "${(longFileSize.toDouble() / 1073741824.0).toLong()}G${suffix}"
    }
}