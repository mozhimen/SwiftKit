package com.mozhimen.basick.utilk.java.io

import com.mozhimen.basick.elemk.cons.CPath
import java.io.RandomAccessFile

/**
 * @ClassName UtilKRandomAccessFile
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/2 17:08
 * @Version 1.0
 */
fun RandomAccessFile.write_use(str: String) {
    UtilKRandomAccessFile.write_use(this, str)
}

fun RandomAccessFile.write_use(bytes: ByteArray) {
    UtilKRandomAccessFile.write_use(this, bytes)
}

object UtilKRandomAccessFile {
    @JvmStatic
    fun get(name: String, mode: String): RandomAccessFile =
        RandomAccessFile(name, mode)

    //////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun readLine(randomAccessFile: RandomAccessFile): String =
        randomAccessFile.readLine()

    @JvmStatic
    fun write_use(randomAccessFile: RandomAccessFile, str: String) {
        write_use(randomAccessFile, str.toByteArray())
    }

    @JvmStatic
    fun write_use(randomAccessFile: RandomAccessFile, bytes: ByteArray) {
        randomAccessFile.use { it.write(bytes) }
    }
}