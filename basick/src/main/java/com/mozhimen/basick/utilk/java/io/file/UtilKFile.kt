package com.mozhimen.basick.utilk.java.io.file

import android.os.FileUtils
import android.text.TextUtils
import android.util.Log
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.utilk.os.UtilKDate
import com.mozhimen.basick.utilk.log.et
import com.mozhimen.basick.utilk.java.io.hash.UtilKMD5
import java.io.*
import java.math.BigInteger
import java.security.MessageDigest
import java.util.Locale

/**
 * @ClassName UtilKFile
 * @Description android:requestLegacyExternalStorage="true" application 设置
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/22 11:59
 * @Version 1.0
 */
object UtilKFile {

    private const val TAG = "UtilKFile>>>>>"
    const val MSG_NOT_EXIST = "fail, make sure it's file or exist"
    const val MSG_WRONG = "something wrong"

    //region # file
    /**
     * 当前小时转文件名
     * @param locale Locale
     * @return String
     */
    @JvmStatic
    fun currentHourStr2FileName(locale: Locale = Locale.CHINA) =
        dateStr2FileName(UtilKDate.Format.yyyyMMddHH, locale)

    /**
     * 当前时间转文件名
     * @param locale Locale
     * @return String
     */
    @JvmStatic
    fun nowStr2FileName(locale: Locale = Locale.CHINA): String =
        dateStr2FileName(locale = locale)

    /**
     * 时间转文件名
     * @param formatDate String
     * @param locale Locale
     * @return String
     */
    @JvmStatic
    fun dateStr2FileName(formatDate: String = UtilKDate.Format.yyyyMMddHHmmss, locale: Locale = Locale.CHINA): String {
        return UtilKDate.getNowStr(formatDate, locale).replace(" ", "~").replace(":", "-")
    }

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
     * 文本转文件
     * @param content String
     * @param filePathWithName String
     * @throws Exception
     */
    @JvmStatic
    fun str2File(content: String, filePathWithName: String): String {
        val tmpContent = content + "\n"
        val tmpFile = createFile(filePathWithName)
        val randomAccessFile = RandomAccessFile(tmpFile, "rwd")
        try {
            randomAccessFile.write(tmpContent.toByteArray())
            return tmpFile.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        } finally {
            randomAccessFile.close()
        }
        return MSG_WRONG
    }

    /**
     * 文本转文件
     * @param content String
     * @param filePathWithName String
     * @throws Exception
     */
    @JvmStatic
    fun str2File2(content: String, filePathWithName: String): String {
        val tmpContent = content + "\n"
        val tmpFile = createFile(filePathWithName)
        val fileOutputStream = FileOutputStream(tmpFile)
        try {
            fileOutputStream.write(tmpContent.toByteArray())
            return tmpFile.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        } finally {
            fileOutputStream.flush()
            fileOutputStream.close()
        }
        return MSG_WRONG
    }

    /**
     * 文件转文本
     * @param filePathWithName String
     * @return String
     */
    @JvmStatic
    fun file2Str(filePathWithName: String): String =
        file2Str(File(filePathWithName))

    /**
     * 文件转文本
     * @param file File
     * @return String
     */
    @JvmStatic
    fun file2Str(file: File): String {
        if (!isFileExist(file)) return MSG_NOT_EXIST
        val fileInputStream = FileInputStream(file)
        try {
            return inputStream2Str(fileInputStream).replace("\\n".toRegex(), "\n")
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        } finally {
            fileInputStream.close()
        }
        return MSG_WRONG
    }

    /**
     * 流转字符串
     * @param inputStream InputStream
     * @return String
     */
    @JvmStatic
    fun inputStream2Str(inputStream: InputStream): String {
        val stringBuilder = StringBuilder()
        val inputStreamReader = InputStreamReader(inputStream, "UTF-8")
        val bufferedReader = BufferedReader(inputStreamReader)
        try {
            var lineString = ""
            while (bufferedReader.readLine()?.also { lineString = it } != null) {
                stringBuilder.append(lineString).append("\n")
            }
            return stringBuilder.deleteCharAt(stringBuilder.length - 1).toString().replace("\\n".toRegex(), "\n")
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        } finally {
            bufferedReader.close()
            inputStreamReader.close()
        }
        return MSG_WRONG
    }

    /**
     * 流转文件
     * @param inputStream InputStream
     * @param destFilePathWithName String
     * @param isOverwrite Boolean
     * @return String
     */
    @JvmStatic
    fun inputStream2File(inputStream: InputStream, destFilePathWithName: String, isOverwrite: Boolean = true): File? =
        inputStream2File(inputStream, File(destFilePathWithName), isOverwrite)

