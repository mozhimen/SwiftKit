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
fun String.str2randomAccessFile(randomAccessFile: RandomAccessFile) {
    UtilKRandomAccessFile.str2randomAccessFile(this, randomAccessFile)
}

object UtilKRandomAccessFile {
    @JvmStatic
    fun str2randomAccessFile(str: String, randomAccessFile: RandomAccessFile) {
        bytes2randomAccessFile(str.appendStrLineBreak().toByteArray(), randomAccessFile)
    }

    @JvmStatic
    fun bytes2randomAccessFile(bytes: ByteArray, randomAccessFile: RandomAccessFile) {
        randomAccessFile.use {
            it.write(bytes)
        }
    }
}