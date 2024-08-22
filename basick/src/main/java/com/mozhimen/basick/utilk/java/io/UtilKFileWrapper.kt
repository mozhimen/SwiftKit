package com.mozhimen.basick.utilk.java.io

import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.elemk.java.util.cons.CDateFormat
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.system.UtilKOs
import com.mozhimen.basick.utilk.android.system.UtilKStructStat
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.utilk.android.util.d
import com.mozhimen.basick.utilk.android.util.e
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.util.UtilKZipOutputStream
import com.mozhimen.basick.utilk.java.text.longDate2strDate
import com.mozhimen.basick.utilk.kotlin.UtilKStrFile
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.nio.channels.FileChannel
import java.util.Vector
import java.util.zip.GZIPInputStream
import kotlin.jvm.Throws

/**
 * @ClassName UtilKFileWrapp[er
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/3/28
 * @Version 1.0
 */
fun File.gerStrCrc_use(): String? =
    UtilKFileWrapper.gerStrCrc_use(this)

fun File.getFileNameNoExtension(): String? =
    UtilKFileWrapper.getFileNameNoExtension(this)

fun File.getUnZippedInputStream(): InputStream? =
    UtilKFileWrapper.getUnZippedInputStream(this)

@RequiresApi(CVersCode.V_21_5_L)
fun File.getLastAccessTime(): Long =
    UtilKFileWrapper.getLastAccessTime(this)

/////////////////////////////////////////////////////////////////////////////////////////////

fun File.getFileSize_ofAvaioflable(): Long? =
    UtilKFileWrapper.getFileSize_ofAvaioflable(this)

fun File.getFileSize_ofTotal(): Long? =
    UtilKFileWrapper.getFileSize_ofTotal(this)

fun File.getFileCreateTime(): Long =
    UtilKFileWrapper.getFileCreateTime(this)

fun File.getFileCreateTimeStr(): String =
    UtilKFileWrapper.getFileCreateTimeStr(this)

fun File.isFileNotExist(): Boolean =
    UtilKFileWrapper.isFileNotExist(this)

fun File.isFileExist(): Boolean =
    UtilKFileWrapper.isFileExist(this)

fun File.createFile(): Boolean =
    UtilKFileWrapper.createFile(this)

fun File.copyFile(fileDest: File, isAppend: Boolean = false): File? =
    UtilKFileWrapper.copyFile(this, fileDest, isAppend)

fun File.zipFile(zipFile: File): File? =
    UtilKFileWrapper.zipFile(this, zipFile)

fun File.deleteFile(): Boolean =
    UtilKFileWrapper.deleteFile(this)

/////////////////////////////////////////////////////////////////////////////////////////////

fun File.getFolderFiles(): Array<File> =
    UtilKFileWrapper.getFolderFiles(this)

fun File.getFolderFiles_ofAll(fileType: String? = null): Vector<File> =
    UtilKFileWrapper.getFolderFiles_ofAll(this, fileType)

fun File.isFolder(): Boolean =
    UtilKFileWrapper.isFolder(this)

fun File.isFolderExist(): Boolean =
    UtilKFileWrapper.isFolderExist(this)

fun File.isFileZipped(): Boolean =
    UtilKFileWrapper.isFileZipped(this)

fun File.createFolder(): Boolean =
    UtilKFileWrapper.createFolder(this)

fun File.deleteFolder(): Boolean =
    UtilKFileWrapper.deleteFolder(this)

/////////////////////////////////////////////////////////////////////////////////////////////

object UtilKFileWrapper : BaseUtilK() {
    //region # file
    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    fun getLastAccessTime(file: File): Long =
        UtilKStructStat.getSt_atime(file.file2strFilePath())

    @JvmStatic
    fun gerStrCrc_use(file: File): String? =
        file.file2fileInputStream()?.getStrCrc32_use()

    @JvmStatic
    fun getFileNameNoExtension(file: File): String? =
        if (!isFileExist(file)) null
        else UtilKFile.getNameWithoutExtension(file)

