package com.mozhimen.basick.utilk.java.util

import android.util.Log
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.bases.IUtilK
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
object UtilKZipOutputStream : IUtilK {

    /**
     * 压缩
     * @param filePathWithName String
     * @param zipFilePathWithName String
     */
    @JvmStatic
    fun file2zipFile(filePathWithName: String, zipFilePathWithName: String) {
        file2zipFile(File(filePathWithName), zipFilePathWithName)
    }

    /**
     * 压缩
     * @param filePathWithName String
     * @param zipFilePathWithName String
     */
    @JvmStatic
    fun file2zipFile(sourceFile: File, zipFilePathWithName: String) {
        val fileOutputStream = FileOutputStream(zipFilePathWithName)
        val zipOutputStream = ZipOutputStream(fileOutputStream)
        val bufferedOutputStream = BufferedOutputStream(zipOutputStream)
        try {
            file2zipFile(zipOutputStream, bufferedOutputStream, sourceFile, sourceFile.name)
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        } finally {
            bufferedOutputStream.flush()
            bufferedOutputStream.close()
            zipOutputStream.flush()
            zipOutputStream.close()
            fileOutputStream.flush()
            fileOutputStream.close()
        }
    }

    /**
     * 压缩
     * @param zipOutputStream ZipOutputStream
     * @param bufferedOutputStream BufferedOutputStream
     * @param sourceFile File
     * @param filePathWithName String
     */
    @JvmStatic
    fun file2zipFile(zipOutputStream: ZipOutputStream, bufferedOutputStream: BufferedOutputStream, sourceFile: File, filePathWithName: String) {
        if (sourceFile.isDirectory) {
            val listFiles = sourceFile.listFiles() ?: emptyArray()
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
                    file2zipFile(zipOutputStream, bufferedOutputStream, childListFiles, stringBuilder.toString())
                }
            }
        } else {
            zipOutputStream.putNextEntry(ZipEntry(filePathWithName))
            val fileInputStream = FileInputStream(sourceFile)
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