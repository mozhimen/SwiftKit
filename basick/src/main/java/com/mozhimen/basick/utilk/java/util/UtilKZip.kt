package com.mozhimen.basick.utilk.java.util

import android.util.Log
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.util.et

import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

/**
 * @ClassName UtilKZip
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/6/12 12:12
 * @Version 1.0
 */
object UtilKZip : BaseUtilK() {

    /**
     * 压缩
     * @param filePathWithName String
     * @param zipFilePathWithName String
     */
    @JvmStatic
    fun zip(filePathWithName: String, zipFilePathWithName: String) {
        val fileOutputStream = FileOutputStream(zipFilePathWithName)
        val zipOutputStream = ZipOutputStream(fileOutputStream)
        val bufferedOutputStream = BufferedOutputStream(zipOutputStream)
        try {
            val file = File(filePathWithName)
            compress(zipOutputStream, bufferedOutputStream, file, file.name)
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        } finally {
            fileOutputStream.flush()
            fileOutputStream.close()
            bufferedOutputStream.flush()
            bufferedOutputStream.close()
            zipOutputStream.flush()
            zipOutputStream.close()
        }
    }

    /**
     * 压缩
     * @param zipOutputStream ZipOutputStream
     * @param bufferedOutputStream BufferedOutputStream
     * @param compressFile File
     * @param filePathWithName String
     */
    @JvmStatic
    fun compress(zipOutputStream: ZipOutputStream, bufferedOutputStream: BufferedOutputStream, compressFile: File, filePathWithName: String) {
        if (compressFile.isDirectory) {
            val listFiles = compressFile.listFiles() ?: emptyArray()
            if (listFiles.isEmpty()) {
                val stringBuilder = StringBuilder().apply {
                    append(filePathWithName).append("/")
                }
                zipOutputStream.putNextEntry(ZipEntry(stringBuilder.toString().also { Log.d(TAG, "compress: stringBuilder $stringBuilder") }))
            } else {
                for (i in listFiles.indices) {
                    val childListFiles = listFiles[i]
                    val stringBuilder = StringBuilder().apply {
                        append(filePathWithName).append("/").append(listFiles[i].name)
                    }
                    compress(zipOutputStream, bufferedOutputStream, childListFiles, stringBuilder.toString())
                }
            }
        } else {
            zipOutputStream.putNextEntry(ZipEntry(filePathWithName))
            val fileInputStream = FileInputStream(compressFile)
            val bufferedInputStream = BufferedInputStream(fileInputStream)
            while (true) {
                val read = bufferedInputStream.read()
                if (read != -1) {
                    bufferedOutputStream.write(read)
                } else {
                    bufferedInputStream.close()
                    fileInputStream.close()
                    return
                }
            }
        }
    }
}