    //获取文件大小
    @JvmStatic
    fun getFileSize_ofAvaioflable(file: File): Long? =
        if (!isFileExist(file)) null
        else file.file2fileInputStream()?.getAvailableLong_use()

    /**
     * 获取文件大小
     * file.length() 方法返回文件的长度，单位是字节，表示整个文件的大小。而 inputStream.available() 方法返回的是当前输入流中可读取的字节数，它可能小于或等于文件的长度，具体取决于输入流的类型和状态。
     */
    @JvmStatic
    fun getFileSize_ofTotal(file: File): Long? =
        if (!isFileExist(file)) null
        else file.length()

    @JvmStatic
    fun getFilesSize_ofTotal(files: List<File>): Long =
        files.sumOf { it.length() }

    //文件创建时间
    @JvmStatic
    fun getFileCreateTime(file: File): Long =
        UtilKFile.getLastModified(file)

    //文件创建时间
    @JvmStatic
    fun getFileCreateTimeStr(file: File, formatDate: String = CDateFormat.Format.yyyy_MM_dd_HH_mm_ss): String =
        getFileCreateTime(file).longDate2strDate(formatDate)

    //Returns the uncompressed input stream if gzip compressed.
    @JvmStatic
    fun getUnZippedInputStream(file: File): InputStream? {
        val pushbackInputStream = file.file2fileInputStream()?.inputStream2pushbackInputStream(2) ?: return null
        val signature = ByteArray(2)
        val len = pushbackInputStream.read(signature)
        pushbackInputStream.unread(signature, 0, len)
        return if (signature[0] == 0x1f.toByte() && signature[1] == 0x8b.toByte())
            pushbackInputStream.inputStream2gZIPInputStream(8 * 1024)
        else
            pushbackInputStream
    }

    //判断是否为文件
    @JvmStatic
    fun isFile(file: File): Boolean =
        file.exists() && file.isFile

    @JvmStatic
    fun isFileZipped(file: File): Boolean =
        file.extension == "zip"

    @JvmStatic
    fun isFileNotExist(file: File): Boolean =
        !isFileExist(file)

    //文件是否存在
    @JvmStatic
    fun isFileExist(file: File): Boolean =
        isFile(file)

    //创建文件
    @JvmStatic
    @Throws(Exception::class)
    fun createFile(file: File): Boolean {
        file.parent?.let {
            UtilKStrFile.createFolder(it)
        } ?: throw Exception("don't have parent folder")

        if (!isFileExist(file)) {
            try {
                return file.createNewFile().also { "createFile: file ${file.absolutePath}".d(TAG) }
            } catch (e: Exception) {
                e.printStackTrace()
                "createFile: file ${file.absolutePath} fail".e(TAG)
            }
        } else {
            "createFile: file is exits".d(TAG)
            return true
        }
        return false
    }

    //删除文件
    @JvmStatic
    fun deleteFile(file: File): Boolean =
        if (!isFileExist(file)) false
        else file.delete().also { "deleteFile: file ${file.absolutePath} success".d(TAG) }

    //批量删除
    fun deleteFiles(vararg files: File) {
        for (file in files)
            deleteFile(file)
    }

    //复制文件
    @JvmStatic
    fun copyFile(fileSource: File, fileDest: File, isAppend: Boolean = false): File? =
        if (!isFileExist(fileSource)) null
        else fileSource.file2fileInputStream()?.inputStream2file_use(fileDest, isAppend)

    @JvmStatic
    fun copyFile_ofFileChannel(fileSource: File, fileDest: File, isAppend: Boolean = false): File? {
        if (!isFileExist(fileSource)) return null
        else {
            var channelSource: FileChannel? = null
            var channelDest: FileChannel? = null
            try {
                // 使用文件通道进行文件复制
                channelSource = fileSource.file2fileInputStream()?.channel ?: return null
                channelDest = fileDest.file2fileOutputStream(isAppend).channel
                channelDest.transferFrom(channelSource, 0, channelSource.size())
                return fileDest
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            } finally {
                channelSource?.close()
                channelDest?.close()
            }
        }
    }

