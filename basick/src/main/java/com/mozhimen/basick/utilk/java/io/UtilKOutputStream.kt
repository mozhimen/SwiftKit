package com.mozhimen.basick.utilk.java.io

import android.os.FileUtils
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.android.util.it
import com.mozhimen.basick.utilk.bases.IUtilK
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.InputStream
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

fun OutputStream.flushClose() {
    UtilKOutputStream.flushClose(this)
}

object UtilKOutputStream : IUtilK {
    @JvmStatic
    fun outputStream2bufferedOutputStream(outputStream: OutputStream): BufferedOutputStream =
        BufferedOutputStream(outputStream)

    @JvmStatic
    fun outputStream2zipOutputStream(outputStream: OutputStream): ZipOutputStream =
        ZipOutputStream(outputStream)

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun flushClose(outputStream: OutputStream) {
        outputStream.apply {
            flush()
            close()
        }
    }
}