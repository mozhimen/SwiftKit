package com.mozhimen.basick.utilk.java.io

import android.os.FileUtils
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.bases.IUtilK
import com.mozhimen.basick.utilk.kotlin.appendStrLineBreak
import com.mozhimen.basick.utilk.kotlin.str2bytes
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

/**
 * @ClassName UtilKFileOutputStream
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/2 16:54
 * @Version 1.0
 */
fun String.writeStr2fileOutputStream(fileOutputStream: FileOutputStream) {
    UtilKFileOutputStream.writeStr2fileOutputStream(this, fileOutputStream)
}

fun ByteArray.writeBytes2fileOutputStream(fileOutputStream: FileOutputStream) {
    UtilKFileOutputStream.writeBytes2fileOutputStream(this, fileOutputStream)
}

fun FileOutputStream.fileOutputStream2file(inputStream: InputStream, destFile: File, bufferSize: Int = 1024) =
    UtilKFileOutputStream.fileOutputStream2file(this, inputStream, destFile, bufferSize)

@RequiresApi(CVersCode.V_29_10_Q)
fun FileOutputStream.fileOutputStream2file2(inputStream: InputStream, destFile: File) =
    UtilKFileOutputStream.fileOutputStream2file2(this, inputStream, destFile)

//////////////////////////////////////////////////////////////////////////

object UtilKFileOutputStream : IUtilK {
    @JvmStatic
    fun writeStr2fileOutputStream(str: String, fileOutputStream: FileOutputStream) {
        writeBytes2fileOutputStream(str.appendStrLineBreak().str2bytes(), fileOutputStream)
    }

    @JvmStatic
    fun writeBytes2fileOutputStream(bytes: ByteArray, fileOutputStream: FileOutputStream) {
        fileOutputStream.flushClose { it.write(bytes) }
    }

    @JvmStatic
    fun fileOutputStream2file(fileOutputStream: FileOutputStream, inputStream: InputStream, destFile: File, bufferSize: Int = 1024): File? =
        UtilKOutputStream.outputStream2file(fileOutputStream, inputStream, destFile, bufferSize)

    @JvmStatic
    @RequiresApi(CVersCode.V_29_10_Q)
    fun fileOutputStream2file2(fileOutputStream: FileOutputStream, inputStream: InputStream, destFile: File): File =
        UtilKOutputStream.outputStream2file2(fileOutputStream, inputStream, destFile)
}