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
fun ByteArrayOutputStream.byteArrayOutputStream2bytes_use(): ByteArray =
    UtilKByteArrayOutputStream.byteArrayOutputStream2bytes_use(this)

fun ByteArrayOutputStream.byteArrayOutputStream2str_use(charset: Charset = Charsets.UTF_8): String =
    UtilKByteArrayOutputStream.byteArrayOutputStream2str_use(this, charset)

fun ByteArrayOutputStream.byteArrayOutputStream2bitmapAny_use(): Bitmap =
    UtilKByteArrayOutputStream.byteArrayOutputStream2bitmapAny_use(this)

fun ByteArrayOutputStream.byteArrayOutputStream2file_use(strFilePathNameDest: String, isAppend: Boolean = false): File =
    UtilKByteArrayOutputStream.byteArrayOutputStream2file_use(this, strFilePathNameDest, isAppend)

fun ByteArrayOutputStream.byteArrayOutputStream2file_use(fileDest: File, isAppend: Boolean = false): File =
    UtilKByteArrayOutputStream.byteArrayOutputStream2file_use(this, fileDest, isAppend)

////////////////////////////////////////////////////////////////////

object UtilKByteArrayOutputStream {
    @JvmStatic
    fun get(): ByteArrayOutputStream =
        ByteArrayOutputStream()

    @JvmStatic
    fun get(file: File): ByteArrayOutputStream =
        ByteArrayOutputStream(file.length().toInt())

    ////////////////////////////////////////////////////////////////////

    @JvmStatic
    @Throws(Exception::class)
    fun byteArrayOutputStream2bytes_use(byteArrayOutputStream: ByteArrayOutputStream): ByteArray =
        byteArrayOutputStream.use { it.toByteArray() }

    @JvmStatic
    fun byteArrayOutputStream2str_use(byteArrayOutputStream: ByteArrayOutputStream, charset: Charset = Charsets.UTF_8): String =
        byteArrayOutputStream.byteArrayOutputStream2bytes_use().bytes2str(charset)

    @JvmStatic
    fun byteArrayOutputStream2bitmapAny_use(byteArrayOutputStream: ByteArrayOutputStream): Bitmap =
        byteArrayOutputStream.byteArrayOutputStream2bytes_use().bytes2bitmapAny()

    @JvmStatic
    fun byteArrayOutputStream2file_use(byteArrayOutputStream: ByteArrayOutputStream, strFilePathNameDest: String, isAppend: Boolean = false): File =
        byteArrayOutputStream2file_use(byteArrayOutputStream, strFilePathNameDest.strFilePath2file(), isAppend)

    @JvmStatic
    fun byteArrayOutputStream2file_use(byteArrayOutputStream: ByteArrayOutputStream, fileDest: File, isAppend: Boolean = false): File =
        byteArrayOutputStream.byteArrayOutputStream2bytes_use().bytes2file(fileDest, isAppend)
}