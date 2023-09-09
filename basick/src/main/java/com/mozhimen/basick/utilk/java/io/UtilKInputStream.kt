package com.mozhimen.basick.utilk.java.io

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapFactory.Options
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.os.FileUtils
import android.text.TextUtils
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.elemk.kotlin.text.cons.CCharsets
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.bases.IUtilK
import com.mozhimen.basick.utilk.java.security.inputStream2strMd52
import com.mozhimen.basick.utilk.kotlin.bytes2str
import com.mozhimen.basick.utilk.kotlin.text.replaceRegexLineBreak
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import kotlin.jvm.Throws

/**
 * @ClassName UtilKInputStream
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/31 11:42
 * @Version 1.0
 */

fun InputStream.getAvailableLong(): Long =
    UtilKInputStream.getAvailableLong(this)

////////////////////////////////////////////////////////////////////////////

fun InputStream.inputStream2bufferedInputStream(): BufferedInputStream =
    UtilKInputStream.inputStream2bufferedInputStream(this)

////////////////////////////////////////////////////////////////////////////

fun InputStream.inputStream2bytes(): ByteArray =
    UtilKInputStream.inputStream2bytes(this)

fun InputStream.inputStream2bytes(fileLength: Long): ByteArray =
    UtilKInputStream.inputStream2bytes(this, fileLength)

////////////////////////////////////////////////////////////////////////////

fun InputStream.inputStream2str(): String? =
    UtilKInputStream.inputStream2str(this)

fun InputStream.inputStream2str(byteArrayOutputStream: ByteArrayOutputStream): String? =
    UtilKInputStream.inputStream2str(this, byteArrayOutputStream)

fun InputStream.inputStream2str2(): String? =
    UtilKInputStream.inputStream2str2(this)

fun InputStream.inputStream2str3(): String? =
    UtilKInputStream.inputStream2str3(this)

////////////////////////////////////////////////////////////////////////////

fun InputStream.inputStream2anyBitmap(): Bitmap =
    UtilKInputStream.inputStream2anyBitmap(this)

fun InputStream.inputStream2anyBitmap(outPadding: Rect?, opts: Options): Bitmap? =
    UtilKInputStream.inputStream2anyBitmap(this, outPadding, opts)

////////////////////////////////////////////////////////////////////////////

fun InputStream.inputStream2bitmapDrawable(): BitmapDrawable =
    UtilKInputStream.inputStream2bitmapDrawable(this)

////////////////////////////////////////////////////////////////////////////

fun InputStream.inputStream2file(destFilePathWithName: String, isAppend: Boolean = false, bufferSize: Int = 1024): File? =
    UtilKInputStream.inputStream2file(this, destFilePathWithName, isAppend, bufferSize)

fun InputStream.inputStream2file(destFile: File, isAppend: Boolean = false, bufferSize: Int = 1024): File? =
    UtilKInputStream.inputStream2file(this, destFile, isAppend, bufferSize)

@RequiresApi(CVersCode.V_29_10_Q)
fun InputStream.inputStream2file2(destFilePathWithName: String, isAppend: Boolean = false): File? =
    UtilKInputStream.inputStream2file2(this, destFilePathWithName, isAppend)

@RequiresApi(CVersCode.V_29_10_Q)
fun InputStream.inputStream2file2(destFile: File, isAppend: Boolean = false): File? =
    UtilKInputStream.inputStream2file2(this, destFile, isAppend)

////////////////////////////////////////////////////////////////////////////

fun InputStream.inputStream2outputStream(outputStream: OutputStream, bufferSize: Int) {
    UtilKInputStream.inputStream2outputStream(this, outputStream, bufferSize)
}

fun InputStream.inputStream2outputStream2(outputStream: OutputStream, bufferSize: Int) {
    UtilKInputStream.inputStream2outputStream2(this, outputStream, bufferSize)
}

fun InputStream.inputStream2outputStream(outputStream: OutputStream) {
    UtilKInputStream.inputStream2outputStream(this, outputStream)
}

