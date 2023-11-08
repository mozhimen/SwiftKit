package com.mozhimen.basick.utilk.kotlin

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.mozhimen.basick.elemk.android.content.cons.CIntent
import com.mozhimen.basick.lintk.annors.ADescription
import com.mozhimen.basick.utilk.bases.IUtilK
import com.mozhimen.basick.utilk.java.io.UtilKFile
import com.mozhimen.basick.utilk.java.io.UtilKFileFormat
import com.mozhimen.basick.utilk.java.io.createFile
import com.mozhimen.basick.utilk.java.io.createFolder
import com.mozhimen.basick.utilk.java.io.deleteFile
import com.mozhimen.basick.utilk.java.io.deleteFolder
import com.mozhimen.basick.utilk.java.io.file2bytes
import com.mozhimen.basick.utilk.java.io.file2bytes2
import com.mozhimen.basick.utilk.java.io.file2bytesCheck
import com.mozhimen.basick.utilk.java.io.file2fileInputStream
import com.mozhimen.basick.utilk.java.io.file2fileOutputStream
import com.mozhimen.basick.utilk.java.io.file2str
import com.mozhimen.basick.utilk.java.io.file2uri
import com.mozhimen.basick.utilk.java.io.getFileSizeAvailable
import com.mozhimen.basick.utilk.java.io.getFileSizeTotal
import com.mozhimen.basick.utilk.java.io.getFolderAllFiles
import com.mozhimen.basick.utilk.java.io.getFolderFiles
import com.mozhimen.basick.utilk.java.io.getNameExpExtension
import com.mozhimen.basick.utilk.java.io.isFileExist
import com.mozhimen.basick.utilk.java.io.isFolder
import com.mozhimen.basick.utilk.java.io.isFolderExist
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
fun String.getFileNameExtension(): String? =
    UtilKStrFile.getFileNameExtension(this)

fun String.getFileNameExpExtension(): String? =
    UtilKStrFile.getFileNameExpExtension(this)

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

fun String.strFilePath2fileInputStream2(): FileInputStream =
    UtilKStrFile.strFilePath2fileInputStream2(this)

fun String.strFilePath2file(): File =
    UtilKStrFile.strFilePath2file(this)

/////////////////////////////////////////////////////////////////

fun String.strFilePath2bytes(): ByteArray? =
    UtilKStrFile.strFilePath2bytes(this)

fun String.strFilePath2bytesCheck(): ByteArray? =
    UtilKStrFile.strFilePath2bytesCheck(this)

fun String.strFilePath2bytes2(): ByteArray? =
    UtilKStrFile.strFilePath2bytes2(this)

fun String.strFilePath2uri(): Uri? =
    UtilKStrFile.strFilePath2uri(this)

fun String.strFilePath2bitmapAny(): Bitmap? =
    UtilKStrFile.strFilePath2bitmapAny(this)

fun String.strFilePath2bitmapAny(opts: BitmapFactory.Options): Bitmap? =
    UtilKStrFile.strFilePath2bitmapAny(this, opts)

/////////////////////////////////////////////////////////////////

fun String.createFile(): File =
    UtilKStrFile.createFile(this)

fun String.copyFile(strFilePathNameDest: String, isAppend: Boolean = false): File? =
    UtilKStrFile.copyFile(this, strFilePathNameDest, isAppend)

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

object UtilKStrFile : IUtilK {
    //region # file
    @JvmStatic
    fun getFileNameExtension(strFilePathName: String): String? =
        if (strFilePathName.isEmpty()) null
        else if (strFilePathName.containStr(".")) strFilePathName.getSplitLast(".") else null

    @JvmStatic
    fun getFileNameExpExtension(strFilePathName: String): String? =
        if (strFilePathName.isEmpty()) null
        else strFilePathName.strFilePath2file().getNameExpExtension()

    /**
     * 获取文件大小
     */
    @JvmStatic
    fun getFileSizeTotal(strFilePathName: String): Long? =
        if (strFilePathName.isEmpty()) null
        else strFilePathName.strFilePath2file().getFileSizeTotal()

    /**
     * 获取文件大小
     */
    @JvmStatic
    fun getFileSizeAvailable(strFilePathName: String): Long? =
        if (strFilePathName.isEmpty()) null
        else strFilePathName.strFilePath2file().getFileSizeAvailable()

    //////////////////////////////////////////////////////////////////////

    /**
     * 判断是否为文件
     */
    @JvmStatic
    fun isFile(strFilePathName: String): Boolean =
        UtilKFile.isFile(strFilePathName.strFilePath2file())

    /**
     * 文件是否存在
     */
    @JvmStatic
    fun isFileExist(strFilePathName: String): Boolean =
        strFilePathName.strFilePath2file().isFileExist()

