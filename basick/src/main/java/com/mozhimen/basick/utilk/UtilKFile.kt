package com.mozhimen.basick.utilk

import android.Manifest
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.content.FileProvider

import java.io.*
import java.lang.StringBuilder
import java.math.BigInteger
import java.security.MessageDigest

/**
 * @ClassName UtilKFile
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/22 11:59
 * @Version 1.0
 */
object UtilKFile {

    private const val TAG = "UtilKFile>>>>>"
    private val _context = UtilKGlobal.instance.getApp()!!

    /**
     * 判断是否是文件夹
     * @param folder File
     * @return Boolean
     */
    @JvmStatic
    fun isFolder(folder: File): Boolean =
        folder.exists() && folder.isDirectory

    /**
     * 判断是否为文件
     * @param file File
     * @return Boolean
     */
    @JvmStatic
    fun isFile(file: File): Boolean =
        file.exists() && file.isFile

    /**
     * 文件是否存在
     * @param filePathWithName String
     * @return Boolean
     */
    fun isFileExist(filePathWithName: String) =
        isFileExist(File(filePathWithName))

    /**
     * 文件夹是否存在
     * @param folderPath String
     * @return Boolean
     */
    fun isFolderExist(folderPath: String) =
        UtilKFile.isFolderExist(File(genFolderPath(folderPath)))

    /**
     * 文件是否存在
     * @param file File
     * @return Boolean
     */
    @JvmStatic
    fun isFileExist(file: File): Boolean =
        isFile(file)

    /**
     * 文件夹是否存在
     * @param folder File
     * @return Boolean
     */
    @JvmStatic
    fun isFolderExist(folder: File): Boolean =
        isFolder(folder)

    /**
     * 创建文件
     * @param filePathWithName String
     * @return File
     * @throws Exception
     */
    @JvmStatic
    @Throws(Exception::class)
    fun createFile(filePathWithName: String): File =
        createFile(File(filePathWithName))

    /**
     * 创建文件
     * @param file File
     * @return File
     * @throws Exception
     */
    @JvmStatic
    @Throws(Exception::class)
    fun createFile(file: File): File {
        file.parent?.let { createFolder(it) } ?: throw Exception("dont have parent folder")
        if (!file.exists()) {
            file.createNewFile()
        }
        return file
    }

    /**
     * 删除文件
     * @param filePathWithName String
     * @throws Exception
     */
    @JvmStatic
    @Throws(Exception::class)
    fun deleteFile(filePathWithName: String) {
        deleteFile(File(filePathWithName))
    }

    /**
     * 删除文件
     * @param file File
     */
    @JvmStatic
    @Throws(Exception::class)
    fun deleteFile(file: File): Boolean {
        return if (file.exists() && file.isFile) {
            file.delete()
        } else false
    }

    /**
     * 创建文件夹
     * @param folderPath String
     */
    @JvmStatic
    @Throws(Exception::class)
    fun createFolder(folderPath: String) {
        createFolder(File(genFolderPath(folderPath)))
    }

    /**
     * 创建文件夹
     * @param folder File
     * @throws Exception
     */
    @JvmStatic
    @Throws(Exception::class)
    fun createFolder(folder: File) {
        if (!folder.exists()) {
            folder.mkdirs()
        }
    }

    /**
     * 删除文件夹
     * @param folderPath String
     * @return Boolean
     * @throws Exception
     */
    @JvmStatic
    @Throws(Exception::class)
    fun deleteFolder(folderPath: String): Boolean {
        return deleteFolder(File(genFolderPath(folderPath)))
    }

    /**
     * 删除文件夹
     * @param folder File
     * @return Boolean
     * @throws Exception
     */
    @JvmStatic
    @Throws(Exception::class)
    fun deleteFolder(folder: File): Boolean {
        if (!folder.exists()) return false
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

    /**
     * 文本转文件
     * @param content String
     * @param filePathWithName String
     * @throws Exception
     */
    @JvmStatic
    @Throws(Exception::class)
    fun string2File(content: String, filePathWithName: String) {
        val tmpContent = content + "\r\n"
        val txtFile = createFile(filePathWithName)
        val randomAccessFile = RandomAccessFile(txtFile, "rwf")
        randomAccessFile.use { raf ->
            raf.write(tmpContent.toByteArray())
        }
    }

    /**
     * 文件转文本
     * @param filePathWithName String
     * @return String
     * @throws Exception
     */
    @JvmStatic
    @Throws(Exception::class)
    fun file2String(filePathWithName: String): String {
        val file = File(filePathWithName)
        if (isFolder(file)) return "this path is a folder"
        val fileInputStream = FileInputStream(file)
        fileInputStream.use { stream ->
            return inputStream2String(stream)
        }
    }

    /**
     * 流转字符串
     * @param inputStream InputStream
     * @return String
     */
    @JvmStatic
    @Throws(Exception::class)
    fun inputStream2String(inputStream: InputStream): String {
        val stringBuilder = StringBuilder()
        val inputStreamReader = InputStreamReader(inputStream, "UTF-8")
        val bufferedReader = BufferedReader(inputStreamReader)
        try {
            var lineString = ""
            while (bufferedReader.readLine()?.also { lineString = it } != null) {
                stringBuilder.append(lineString).append("\n")
            }
            return stringBuilder.toString()
        } finally {
            bufferedReader.close()
            inputStreamReader.close()
        }
    }

    /**
     * 复制文件
     * @param sourceFilePathWithName String
     * @param destFilePathWithName String
     * @throws Exception
     */
    @JvmStatic
    @Throws(Exception::class)
    fun copyFile(sourceFilePathWithName: String, destFilePathWithName: String) {
        copyFile(File(sourceFilePathWithName), File(destFilePathWithName))
    }

    /**
     * 复制文件
     * @param sourceFile File
     * @param destFile File
     */
    @JvmStatic
    @Throws(Exception::class)
    fun copyFile(sourceFile: File, destFile: File) {
        if (!sourceFile.exists()) return
        if (!destFile.exists()) createFile(destFile)
        val fileInputStream = FileInputStream(sourceFile)
        val fileOutputStream = FileOutputStream(destFile)
        try {
            val buffer = ByteArray(16384)
            var bufferLength: Int
            while (fileInputStream.read(buffer).also { bufferLength = it } > 0) {
                fileOutputStream.write(buffer, 0, bufferLength)
            }
        } finally {
            fileInputStream.close()
            fileOutputStream.close()
        }
    }

    /**
     * 文件转Uri
     * @param file File
     * @return Uri
     */
    @JvmStatic
    @Throws(Exception::class)
    fun file2Uri(file: File): Uri =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            FileProvider.getUriForFile(_context, "${_context.packageName}.fileProvider", file)
        else Uri.fromFile(file)

    /**
     * 文件转Md5
     * @param inputStream InputStream
     * @return String
     */
    @JvmStatic
    @Throws(Exception::class)
    fun file2Md5(inputStream: InputStream): String {
        val buffer = ByteArray(1024)
        val messageDigest: MessageDigest = MessageDigest.getInstance("md5") ?: throw Exception("get md5 fail")
        var bufferLength: Int
        while (inputStream.read(buffer, 0, 1024).also { bufferLength = it } != -1) {
            messageDigest.update(buffer, 0, bufferLength)
        }
        val bigInteger = BigInteger(1, messageDigest.digest())
        return bigInteger.toString(16)
    }

    private fun genFolderPath(folderPath: String): String {
        var tmpFolderPath = folderPath
        if (!tmpFolderPath.endsWith(File.separator)) tmpFolderPath += File.separator
        return tmpFolderPath
    }
}