@RequiresApi(CVersCode.V_29_10_Q)
fun InputStream.inputStream2outputStream2(outputStream: OutputStream) {
    UtilKInputStream.inputStream2outputStream2(this, outputStream)
}

////////////////////////////////////////////////////////////////////////////

fun InputStream.readBytesForInputStream(bytes: ByteArray) {
    UtilKInputStream.readBytesForInputStream(this, bytes)
}

////////////////////////////////////////////////////////////////////////////

object UtilKInputStream : IUtilK {

    @JvmStatic
    fun getAvailableLong(inputStream: InputStream): Long =
        inputStream.use { it.available().toLong() }

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun inputStream2bufferedInputStream(inputStream: InputStream): BufferedInputStream =
        BufferedInputStream(inputStream)

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun inputStream2bytes(inputStream: InputStream): ByteArray {
        val bytes = ByteArray(inputStream.available())
        inputStream.readBytesForInputStream(bytes)
        return bytes
    }

    @JvmStatic
    fun inputStream2bytes(inputStream: InputStream, fileLength: Long): ByteArray {
        inputStream.use {
            val bytes = ByteArray(fileLength.toInt())
            var offset = 0
            var readCount = 0
            while (offset < bytes.size && inputStream.read(bytes, offset, bytes.size - offset).also { readCount = it } != -1)
                offset += readCount
            // 确保所有数据均被读取
            if (offset != bytes.size) throw IOException("Could not completely read file.")
            return bytes
        }
    }

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun inputStream2str(inputStream: InputStream): String? {
        val inputStringBuilder = StringBuilder()
        var inputStreamReader: InputStreamReader? = null
        var inputBufferedReader: BufferedReader? = null
        try {
            inputStreamReader = InputStreamReader(inputStream, CCharsets.UTF_8)
            inputBufferedReader = BufferedReader(inputStreamReader)

            var strLineInput = ""
            while (inputBufferedReader.readLine()?.also { strLineInput = it } != null)
                inputStringBuilder.append(strLineInput).append("\n")
            return inputStringBuilder.deleteCharAt(inputStringBuilder.length - 1).toString().replaceRegexLineBreak()
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(UtilKFile.TAG)
        } finally {
            inputBufferedReader?.close()
            inputStreamReader?.close()
            inputStream.close()
        }
        return null
    }

    @JvmStatic
    fun inputStream2str(inputStream: InputStream, byteArrayOutputStream: ByteArrayOutputStream): String? {
        try {
            inputStream.inputStream2outputStream(byteArrayOutputStream)
            return byteArrayOutputStream.byteArrayOutputStream2str().replaceRegexLineBreak()
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        } finally {
            inputStream.close()
            byteArrayOutputStream.flushClose()
        }
        return null
    }

    @JvmStatic
    fun inputStream2str2(inputStream: InputStream): String? =
        inputStream2str(inputStream, ByteArrayOutputStream())

    @JvmStatic
    fun inputStream2str3(inputStream: InputStream): String? {
        val inputStringBuilder = StringBuilder()
        try {
            var readCount: Int
            val bytes = ByteArray(1024)
            while (inputStream.read(bytes).also { readCount = it } != -1)
                inputStringBuilder.append(bytes.bytes2str(0, readCount))
            return inputStringBuilder.toString().replaceRegexLineBreak()
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        } finally {
            inputStream.close()
        }
        return null
    }

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun inputStream2anyBitmap(inputStream: InputStream): Bitmap =
        inputStream.use { BitmapFactory.decodeStream(it) }

    @JvmStatic
    fun inputStream2anyBitmap(inputStream: InputStream, outPadding: Rect?, opts: Options): Bitmap? =
        inputStream.use { BitmapFactory.decodeStream(it, outPadding, opts) }

    ////////////////////////////////////////////////////////////////////////////
    @JvmStatic
    fun inputStream2bitmapDrawable(inputStream: InputStream): BitmapDrawable =
        inputStream.use { BitmapDrawable(null, it) }

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun inputStream2file(inputStream: InputStream, destFilePathWithName: String, isAppend: Boolean = false, bufferSize: Int = 1024): File? =
        inputStream2file(inputStream, UtilKFile.createFile(destFilePathWithName), isAppend, bufferSize)

