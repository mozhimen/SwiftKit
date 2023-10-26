package com.mozhimen.basick.utilk.java.io

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.os.FileUtils
import android.util.Log
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.elemk.commons.IAB_Listener
import com.mozhimen.basick.elemk.kotlin.text.cons.CCharsets
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.bases.IUtilK
import com.mozhimen.basick.utilk.java.security.UtilKMd5
import com.mozhimen.basick.utilk.kotlin.bytes2str
import com.mozhimen.basick.utilk.kotlin.bytes2strHex
import com.mozhimen.basick.utilk.kotlin.createFile
import com.mozhimen.basick.utilk.kotlin.normalize
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

fun InputStream.inputStream2bytesCheck(): ByteArray =
    UtilKInputStreamFormat.inputStream2bytesCheck(this)

fun InputStream.inputStream2bytesCheck(fileLength: Long): ByteArray =
    UtilKInputStreamFormat.inputStream2bytesCheck(this, fileLength)

////////////////////////////////////////////////////////////////////////////

fun InputStream.inputStream2strMd5(): String =
    UtilKInputStreamFormat.inputStream2strMd5(this)

fun InputStream.inputStream2strMd52(): String =
    UtilKInputStreamFormat.inputStream2strMd52(this)

fun InputStream.inputStream2str(): String =
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

fun InputStream.inputStream2file(destFilePathWithName: String, isAppend: Boolean = false, bufferSize: Int = 1024, block: IAB_Listener<Int, Float>? = null): File? =
    UtilKInputStreamFormat.inputStream2file(this, destFilePathWithName, isAppend, bufferSize, block)

fun InputStream.inputStream2file(destFile: File, isAppend: Boolean = false, bufferSize: Int = 1024, block: IAB_Listener<Int, Float>? = null): File? =
    UtilKInputStreamFormat.inputStream2file(this, destFile, isAppend, bufferSize, block)

@RequiresApi(CVersCode.V_29_10_Q)
fun InputStream.inputStream2file2(destFilePathWithName: String, isAppend: Boolean = false): File? =
    UtilKInputStreamFormat.inputStream2file2(this, destFilePathWithName, isAppend)

@RequiresApi(CVersCode.V_29_10_Q)
fun InputStream.inputStream2file2(destFile: File, isAppend: Boolean = false): File? =
    UtilKInputStreamFormat.inputStream2file2(this, destFile, isAppend)

////////////////////////////////////////////////////////////////////////////

fun InputStream.inputStream2outputStream(outputStream: OutputStream, bufferSize: Int, block: IAB_Listener<Int, Float>? = null) {
    UtilKInputStreamFormat.inputStream2outputStream(this, outputStream, bufferSize, block)
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

object UtilKInputStreamFormat : IUtilK {
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

    /**
     * 和方法一一样(增加完整性校验)
     */
    @JvmStatic
    fun inputStream2bytesCheck(inputStream: InputStream): ByteArray {
        val size = inputStream.available()
        val bytes = ByteArray(size)
        inputStream.use {
            val readSize = inputStream.read(bytes)
            if (readSize.toLong() < size) throw IOException(String.format("File length is [{}] but read [{}]!", *arrayOf<Any>(size, readSize)))
        }
        return bytes
    }

    /**
     * 和方法二一样(增加完整性校验)
     */
    @JvmStatic
    fun inputStream2bytesCheck(inputStream: InputStream, fileLength: Long): ByteArray {
        val bytes = ByteArray(fileLength.toInt())
        inputStream.use {
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

    /**
     * 针对字符串文本
     */
    @JvmStatic
    fun inputStream2str(inputStream: InputStream): String {
        val stringBuilder = StringBuilder()
        var inputStreamReader: InputStreamReader? = null
        var bufferedReader: BufferedReader? = null
        try {
            inputStreamReader = InputStreamReader(inputStream, CCharsets.UTF_8)
            bufferedReader = BufferedReader(inputStreamReader)
            var line = ""
            while (bufferedReader.readLine()?.also { line = it } != null)
                stringBuilder.append(line)
            return stringBuilder.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            bufferedReader?.close()
            inputStreamReader?.close()
            inputStream.close()
        }
        return ""
    }

    @JvmStatic
    fun inputStream2str2(inputStream: InputStream): String =
        inputStream2str(inputStream, ByteArrayOutputStream())

    @JvmStatic
    fun inputStream2str(inputStream: InputStream, byteArrayOutputStream: ByteArrayOutputStream): String {
        inputStream.inputStream2outputStream(byteArrayOutputStream)
        return byteArrayOutputStream.byteArrayOutputStream2str()
    }

    @JvmStatic
    fun inputStream2str3(inputStream: InputStream): String? {
        val inputStringBuilder = StringBuilder()
        try {
            var readCount: Int
            val bytes = ByteArray(1024)
            while (inputStream.read(bytes).also { readCount = it } != -1)
                inputStringBuilder.append(bytes.bytes2str(0, readCount))
            return inputStringBuilder.toString()
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
    fun inputStream2file(inputStream: InputStream, destFilePathWithName: String, isAppend: Boolean = false, bufferSize: Int = 1024, block: IAB_Listener<Int, Float>? = null): File? =
        inputStream2file(inputStream, destFilePathWithName.createFile(), isAppend, bufferSize, block)

    @JvmStatic
    fun inputStream2file(inputStream: InputStream, destFile: File, isAppend: Boolean = false, bufferSize: Int = 1024, block: IAB_Listener<Int, Float>? = null): File? {
        UtilKFile.createFile(destFile)
        /*//        val fileInputStream = FileInputStream(file)
        //        Log.d(TAG, "inputStream2file: inputStream ${inputStream.available()}")
        //        if (isInputStreamSame(inputStream, fileInputStream)) {//相似内容就直接返回地址
        //            Log.d(TAG, "assetCopyFile: the two files is same")
        //            return file//"the two files is same, don't need overwrite"
        //        }*/
        try {
            inputStream.inputStream2outputStream(destFile.file2fileOutputStream(isAppend), bufferSize, block)
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
        inputStream2file2(inputStream, filePathWithName.createFile(), isAppend)

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
    fun inputStream2outputStream(inputStream: InputStream, outputStream: OutputStream, bufferSize: Int, block: IAB_Listener<Int, Float>? = null) {
        try {
            val bytes = ByteArray(bufferSize)
            val totalCount = inputStream.available()
            var readCount: Int
            var offset = 0
            var percent: Float = 0f
            Log.d(TAG, "inputStream2outputStream: totalCount $totalCount")
            while (inputStream.read(bytes).also { readCount = it } != -1) {
                offset += readCount
                outputStream.write(bytes, 0, readCount)
                percent = (offset.toFloat() / totalCount.toFloat()).normalize(0f, 1f)
                //Log.d(TAG, "inputStream2outputStream: offset $offset total $totalCount percent $percent")
                block?.invoke(readCount, percent)
            }
        } catch (e: Exception) {
            e.printStackTrace()
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