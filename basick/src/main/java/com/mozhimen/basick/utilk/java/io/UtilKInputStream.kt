package com.mozhimen.basick.utilk.java.io

import android.os.FileUtils
import android.text.TextUtils
import android.util.Log
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.elemk.cons.CMsg
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.java.security.UtilKMD5
import com.mozhimen.basick.utilk.kotlin.regexLineBreak2str
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.math.BigInteger
import java.security.MessageDigest

/**
 * @ClassName UtilKInputStream
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/31 11:42
 * @Version 1.0
 */
fun InputStream.asMd5(): String =
    UtilKInputStream.inputStream2md5(this)

fun InputStream.asBytes(): ByteArray? =
    UtilKInputStream.inputStream2bytes(this)

fun InputStream.asBytes2(fileLength: Long): ByteArray? =
    UtilKInputStream.inputStream2bytes2(this, fileLength)

fun InputStream.asStr(): String =
    UtilKInputStream.inputStream2str(this)

fun InputStream.asFile(destFilePathWithName: String, isOverwrite: Boolean = true): File? =
    UtilKInputStream.inputStream2file(this, destFilePathWithName, isOverwrite)

fun InputStream.asFile(destFile: File, isOverwrite: Boolean = true): File? =
    UtilKInputStream.inputStream2file(this, destFile, isOverwrite)

@RequiresApi(CVersCode.V_29_10_Q)
fun InputStream.asFile2(destFilePathWithName: String, isOverwrite: Boolean = true): File? =
    UtilKInputStream.inputStream2file2(this, destFilePathWithName, isOverwrite)

@RequiresApi(CVersCode.V_29_10_Q)
fun InputStream.asFile2(destFile: File, isOverwrite: Boolean = true): File? =
    UtilKInputStream.inputStream2file2(this, destFile, isOverwrite)

object UtilKInputStream {
    /**
     * 流转文件
     * @param inputStream InputStream
     * @param destFilePathWithName String
     * @param isOverwrite Boolean
     * @return String
     */
    @JvmStatic
    fun inputStream2file(inputStream: InputStream, destFilePathWithName: String, isOverwrite: Boolean = true): File? =
        inputStream2file(inputStream, File(destFilePathWithName), isOverwrite)

    /**
     * 输入流转文件
     * @param inputStream InputStream
     * @return String
     */
    @JvmStatic
    fun inputStream2file(inputStream: InputStream, destFile: File, isOverwrite: Boolean = true): File? {
        var fileInputStream: FileInputStream? = null
        if (!UtilKFile.isFileExist(destFile)) {
            UtilKFile.createFile(destFile)
        } else {
            fileInputStream = FileInputStream(destFile)
            if (isSame(inputStream, fileInputStream)) {//相似内容就直接返回地址
                Log.d(UtilKFile.TAG, "assetCopyFile: the two files is same")
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
            e.message?.et(UtilKFile.TAG)
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
    @RequiresApi(CVersCode.V_29_10_Q)
    @JvmStatic
    fun inputStream2file2(inputStream: InputStream, destFilePathWithName: String, isOverwrite: Boolean = true): File? =
        inputStream2file2(inputStream, File(destFilePathWithName), isOverwrite)

    /**
     * 输入流转文件
     * @param inputStream InputStream
     * @param destFile File
     * @param isOverwrite Boolean
     * @return File?
     */
    @RequiresApi(CVersCode.V_29_10_Q)
    @JvmStatic
    fun inputStream2file2(inputStream: InputStream, destFile: File, isOverwrite: Boolean = true): File? {
        var fileInputStream: FileInputStream? = null
        if (!UtilKFile.isFileExist(destFile)) {
            UtilKFile.createFile(destFile)
        } else {
            fileInputStream = FileInputStream(destFile)
            if (UtilKInputStream.isSame(inputStream, fileInputStream)) {//相似内容就直接返回地址
                Log.d(UtilKFile.TAG, "assetCopyFile: the two files is same")
                return null//"the two files is same, don't need overwrite"
            }
        }
        val fileOutputStream = FileOutputStream(destFile, !isOverwrite)
        try {
            FileUtils.copy(inputStream, fileOutputStream)
            return destFile
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(UtilKFile.TAG)
        } finally {
            fileOutputStream.flush()
            fileOutputStream.close()
            fileInputStream?.close()
            inputStream.close()
        }
        return null
    }

    /**
     * 流转字符串
     * @param inputStream InputStream
     * @return String
     */
    @JvmStatic
    fun inputStream2str(inputStream: InputStream): String {
        val stringBuilder = StringBuilder()
        val inputStreamReader = InputStreamReader(inputStream, "UTF-8")
        val bufferedReader = BufferedReader(inputStreamReader)
        try {
            var lineString = ""
            while (bufferedReader.readLine()?.also { lineString = it } != null) {
                stringBuilder.append(lineString).append("\n")
            }
            return stringBuilder.deleteCharAt(stringBuilder.length - 1).toString().regexLineBreak2str()
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(UtilKFile.TAG)
        } finally {
            bufferedReader.close()
            inputStreamReader.close()
        }
        return CMsg.WRONG
    }

    @JvmStatic
    fun inputStream2bytes(inputStream: InputStream): ByteArray? {
        try {
            val bytes = ByteArray(inputStream.available())
            inputStream.read(bytes)
            return bytes
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(UtilKFile.TAG)
        } finally {
            inputStream.close()
        }
        return null
    }

    fun inputStream2bytes2(inputStream: InputStream, fileLength: Long): ByteArray? {
        try {
            val bytes = ByteArray(fileLength.toInt())
            var offset = 0
            var numRead = 0
            while (offset < bytes.size && inputStream.read(bytes, offset, bytes.size - offset).also { numRead = it } >= 0) {
                offset += numRead
            }
            // 确保所有数据均被读取
            if (offset != bytes.size) throw IOException("Could not completely read file.")
            return bytes
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(UtilKFile.TAG)
        } finally {
            inputStream.close()
        }
        return null
    }

    /**
     * 文件转Md5
     * @param inputStream InputStream
     * @return String
     */
    @JvmStatic
    fun inputStream2md5(inputStream: InputStream): String {
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
            e.message?.et(UtilKFile.TAG)
        }
        return CMsg.WRONG
    }

    @JvmStatic
    fun isSame(inputStream1: InputStream, inputStream2: InputStream): Boolean =
        TextUtils.equals(inputStream2md5(inputStream1), inputStream2md5(inputStream2))
}