    /**
     * 输入流转文件
     * @param inputStream InputStream
     * @return String
     */
    @JvmStatic
    fun inputStream2File(inputStream: InputStream, destFile: File, isOverwrite: Boolean = true): File? {
        var fileInputStream: FileInputStream? = null
        if (!isFileExist(destFile)) {
            createFile(destFile)
        } else {
            fileInputStream = FileInputStream(destFile)
            if (isFilesSame(inputStream, fileInputStream)) {//相似内容就直接返回地址
                Log.d(TAG, "assetCopyFile: the two files is same")
                return null//"the two files is same, don't need overwrite"
            }
        }
        val fileOutputStream = FileOutputStream(destFile, !isOverwrite)
        try {
            var bufferLength: Int
            val buffer = ByteArray(1024)
            while (inputStream.read(buffer).also { bufferLength = it } != -1) {
                fileOutputStream.write(buffer, 0, bufferLength)
            }
            return destFile
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        } finally {
            fileOutputStream.flush()
            fileOutputStream.close()
            fileInputStream?.close()
            inputStream.close()
        }
        return null
    }

    /**
     * 输入流转文件
     * @param inputStream InputStream
     * @param destFilePathWithName String
     * @param isOverwrite Boolean
     * @return File?
     */
    @RequiresApi(CVersionCode.V_29_10_Q)
    @JvmStatic
    fun inputStream2File2(inputStream: InputStream, destFilePathWithName: String, isOverwrite: Boolean = true): File? =
        inputStream2File2(inputStream, File(destFilePathWithName), isOverwrite)

    /**
     * 输入流转文件
     * @param inputStream InputStream
     * @param destFile File
     * @param isOverwrite Boolean
     * @return File?
     */
    @RequiresApi(CVersionCode.V_29_10_Q)
    @JvmStatic
    fun inputStream2File2(inputStream: InputStream, destFile: File, isOverwrite: Boolean = true): File? {
        var fileInputStream: FileInputStream? = null
        if (!isFileExist(destFile)) {
            createFile(destFile)
        } else {
            fileInputStream = FileInputStream(destFile)
            if (isFilesSame(inputStream, fileInputStream)) {//相似内容就直接返回地址
                Log.d(TAG, "assetCopyFile: the two files is same")
                return null//"the two files is same, don't need overwrite"
            }
        }
        val fileOutputStream = FileOutputStream(destFile, !isOverwrite)
        try {
            FileUtils.copy(inputStream, fileOutputStream)
            return destFile
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        } finally {
            fileOutputStream.flush()
            fileOutputStream.close()
            fileInputStream?.close()
            inputStream.close()
        }
        return null
    }

    /**
     * 输出流转文件
     * @param byteArrayOutputStream ByteArrayOutputStream
     * @param filePathWithName String
     * @param isOverwrite Boolean
     * @return String
     */
    @JvmStatic
    fun byteArrayOutputStream2File(byteArrayOutputStream: ByteArrayOutputStream, filePathWithName: String, isOverwrite: Boolean = true): String =
        byteArrayOutputStream2File(byteArrayOutputStream, File(filePathWithName), isOverwrite)

    /**
     * 输出流转文件
     * @param byteArrayOutputStream ByteArrayOutputStream
     * @param destFile File
     * @param isOverwrite Boolean
     */
    @JvmStatic
    fun byteArrayOutputStream2File(byteArrayOutputStream: ByteArrayOutputStream, destFile: File, isOverwrite: Boolean = true): String {
        createFile(destFile)
        val fileOutputStream = FileOutputStream(destFile)
        try {
            fileOutputStream.write(byteArrayOutputStream.toByteArray())
            return destFile.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        } finally {
            byteArrayOutputStream.flush()
            byteArrayOutputStream.close()
            fileOutputStream.flush()
            fileOutputStream.close()
        }
        return MSG_WRONG
    }

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
    fun copyFile(sourceFile: File, destFile: File, isOverwrite: Boolean = true): File? {
        if (!isFileExist(sourceFile)) return null
        val fileInputStream = FileInputStream(sourceFile)
        try {
            return inputStream2File(fileInputStream, destFile, isOverwrite)
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        } finally {
            fileInputStream.close()
        }
        return null
    }

    /**
     * 文件转Md5
     * @param inputStream InputStream
     * @return String
     */
    @JvmStatic
    fun file2Md5(inputStream: InputStream): String {
        val messageDigest: MessageDigest = UtilKMD5.get()
        try {
            var bufferLength: Int
            val buffer = ByteArray(1024)
            while (inputStream.read(buffer, 0, 1024).also { bufferLength = it } != -1) {
                messageDigest.update(buffer, 0, bufferLength)
            }
            val bigInteger = BigInteger(1, messageDigest.digest())
            return bigInteger.toString(16)
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        }
        return MSG_WRONG
    }

    /**
     * 文件内容是否一样
     * @param inputStream1 InputStream
     * @param inputStream2 InputStream
     * @return Boolean
     */
    @JvmStatic
    fun isFilesSame(inputStream1: InputStream, inputStream2: InputStream): Boolean {
        return TextUtils.equals(file2Md5(inputStream1), file2Md5(inputStream2))
    }

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

    @JvmStatic
    fun genFolderPath(folderPath: String): String {
        var tmpFolderPath = folderPath
        if (!tmpFolderPath.endsWith("/")) tmpFolderPath += "/"
        return tmpFolderPath
    }
    //endregion
}

