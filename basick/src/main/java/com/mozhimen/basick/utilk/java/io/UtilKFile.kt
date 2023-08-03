package com.mozhimen.basick.utilk.java.io

import android.util.Log
import com.mozhimen.basick.elemk.java.util.cons.CDateFormat
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CApplication
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.util.UtilKDate
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.RandomAccessFile
import java.util.Locale

/**
 * @ClassName UtilKFile
 * @Description android:requestLegacyExternalStorage="true" application 设置
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/22 11:59
 * @Version 1.0
 */
@AManifestKRequire(CApplication.REQUEST_LEGACY_EXTERNAL_STORAGE)
object UtilKFile : BaseUtilK() {

    /**
     * 当前小时转文件名
     * @param locale Locale
     * @return String
     */
    @JvmStatic
    fun getStrFileNameForStrCurrentHour(locale: Locale = Locale.CHINA): String =
            getStrFileNameForStrDate(CDateFormat.yyyyMMddHH, locale)

    /**
     * 当前时间转文件名
     * @param locale Locale
     * @return String
     */
    @JvmStatic
    fun getStrFileNameForStrNowDate(locale: Locale = Locale.CHINA): String =
            getStrFileNameForStrDate(locale = locale)

    /**
     * 时间转文件名
     * @param formatDate String
     * @param locale Locale
     * @return String
     */
    @JvmStatic
    fun getStrFileNameForStrDate(formatDate: String = CDateFormat.yyyyMMddHHmmss, locale: Locale = Locale.CHINA): String {
        return UtilKDate.getNowStr(formatDate, locale).replace(" ", "~").replace(":", "-")
    }

    //region # file
    /**
     * 判断是否为文件
     * @param file File
     * @return Boolean
     */
    @JvmStatic
    fun isFile(file: File): Boolean =
            file.exists() && file.isFile

    /**
     * 判断是否为文件
     * @param filePathWithName String
     * @return Boolean
     */
    @JvmStatic
    fun isFile(filePathWithName: String): Boolean =
            isFile(File(filePathWithName))

    /**
     * 文件是否存在
     * @param file File
     * @return Boolean
     */
    @JvmStatic
    fun isFileExist(file: File): Boolean =
            isFile(file)

    /**
     * 文件是否存在
     * @param filePathWithName String
     * @return Boolean
     */
    @JvmStatic
    fun isFileExist(filePathWithName: String) =
            isFileExist(File(filePathWithName))

    /**
     * 创建文件
     * @param file File
     * @return File
     * @throws Exception
     */
    @JvmStatic
    fun createFile(file: File): File {
        file.parent?.let { createFolder(it) } ?: throw Exception("don't have parent folder")
        Log.d(TAG, "createFile: file ${file.absolutePath}")
        if (!isFileExist(file)) {
            file.createNewFile()
        }
        return file
    }

    /**
     * 创建文件
     * @param filePathWithName String
     * @return File
     * @throws Exception
     */
    @JvmStatic
    fun createFile(filePathWithName: String): File =
            createFile(File(filePathWithName))

    /**
     * 批量删除
     * @param files Array<out File>
     */
    fun deleteFiles(vararg files: File) {
        for (file in files) {
            deleteFile(file)
        }
    }

    /**
     * 删除文件
     * @param file File
     */
    @JvmStatic
    fun deleteFile(file: File): Boolean {
        return if (isFileExist(file)) {
            file.delete().also {
                Log.d(TAG, "deleteFile: file ${file.absolutePath} success")
            }
        } else false
    }

    /**
     * 删除文件
     * @param filePathWithName String
     * @throws Exception
     */
    @JvmStatic
    fun deleteFile(filePathWithName: String): Boolean =
            deleteFile(File(filePathWithName))

    /**
     * 复制文件
     * @param sourceFilePathWithName String
     * @param destFilePathWithName String
     * @throws Exception
     */
    @JvmStatic
    fun copyFile(sourceFilePathWithName: String, destFilePathWithName: String): File? =
            copyFile(File(sourceFilePathWithName), File(destFilePathWithName))

