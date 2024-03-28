package com.mozhimen.basick.utilk.java.io

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.os.FileUtils
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.elemk.commons.IAB_Listener
import com.mozhimen.basick.utilk.android.util.e
import com.mozhimen.basick.utilk.commons.IUtilK
import com.mozhimen.basick.utilk.java.security.UtilKMd5
import com.mozhimen.basick.utilk.kotlin.UtilKByteArray
import com.mozhimen.basick.utilk.kotlin.bytes2str
import com.mozhimen.basick.utilk.kotlin.bytes2strHex
import com.mozhimen.basick.utilk.kotlin.bytes2strHex_ofHexString
import com.mozhimen.basick.utilk.kotlin.bytes2strHex_ofBigInteger
import com.mozhimen.basick.utilk.kotlin.createFile
import com.mozhimen.basick.utilk.kotlin.constraint
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.nio.charset.Charset
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

fun InputStream.inputStream2bytes_use(): ByteArray =
    UtilKInputStreamFormat.inputStream2bytes_use(this)

////////////////////////////////////////////////////////////////////////////

fun InputStream.inputStream2bytesMd5_use(bufferSize: Int = 1024 * 1024): ByteArray =
    UtilKInputStreamFormat.inputStream2bytesMd5_use(this, bufferSize)

fun InputStream.inputStream2strMd5_use(): String =
    UtilKInputStreamFormat.inputStream2strMd5_use(this)

fun InputStream.inputStream2strMd5_use_ofBigInteger(): String =
    UtilKInputStreamFormat.inputStream2strMd5_use_ofBigInteger(this)

fun InputStream.inputStream2strMd5_use_ofHexString(): String =
    UtilKInputStreamFormat.inputStream2strMd5_use_ofHexString(this)

fun InputStream.inputStream2str_use_ofBufferedReader(charset: String? = null, bufferSize: Int = 1024): String =
    UtilKInputStreamFormat.inputStream2str_use_ofBufferedReader(this, charset, bufferSize)

fun InputStream.inputStream2strOfBytesOutStream(byteArrayOutputStream: ByteArrayOutputStream): String =
    UtilKInputStreamFormat.inputStream2strOfBytesOutStream(this, byteArrayOutputStream)

fun InputStream.inputStream2strOfBytesOutStream(): String =
    UtilKInputStreamFormat.inputStream2strOfBytesOutStream(this)

fun InputStream.inputStream2strOfBytes(): String? =
    UtilKInputStreamFormat.inputStream2strOfBytes(this)

////////////////////////////////////////////////////////////////////////////

fun InputStream.inputStream2bitmapAny(): Bitmap =
    UtilKInputStreamFormat.inputStream2bitmapAny(this)

fun InputStream.inputStream2bitmapAny(outPadding: Rect?, opts: BitmapFactory.Options): Bitmap? =
    UtilKInputStreamFormat.inputStream2bitmapAny(this, outPadding, opts)

////////////////////////////////////////////////////////////////////////////

fun InputStream.inputStream2bitmapDrawable(): BitmapDrawable =
    UtilKInputStreamFormat.inputStream2bitmapDrawable(this)

////////////////////////////////////////////////////////////////////////////

fun InputStream.inputStream2file(strFilePathNameDest: String, isAppend: Boolean = false, bufferSize: Int = 1024, block: IAB_Listener<Int, Float>? = null): File? =
    UtilKInputStreamFormat.inputStream2file(this, strFilePathNameDest, isAppend, bufferSize, block)

fun InputStream.inputStream2file(fileDest: File, isAppend: Boolean = false, bufferSize: Int = 1024, block: IAB_Listener<Int, Float>? = null): File? =
    UtilKInputStreamFormat.inputStream2file(this, fileDest, isAppend, bufferSize, block)

fun InputStream.inputStream2fileOfBufferedOps(strFilePathNameDest: String, isAppend: Boolean = false, bufferSize: Int = 1024, block: IAB_Listener<Int, Float>? = null): File? =
    UtilKInputStreamFormat.inputStream2fileOfBufferedOps(this, strFilePathNameDest, isAppend, bufferSize, block)

fun InputStream.inputStream2fileOfBufferedOps(fileDest: File, isAppend: Boolean = false, bufferSize: Int = 1024, block: IAB_Listener<Int, Float>? = null): File? =
    UtilKInputStreamFormat.inputStream2fileOfBufferedOps(this, fileDest, isAppend, bufferSize, block)

@RequiresApi(CVersCode.V_29_10_Q)
fun InputStream.inputStream2fileOfFileUtils(strFilePathNameDest: String, isAppend: Boolean = false): File? =
    UtilKInputStreamFormat.inputStream2fileOfFileUtils(this, strFilePathNameDest, isAppend)

@RequiresApi(CVersCode.V_29_10_Q)
fun InputStream.inputStream2fileOfFileUtils(fileDest: File, isAppend: Boolean = false): File? =
    UtilKInputStreamFormat.inputStream2fileOfFileUtils(this, fileDest, isAppend)

