package com.mozhimen.basick.utilk.kotlin

import java.text.DecimalFormat

/**
 * @ClassName UtilKLongFormat
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/20 16:51
 * @Version 1.0
 */
fun Long.longFileSize2strFileSize(): String =
    UtilKLongFormat.longFileSize2strFileSize(this)

fun Long.longFileSize2strFileSizeLong(): String =
    UtilKLongFormat.longFileSize2strFileSizeLong(this)

object UtilKLongFormat {
    @JvmStatic
    fun longFileSize2strFileSize(fileSize: Long): String {
        val decimalFormat = DecimalFormat("#.00")
        return if (fileSize <= 0) "0B"
        else if (fileSize < 1024)
            decimalFormat.format(fileSize) + "B"
        else if (fileSize < 1048576)
            decimalFormat.format(fileSize.toDouble() / 1024.0) + "K"
        else if (fileSize < 1073741824)
            decimalFormat.format(fileSize.toDouble() / 1048576.0) + "M"
        else
            decimalFormat.format(fileSize.toDouble() / 1073741824.0) + "G"
    }

    @JvmStatic
    fun longFileSize2strFileSizeLong(fileSize: Long): String {
        return if (fileSize <= 0) "0B"
        else if (fileSize < 1024)
            "${fileSize}B"
        else if (fileSize < 1048576)
            "${(fileSize.toDouble() / 1024.0).toLong()}K"
        else if (fileSize < 1073741824)
            "${(fileSize.toDouble() / 1048576.0).toLong()}M"
        else
            "${(fileSize.toDouble() / 1073741824.0).toLong()}G"
    }
}