    /**
     * 复制文件
     * @param sourceFile File
     * @param destFile File
     */
    @JvmStatic
    fun copyFile(sourceFile: File, destFile: File, isOverwrite: Boolean = true): File? =
        if (!isFileExist(sourceFile)) null
        else FileInputStream(sourceFile).use { it.inputStream2file(destFile, isOverwrite) }

    /**
     * 获取文件大小
     * @param filePathWithName String
     * @return Long
     */
    @JvmStatic
    fun getFileSize(filePathWithName: String): Long {
        if (filePathWithName.isEmpty()) return 0L
        return getFileSize(File(filePathWithName))
    }

    /**
     * 获取文件大小
     * @param file File
     * @return Long
     */
    @JvmStatic
    fun getFileSize(file: File): Long {
        if (!isFileExist(file)) return 0L
        val size = 0L
        val fileInputStream = FileInputStream(file)
        try {
            return fileInputStream.available().toLong()
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        } finally {
            fileInputStream.close()
        }
        return size
    }

    /**
     * 获取文件大小
     * @param filePathWithName String
     * @return Long
     */
    @JvmStatic
    fun getFileSize2(filePathWithName: String): Long {
        if (filePathWithName.isEmpty()) return 0L
        return getFileSize2(File(filePathWithName))
    }

    /**
     * 获取文件大小
     * @param file File
     * @return Long
     */
    @JvmStatic
    fun getFileSize2(file: File): Long {
        if (!isFileExist(file)) return 0L
        return file.length()
    }
//endregion

/////////////////////////////////////////////////////////////////////////////////////////

//region # folder
    /**
     * 判断是否是文件夹
     * @param folder File
     * @return Boolean
     */
    @JvmStatic
    fun isFolder(folder: File): Boolean =
            folder.exists() && folder.isDirectory

    /**
     * 判断是否是文件夹
     * @param folderPath String
     * @return Boolean
     */
    @JvmStatic
    fun isFolder(folderPath: String): Boolean =
            isFolder(File(folderPath))

    /**
     * 文件夹是否存在
     * @param folderPath String
     * @return Boolean
     */
    @JvmStatic
    fun isFolderExist(folderPath: String) =
            isFolderExist(File(genFolderPath(folderPath)))

    /**
     * 文件夹是否存在
     * @param folder File
     * @return Boolean
     */
    @JvmStatic
    fun isFolderExist(folder: File): Boolean =
            isFolder(folder)

    /**
     * 创建文件夹
     * @param folderPath String
     * @return File
     */
    @JvmStatic
    fun createFolder(folderPath: String) =
            createFolder(File(genFolderPath(folderPath)))

    /**
     * 创建文件夹
     * @param folder File
     * @return File
     */
    @JvmStatic
    fun createFolder(folder: File): File {
        if (!isFolderExist(folder)) {
            folder.mkdirs()
        }
        return folder
    }

    /**
     * 删除文件夹
     * @param folderPath String
     * @return Boolean
     * @throws Exception
     */
    @JvmStatic
    fun deleteFolder(folderPath: String): Boolean =
            deleteFolder(File(genFolderPath(folderPath)))

    /**
     * 删除文件夹
     * @param folder File
     * @return Boolean
     * @throws Exception
     */
    @JvmStatic
    fun deleteFolder(folder: File): Boolean {
        if (!isFolderExist(folder)) return false
        val listFiles: Array<File> = folder.listFiles() ?: emptyArray()
        if (listFiles.isNotEmpty()) {
            for (file in listFiles) {
                if (isFolder(file)) { // 判断是否为文件夹
                    deleteFolder(file)
                    file.delete()
                } else {
                    deleteFile(file)
                }
            }
        }
        return true
    }

/////////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun genFolderPath(folderPath: String): String {
        var tmpFolderPath = folderPath
        if (!tmpFolderPath.endsWith("/")) tmpFolderPath += "/"
        return tmpFolderPath
    }
//endregion
}