////////////////////////////////////////////////////////////////////////////

fun InputStream.inputStream2outputStream(outputStream: OutputStream, bufferSize: Int, block: IAB_Listener<Int, Float>? = null) {
    UtilKInputStreamFormat.inputStream2outputStream(this, outputStream, bufferSize, block)
}

fun InputStream.inputStream2outputStream_ofFileUtils(outputStream: OutputStream, bufferSize: Int) {
    UtilKInputStreamFormat.inputStream2outputStreamOfFileUtils(this, outputStream, bufferSize)
}

fun InputStream.inputStream2outputStream(outputStream: OutputStream) {
    UtilKInputStreamFormat.inputStream2outputStream(this, outputStream)
}

@RequiresApi(CVersCode.V_29_10_Q)
fun InputStream.inputStream2outputStream_ofFileUtils(outputStream: OutputStream) {
    UtilKInputStreamFormat.inputStream2outputStreamOfFileUtils(this, outputStream)
}

////////////////////////////////////////////////////////////////////////////

object UtilKInputStreamFormat : IUtilK {
    @JvmStatic
    fun inputStream2bufferedInputStream(inputStream: InputStream): BufferedInputStream =
        UtilKBufferedInputStream.get(inputStream)

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun inputStream2bytes_use(inputStream: InputStream): ByteArray =
        inputStream2bytes_use(inputStream, false)

    @JvmStatic
    fun inputStream2bytes_use(inputStream: InputStream, isVerify: Boolean): ByteArray {
        val bytes = UtilKByteArray.get(inputStream)
        val readSize = inputStream.read_use(bytes)
        if (isVerify && readSize.toLong() < inputStream.available())
            throw IOException(String.format("File length is [{}] but read [{}]!", *arrayOf<Any>(inputStream.available(), readSize)))
        return bytes
    }

