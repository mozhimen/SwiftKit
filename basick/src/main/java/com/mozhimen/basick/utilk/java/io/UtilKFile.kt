package com.mozhimen.basick.utilk.java.io

import com.bumptech.glide.util.Util
import com.mozhimen.basick.elemk.java.util.cons.CDateFormat
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CApplication
import com.mozhimen.basick.utilk.android.util.dt
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.util.UtilKDate
import com.mozhimen.basick.utilk.java.util.UtilKZipOutputStream
import com.mozhimen.basick.utilk.java.util.longDate2strDate
import java.io.File
import java.io.FileInputStream
import java.util.Locale

/**
 * @ClassName UtilKFile
 * @Description android:requestLegacyExternalStorage="true" application 设置
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/22 11:59
 * @Version 1.0
 */
fun String.isFileExist(): Boolean =
    UtilKFile.isFile(this)

fun File.isFileExist(): Boolean =
    UtilKFile.isFile(this)

fun String.createFile(): File =
    UtilKFile.createFile(this)

fun File.createFile(): File =
    UtilKFile.createFile(this)

fun String.deleteFile(): Boolean =
    UtilKFile.deleteFile(this)

fun File.deleteFile(): Boolean =
    UtilKFile.deleteFile(this)

fun String.getFileSizeAvailable(): Long? =
    UtilKFile.getFileSizeAvailable(this)

fun File.getFileSizeAvailable(): Long? =
    UtilKFile.getFileSizeAvailable(this)

fun String.getFileSizeTotal(): Long? =
    UtilKFile.getFileSizeTotal(this)

fun File.getFileSizeTotal(): Long? =
    UtilKFile.getFileSizeTotal(this)

fun File.getFileCreateTime(): Long =
    UtilKFile.getFileCreateTime(this)

fun File.getFileCreateTimeStr(): String =
    UtilKFile.getFileCreateTimeStr(this)

/////////////////////////////////////////////////////////////////////////////////////////////

fun String.getStrFolderPath(): String =
    UtilKFile.getStrFolderPath(this)

fun String.isFolderExist(): Boolean =
    UtilKFile.isFolderExist(this)

fun File.isFolderExist(): Boolean =
    UtilKFile.isFolderExist(this)

fun String.createFolder(): File =
    UtilKFile.createFolder(this)

fun File.createFolder(): File =
    UtilKFile.createFolder(this)

fun String.deleteFolder(): Boolean =
    UtilKFile.deleteFolder(this)

fun File.deleteFolder(): Boolean =
    UtilKFile.deleteFolder(this)

fun String.getFolderFiles(): Array<File> =
    UtilKFile.getFolderFiles(this)

fun File.getFolderFiles(): Array<File> =
    UtilKFile.getFolderFiles(this)

/////////////////////////////////////////////////////////////////////////////////////////////

@AManifestKRequire(CApplication.REQUEST_LEGACY_EXTERNAL_STORAGE)
object UtilKFile : BaseUtilK() {

    //region # file
    @JvmStatic
    fun getStrFileNameForStrToday(locale: Locale = Locale.CHINA): String =
        getStrFileNameForStrDate(CDateFormat.yyyy_MM_dd, locale)

    /**
     * 当前小时转文件名
     */
    @JvmStatic
    fun getStrFileNameForStrCurrentHour(locale: Locale = Locale.CHINA): String =
        getStrFileNameForStrDate(CDateFormat.yyyy_MM_dd_HH, locale)

    /**
     * 当前时间转文件名
     * @return 2023-08-19~11-46-00
     */
    @JvmStatic
    fun getStrFileNameForStrNowDate(locale: Locale = Locale.CHINA): String =
        getStrFileNameForStrDate(locale = locale)

    /**
     * 时间转文件名
     */
    @JvmStatic
    fun getStrFileNameForStrDate(formatDate: String = CDateFormat.yyyy_MM_dd_HH_mm_ss, locale: Locale = Locale.CHINA): String =
        UtilKDate.getNowStr(formatDate, locale).replace(" ", "~").replace(":", "-")

    /////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 判断是否为文件
     */
    @JvmStatic
    fun isFile(filePathWithName: String): Boolean =
        isFile(filePathWithName.strFilePath2file())

    /**
     * 判断是否为文件
     */
    @JvmStatic
    fun isFile(file: File): Boolean =
        file.exists() && file.isFile

    /**
     * 文件是否存在
     */
    @JvmStatic
    fun isFileExist(filePathWithName: String): Boolean =
        isFileExist(filePathWithName.strFilePath2file())

    /**
     * 文件是否存在
     */
    @JvmStatic
    fun isFileExist(file: File): Boolean =
        isFile(file)

    /**
     * 创建文件
     */
    @JvmStatic
    fun createFile(filePathWithName: String): File =
        createFile(filePathWithName.strFilePath2file())

    /**
     * 创建文件
     */
    @JvmStatic
    fun createFile(file: File): File {
        file.parent?.let { createFolder(it) } ?: throw Exception("don't have parent folder")

        if (!isFileExist(file)) {
            file.createNewFile()
            "createFile: file ${file.absolutePath}".dt(TAG)
        } else "createFile: file is exits".dt(TAG)
        return file
    }

    /**
     * 删除文件
     */
    @JvmStatic
    fun deleteFile(filePathWithName: String): Boolean =
        deleteFile(filePathWithName.strFilePath2file())

    /**
     * 删除文件
     */
    @JvmStatic
    fun deleteFile(file: File): Boolean =
        if (!isFileExist(file)) false
        else file.delete().also { "deleteFile: file ${file.absolutePath} success".dt(TAG) }

