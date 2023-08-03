package com.mozhimen.basick.utilk.java.io

import android.graphics.Bitmap
import androidx.annotation.IntRange
import com.mozhimen.basick.utilk.android.graphics.UtilKBitmapFormat
import com.mozhimen.basick.utilk.android.graphics.anyBytes2anyBitmap
import com.mozhimen.basick.utilk.android.graphics.applyAnyBitmapCompress
import com.mozhimen.basick.utilk.android.util.et
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

fun ByteArrayOutputStream.byteArrayOutputStream2file(filePathWithName: String, isOverwrite: Boolean = true): File =
    UtilKByteArrayOutputStream.byteArrayOutputStream2file(this, filePathWithName, isOverwrite)

fun ByteArrayOutputStream.byteArrayOutputStream2file(destFile: File, isOverwrite: Boolean = true): File =
    UtilKByteArrayOutputStream.byteArrayOutputStream2file(this, destFile, isOverwrite)


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
        byteArrayOutputStream.flushClose { it.byteArrayOutputStream2bytes().anyBytes2anyBitmap() }

    /**
     * 输出流转文件
     * @param byteArrayOutputStream ByteArrayOutputStream
     * @param filePathWithName String
     * @param isOverwrite Boolean
     * @return String
     */
    @JvmStatic
    fun byteArrayOutputStream2file(byteArrayOutputStream: ByteArrayOutputStream, filePathWithName: String, isOverwrite: Boolean = true): File =
        byteArrayOutputStream2file(byteArrayOutputStream, filePathWithName.strFilePath2file(), isOverwrite)

    /**
     * 输出流转文件
     * @param byteArrayOutputStream ByteArrayOutputStream
     * @param destFile File
     * @param isOverwrite Boolean
     */
    @JvmStatic
    fun byteArrayOutputStream2file(byteArrayOutputStream: ByteArrayOutputStream, destFile: File, isOverwrite: Boolean = true): File =
        byteArrayOutputStream.flushClose { it.byteArrayOutputStream2bytes().bytes2file(destFile, isOverwrite) }
}