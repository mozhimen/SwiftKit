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
fun ByteArrayOutputStream.byteArrayOutputStream2bytes(): ByteArray =
    UtilKByteArrayOutputStream.byteArrayOutputStream2bytes(this)

fun ByteArrayOutputStream.byteArrayOutputStream2str(charset: Charset = Charsets.UTF_8): String =
    UtilKByteArrayOutputStream.byteArrayOutputStream2str(this, charset)

fun ByteArrayOutputStream.byteArrayOutputStream2bitmapAny(): Bitmap =
    UtilKByteArrayOutputStream.byteArrayOutputStream2bitmapAny(this)

fun ByteArrayOutputStream.byteArrayOutputStream2file(strFilePathNameDest: String, isAppend: Boolean = false): File =
    UtilKByteArrayOutputStream.byteArrayOutputStream2file(this, strFilePathNameDest, isAppend)

fun ByteArrayOutputStream.byteArrayOutputStream2file(fileDest: File, isAppend: Boolean = false): File =
    UtilKByteArrayOutputStream.byteArrayOutputStream2file(this, fileDest, isAppend)


object UtilKByteArrayOutputStream {
    @JvmStatic
    @Throws(Exception::class)
    fun byteArrayOutputStream2bytes(byteArrayOutputStream: ByteArrayOutputStream): ByteArray =
        byteArrayOutputStream.flushClose { it.toByteArray() }

    @JvmStatic
    fun byteArrayOutputStream2str(byteArrayOutputStream: ByteArrayOutputStream, charset: Charset = Charsets.UTF_8): String =
        byteArrayOutputStream.byteArrayOutputStream2bytes().bytes2str(charset)

    @JvmStatic
    fun byteArrayOutputStream2bitmapAny(byteArrayOutputStream: ByteArrayOutputStream): Bitmap =
        byteArrayOutputStream.byteArrayOutputStream2bytes().bytes2bitmapAny()

    @JvmStatic
    fun byteArrayOutputStream2file(byteArrayOutputStream: ByteArrayOutputStream, strFilePathNameDest: String, isAppend: Boolean = false): File =
        byteArrayOutputStream2file(byteArrayOutputStream, strFilePathNameDest.strFilePath2file(), isAppend)

    @JvmStatic
    fun byteArrayOutputStream2file(byteArrayOutputStream: ByteArrayOutputStream, fileDest: File, isAppend: Boolean = false): File =
        byteArrayOutputStream.byteArrayOutputStream2bytes().bytes2file(fileDest, isAppend)
}