package com.mozhimen.basick.utilk.java.io

import android.graphics.Bitmap
import com.mozhimen.basick.utilk.android.graphics.anyBytes2anyBitmap
import com.mozhimen.basick.utilk.kotlin.bytes2file
import com.mozhimen.basick.utilk.kotlin.bytes2str
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

fun ByteArrayOutputStream.byteArrayOutputStream2anyBitmap(): Bitmap =
    UtilKByteArrayOutputStream.byteArrayOutputStream2anyBitmap(this)

fun ByteArrayOutputStream.byteArrayOutputStream2file(destFilePathWithName: String, isAppend: Boolean = false): File =
    UtilKByteArrayOutputStream.byteArrayOutputStream2file(this, destFilePathWithName, isAppend)

fun ByteArrayOutputStream.byteArrayOutputStream2file(destFile: File, isAppend: Boolean = false): File =
    UtilKByteArrayOutputStream.byteArrayOutputStream2file(this, destFile, isAppend)


object UtilKByteArrayOutputStream {
    @JvmStatic
    @Throws(Exception::class)
    fun byteArrayOutputStream2bytes(byteArrayOutputStream: ByteArrayOutputStream): ByteArray =
        byteArrayOutputStream.flushClose { it.toByteArray() }

    @JvmStatic
    fun byteArrayOutputStream2str(byteArrayOutputStream: ByteArrayOutputStream, charset: Charset = Charsets.UTF_8): String =
        byteArrayOutputStream.byteArrayOutputStream2bytes().bytes2str(charset)

    @JvmStatic
    fun byteArrayOutputStream2anyBitmap(byteArrayOutputStream: ByteArrayOutputStream): Bitmap =
        byteArrayOutputStream.byteArrayOutputStream2bytes().anyBytes2anyBitmap()

    @JvmStatic
    fun byteArrayOutputStream2file(byteArrayOutputStream: ByteArrayOutputStream, destFilePathWithName: String, isAppend: Boolean = false): File =
        byteArrayOutputStream2file(byteArrayOutputStream, destFilePathWithName.strFilePath2file(), isAppend)

    @JvmStatic
    fun byteArrayOutputStream2file(byteArrayOutputStream: ByteArrayOutputStream, destFile: File, isAppend: Boolean = false): File =
        byteArrayOutputStream.byteArrayOutputStream2bytes().bytes2file(destFile, isAppend)
}