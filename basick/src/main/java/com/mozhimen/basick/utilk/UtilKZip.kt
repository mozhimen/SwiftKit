package com.mozhimen.basick.utilk

import android.util.Log
import com.mozhimen.basick.logk.LogK
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
object UtilKZip {
    private const val TAG = "UtilKZip>>>>>"

    fun zip(srcName: String, zipName: String) {
        Log.d(TAG, "zip start")
        val zipOutputStream = ZipOutputStream(FileOutputStream(zipName))
        val bufferedOutputStream = BufferedOutputStream(zipOutputStream)
        try {
            val file = File(srcName)
            compress(zipOutputStream, bufferedOutputStream, file, file.name)
        } catch (e: Exception) {
            LogK.et(TAG, "zip error ${e.message ?: ""}")
            e.printStackTrace()
        } finally {
            bufferedOutputStream.close()
            zipOutputStream.close()
            Log.d(TAG, "zip: end")
        }
    }

    fun compress(zipOutputStream: ZipOutputStream, bufferedOutputStream: BufferedOutputStream, compressFile: File, filePath: String) {
        if (compressFile.isDirectory) {
            val listFiles = compressFile.listFiles() ?: emptyArray()
            if (listFiles.isEmpty()) {
                val stringBuilder = StringBuilder()
                stringBuilder.append(filePath)
                stringBuilder.append("/")
                Log.d(TAG, "compress: stringBuilder $stringBuilder")

                val stringBuilder2 = StringBuilder()
                stringBuilder2.append(filePath)
                stringBuilder2.append("/")
                zipOutputStream.putNextEntry(ZipEntry(stringBuilder2.toString()))
            } else {
                for (i in listFiles.indices) {
                    val childListFiles = listFiles[i]
                    val stringBuilder3 = StringBuilder()
                    stringBuilder3.append(filePath)
                    stringBuilder3.append("/")
                    stringBuilder3.append(listFiles[i].name)
                    compress(zipOutputStream, bufferedOutputStream, childListFiles, stringBuilder3.toString())
                }
            }
        } else {
            zipOutputStream.putNextEntry(ZipEntry(filePath))
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