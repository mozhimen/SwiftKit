package com.mozhimen.basick.utilk.file

import android.net.Uri
import android.os.Build
import android.text.TextUtils
import android.util.Log
import androidx.core.content.FileProvider
import com.mozhimen.basick.utilk.context.UtilKApplication
import java.io.*
import java.math.BigInteger
import java.security.MessageDigest

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
    private val _context = UtilKApplication.instance.get()

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
     * 文本转文件
     * @param content String
     * @param filePathWithName String
     * @throws Exception
     */
    @JvmStatic
    fun string2File(content: String, filePathWithName: String): String {
        val tmpContent = content + "\n"
        val tmpFile = createFile(filePathWithName)
        val randomAccessFile = RandomAccessFile(tmpFile, "rwd")
        try {
            randomAccessFile.write(tmpContent.toByteArray())
            return tmpFile.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
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
    fun string2File2(content: String, filePathWithName: String): String {
        val tmpContent = content + "\n"
        val tmpFile = createFile(filePathWithName)
        val fileOutputStream = FileOutputStream(tmpFile)
        try {
            fileOutputStream.write(tmpContent.toByteArray())
            return tmpFile.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
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
    fun file2String(filePathWithName: String): String =
        file2String(File(filePathWithName))

    /**
     * 文件转文本
     * @param file File
     * @return String
     */
    @JvmStatic
    fun file2String(file: File): String {
        if (!isFileExist(file)) return MSG_NOT_EXIST
        val fileInputStream = FileInputStream(file)
        try {
            return inputStream2String(fileInputStream).replace("\\n".toRegex(), "\n")
        } catch (e: Exception) {
            e.printStackTrace()
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
    fun inputStream2String(inputStream: InputStream): String {
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
    fun inputStream2File(inputStream: InputStream, destFilePathWithName: String, isOverwrite: Boolean = true): String =
        inputStream2File(inputStream, File(destFilePathWithName), isOverwrite)

    /**
     * 输入流转文件
     * @param inputStream InputStream
     * @return String
     */
    @JvmStatic
    fun inputStream2File(inputStream: InputStream, destFile: File, isOverwrite: Boolean = true): String {
        var fileInputStream: FileInputStream? = null
        if (!isFileExist(destFile)) {
            createFile(destFile)
        } else {
            fileInputStream = FileInputStream(destFile)
            if (isFilesSame(inputStream, fileInputStream)) {//相似内容就直接返回地址
                Log.d(TAG, "assetCopyFile: the two files is same")
                return "the two files is same, don't need overwrite"
            }
        }
        val fileOutputStream = FileOutputStream(destFile, !isOverwrite)
        try {
            var bufferLength: Int
            val buffer = ByteArray(1024)
            while (inputStream.read(buffer).also { bufferLength = it } != -1) {
                fileOutputStream.write(buffer, 0, bufferLength)
            }
            return destFile.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            fileOutputStream.flush()
            fileOutputStream.close()
            fileInputStream?.close()
            inputStream.close()
        }
        return MSG_WRONG
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
    fun copyFile(sourceFilePathWithName: String, destFilePathWithName: String): String =
        copyFile(File(sourceFilePathWithName), File(destFilePathWithName))

    /**
     * 复制文件
     * @param sourceFile File
     * @param destFile File
     */
    @JvmStatic
    fun copyFile(sourceFile: File, destFile: File, isOverwrite: Boolean = true): String {
        if (!isFileExist(sourceFile)) return MSG_NOT_EXIST
        val fileInputStream = FileInputStream(sourceFile)
        try {
            return inputStream2File(fileInputStream, destFile, isOverwrite)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            fileInputStream.close()
        }
        return MSG_WRONG
    }

    /**
     * 文件转Uri
     * if build sdk > N you also add provider and @xml/file_paths

     * AndroidManifest.xml
    <provider
    android:name="androidx.core.content.FileProvider"
    android:authorities="包名.fileprovider"
    android:exported="false"
    android:grantUriPermissions="true">
    <meta-data
    android:name="android.support.FILE_PROVIDER_PATHS"
    android:resource="@xml/file_paths"  />
    </provider>

     * file_paths.xml
    <paths>
    <files-path
    name="files-path"
    path="." />
    </paths>

     * @param filePathWithName String
     * @return Uri
     */
    @JvmStatic
    fun file2Uri(filePathWithName: String): Uri? {
        if (filePathWithName.isEmpty()) {
            Log.e(TAG, "file2Uri: isEmpty true")
            return null
        }
        return file2Uri(File(filePathWithName))
    }

    /**
     * 文件转Uri
     * if build sdk > N you also add provider and @xml/file_paths

     * AndroidManifest.xml
    <provider
    android:name="androidx.core.content.FileProvider"
    android:authorities="包名.fileprovider"
    android:exported="false"
    android:grantUriPermissions="true">
    <meta-data
    android:name="android.support.FILE_PROVIDER_PATHS"
    android:resource="@xml/file_paths"  />
    </provider>

     * file_paths.xml
    <paths>
    <files-path
    name="files-path"
    path="." />
    </paths>

     * @param file File
     * @return Uri
     */
    @JvmStatic
    fun file2Uri(file: File): Uri? {
        if (!isFileExist(file)) {
            Log.e(TAG, "file2Uri: file isFileExist false")
            return null
        }
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            FileProvider.getUriForFile(_context, "${_context.packageName}.fileProvider", file)
        else Uri.fromFile(file)
    }

    /**
     * 文件转Md5
     * @param inputStream InputStream
     * @return String
     */
    @JvmStatic
    fun file2Md5(inputStream: InputStream): String {
        val messageDigest: MessageDigest = MessageDigest.getInstance("md5") ?: throw Exception("get md5 fail")
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

