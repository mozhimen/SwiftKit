package com.mozhimen.basick.utilk.java.io

import android.os.FileUtils
import android.util.Log
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.android.util.it
import com.mozhimen.basick.utilk.bases.IUtilK
import java.io.File
import java.io.InputStream
import java.io.OutputStream

/**
 * @ClassName UtilKOutputStream
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/4 13:49
 * @Version 1.0
 */
fun OutputStream.flushClose() {
    UtilKOutputStream.flushClose(this)
}

fun OutputStream.outputStream2file(inputStream: InputStream, destFile: File, bufferSize: Int = 1024): File? =
    UtilKOutputStream.outputStream2file(this, inputStream, destFile, bufferSize)

@RequiresApi(CVersCode.V_29_10_Q)
fun OutputStream.outputStream2file2(inputStream: InputStream, destFile: File): File =
    UtilKOutputStream.outputStream2file2(this, inputStream, destFile)

object UtilKOutputStream : IUtilK {
    @JvmStatic
    fun flushClose(outputStream: OutputStream) {
        outputStream.apply {
            flush()
            close()
        }
    }

    @JvmStatic
    fun outputStream2file(outputStream: OutputStream, inputStream: InputStream, destFile: File, bufferSize: Int = 1024): File? {
        try {
            var readCount: Int
            var count = 0
            val bytes = ByteArray(bufferSize)
            "outputStream2file ${inputStream.available()}".it(TAG)
            while (inputStream.read(bytes).also { readCount = it } != -1) {
                outputStream.write(bytes, 0, readCount)
                count++
            }

            Log.d(TAG, "outputStream2file: readCount $readCount count $count")
            return destFile
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        } finally {
            outputStream.flushClose()
            inputStream.close()
        }
        return null
    }

    @JvmStatic
    @RequiresApi(CVersCode.V_29_10_Q)
    fun outputStream2file2(outputStream: OutputStream, inputStream: InputStream, destFile: File): File {
        try {
            FileUtils.copy(inputStream, outputStream)
            return destFile
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        } finally {
            outputStream.flushClose()
            inputStream.close()
        }
        return destFile
    }

}