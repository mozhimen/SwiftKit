package com.mozhimen.basick.utilk.java.io

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.mozhimen.basick.utilk.kotlin.bytes2file
import java.io.ByteArrayOutputStream
import java.io.File

/**
 * @ClassName UtilKOutputStream
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/31 14:07
 * @Version 1.0
 */
fun ByteArrayOutputStream.byteArrayOutputStream2bytes(): ByteArray =
        UtilKByteArrayOutputStream.byteArrayOutputStream2bytes(this)

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
    fun byteArrayOutputStream2anyBitmap(byteArrayOutputStream: ByteArrayOutputStream): Bitmap =
            byteArrayOutputStream.flushClose { BitmapFactory.decodeByteArray(byteArrayOutputStream.byteArrayOutputStream2bytes(), 0, byteArrayOutputStream.size()) }

    /**
     * 输出流转文件
     * @param byteArrayOutputStream ByteArrayOutputStream
     * @param filePathWithName String
     * @param isOverwrite Boolean
     * @return String
     */
    @JvmStatic
    fun byteArrayOutputStream2file(byteArrayOutputStream: ByteArrayOutputStream, filePathWithName: String, isOverwrite: Boolean = true): File =
            byteArrayOutputStream2file(byteArrayOutputStream, File(filePathWithName), isOverwrite)

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