    @JvmStatic
    fun inputStream2file(inputStream: InputStream, destFile: File, isAppend: Boolean = false, bufferSize: Int = 1024): File? {
        UtilKFile.createFile(destFile)
        /*//        val fileInputStream = FileInputStream(file)
        //        Log.d(TAG, "inputStream2file: inputStream ${inputStream.available()}")
        //        if (isInputStreamSame(inputStream, fileInputStream)) {//相似内容就直接返回地址
        //            Log.d(TAG, "assetCopyFile: the two files is same")
        //            return file//"the two files is same, don't need overwrite"
        //        }*/
        try {
            inputStream.inputStream2outputStream(destFile.file2fileOutputStream(isAppend), bufferSize)
            return destFile
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        }
        return null
    }

    @JvmStatic
    @RequiresApi(CVersCode.V_29_10_Q)
    fun inputStream2file2(inputStream: InputStream, filePathWithName: String, isAppend: Boolean = false): File? =
        inputStream2file2(inputStream, UtilKFile.createFile(filePathWithName), isAppend)

    @JvmStatic
    @RequiresApi(CVersCode.V_29_10_Q)
    fun inputStream2file2(inputStream: InputStream, destFile: File, isAppend: Boolean = false): File? {
        UtilKFile.createFile(destFile)
        /*//        val fileInputStream = FileInputStream(file)
        //        if (isInputStreamSame(inputStream, fileInputStream)) {//相似内容就直接返回地址
        //            Log.d(UtilKFile.TAG, "assetCopyFile: the two files is same")
        //            return file//"the two files is same, don't need overwrite"
        //        }*/
        try {
            inputStream.inputStream2outputStream2(destFile.file2fileOutputStream(isAppend))
            return destFile
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        }
        return null
    }

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @Throws(Exception::class)
    fun inputStream2outputStream(inputStream: InputStream, outputStream: OutputStream, bufferSize: Int) {
        try {
            var readCount: Int
            val bytes = ByteArray(bufferSize)
            while (inputStream.read(bytes).also { readCount = it } != -1)
                outputStream.write(bytes, 0, readCount)
        } catch (e: Exception) {
            throw e
        } finally {
            inputStream.close()
            outputStream.flushClose()
        }
    }

    @JvmStatic
    @Throws(Exception::class)
    fun inputStream2outputStream2(inputStream: InputStream, outputStream: OutputStream, bufferSize: Int) {
        try {
            var readCount: Int
            val bytes = ByteArray(bufferSize)
            while (inputStream.read(bytes, 0, bufferSize).also { readCount = it } != -1)
                outputStream.write(bytes, 0, readCount)
        } catch (e: Exception) {
            throw e
        } finally {
            inputStream.close()
            outputStream.flushClose()
        }
    }

    @JvmStatic
    @Throws(Exception::class)
    fun inputStream2outputStream(inputStream: InputStream, outputStream: OutputStream) {
        try {
            var readCount: Int
            while (inputStream.read().also { readCount = it } != -1)
                outputStream.write(readCount)
        } catch (e: Exception) {
            throw e
        } finally {
            inputStream.close()
            outputStream.flushClose()
        }
    }

    @JvmStatic
    @Throws(Exception::class)
    @RequiresApi(CVersCode.V_29_10_Q)
    fun inputStream2outputStream2(inputStream: InputStream, outputStream: OutputStream) {
        try {
            FileUtils.copy(inputStream, outputStream)
        } catch (e: Exception) {
            throw e
        } finally {
            inputStream.close()
            outputStream.flushClose()
        }
    }

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun isInputStreamSame(inputStream1: InputStream, inputStream2: InputStream): Boolean =
        TextUtils.equals(inputStream1.inputStream2strMd52(), inputStream2.inputStream2strMd52())

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun readBytesForInputStream(inputStream: InputStream, bytes: ByteArray) {
        inputStream.use { inputStream.read(bytes) }
    }
}