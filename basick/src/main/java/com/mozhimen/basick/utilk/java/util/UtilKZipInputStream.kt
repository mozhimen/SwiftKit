package com.mozhimen.basick.utilk.java.util

import com.mozhimen.basick.utilk.java.io.inputStream2file_use_ofCopyTo
import java.io.File
import java.util.zip.ZipInputStream

/**
 * @ClassName UtilKZipInputStream
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/5/11
 * @Version 1.0
 */

fun ZipInputStream.extractEntryToFile_use(entryName: String, gameFile: File) {
    UtilKZipInputStream.extractEntryToFile_use(this, entryName, gameFile)
}

///////////////////////////////////////////////////////////////////////////////////

object UtilKZipInputStream {

    @JvmStatic
    fun extractEntryToFile_use(zipInputStream: ZipInputStream, entryName: String, gameFile: File) {
        zipInputStream.use { inputStream ->
            while (true) {
                val entry = inputStream.nextEntry
                if (entry.name == entryName) break
            }
            inputStream.inputStream2file_use_ofCopyTo(gameFile)
        }
    }
}