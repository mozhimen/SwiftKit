package com.mozhimen.basick.utilk.java.io

import android.graphics.Bitmap
import com.mozhimen.basick.utilk.kotlin.bytes2bitmapAny
import com.mozhimen.basick.utilk.kotlin.bytes2file
import com.mozhimen.basick.utilk.kotlin.bytes2str
import com.mozhimen.basick.utilk.kotlin.strFilePath2file
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.charset.Charset

/**
 * @ClassName UtilKOutputStream
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/31 14:07
 * @Version 1.0
 */
fun ByteArrayOutputStream.byteArrayOutputStream2bytes_flushClose(): ByteArray =
    UtilKByteArrayOutputStream.byteArrayOutputStream2bytes_flushClose(this)

fun ByteArrayOutputStream.byteArrayOutputStream2str_flushClose(charset: Charset = Charsets.UTF_8): String =
    UtilKByteArrayOutputStream.byteArrayOutputStream2str_flushClose(this, charset)

fun ByteArrayOutputStream.byteArrayOutputStream2bitmapAny_flushClose(): Bitmap =
    UtilKByteArrayOutputStream.byteArrayOutputStream2bitmapAny_flushClose(this)

fun ByteArrayOutputStream.byteArrayOutputStream2file_flushClose(strFilePathNameDest: String, isAppend: Boolean = false): File =
    UtilKByteArrayOutputStream.byteArrayOutputStream2file_flushClose(this, strFilePathNameDest, isAppend)

fun ByteArrayOutputStream.byteArrayOutputStream2file_flushClose(fileDest: File, isAppend: Boolean = false): File =
    UtilKByteArrayOutputStream.byteArrayOutputStream2file_flushClose(this, fileDest, isAppend)

////////////////////////////////////////////////////////////////////

object UtilKByteArrayOutputStream {
    @JvmStatic
    fun get(file: File): ByteArrayOutputStream =
        ByteArrayOutputStream(file.length().toInt())

    ////////////////////////////////////////////////////////////////////

    @JvmStatic
    @Throws(Exception::class)
    fun byteArrayOutputStream2bytes_flushClose(byteArrayOutputStream: ByteArrayOutputStream): ByteArray =
        byteArrayOutputStream.flushClose { it.toByteArray() }

    @JvmStatic
    fun byteArrayOutputStream2str_flushClose(byteArrayOutputStream: ByteArrayOutputStream, charset: Charset = Charsets.UTF_8): String =
        byteArrayOutputStream.byteArrayOutputStream2bytes_flushClose().bytes2str(charset)

    @JvmStatic
    fun byteArrayOutputStream2bitmapAny_flushClose(byteArrayOutputStream: ByteArrayOutputStream): Bitmap =
        byteArrayOutputStream.byteArrayOutputStream2bytes_flushClose().bytes2bitmapAny()

    @JvmStatic
    fun byteArrayOutputStream2file_flushClose(byteArrayOutputStream: ByteArrayOutputStream, strFilePathNameDest: String, isAppend: Boolean = false): File =
        byteArrayOutputStream2file_flushClose(byteArrayOutputStream, strFilePathNameDest.strFilePath2file(), isAppend)

    @JvmStatic
    fun byteArrayOutputStream2file_flushClose(byteArrayOutputStream: ByteArrayOutputStream, fileDest: File, isAppend: Boolean = false): File =
        byteArrayOutputStream.byteArrayOutputStream2bytes_flushClose().bytes2file(fileDest, isAppend)
}