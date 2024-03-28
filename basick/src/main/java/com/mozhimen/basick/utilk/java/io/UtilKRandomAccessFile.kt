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
    /**
     * cpu使用率
     * @return Float
     */
    @JvmStatic
    fun getCpuUsage(): Float {
        var randomAccessFile: RandomAccessFile? = null
        var strLine: String
        try {
            randomAccessFile = RandomAccessFile(CPath.SYSTEM_XBIN_WHICH, "r")
            strLine = randomAccessFile.readLine()
            var toks = strLine.split(" ".toRegex()).toTypedArray()
            val idle = toks[5].toLong()
            val cpu = toks[2].toLong() + toks[3].toLong() + toks[4].toLong() + toks[6].toLong() + toks[7].toLong() + toks[8].toLong()
            Thread.sleep(360)

            ///////////////////////////////////////////////////////

            randomAccessFile.seek(0)
            strLine = randomAccessFile.readLine()
            toks = strLine.split(" ".toRegex()).toTypedArray()
            val newIdle = toks[5].toLong()
            val newCpu = toks[2].toLong() + toks[3].toLong() + toks[4].toLong() + toks[6].toLong() + toks[7].toLong() + toks[8].toLong()
            return (newCpu - cpu).toFloat() / (newCpu + newIdle - (cpu + idle))
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            randomAccessFile?.close()
        }
        return 0f
    }

    //////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun write_use(randomAccessFile: RandomAccessFile, str: String) {
        write_use(randomAccessFile, str.toByteArray())
    }

    @JvmStatic
    fun write_use(randomAccessFile: RandomAccessFile, bytes: ByteArray) {
        randomAccessFile.use { it.write(bytes) }
    }
}