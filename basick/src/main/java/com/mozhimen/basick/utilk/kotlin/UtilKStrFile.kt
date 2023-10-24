package com.mozhimen.basick.utilk.kotlin

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.mozhimen.basick.elemk.android.content.cons.CIntent
import com.mozhimen.basick.lintk.annors.ADescription
import com.mozhimen.basick.utilk.java.io.UtilKFile
import com.mozhimen.basick.utilk.java.io.UtilKFileFormat
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.Vector

/**
 * @ClassName UtilKStrFilePath
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/10/18 23:35
 * @Version 1.0
 */

//region # file
fun String.getFileNameExExtension(): String? =
    UtilKStrFile.getFileNameExExtension(this)

fun String.getFileSizeTotal(): Long? =
    UtilKStrFile.getFileSizeTotal(this)

fun String.getFileSizeAvailable(): Long? =
    UtilKStrFile.getFileSizeAvailable(this)

/////////////////////////////////////////////////////////////////

fun String.isFileExist(): Boolean =
    UtilKStrFile.isFile(this)

/////////////////////////////////////////////////////////////////

fun String.strFilePath2str(): String? =
    UtilKStrFile.strFilePath2str(this)

fun String.strFilePath2fileOutputStream(isAppend: Boolean = false): FileOutputStream =
    UtilKStrFile.strFilePath2fileOutputStream(this, isAppend)

fun String.strFilePath2fileInputStream(): FileInputStream =
    UtilKStrFile.strFilePath2fileInputStream(this)

fun String.strFilePath2file(): File =
    UtilKStrFile.strFilePath2file(this)

/////////////////////////////////////////////////////////////////

fun String.strFilePath2bytes(): ByteArray? =
    UtilKStrFile.strFilePath2bytes(this)

fun String.strFilePath2bytes2(): ByteArray? =
    UtilKStrFile.strFilePath2bytes2(this)

fun String.strFilePath2bytes3(): ByteArray? =
    UtilKStrFile.strFilePath2bytes3(this)

fun String.strFilePath2uri(): Uri? =
    UtilKStrFile.strFilePath2uri(this)

fun String.strFilePath2anyBitmap(): Bitmap? =
    UtilKStrFile.strFilePath2anyBitmap(this)

fun String.strFilePath2anyBitmap(opts: BitmapFactory.Options): Bitmap? =
    UtilKStrFile.strFilePath2anyBitmap(this, opts)

/////////////////////////////////////////////////////////////////

fun String.createFile(): File =
    UtilKStrFile.createFile(this)

fun String.copyFile(destFilePathWithName: String, isAppend: Boolean = false): File? =
    UtilKStrFile.copyFile(this, destFilePathWithName, isAppend)

fun String.zipFile(zipFilePathWithName: String): File? =
    UtilKStrFile.zipFile(this, zipFilePathWithName)

fun String.deleteFile(): Boolean =
    UtilKStrFile.deleteFile(this)
//endregion

//region # folder
fun String.getStrFolderPath(): String =
    UtilKStrFile.getStrFolderPath(this)

fun String.getFolderFiles(): Array<File> =
    UtilKStrFile.getFolderFiles(this)

fun String.getFolderAllFiles(fileType: String? = null): Vector<File> =
    UtilKStrFile.getFolderAllFiles(this, fileType)

fun String.isFolder(): Boolean =
    UtilKStrFile.isFolder(this)

fun String.isFolderExist(): Boolean =
    UtilKStrFile.isFolderExist(this)

fun String.createFolder(): File =
    UtilKStrFile.createFolder(this)

fun String.deleteFolder(): Boolean =
    UtilKStrFile.deleteFolder(this)
//endregion

object UtilKStrFile {
    //region # file
    @JvmStatic
    fun getFileNameExExtension(filePathWithName: String): String? =
        if (filePathWithName.isEmpty()) null
        else UtilKFile.getNameExExtension(filePathWithName.strFilePath2file())

    /**
     * 获取文件大小
     */
    @JvmStatic
    fun getFileSizeTotal(filePathWithName: String): Long? =
        if (filePathWithName.isEmpty()) null
        else UtilKFile.getFileSizeTotal(filePathWithName.strFilePath2file())

    /**
     * 获取文件大小
     */
    @JvmStatic
    fun getFileSizeAvailable(filePathWithName: String): Long? =
        if (filePathWithName.isEmpty()) null
        else UtilKFile.getFileSizeAvailable(filePathWithName.strFilePath2file())

    //////////////////////////////////////////////////////////////////////

    /**
     * 判断是否为文件
     */
    @JvmStatic
    fun isFile(filePathWithName: String): Boolean =
        UtilKFile.isFile(filePathWithName.strFilePath2file())

    /**
     * 文件是否存在
     */
    @JvmStatic
    fun isFileExist(filePathWithName: String): Boolean =
        UtilKFile.isFileExist(filePathWithName.strFilePath2file())

