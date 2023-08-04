package com.mozhimen.basick.utilk.java.io

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.RandomAccessFile

/**
 * @ClassName UtilKFileFormat
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/3 16:42
 * @Version 1.0
 */
fun String.strFilePath2file(): File =
        UtilKFileFormat.strFilePath2file(this)

fun File.file2strFilePath(): String =
        UtilKFileFormat.file2strFilePath(this)

object UtilKFileFormat {
    @JvmStatic
    fun strFilePath2file(filePathWithName: String): File =
            File(filePathWithName)

    @JvmStatic
    fun file2strFilePath(file: File): String =
            file.absolutePath

    @JvmStatic
    fun str2file(str: String, filePathWithName: String): File =
            str2file(str, UtilKFile.createFile(filePathWithName))

    /**
     * Str2file
     * 文本转文件
     * @param str
     * @param destFile
     * @return
     */
    @JvmStatic
    fun str2file(str: String, destFile: File): File {
        UtilKFile.createFile(destFile)
        RandomAccessFile(destFile, "rwd").use { str.writeStr2randomAccessFile(it) }
        return destFile
    }

    @JvmStatic
    fun str2file2(str: String, filePathWithName: String, isOverwrite: Boolean = true): File =
            str2file2(str, UtilKFile.createFile(filePathWithName), isOverwrite)

    @JvmStatic
    fun str2file2(str: String, file: File, isOverwrite: Boolean = true): File {
        UtilKFile.createFile(file)
        FileOutputStream(file, !isOverwrite).flushClose { str.writeStr2fileOutputStream(it) }
        return file
    }

    ////////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun strFilePath2str(filePathWithName: String): String =
            file2str(filePathWithName.strFilePath2file())

    @JvmStatic
    fun strFilePath2Bytes(filePathWithName: String): ByteArray? =
            file2Bytes(filePathWithName.strFilePath2file())

    @JvmStatic
    fun strFilePath2bytes2(filePathWithName: String): ByteArray? =
            file2bytes2(filePathWithName.strFilePath2file())

    @JvmStatic
    fun file2str(file: File): String =
            if (!UtilKFile.isFileExist(file)) ""
            else FileInputStream(file).use { it.inputStream2str() }

    @JvmStatic
    fun file2Bytes(file: File): ByteArray? =
            if (!UtilKFile.isFileExist(file)) null
            else FileInputStream(file).use { it.inputStream2bytes() }

    @JvmStatic
    fun file2bytes2(file: File): ByteArray? =
            if (!UtilKFile.isFileExist(file)) null
            else FileInputStream(file).use { it.inputStream2bytes2(file.length()) }
}