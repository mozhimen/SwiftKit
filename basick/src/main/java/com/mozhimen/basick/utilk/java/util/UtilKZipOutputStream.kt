package com.mozhimen.basick.utilk.java.util

import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.utilk.commons.IUtilK
import com.mozhimen.basick.utilk.java.io.UtilKFile
import com.mozhimen.basick.utilk.java.io.file2fileInputStream
import com.mozhimen.basick.utilk.java.io.flushClose
import com.mozhimen.basick.utilk.java.io.getFolderFiles
import com.mozhimen.basick.utilk.java.io.inputStream2bufferedInputStream
import com.mozhimen.basick.utilk.java.io.inputStream2outputStream
import java.io.BufferedOutputStream
import java.io.File
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
     */
    @JvmStatic
    fun zipOutputStream2bufferedOutputStream(zipOutputStream: ZipOutputStream, bufferedOutputStream: BufferedOutputStream, fileSource: File, fileName: String) {
        try {
            if (UtilKFile.isFolder(fileSource)) {
                val listFiles = fileSource.getFolderFiles()
                if (listFiles.isEmpty()) {
                    val stringBuilder = StringBuilder().apply { append(fileName).append("/") }
                    zipOutputStream.putNextEntry(ZipEntry(stringBuilder.toString().also { UtilKLogWrapper.d(TAG, "compress: stringBuilder $stringBuilder") }))
                } else
                    for (file in listFiles) {
                        val stringBuilder = StringBuilder().apply { append(fileName).append("/").append(file.name) }
                        zipOutputStream2bufferedOutputStream(zipOutputStream, bufferedOutputStream, file, stringBuilder.toString())
                    }
            } else {
                zipOutputStream.putNextEntry(ZipEntry(fileName))
                fileSource.file2fileInputStream().inputStream2bufferedInputStream().inputStream2outputStream(bufferedOutputStream)
            }
        } finally {
            zipOutputStream.flushClose()
        }
    }
}