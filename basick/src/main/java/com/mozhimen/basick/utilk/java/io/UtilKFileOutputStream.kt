package com.mozhimen.basick.utilk.java.io

import android.os.FileUtils
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.utilk.kotlin.appendStrLineBreak
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

fun FileOutputStream.fileOutputStream2file(inputStream: InputStream, destFile: File) =
        UtilKFileOutputStream.fileOutputStream2file(this, inputStream, destFile)

@RequiresApi(CVersCode.V_29_10_Q)
fun FileOutputStream.fileOutputStream2file2(inputStream: InputStream, destFile: File) =
        UtilKFileOutputStream.fileOutputStream2file2(this, inputStream, destFile)

//////////////////////////////////////////////////////////////////////////

object UtilKFileOutputStream {
    @JvmStatic
    fun writeStr2fileOutputStream(str: String, fileOutputStream: FileOutputStream) {
        writeBytes2fileOutputStream(str.appendStrLineBreak().toByteArray(), fileOutputStream)
    }

    @JvmStatic
    fun writeBytes2fileOutputStream(bytes: ByteArray, fileOutputStream: FileOutputStream) {
        fileOutputStream.flushClose { it.write(bytes) }
    }

    @JvmStatic
    fun fileOutputStream2file(fileOutputStream: FileOutputStream, inputStream: InputStream, destFile: File): File {
        fileOutputStream.flushClose {
            var readCount: Int
            val bytes = ByteArray(1024)
            while (inputStream.read(bytes).also { readCount = it } != -1)
                fileOutputStream.write(bytes, 0, readCount)
            return destFile
        }
    }

    @JvmStatic
    @RequiresApi(CVersCode.V_29_10_Q)
    fun fileOutputStream2file2(fileOutputStream: FileOutputStream, inputStream: InputStream, destFile: File): File {
        fileOutputStream.flushClose {
            FileUtils.copy(inputStream, it)
            return destFile
        }
    }
}