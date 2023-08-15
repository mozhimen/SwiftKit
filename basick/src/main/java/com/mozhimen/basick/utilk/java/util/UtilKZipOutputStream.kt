package com.mozhimen.basick.utilk.java.util

import android.util.Log
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.bases.IUtilK
import com.mozhimen.basick.utilk.java.io.UtilKFile
import com.mozhimen.basick.utilk.java.io.flushClose
import com.mozhimen.basick.utilk.java.io.getFolderFiles
import java.io.*
import java.nio.Buffer
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

    @JvmStatic
    fun file2zipFile(file: File, zipFile: File) {
        var fileOutputStream: FileOutputStream? = null
        var zipOutputStream: ZipOutputStream? = null
        var bufferedOutputStream: BufferedOutputStream? = null
        try {
            fileOutputStream = FileOutputStream(zipFile, false)
            zipOutputStream = ZipOutputStream(fileOutputStream)
            bufferedOutputStream = BufferedOutputStream(zipOutputStream)
            file2zipFile(zipOutputStream, bufferedOutputStream, file, file.name)
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        } finally {
            bufferedOutputStream?.flushClose()
            zipOutputStream?.flushClose()
            fileOutputStream?.flushClose()
        }
    }

    /**
     * 压缩
     * @param zipOutputStream ZipOutputStream
     * @param bufferedOutputStream BufferedOutputStream
     * @param sourceFile File
     * @param fileName String
     */
    @JvmStatic
    fun file2zipFile(zipOutputStream: ZipOutputStream, bufferedOutputStream: BufferedOutputStream, sourceFile: File, fileName: String) {
        try {
            if (UtilKFile.isFolder(sourceFile)) {
                val listFiles = sourceFile.getFolderFiles()
                if (listFiles.isEmpty()) {
                    val stringBuilder = StringBuilder().apply {
                        append(fileName).append("/")
                    }
                    zipOutputStream.putNextEntry(ZipEntry(stringBuilder.toString().also { Log.d(TAG, "compress: stringBuilder $stringBuilder") }))
                } else {
                    for (file in listFiles) {
                        val stringBuilder = StringBuilder().apply {
                            append(fileName).append("/").append(file.name)
                        }
                        file2zipFile(zipOutputStream, bufferedOutputStream, file, stringBuilder.toString())
                    }
                }
            } else {
                zipOutputStream.putNextEntry(ZipEntry(fileName))
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
        } finally {
            zipOutputStream.flushClose()
            bufferedOutputStream.flushClose()
        }
    }
}