    /**
     * 批量删除
     */
    fun deleteFiles(vararg files: File) {
        for (file in files)
            deleteFile(file)
    }

    /**
     * 复制文件
     */
    @JvmStatic
    fun copyFile(sourceFilePathWithName: String, destFilePathWithName: String, isAppend: Boolean = false): File? =
        copyFile(sourceFilePathWithName.strFilePath2file(), destFilePathWithName.strFilePath2file(), isAppend)

    /**
     * 复制文件
     */
    @JvmStatic
    fun copyFile(sourceFile: File, destFile: File, isAppend: Boolean = false): File? =
        if (!isFileExist(sourceFile)) null
        else FileInputStream(sourceFile).inputStream2file(destFile, isAppend)

    /**
     * 压缩文件
     */
    @JvmStatic
    fun zipFile(sourceFilePathWithName: String, zipFilePathWithName: String): File? =
        zipFile(sourceFilePathWithName.strFilePath2file(), zipFilePathWithName.strFilePath2file())

    /**
     * 压缩文件
     */
    @JvmStatic
    fun zipFile(sourceFile: File, zipFile: File): File? =
        if (!isFileExist(sourceFile)) null
        else {
            val zipOutputStream = zipFile.file2fileOutputStream().outputStream2zipOutputStream()
            UtilKZipOutputStream.zipOutputStream2bufferedOutputStream(zipOutputStream, zipOutputStream.outputStream2bufferedOutputStream(), sourceFile, sourceFile.name)
            zipFile
        }

    @JvmStatic
    fun getNameWithoutExtension(filePathWithName: String): String? =
        if (filePathWithName.isEmpty()) null
        else getNameWithoutExtension(filePathWithName.strFilePath2file())

    @JvmStatic
    fun getNameWithoutExtension(file: File): String? =
        if (!isFileExist(file)) null
        else file.nameWithoutExtension

    /**
     * 获取文件大小
     */
    @JvmStatic
    fun getFileSizeAvailable(filePathWithName: String): Long? =
        if (filePathWithName.isEmpty()) null
        else getFileSizeAvailable(filePathWithName.strFilePath2file())

    /**
     * 获取文件大小
     */
    @JvmStatic
    fun getFileSizeAvailable(file: File): Long? =
        if (!isFileExist(file)) null
        else FileInputStream(file).getAvailableLong()

    /**
     * 获取文件大小
     */
    @JvmStatic
    fun getFileSizeTotal(filePathWithName: String): Long? =
        if (filePathWithName.isEmpty()) null
        else getFileSizeTotal(filePathWithName.strFilePath2file())

    /**
     * 获取文件大小
     * file.length() 方法返回文件的长度，单位是字节，表示整个文件的大小。而 inputStream.available() 方法返回的是当前输入流中可读取的字节数，它可能小于或等于文件的长度，具体取决于输入流的类型和状态。
     */
    @JvmStatic
    fun getFileSizeTotal(file: File): Long? =
        if (!isFileExist(file)) null
        else file.length()

    /**
     * 文件创建时间
     */
    @JvmStatic
    fun getFileCreateTime(file: File): Long =
        file.lastModified()

    /**
     * 文件创建时间
     */
    @JvmStatic
    fun getFileCreateTimeStr(file: File, formatDate: String = CDateFormat.yyyy_MM_dd_HH_mm_ss): String =
        getFileCreateTime(file).longDate2strDate(formatDate)
//endregion

/////////////////////////////////////////////////////////////////////////////////////////

    //region # folder
    @JvmStatic
    fun getStrFolderPath(folderPath: String): String =
        if (!folderPath.endsWith("/")) "$folderPath/" else folderPath

    /////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 判断是否是文件夹
     */
    @JvmStatic
    fun isFolder(folderPath: String): Boolean =
        isFolder(folderPath.getStrFolderPath().strFilePath2file())

    /**
     * 判断是否是文件夹
     */
    @JvmStatic
    fun isFolder(folder: File): Boolean =
        folder.exists() && folder.isDirectory

    /**
     * 文件夹是否存在
     */
    @JvmStatic
    fun isFolderExist(folderPath: String): Boolean =
        isFolderExist(folderPath.getStrFolderPath().strFilePath2file())

    /**
     * 文件夹是否存在
     */
    @JvmStatic
    fun isFolderExist(folder: File): Boolean =
        isFolder(folder)

    /**
     * 创建文件夹
     */
    @JvmStatic
    fun createFolder(folderPath: String): File =
        createFolder(folderPath.getStrFolderPath().strFilePath2file())

    /**
     * 创建文件夹
     */
    @JvmStatic
    fun createFolder(folder: File): File {
        if (!isFolderExist(folder)) folder.mkdirs()
        return folder
    }

    /**
     * 删除文件夹
     */
    @JvmStatic
    fun deleteFolder(folderPath: String): Boolean =
        deleteFolder(folderPath.getStrFolderPath().strFilePath2file())

    /**
     * 删除文件夹
     */
    @JvmStatic
    fun deleteFolder(folder: File): Boolean {
        if (!isFolderExist(folder)) return false
        val listFiles: Array<File> = getFolderFiles(folder)
        if (listFiles.isNotEmpty()) {
            for (file in listFiles) {
                if (isFolder(file)) { // 判断是否为文件夹
                    deleteFolder(file)
                    file.delete()
                } else
                    deleteFile(file)
            }
        }
        return true
    }

    @JvmStatic
    fun getFolderFiles(folderPath: String): Array<File> =
        getFolderFiles(File(folderPath))

    @JvmStatic
    fun getFolderFiles(folder: File): Array<File> =
        folder.listFiles() ?: emptyArray()
//endregion
}