    //压缩文件
    @JvmStatic
    fun zipFile(fileSource: File, zipFile: File): File? =
        if (!isFileExist(fileSource)) null
        else {
            val zipOutputStream = zipFile.file2fileOutputStream().outputStream2zipOutputStream()
            UtilKZipOutputStream.read_write_use(zipOutputStream, zipOutputStream.outputStream2bufferedOutputStream(), fileSource, fileSource.name)
            zipFile
        }
    //endregion

    /////////////////////////////////////////////////////////////////////////////////////////

    //region # folder
    @JvmStatic
    fun getFolderFiles(folder: File): Array<File> =
        if (!isFolderExist(folder)) emptyArray()
        else folder.listFiles() ?: emptyArray()

    @JvmStatic
    fun getFolderFiles_ofAll(folder: File, fileType: String? = null): Vector<File> {
        val fileVector = Vector<File>()
        if (!isFolderExist(folder)) return fileVector//判断路径是否存在
        val files = getFolderFiles(folder)
        if (files.isEmpty()) { //判断权限
            return fileVector
        }
        for (file in files) { //遍历目录
            if (file.isFile) {
                if (fileType == null) {
                    fileVector.add(file)
                } else if (file.endsWith(fileType)) {
                    fileVector.add(file)
                }
            } else if (file.isDirectory) { //查询子目录
                UtilKStrFile.getFolderAllFiles(file.absolutePath, fileType)
            }
        }
        return fileVector
    }

    @JvmStatic
    fun getFolderFiles_ofAllSorted(files: List<File>): List<File> =
        files.map { it.walkBottomUp() }.asSequence().flatten()
            .filter { it.isFile }
            .sortedBy {
                if (UtilKBuildVersion.isAfterV_21_5_L())
                    it.getLastAccessTime()
                else
                    it.lastModified()
            }
            .toList()


    //判断是否是文件夹
    @JvmStatic
    fun isFolder(folder: File): Boolean =
        folder.exists() && folder.isDirectory

    //文件夹是否存在
    @JvmStatic
    fun isFolderExist(folder: File): Boolean =
        isFolder(folder)

    //创建文件夹
    @JvmStatic
    fun createFolder(folder: File): Boolean =
        if (isFolderExist(folder)) true
        else {
            try {
                folder.mkdirs().also { UtilKLogWrapper.d(TAG, "createFolder: create path ${folder.absolutePath} $it") }
            } catch (e: Exception) {
                e.printStackTrace()
                UtilKLogWrapper.e(TAG, "createFolder: create path ${folder.absolutePath}", e)
                false
            }
        }

    /**
     * 删除文件夹(不保留文件夹)
     */
    @JvmStatic
    fun deleteFolder(folder: File): Boolean {
        if (!isFolderExist(folder)) return false
        val listFiles: Array<File> = getFolderFiles(folder)
        if (listFiles.isNotEmpty()) {
            for (file in listFiles) {
                if (isFolder(file))  // 判断是否为文件夹
                    deleteFolder(file)
                else
                    deleteFile(file)
            }
        }
        folder.delete()
        return true.also { UtilKLogWrapper.d(TAG, "deleteFolder: success") }
    }

    /**
     * 删除文件夹(保留文件夹)
     */
    @JvmStatic
    fun deleteFolderNoSelf(folder: File): Boolean {
        if (!isFolderExist(folder)) return false
        val listFiles: Array<File> = getFolderFiles(folder)
        if (listFiles.isNotEmpty()) {
            for (file in listFiles) {
                if (isFolder(file)) { // 判断是否为文件夹
                    deleteFolderNoSelf(file)
                    file.delete()
                } else
                    deleteFile(file)
            }
        }
        return true.also { UtilKLogWrapper.d(TAG, "deleteFolder: success") }
    }
//endregion
}