    /////////////////////////////////////////////////////////////////

    @JvmStatic
    fun strFilePath2str(filePathWithName: String): String? =
        UtilKFileFormat.file2str(filePathWithName.strFilePath2file())

    @JvmStatic
    fun strFilePath2fileOutputStream(filePathWithName: String, isAppend: Boolean = false): FileOutputStream =
        UtilKFileFormat.file2fileOutputStream(filePathWithName.strFilePath2file(), isAppend)

    @JvmStatic
    fun strFilePath2fileInputStream(filePathWithName: String): FileInputStream =
        UtilKFileFormat.file2fileInputStream(filePathWithName.strFilePath2file())

    @JvmStatic
    fun strFilePath2file(destFilePathWithName: String): File =
        File(destFilePathWithName)

    ////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun strFilePath2bytes(filePathWithName: String): ByteArray? =
        UtilKFileFormat.file2bytes(filePathWithName.strFilePath2file())

    @JvmStatic
    fun strFilePath2bytes2(filePathWithName: String): ByteArray? =
        UtilKFileFormat.file2bytes2(filePathWithName.strFilePath2file())

    @JvmStatic
    fun strFilePath2bytes3(filePathWithName: String): ByteArray? =
        UtilKFileFormat.file2bytes3(filePathWithName.strFilePath2file())

    /**
     * 文件转Uri
     */
    @JvmStatic
    @ADescription(CIntent.FLAG_GRANT_READ_URI_PERMISSION.toString(), CIntent.FLAG_GRANT_WRITE_URI_PERMISSION.toString())
    fun strFilePath2uri(filePathWithName: String): Uri? =
        UtilKFileFormat.file2uri(File(filePathWithName))

    @JvmStatic
    fun strFilePath2anyBitmap(filePathWithName: String): Bitmap? =
        if (filePathWithName.isEmpty() || filePathWithName.hasSpace()) null
        else BitmapFactory.decodeFile(filePathWithName)

    @JvmStatic
    fun strFilePath2anyBitmap(filePathWithName: String, opts: BitmapFactory.Options): Bitmap? =
        if (filePathWithName.isEmpty() || filePathWithName.hasSpace()) null
        else BitmapFactory.decodeFile(filePathWithName, opts)

    //////////////////////////////////////////////////////////////////////

    /**
     * 创建文件
     */
    @JvmStatic
    fun createFile(filePathWithName: String): File =
        UtilKFile.createFile(filePathWithName.strFilePath2file())

    /**
     * 复制文件
     */
    @JvmStatic
    fun copyFile(sourceFilePathWithName: String, destFilePathWithName: String, isAppend: Boolean = false): File? =
        UtilKFile.copyFile(sourceFilePathWithName.strFilePath2file(), destFilePathWithName.strFilePath2file(), isAppend)

    /**
     * 压缩文件
     */
    @JvmStatic
    fun zipFile(sourceFilePathWithName: String, zipFilePathWithName: String): File? =
        UtilKFile.zipFile(sourceFilePathWithName.strFilePath2file(), zipFilePathWithName.strFilePath2file())

    /**
     * 删除文件
     */
    @JvmStatic
    fun deleteFile(filePathWithName: String): Boolean =
        UtilKFile.deleteFile(filePathWithName.strFilePath2file())
    //endregion

    //region # folder
    @JvmStatic
    fun getStrFolderPath(folderPath: String): String =
        if (!folderPath.endsWith("/")) "$folderPath/" else folderPath

    @JvmStatic
    fun getFolderFiles(folderPath: String): Array<File> =
        UtilKFile.getFolderFiles(folderPath.getStrFolderPath().strFilePath2file())

    @JvmStatic
    fun getFolderAllFiles(folderPath: String, fileType: String? = null): Vector<File> =
        UtilKFile.getFolderAllFiles(folderPath.getStrFolderPath().strFilePath2file(), fileType)

    //////////////////////////////////////////////////////////////////////

    /**
     * 判断是否是文件夹
     */
    @JvmStatic
    fun isFolder(folderPath: String): Boolean =
        UtilKFile.isFolder(folderPath.getStrFolderPath().strFilePath2file())

    /**
     * 文件夹是否存在
     */
    @JvmStatic
    fun isFolderExist(folderPath: String): Boolean =
        UtilKFile.isFolderExist(folderPath.getStrFolderPath().strFilePath2file())

    /**
     * 创建文件夹
     */
    @JvmStatic
    fun createFolder(folderPath: String): File =
        UtilKFile.createFolder(folderPath.getStrFolderPath().strFilePath2file())

    /**
     * 删除文件夹
     */
    @JvmStatic
    fun deleteFolder(folderPath: String): Boolean =
        UtilKFile.deleteFolder(folderPath.getStrFolderPath().strFilePath2file())
    //endregion
}