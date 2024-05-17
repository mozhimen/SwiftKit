package com.mozhimen.basick.utilk.kotlin

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.mozhimen.basick.elemk.android.content.cons.CIntent
import com.mozhimen.basick.elemk.android.media.cons.CMediaFormat
import com.mozhimen.basick.elemk.java.util.cons.CDateFormat
import com.mozhimen.basick.lintk.annors.ADescription
import com.mozhimen.basick.utilk.commons.IUtilK
import com.mozhimen.basick.utilk.java.io.UtilKFileWrapper
import com.mozhimen.basick.utilk.java.io.createFile
import com.mozhimen.basick.utilk.java.io.createFolder
import com.mozhimen.basick.utilk.java.io.deleteFile
import com.mozhimen.basick.utilk.java.io.deleteFolder
import com.mozhimen.basick.utilk.java.io.file2bytes_use
import com.mozhimen.basick.utilk.java.io.file2bytes_use_ofReadWrite
import com.mozhimen.basick.utilk.java.io.file2fileInputStream
import com.mozhimen.basick.utilk.java.io.file2fileOutputStream
import com.mozhimen.basick.utilk.java.io.file2str_use
import com.mozhimen.basick.utilk.java.io.file2uri
import com.mozhimen.basick.utilk.java.io.getFileNameNoExtension
import com.mozhimen.basick.utilk.java.io.getFileSize_ofAvaioflable
import com.mozhimen.basick.utilk.java.io.getFileSize_ofTotal
import com.mozhimen.basick.utilk.java.io.getFolderFiles
import com.mozhimen.basick.utilk.java.io.getFolderFiles_ofAll
import com.mozhimen.basick.utilk.java.io.isFileExist
import com.mozhimen.basick.utilk.java.io.isFolder
import com.mozhimen.basick.utilk.java.io.isFolderExist
import com.mozhimen.basick.utilk.java.util.UtilKDateWrapper
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.Locale
import java.util.Vector

/**
 * @ClassName UtilKStrFilePath
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/10/18 23:35
 * @Version 1.0
 */

//region # file
fun String.getStrFileExtension(): String? =
    UtilKStrFile.getStrFileExtension(this)

fun String.getStrFileNameNoExtension(): String? =
    UtilKStrFile.getStrFileNameNoExtension(this)

fun String.getStrFileName(): String? =
    UtilKStrFile.getStrFileName(this)

fun String.getStrFileParentPath(): String? =
    UtilKStrFile.getStrFileParentPath(this)

/////////////////////////////////////////////////////////////////

fun String.getFileSizeTotal(): Long? =
    UtilKStrFile.getFileSizeTotal(this)

fun String.getFileSizeAvailable(): Long? =
    UtilKStrFile.getFileSizeAvailable(this)

/////////////////////////////////////////////////////////////////

fun String.isFileExist(): Boolean =
    UtilKStrFile.isFile(this)

/////////////////////////////////////////////////////////////////

fun String.strFileExtension2strMineTypeImage(): String =
    UtilKStrFile.strFileExtension2strMineTypeImage(this)

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

/////////////////////////////////////////////////////////////////

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

/////////////////////////////////////////////////////////////////

object UtilKStrFile : IUtilK {
    //region # file
    @JvmStatic
    fun extractExtension(fileName: String): String =
        fileName.substringAfterLast(".", "").toLowerCase(Locale.US)

    @JvmStatic
    fun discardExtension(fileName: String): String = fileName.substringBeforeLast(".")

    @JvmStatic
    fun getStrFileName_ofToday(locale: Locale = Locale.CHINA): String =
        getStrFileName(CDateFormat.yyyy_MM_dd, locale)

    //当前小时转文件名
    @JvmStatic
    fun getStrFileName_ofCurrentHour(locale: Locale = Locale.CHINA): String =
        getStrFileName(CDateFormat.yyyy_MM_dd_HH, locale)

    /**
     * 当前时间转文件名
     * @return 2023-08-19~11-46-00
     */
    @JvmStatic
    fun getStrFileName_ofNow(locale: Locale = Locale.CHINA): String =
        getStrFileName(locale = locale)

    //时间转文件名
    @JvmStatic
    fun getStrFileName(formatDate: String = CDateFormat.yyyy_MM_dd_HH_mm_ss, locale: Locale = Locale.CHINA): String =
        UtilKDateWrapper.getNowStr(formatDate, locale).replace(" ", "~").replace(":", "-")

    /////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getStrFileExtension(strFilePathName: String): String? =
        if (strFilePathName.isEmpty()) null
        else if (strFilePathName.containStr(".")) strFilePathName.getSplitLastIndexToEnd(".") else null

