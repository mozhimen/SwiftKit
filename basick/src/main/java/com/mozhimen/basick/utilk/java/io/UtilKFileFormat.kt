package com.mozhimen.basick.utilk.java.io

import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.bases.IUtilK
import com.mozhimen.basick.utilk.java.util.UtilKZipOutputStream
import java.io.ByteArrayOutputStream
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

////////////////////////////////////////////////////////////////////////////////////////

fun String.strFilePath2fileInputStream(): FileInputStream =
    UtilKFileFormat.strFilePath2fileInputStream(this)

fun File.file2fileInputStream(): FileInputStream =
    UtilKFileFormat.file2fileInputStream(this)

fun String.strFilePath2fileOutputStream(isAppend: Boolean = false): FileOutputStream =
    UtilKFileFormat.strFilePath2fileOutputStream(this, isAppend)

fun File.file2fileOutputStream(isAppend: Boolean = false): FileOutputStream =
    UtilKFileFormat.file2fileOutputStream(this, isAppend)

////////////////////////////////////////////////////////////////////////////////////////

fun String.str2file(destFilePathWithName: String): File? =
    UtilKFileFormat.str2file(this, destFilePathWithName)

fun String.str2file(destFile: File): File? =
    UtilKFileFormat.str2file(this, destFile)

fun String.str2file2(filePathWithName: String, isAppend: Boolean = false): File? =
    UtilKFileFormat.str2file2(this, filePathWithName, isAppend)

fun String.str2file2(destFile: File, isAppend: Boolean = false): File? =
    UtilKFileFormat.str2file2(this, destFile, isAppend)

////////////////////////////////////////////////////////////////////////////////////////

fun String.strFilePath2str(): String? =
    UtilKFileFormat.strFilePath2str(this)

fun File.file2str(): String? =
    UtilKFileFormat.file2str(this)

////////////////////////////////////////////////////////////////////////////////////////

fun String.strFilePath2bytes(): ByteArray? =
    UtilKFileFormat.strFilePath2bytes(this)

fun File.file2bytes(): ByteArray? =
    UtilKFileFormat.file2bytes(this)

fun String.strFilePath2bytes2(): ByteArray? =
    UtilKFileFormat.strFilePath2bytes2(this)

fun File.file2bytes2(): ByteArray? =
    UtilKFileFormat.file2bytes2(this)

fun String.strFilePath2bytes3(): ByteArray? =
    UtilKFileFormat.strFilePath2bytes3(this)

fun File.file2bytes3(): ByteArray? =
    UtilKFileFormat.file2bytes3(this)

object UtilKFileFormat : IUtilK {
    @JvmStatic
    fun strFilePath2file(destFilePathWithName: String): File =
        File(destFilePathWithName)

    @JvmStatic
    fun file2strFilePath(file: File): String =
        file.absolutePath

    ////////////////////////////////////////////////////////////////////////////////////////

    fun strFilePath2fileInputStream(filePathWithName: String): FileInputStream =
        file2fileInputStream(filePathWithName.strFilePath2file())

    @JvmStatic
    fun file2fileInputStream(file: File): FileInputStream =
        FileInputStream(file)

    fun strFilePath2fileOutputStream(filePathWithName: String, isAppend: Boolean = false): FileOutputStream =
        file2fileOutputStream(filePathWithName.strFilePath2file(), isAppend)

    @JvmStatic
    fun file2fileOutputStream(file: File, isAppend: Boolean = false): FileOutputStream =
        FileOutputStream(file, isAppend)

    ////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 文本转文件
     */
    @JvmStatic
    fun str2file(str: String, destFilePathWithName: String): File? =
        str2file(str, UtilKFile.createFile(destFilePathWithName))

    /**
     * 文本转文件
     */
    @JvmStatic
    fun str2file(str: String, destFile: File): File? {
        UtilKFile.createFile(destFile)
        try {
            RandomAccessFile(destFile, "rwd").writeStr2randomAccessFile(str)
            return destFile
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        }
        return null
    }

    @JvmStatic
    fun str2file2(str: String, filePathWithName: String, isAppend: Boolean = false): File? =
        str2file2(str, UtilKFile.createFile(filePathWithName), isAppend)

    @JvmStatic
    fun str2file2(str: String, destFile: File, isAppend: Boolean = false): File? {
        UtilKFile.createFile(destFile)
        try {
            destFile.file2fileOutputStream(isAppend).writeStr2fileOutputStream(str)
            return destFile
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        }
        return null
    }

    ////////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun strFilePath2str(filePathWithName: String): String? =
        file2str(filePathWithName.strFilePath2file())

    @JvmStatic
    fun file2str(file: File): String? =
        if (!UtilKFile.isFileExist(file)) null
        else FileInputStream(file).inputStream2str()

    ////////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun strFilePath2bytes(filePathWithName: String): ByteArray? =
        file2bytes(filePathWithName.strFilePath2file())

    @JvmStatic
    fun file2bytes(file: File): ByteArray? =
        if (!UtilKFile.isFileExist(file)) null
        else FileInputStream(file).inputStream2bytes()

    @JvmStatic
    fun strFilePath2bytes2(filePathWithName: String): ByteArray? =
        file2bytes2(filePathWithName.strFilePath2file())

    @JvmStatic
    fun file2bytes2(file: File): ByteArray? =
        if (!UtilKFile.isFileExist(file)) null
        else FileInputStream(file).inputStream2bytes(file.length())

    @JvmStatic
    fun strFilePath2bytes3(filePathWithName: String): ByteArray? =
        file2bytes3(filePathWithName.strFilePath2file())

    @JvmStatic
    fun file2bytes3(file: File): ByteArray? =
        if (!UtilKFile.isFileExist(file)) null
        else {
            val byteArrayOutputStream = ByteArrayOutputStream(file.length().toInt())
            file.file2fileInputStream().inputStream2bufferedInputStream().inputStream2outputStream2(byteArrayOutputStream, 1024)
            byteArrayOutputStream.byteArrayOutputStream2bytes()
        }
}