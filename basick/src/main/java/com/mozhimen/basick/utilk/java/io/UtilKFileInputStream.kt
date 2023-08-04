package com.mozhimen.basick.utilk.java.io

import java.io.FileInputStream

/**
 * @ClassName UtilKFileInputStream
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/8/3 23:44
 * @Version 1.0
 */
fun FileInputStream.getAvailableLong(): Long =
    UtilKFileInputStream.getAvailableLong(this)

object UtilKFileInputStream {
    @JvmStatic
    fun getAvailableLong(fileInputStream: FileInputStream): Long =
        fileInputStream.use { it.available().toLong() }
}