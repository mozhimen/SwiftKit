package com.mozhimen.basick.utilk.kotlin

import com.mozhimen.basick.utilk.java.text.UtilKDecimalFormat
import android.util.Log
import com.mozhimen.basick.utilk.bases.IUtilK

/**
 * @ClassName UtilKLongFormat
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/20 16:51
 * @Version 1.0
 */
fun Long.longFileSize2strFileSize(): String =
    UtilKLongFormat.longFileSize2strFileSize(this)

fun Long.longFileSize2strFileSizeLong(suffix: String = "B"): String =
    UtilKLongFormat.longFileSize2strFileSizeLong(this)

object UtilKLongFormat : IUtilK {
    @JvmStatic
    fun longFileSize2strFileSize(fileSize: Long, suffix: String = "B"): String {
        val decimalFormat = UtilKDecimalFormat.getOf(2)
        return (if (fileSize <= 0) "0B"
        else if (fileSize < 1024)
            "${fileSize}B"
        else if (fileSize < 1048576)
            decimalFormat.format(fileSize.toDouble() / 1024.0) + "K" + suffix
        else if (fileSize < 1073741824)
            decimalFormat.format(fileSize.toDouble() / 1048576.0) + "M" + suffix
        else
            decimalFormat.format(fileSize.toDouble() / 1073741824.0) + "G" + suffix).replace(",", ".").also { Log.d(TAG, "longFileSize2strFileSize: $it") }
    }

    @JvmStatic
    fun longFileSize2strFileSizeLong(fileSize: Long, suffix: String = "B"): String {
        return if (fileSize <= 0) "0B"
        else if (fileSize < 1024)
            "${fileSize}B"
        else if (fileSize < 1048576)
            "${(fileSize.toDouble() / 1024.0).toLong()}K${suffix}"
        else if (fileSize < 1073741824)
            "${(fileSize.toDouble() / 1048576.0).toLong()}M${suffix}"
        else
            "${(fileSize.toDouble() / 1073741824.0).toLong()}G${suffix}"
    }
}