    @JvmStatic
    fun getStrFileNameNoExtension(strFilePathName: String): String? =
        if (strFilePathName.isEmpty()) null
        else strFilePathName.strFilePath2file().getFileNameNoExtension()

    @JvmStatic
    fun getStrFileName(strFilePathName: String): String? =
        if (strFilePathName.isEmpty()) null
        else strFilePathName.getSplitLastIndexToEnd("/")

    @JvmStatic
    fun getStrFileParentPath(strFilePathName: String): String? =
        if (strFilePathName.isEmpty()) null
        else strFilePathName.getSplitLastIndexToStart("/")

    //////////////////////////////////////////////////////////////////////

    /**
     * 获取文件大小
     */
    @JvmStatic
    fun getFileSizeTotal(strFilePathName: String): Long? =
        if (strFilePathName.isEmpty()) null
        else strFilePathName.strFilePath2file().getFileSize_ofTotal()

    /**
     * 获取文件大小
     */
    @JvmStatic
    fun getFileSizeAvailable(strFilePathName: String): Long? =
        if (strFilePathName.isEmpty()) null
        else strFilePathName.strFilePath2file().getFileSize_ofAvaioflable()

    //////////////////////////////////////////////////////////////////////

    /**
     * 判断是否为文件
     */
    @JvmStatic
    fun isFile(strFilePathName: String): Boolean =
        UtilKFileWrapper.isFile(strFilePathName.strFilePath2file())

    /**
     * 文件是否存在
     */
    @JvmStatic
    fun isFileExist(strFilePathName: String): Boolean =
        strFilePathName.strFilePath2file().isFileExist()

    /////////////////////////////////////////////////////////////////

    @JvmStatic
    fun strFileExtension2strMineTypeImage(strFileExtension: String): String =
        when (strFileExtension) {
            "jpeg", "jpg" -> CMediaFormat.MIMETYPE_IMAGE_JPEG
            "png" -> CMediaFormat.MIMETYPE_IMAGE_PNG
            else -> CMediaFormat.MIMETYPE_IMAGE_ALL
        }

    @JvmStatic
    fun strFilePath2str(strFilePathName: String): String? =
        strFilePathName.strFilePath2file().file2str_use()

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
        strFilePathName.strFilePath2file().file2bytes_use()

    @JvmStatic
    fun strFilePath2bytes2(strFilePathName: String): ByteArray? =
        strFilePathName.strFilePath2file().file2bytes_use_ofReadWrite()

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
        UtilKFileWrapper.copyFile(strFilePathNameSource.strFilePath2file(), strFilePathNameDest.strFilePath2file(), isAppend)

    /**
     * 压缩文件
     */
    @JvmStatic
    fun zipFile(strFilePathNameSource: String, zipFilePathWithName: String): File? =
        UtilKFileWrapper.zipFile(strFilePathNameSource.strFilePath2file(), zipFilePathWithName.strFilePath2file())

    /**
     * 删除文件
     */
    @JvmStatic
    fun deleteFile(strFilePathName: String): Boolean =
        strFilePathName.strFilePath2file().deleteFile()
    //endregion

    //region # folder
    @JvmStatic
    fun getStrFolderPath(strFolderPath: String): String =
        if (!strFolderPath.endsWith("/")) "$strFolderPath/" else strFolderPath

    @JvmStatic
    fun getFolderFiles(strFolderPath: String): Array<File> =
        strFolderPath.getStrFolderPath().strFilePath2file().getFolderFiles()

    @JvmStatic
    fun getFolderAllFiles(strFolderPath: String, fileType: String? = null): Vector<File> =
        strFolderPath.getStrFolderPath().strFilePath2file().getFolderFiles_ofAll(fileType)

    //////////////////////////////////////////////////////////////////////

    /**
     * 判断是否是文件夹
     */
    @JvmStatic
    fun isFolder(strFolderPath: String): Boolean =
        strFolderPath.getStrFolderPath().strFilePath2file().isFolder()

    /**
     * 文件夹是否存在
     */
    @JvmStatic
    fun isFolderExist(strFolderPath: String): Boolean =
        strFolderPath.getStrFolderPath().strFilePath2file().isFolderExist()

    /**
     * 创建文件夹
     */
    @JvmStatic
    fun createFolder(strFolderPath: String): File =
        strFolderPath.getStrFolderPath().strFilePath2file().createFolder()

    /**
     * 删除文件夹
     */
    @JvmStatic
    fun deleteFolder(strFolderPath: String): Boolean =
        strFolderPath.getStrFolderPath().strFilePath2file().deleteFolder()
    //endregion
}