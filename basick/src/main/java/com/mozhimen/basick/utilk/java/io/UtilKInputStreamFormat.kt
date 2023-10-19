package com.mozhimen.basick.utilk.java.io

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.os.FileUtils
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.elemk.kotlin.text.cons.CCharsets
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.java.security.UtilKMd5
import com.mozhimen.basick.utilk.kotlin.bytes2str
import com.mozhimen.basick.utilk.kotlin.bytes2strHex
import com.mozhimen.basick.utilk.kotlin.text.replaceRegexLineBreak
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * @ClassName UtilKInputStreamFormat
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/10/18 23:43
 * @Version 1.0
 */

fun InputStream.inputStream2bufferedInputStream(): BufferedInputStream =
    UtilKInputStreamFormat.inputStream2bufferedInputStream(this)

////////////////////////////////////////////////////////////////////////////

fun InputStream.inputStream2bytes(): ByteArray =
    UtilKInputStreamFormat.inputStream2bytes(this)

fun InputStream.inputStream2bytes2(): ByteArray =
    UtilKInputStreamFormat.inputStream2bytes2(this)

fun InputStream.inputStream2bytes(fileLength: Long): ByteArray =
    UtilKInputStreamFormat.inputStream2bytes(this, fileLength)

////////////////////////////////////////////////////////////////////////////

fun InputStream.inputStream2strMd5(): String =
    UtilKInputStreamFormat.inputStream2strMd5(this)

fun InputStream.inputStream2strMd52(): String =
    UtilKInputStreamFormat.inputStream2strMd52(this)

fun InputStream.inputStream2str(): String? =
    UtilKInputStreamFormat.inputStream2str(this)

fun InputStream.inputStream2str(byteArrayOutputStream: ByteArrayOutputStream): String? =
    UtilKInputStreamFormat.inputStream2str(this, byteArrayOutputStream)

fun InputStream.inputStream2str2(): String? =
    UtilKInputStreamFormat.inputStream2str2(this)

fun InputStream.inputStream2str3(): String? =
    UtilKInputStreamFormat.inputStream2str3(this)

////////////////////////////////////////////////////////////////////////////

fun InputStream.inputStream2anyBitmap(): Bitmap =
    UtilKInputStreamFormat.inputStream2anyBitmap(this)

fun InputStream.inputStream2anyBitmap(outPadding: Rect?, opts: BitmapFactory.Options): Bitmap? =
    UtilKInputStreamFormat.inputStream2anyBitmap(this, outPadding, opts)

////////////////////////////////////////////////////////////////////////////

fun InputStream.inputStream2bitmapDrawable(): BitmapDrawable =
    UtilKInputStreamFormat.inputStream2bitmapDrawable(this)

////////////////////////////////////////////////////////////////////////////

fun InputStream.inputStream2file(destFilePathWithName: String, isAppend: Boolean = false, bufferSize: Int = 1024): File? =
    UtilKInputStreamFormat.inputStream2file(this, destFilePathWithName, isAppend, bufferSize)

fun InputStream.inputStream2file(destFile: File, isAppend: Boolean = false, bufferSize: Int = 1024): File? =
    UtilKInputStreamFormat.inputStream2file(this, destFile, isAppend, bufferSize)

@RequiresApi(CVersCode.V_29_10_Q)
fun InputStream.inputStream2file2(destFilePathWithName: String, isAppend: Boolean = false): File? =
    UtilKInputStreamFormat.inputStream2file2(this, destFilePathWithName, isAppend)

@RequiresApi(CVersCode.V_29_10_Q)
fun InputStream.inputStream2file2(destFile: File, isAppend: Boolean = false): File? =
    UtilKInputStreamFormat.inputStream2file2(this, destFile, isAppend)

////////////////////////////////////////////////////////////////////////////

fun InputStream.inputStream2outputStream(outputStream: OutputStream, bufferSize: Int) {
    UtilKInputStreamFormat.inputStream2outputStream(this, outputStream, bufferSize)
}

fun InputStream.inputStream2outputStream2(outputStream: OutputStream, bufferSize: Int) {
    UtilKInputStreamFormat.inputStream2outputStream2(this, outputStream, bufferSize)
}

fun InputStream.inputStream2outputStream(outputStream: OutputStream) {
    UtilKInputStreamFormat.inputStream2outputStream(this, outputStream)
}

@RequiresApi(CVersCode.V_29_10_Q)
fun InputStream.inputStream2outputStream2(outputStream: OutputStream) {
    UtilKInputStreamFormat.inputStream2outputStream2(this, outputStream)
}

////////////////////////////////////////////////////////////////////////////

object UtilKInputStreamFormat {
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
    fun inputStream2bytes2(inputStream: InputStream): ByteArray {
        val size = inputStream.available()
        val bytes = ByteArray(size)
        try {
            val readSize = inputStream.read(bytes)
            if (readSize.toLong() < size) throw IOException(String.format("File length is [{}] but read [{}]!", *arrayOf<Any>(size, readSize)))
        } catch (e: Exception) {
            throw Exception(e)
        } finally {
            inputStream.close()
        }
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
    @Throws(NoSuchAlgorithmException::class)
    fun inputStream2strMd5(inputStream: InputStream): String {
        val messageDigest: MessageDigest = UtilKMd5.get()
        var readCount: Int
        val bytes = ByteArray(1024 * 1024)
        while (inputStream.read(bytes).also { readCount = it } != -1)
            messageDigest.update(bytes, 0, readCount)
        return messageDigest.digest().bytes2strHex()
    }

    @JvmStatic
    @Throws(NoSuchAlgorithmException::class)
    fun inputStream2strMd52(inputStream: InputStream): String {
        val messageDigest: MessageDigest = UtilKMd5.get()
        var readCount: Int
        val bytes = ByteArray(1024)
        while (inputStream.read(bytes, 0, 1024).also { readCount = it } != -1)
            messageDigest.update(bytes, 0, readCount)
        return BigInteger(1, messageDigest.digest()).toString(16)
    }

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
            e.message?.et(UtilKInputStream.TAG)
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
            e.message?.et(UtilKInputStream.TAG)
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
    fun inputStream2anyBitmap(inputStream: InputStream, outPadding: Rect?, opts: BitmapFactory.Options): Bitmap? =
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
            e.message?.et(UtilKInputStream.TAG)
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
            e.message?.et(UtilKInputStream.TAG)
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
}