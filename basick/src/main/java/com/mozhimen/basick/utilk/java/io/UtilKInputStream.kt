package com.mozhimen.basick.utilk.java.io

import android.text.TextUtils
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.elemk.commons.IAB_Listener
import com.mozhimen.basick.utilk.android.os.UtilKFileUtils
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.utilk.commons.IUtilK
import com.mozhimen.basick.utilk.kotlin.UtilKLongFormat
import com.mozhimen.basick.utilk.kotlin.long2strCrc32
import com.mozhimen.basick.utilk.kotlin.ranges.constraint
import java.io.InputStream
import java.io.OutputStream
import java.util.zip.CRC32
import java.util.zip.CheckedInputStream

/**
 * @ClassName UtilKInputStream
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/31 11:42
 * @Version 1.0
 */
fun InputStream.getStrCrc32_use(): String =
    UtilKInputStream.getStrCrc32_use(this)

fun InputStream.getAvailableLong_use(): Long =
    UtilKInputStream.getAvailableLong_use(this)

fun InputStream.read_use(bytes: ByteArray): Int =
    UtilKInputStream.read_use(this, bytes)

////////////////////////////////////////////////////////////////////////////

object UtilKInputStream : IUtilK {
    @JvmStatic
    fun getStrCrc32_use(inputStream: InputStream): String {
        val buffer = ByteArray(1024)
        inputStream.use {
            CheckedInputStream(inputStream, CRC32()).use { crcStream ->
                while (crcStream.read(buffer) != -1) {
                    // Read file in completely
                }
                return crcStream.checksum.value.long2strCrc32()
            }
        }
    }

    @JvmStatic
    fun getAvailableLong_use(inputStream: InputStream): Long =
        inputStream.use { it.available().toLong() }

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun isInputStreamSame_use(inputStream1: InputStream, inputStream2: InputStream): Boolean =
        TextUtils.equals(inputStream1.inputStream2strMd5_use_ofBigInteger(), inputStream2.inputStream2strMd5_use_ofBigInteger())

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun read_use(inputStream: InputStream, bytes: ByteArray): Int =
        inputStream.use { inputStream.read(bytes) }

    @JvmStatic
    @Throws(Exception::class)
    fun read_write_use(inputStream: InputStream, outputStream: OutputStream) {
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
    fun read_write_use(inputStream: InputStream, outputStream: OutputStream, bufferSize: Int) {
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
    fun read_write_use(inputStream: InputStream, outputStream: OutputStream, bufferSize: Int, block: IAB_Listener<Int, Float>? = null) {
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
    @RequiresApi(CVersCode.V_29_10_Q)
    fun read_write_use_ofFileUtils(inputStream: InputStream, outputStream: OutputStream) {
        UtilKFileUtils.copy_use(inputStream, outputStream)
    }
}