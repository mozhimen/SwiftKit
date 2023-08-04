package com.mozhimen.basick.utilk.java.io

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.TextUtils
import android.util.Log
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.utilk.android.util.dt
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.bases.IUtilK
import com.mozhimen.basick.utilk.java.security.inputStream2md5Str2
import com.mozhimen.basick.utilk.kotlin.text.replaceRegexLineBreak
import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

/**
 * @ClassName UtilKInputStream
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/31 11:42
 * @Version 1.0
 */
fun InputStream.inputStream2bytes(): ByteArray =
    UtilKInputStream.inputStream2bytes(this)

fun ByteArray.readBytes2inputStream(inputStream: InputStream) {
    UtilKInputStream.readBytes2inputStream(this, inputStream)
}

fun InputStream.inputStream2bytes2(fileLength: Long): ByteArray =
    UtilKInputStream.inputStream2bytes2(this, fileLength)

fun InputStream.inputStream2anyBitmap(): Bitmap =
    UtilKInputStream.inputStream2anyBitmap(this)

fun InputStream.inputStream2str(): String =
    UtilKInputStream.inputStream2str(this)

fun InputStream.inputStream2file(destFilePathWithName: String, isOverwrite: Boolean = true): File? =
    UtilKInputStream.inputStream2file(this, destFilePathWithName, isOverwrite)

fun InputStream.inputStream2file(destFile: File, isOverwrite: Boolean = true): File? =
    UtilKInputStream.inputStream2file(this, destFile, isOverwrite)

@RequiresApi(CVersCode.V_29_10_Q)
fun InputStream.inputStream2file2(destFilePathWithName: String, isOverwrite: Boolean = true): File? =
    UtilKInputStream.inputStream2file2(this, destFilePathWithName, isOverwrite)

@RequiresApi(CVersCode.V_29_10_Q)
fun InputStream.inputStream2file2(destFile: File, isOverwrite: Boolean = true): File? =
    UtilKInputStream.inputStream2file2(this, destFile, isOverwrite)

object UtilKInputStream : IUtilK {

    @JvmStatic
    fun readBytes2inputStream(bytes: ByteArray, inputStream: InputStream) {
        inputStream.use { inputStream.read(bytes) }
    }

    @JvmStatic
    fun inputStream2bytes(inputStream: InputStream): ByteArray {
        val bytes = ByteArray(inputStream.available())
        inputStream.use { bytes.readBytes2inputStream(inputStream) }
        return bytes
    }

    @JvmStatic
    fun inputStream2bytes2(inputStream: InputStream, fileLength: Long): ByteArray {
        inputStream.use {
            val bytes = ByteArray(fileLength.toInt())
            var offset = 0
            var readCount = 0
            while (offset < bytes.size && inputStream.read(bytes, offset, bytes.size - offset).also { readCount = it } != -1) {
                offset += readCount
            }
            // 确保所有数据均被读取
            if (offset != bytes.size) throw IOException("Could not completely read file.")
            return bytes
        }
    }

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
            return stringBuilder.deleteCharAt(stringBuilder.length - 1).toString().replaceRegexLineBreak()
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(UtilKFile.TAG)
        } finally {
            bufferedReader.close()
            inputStreamReader.close()
        }
        return ""
    }

    @JvmStatic
    fun inputStream2str2(inputStream: InputStream): String =
        inputStream2str2(inputStream, ByteArrayOutputStream())

    @JvmStatic
    fun inputStream2str2(inputStream: InputStream, byteArrayOutputStream: ByteArrayOutputStream): String =
        byteArrayOutputStream.flushClose { stream ->
            var readCount: Int
            while (inputStream.read().also { readCount = it } != -1) {
                stream.write(readCount)
            }
            stream.byteArrayOutputStream2str()
        }

    @JvmStatic
    fun inputStream2anyBitmap(inputStream: InputStream): Bitmap =
        inputStream.use { BitmapFactory.decodeStream(it) }

    @JvmStatic
    fun inputStream2file(inputStream: InputStream, destFilePathWithName: String, isOverwrite: Boolean = true): File? =
        inputStream.use { inputStream2file(it, UtilKFile.createFile(destFilePathWithName), isOverwrite) }
    
    @JvmStatic
    fun inputStream2file(inputStream: InputStream, file: File, isOverwrite: Boolean = true): File? {
        UtilKFile.createFile(file)
        val fileInputStream = FileInputStream(file)
        if (isInputStreamSame(inputStream, fileInputStream)) {//相似内容就直接返回地址
            Log.d(TAG, "assetCopyFile: the two files is same")
            return file//"the two files is same, don't need overwrite"
        }
        try {
            return FileOutputStream(file, !isOverwrite).flushClose { it.fileOutputStream2file(inputStream, file) }
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(UtilKFile.TAG)
        } finally {
            fileInputStream.close()
            inputStream.close()
        }
        return null
    }

    @JvmStatic
    @RequiresApi(CVersCode.V_29_10_Q)
    fun inputStream2file2(inputStream: InputStream, filePathWithName: String, isOverwrite: Boolean = true): File? =
        inputStream2file2(inputStream, UtilKFile.createFile(filePathWithName), isOverwrite)

    @JvmStatic
    @RequiresApi(CVersCode.V_29_10_Q)
    fun inputStream2file2(inputStream: InputStream, file: File, isOverwrite: Boolean = true): File? {
        UtilKFile.createFile(file)
        val fileInputStream = FileInputStream(file)
        if (isInputStreamSame(inputStream, fileInputStream)) {//相似内容就直接返回地址
            Log.d(UtilKFile.TAG, "assetCopyFile: the two files is same")
            return file//"the two files is same, don't need overwrite"
        }
        try {
            return FileOutputStream(file, !isOverwrite).flushClose { it.fileOutputStream2file2(inputStream, file) }
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(UtilKFile.TAG)
        } finally {
            fileInputStream.close()
            inputStream.close()
        }
        return null
    }

    @JvmStatic
    fun isInputStreamSame(inputStream1: InputStream, inputStream2: InputStream): Boolean =

    @JvmStatic
    @RequiresApi(CVersCode.V_29_10_Q)
    fun inputStream2file2(inputStream: InputStream, file: File, isOverwrite: Boolean = true): File? {
        UtilKFile.createFile(file)
        val fileInputStream = FileInputStream(file)
        if (isInputStreamSame(inputStream, fileInputStream)) {//相似内容就直接返回地址
            Log.d(UtilKFile.TAG, "assetCopyFile: the two files is same")
            return file//"the two files is same, don't need overwrite"
        }
        try {
            return FileOutputStream(file, !isOverwrite).flushClose { it.fileOutputStream2file2(inputStream, file) }
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(UtilKFile.TAG)
        } finally {
            fileInputStream.close()
            inputStream.close()
        }
        return null
    }

    @JvmStatic
    fun isInputStreamSame(inputStream1: InputStream, inputStream2: InputStream): Boolean =
        TextUtils.equals(inputStream1.inputStream2md5Str2(), inputStream2.inputStream2md5Str2())
}