    /**
     * 和方法二一样(增加完整性校验)
     */
    @JvmStatic
    fun inputStream2bytes_use_ofVerify(inputStream: InputStream): ByteArray {
        val bytes = UtilKByteArray.get(inputStream)
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
    fun inputStream2bytesMd5_use(inputStream: InputStream, bufferSize: Int = 1024 * 1024): ByteArray =
        inputStream.use {
            val messageDigest: MessageDigest = UtilKMd5.get()
            var readCount: Int
            val bytes = ByteArray(bufferSize)
            while (inputStream.read(bytes).also { readCount = it } != -1)
                messageDigest.update(bytes, 0, readCount)
            messageDigest.digest()
        }

    @JvmStatic
    @Throws(NoSuchAlgorithmException::class)
    fun inputStream2strMd5_use(inputStream: InputStream): String {
        return inputStream.inputStream2bytesMd5_use().bytes2strHex()
    }

    @JvmStatic
    @Throws(NoSuchAlgorithmException::class)
    fun inputStream2strMd5_use_ofBigInteger(inputStream: InputStream): String {
        return inputStream.inputStream2bytesMd5_use().bytes2strHex_ofBigInteger()
    }

    @JvmStatic
    @Throws(NoSuchAlgorithmException::class)
    fun inputStream2strMd5_use_ofHexString(inputStream: InputStream): String {
        return inputStream.inputStream2bytesMd5_use().bytes2strHex_ofHexString()
    }

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun inputStream2str_use_ofBufferedReader(inputStream: InputStream, charset: String? = null, bufferSize: Int = 1024): String =
        UtilKInputStreamReader.readLine_use(inputStream, charset, bufferSize)

    @JvmStatic
    fun inputStream2strOfBytesOutStream(inputStream: InputStream): String =
        inputStream2strOfBytesOutStream(inputStream, ByteArrayOutputStream())

    @JvmStatic
    fun inputStream2strOfBytesOutStream(inputStream: InputStream, byteArrayOutputStream: ByteArrayOutputStream, charset: Charset = Charsets.UTF_8): String {
        inputStream.inputStream2outputStream(byteArrayOutputStream)
        return byteArrayOutputStream.byteArrayOutputStream2str_flushClose(charset)
    }

    @JvmStatic
    fun inputStream2strOfBytes(inputStream: InputStream): String? {
        val inputStringBuilder = StringBuilder()
        try {
            var readCount: Int
            val bytes = ByteArray(1024)
            while (inputStream.read(bytes).also { readCount = it } != -1)
                inputStringBuilder.append(bytes.bytes2str(0, readCount))
            return inputStringBuilder.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.e(TAG)
        } finally {
            inputStream.close()
        }
        return null
    }

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun inputStream2bitmapAny(inputStream: InputStream): Bitmap =
        inputStream.use { BitmapFactory.decodeStream(it) }

    @JvmStatic
    fun inputStream2bitmapAny(inputStream: InputStream, outPadding: Rect?, opts: BitmapFactory.Options): Bitmap? =
        inputStream.use { BitmapFactory.decodeStream(it, outPadding, opts) }

    ////////////////////////////////////////////////////////////////////////////
    @JvmStatic
    fun inputStream2bitmapDrawable(inputStream: InputStream): BitmapDrawable =
        inputStream.use { BitmapDrawable(null, it) }

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun inputStream2file(inputStream: InputStream, strFilePathNameDest: String, isAppend: Boolean = false, bufferSize: Int = 1024, block: IAB_Listener<Int, Float>? = null): File? =
        inputStream2file(inputStream, strFilePathNameDest.createFile(), isAppend, bufferSize, block)

    @JvmStatic
    fun inputStream2file(inputStream: InputStream, fileDest: File, isAppend: Boolean = false, bufferSize: Int = 1024, block: IAB_Listener<Int, Float>? = null): File? {
        UtilKFileWrapper.createFile(fileDest)
        /*//        val fileInputStream = file.file2fileInputStream()
        //        UtilKLogWrapper.d(TAG, "inputStream2file: inputStream ${inputStream.available()}")
        //        if (isInputStreamSame(inputStream, fileInputStream)) {//相似内容就直接返回地址
        //            UtilKLogWrapper.d(TAG, "assetCopyFile: the two files is same")
        //            return file//"the two files is same, don't need overwrite"
        //        }*/
        try {
            inputStream.inputStream2outputStream(fileDest.file2fileOutputStream(isAppend), bufferSize, block)
            return fileDest
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.e(TAG)
        }
        return null
    }

    @JvmStatic
    fun inputStream2fileOfBufferedOps(inputStream: InputStream, strFilePathNameDest: String, isAppend: Boolean = false, bufferSize: Int = 1024, block: IAB_Listener<Int, Float>? = null): File? =
        inputStream2fileOfBufferedOps(inputStream, strFilePathNameDest.createFile(), isAppend, bufferSize, block)

    @JvmStatic
    fun inputStream2fileOfBufferedOps(inputStream: InputStream, fileDest: File, isAppend: Boolean = false, bufferSize: Int = 1024, block: IAB_Listener<Int, Float>? = null): File? {
        UtilKFileWrapper.createFile(fileDest)
        try {
            inputStream.inputStream2outputStream(fileDest.file2fileBufferedOutputStream(isAppend), bufferSize, block)
            return fileDest
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.e(TAG)
        }
        return null
    }

    @JvmStatic
    @RequiresApi(CVersCode.V_29_10_Q)
    fun inputStream2fileOfFileUtils(inputStream: InputStream, strFilePathNameDest: String, isAppend: Boolean = false): File? =
        inputStream2fileOfFileUtils(inputStream, strFilePathNameDest.createFile(), isAppend)

    @JvmStatic
    @RequiresApi(CVersCode.V_29_10_Q)
    fun inputStream2fileOfFileUtils(inputStream: InputStream, fileDest: File, isAppend: Boolean = false): File? {
        UtilKFileWrapper.createFile(fileDest)
        /*//        val fileInputStream = file.file2fileInputStream()
        //        if (isInputStreamSame(inputStream, fileInputStream)) {//相似内容就直接返回地址
        //            UtilKLogWrapper.d(UtilKFile.TAG, "assetCopyFile: the two files is same")
        //            return file//"the two files is same, don't need overwrite"
        //        }*/
        try {
            inputStream.inputStream2outputStream_ofFileUtils(fileDest.file2fileOutputStream(isAppend))
            return fileDest
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.e(TAG)
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
            var percent: Float
            UtilKLogWrapper.d(TAG, "inputStream2outputStream: totalCount $totalCount")
            while (inputStream.read(bytes).also { readCount = it } != -1) {
                offset += readCount
                outputStream.write(bytes, 0, readCount)
                percent = (offset.toFloat() / totalCount.toFloat()).constraint(0f, 1f)
                //UtilKLogWrapper.d(TAG, "inputStream2outputStream: offset $offset total $totalCount percent $percent")
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
    fun inputStream2outputStreamOfFileUtils(inputStream: InputStream, outputStream: OutputStream, bufferSize: Int) {
        try {
            var readCount: Int
            val bytes = ByteArray(bufferSize)
            while (inputStream.read(bytes, 0, bufferSize).also { readCount = it } != -1)
                outputStream.write(bytes, 0, readCount)
        } catch (e: Exception) {
            e.printStackTrace()
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
            e.printStackTrace()
        } finally {
            inputStream.close()
            outputStream.flushClose()
        }
    }

    @JvmStatic
    @Throws(Exception::class)
    @RequiresApi(CVersCode.V_29_10_Q)
    fun inputStream2outputStreamOfFileUtils(inputStream: InputStream, outputStream: OutputStream) {
        try {
            FileUtils.copy(inputStream, outputStream)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            inputStream.close()
            outputStream.flushClose()
        }
    }
}