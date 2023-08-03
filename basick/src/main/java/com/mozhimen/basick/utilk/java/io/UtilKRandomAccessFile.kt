package com.mozhimen.basick.utilk.java.io

import com.mozhimen.basick.utilk.kotlin.appendStrLineBreak
import java.io.RandomAccessFile

/**
 * @ClassName UtilKRandomAccessFile
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/2 17:08
 * @Version 1.0
 */
fun String.writeStr2randomAccessFile(randomAccessFile: RandomAccessFile) {
    UtilKRandomAccessFile.writeStr2randomAccessFile(this, randomAccessFile)
}

object UtilKRandomAccessFile {
    @JvmStatic
    fun writeStr2randomAccessFile(str: String, randomAccessFile: RandomAccessFile) {
        writeBytes2randomAccessFile(str.appendStrLineBreak().toByteArray(), randomAccessFile)
    }

    @JvmStatic
    fun writeBytes2randomAccessFile(bytes: ByteArray, randomAccessFile: RandomAccessFile) {
        randomAccessFile.use { it.write(bytes) }
    }
}