    /////////////////////////////////////////////////////////////////

    @JvmStatic
    fun strFilePath2str(strFilePathName: String): String? =
        strFilePathName.strFilePath2file().file2str()

    @JvmStatic
    fun strFilePath2fileOutputStream(strFilePathName: String, isAppend: Boolean = false): FileOutputStream =
        strFilePathName.strFilePath2file().file2fileOutputStream(isAppend)

    @JvmStatic
    fun strFilePath2fileInputStream(strFilePathName: String): FileInputStream =
        FileInputStream(strFilePathName)

    @JvmStatic
    fun strFilePath2fileInputStream2(strFilePathName: String): FileInputStream =
        strFilePathName.strFilePath2file().file2fileInputStream()

    @JvmStatic
    fun strFilePath2file(strFilePathNameDest: String): File =
        File(strFilePathNameDest)

    ////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun strFilePath2bytes(strFilePathName: String): ByteArray? =
        strFilePathName.strFilePath2file().file2bytes()

    @JvmStatic
    fun strFilePath2bytesCheck(strFilePathName: String): ByteArray? =
        strFilePathName.strFilePath2file().file2bytesCheck()

    @JvmStatic
    fun strFilePath2bytes2(strFilePathName: String): ByteArray? =
        strFilePathName.strFilePath2file().file2bytes2()

    /**
     * 文件转Uri
     */
    @JvmStatic
    @ADescription(CIntent.FLAG_GRANT_READ_URI_PERMISSION.toString(), CIntent.FLAG_GRANT_WRITE_URI_PERMISSION.toString())
    fun strFilePath2uri(strFilePathName: String): Uri? =
        strFilePathName.strFilePath2file().file2uri()

    @JvmStatic
    fun strFilePath2bitmapAny(strFilePathName: String): Bitmap? =
        if (strFilePathName.isEmpty() || strFilePathName.hasSpace()) null
        else BitmapFactory.decodeFile(strFilePathName)

    @JvmStatic
    fun strFilePath2bitmapAny(strFilePathName: String, opts: BitmapFactory.Options): Bitmap? =
        if (strFilePathName.isEmpty() || strFilePathName.hasSpace()) null
        else BitmapFactory.decodeFile(strFilePathName, opts)

    //////////////////////////////////////////////////////////////////////

    /**
     * 创建文件
     */
    @JvmStatic
    fun createFile(strFilePathName: String): File =
        strFilePathName.strFilePath2file().createFile()

    /**
     * 复制文件
     */
    @JvmStatic
    fun copyFile(strFilePathNameSource: String, strFilePathNameDest: String, isAppend: Boolean = false): File? =
        UtilKFile.copyFile(strFilePathNameSource.strFilePath2file(), strFilePathNameDest.strFilePath2file(), isAppend)

    /**
     * 压缩文件
     */
    @JvmStatic
    fun zipFile(strFilePathNameSource: String, zipFilePathWithName: String): File? =
        UtilKFile.zipFile(strFilePathNameSource.strFilePath2file(), zipFilePathWithName.strFilePath2file())

    /**
     * 删除文件
     */
    @JvmStatic
    fun deleteFile(strFilePathName: String): Boolean =
        strFilePathName.strFilePath2file().deleteFile()
    //endregion

    //region # folder
    @JvmStatic
    fun getStrFolderPath(folderPath: String): String =
        if (!folderPath.endsWith("/")) "$folderPath/" else folderPath

    @JvmStatic
    fun getFolderFiles(folderPath: String): Array<File> =
        folderPath.getStrFolderPath().strFilePath2file().getFolderFiles()

    @JvmStatic
    fun getFolderAllFiles(folderPath: String, fileType: String? = null): Vector<File> =
        folderPath.getStrFolderPath().strFilePath2file().getFolderAllFiles(fileType)

    //////////////////////////////////////////////////////////////////////

    /**
     * 判断是否是文件夹
     */
    @JvmStatic
    fun isFolder(folderPath: String): Boolean =
        folderPath.getStrFolderPath().strFilePath2file().isFolder()

    /**
     * 文件夹是否存在
     */
    @JvmStatic
    fun isFolderExist(folderPath: String): Boolean =
        folderPath.getStrFolderPath().strFilePath2file().isFolderExist()

    /**
     * 创建文件夹
     */
    @JvmStatic
    fun createFolder(folderPath: String): File =
        folderPath.getStrFolderPath().strFilePath2file().createFolder()

    /**
     * 删除文件夹
     */
    @JvmStatic
    fun deleteFolder(folderPath: String): Boolean =
        folderPath.getStrFolderPath().strFilePath2file().deleteFolder()
    //endregion
}