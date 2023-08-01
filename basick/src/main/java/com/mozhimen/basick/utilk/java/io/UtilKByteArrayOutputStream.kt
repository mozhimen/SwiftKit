package com.mozhimen.basick.utilk.java.io

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.kotlin.asFile
import java.io.ByteArrayOutputStream
import java.io.File

/**
 * @ClassName UtilKOutputStream
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/31 14:07
 * @Version 1.0
 */
fun ByteArrayOutputStream.asBytes(): ByteArray =
    UtilKByteArrayOutputStream.asBytes(this)

fun ByteArrayOutputStream.asFile(destFilePathWithName: String, isOverwrite: Boolean = true): File? =
    UtilKByteArrayOutputStream.asFile(this, destFilePathWithName, isOverwrite)

fun ByteArrayOutputStream.asFile(destFile: File, isOverwrite: Boolean = true): File? =
    UtilKByteArrayOutputStream.asFile(this, destFile, isOverwrite)

object UtilKByteArrayOutputStream {
    @JvmStatic
    @Throws(Exception::class)
    fun asBytes(byteArrayOutputStream: ByteArrayOutputStream): ByteArray =
        byteArrayOutputStream.use { it.toByteArray() }

    @JvmStatic
    fun asBitmap(byteArrayOutputStream: ByteArrayOutputStream): Bitmap =
        byteArrayOutputStream.use { BitmapFactory.decodeByteArray(byteArrayOutputStream.asBytes(), 0, byteArrayOutputStream.size()) }

    /**
     * 输出流转文件
     * @param byteArrayOutputStream ByteArrayOutputStream
     * @param filePathWithName String
     * @param isOverwrite Boolean
     * @return String
     */
    @JvmStatic
    fun asFile(byteArrayOutputStream: ByteArrayOutputStream, filePathWithName: String, isOverwrite: Boolean = true): File? =
        asFile(byteArrayOutputStream, File(filePathWithName), isOverwrite)

    /**
     * 输出流转文件
     * @param byteArrayOutputStream ByteArrayOutputStream
     * @param destFile File
     * @param isOverwrite Boolean
     */
    @JvmStatic
    fun asFile(byteArrayOutputStream: ByteArrayOutputStream, destFile: File, isOverwrite: Boolean = true): File? {
        try {
            byteArrayOutputStream.asBytes().asFile(destFile, isOverwrite)
            return destFile
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(UtilKFile.TAG)
        } finally {
            byteArrayOutputStream.flush()
            byteArrayOutputStream.close()
        }
        return null
    }
}