package com.mozhimen.basick.utilk.java.io

import com.mozhimen.basick.utilk.commons.IUtilK
import java.io.BufferedOutputStream
import java.io.OutputStream
import java.util.zip.ZipOutputStream

/**
 * @ClassName UtilKOutputStream
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/4 13:49
 * @Version 1.0
 */
fun OutputStream.outputStream2bufferedOutputStream(): BufferedOutputStream =
    UtilKOutputStream.outputStream2bufferedOutputStream(this)

fun OutputStream.outputStream2zipOutputStream(): ZipOutputStream =
    UtilKOutputStream.outputStream2zipOutputStream(this)

object UtilKOutputStream : IUtilK {
    @JvmStatic
    fun outputStream2bufferedOutputStream(outputStream: OutputStream): BufferedOutputStream =
        BufferedOutputStream(outputStream)

    @JvmStatic
    fun outputStream2zipOutputStream(outputStream: OutputStream): ZipOutputStream =
        ZipOutputStream(outputStream)
}