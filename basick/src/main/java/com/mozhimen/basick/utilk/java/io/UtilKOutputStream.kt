package com.mozhimen.basick.utilk.java.io

import com.mozhimen.basick.elemk.cons.CMsg
import com.mozhimen.basick.utilk.android.util.et
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

/**
 * @ClassName UtilKOutputStream
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/31 14:07
 * @Version 1.0
 */
fun ByteArrayOutputStream.asFile(filePathWithName: String, isOverwrite: Boolean = true): String =
    UtilKOutputStream.byteArrayOutputStream2file(this, filePathWithName, isOverwrite)

fun ByteArrayOutputStream.asFile(destFile: File, isOverwrite: Boolean = true): String =
    UtilKOutputStream.byteArrayOutputStream2file(this, destFile, isOverwrite)

object UtilKOutputStream {
    /**
     * 输出流转文件
     * @param byteArrayOutputStream ByteArrayOutputStream
     * @param filePathWithName String
     * @param isOverwrite Boolean
     * @return String
     */
    @JvmStatic
    fun byteArrayOutputStream2file(byteArrayOutputStream: ByteArrayOutputStream, filePathWithName: String, isOverwrite: Boolean = true): String =
        byteArrayOutputStream2file(byteArrayOutputStream, File(filePathWithName), isOverwrite)

    /**
     * 输出流转文件
     * @param byteArrayOutputStream ByteArrayOutputStream
     * @param destFile File
     * @param isOverwrite Boolean
     */
    @JvmStatic
    fun byteArrayOutputStream2file(byteArrayOutputStream: ByteArrayOutputStream, destFile: File, isOverwrite: Boolean = true): String {
        UtilKFile.createFile(destFile)
        val fileOutputStream = FileOutputStream(destFile)
        try {
            fileOutputStream.write(byteArrayOutputStream.toByteArray())
            return destFile.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(UtilKFile.TAG)
        } finally {
            byteArrayOutputStream.flush()
            byteArrayOutputStream.close()
            fileOutputStream.flush()
            fileOutputStream.close()
        }
        return CMsg.WRONG
    }
}