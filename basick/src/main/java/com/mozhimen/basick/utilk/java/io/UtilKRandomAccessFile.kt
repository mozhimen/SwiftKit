package com.mozhimen.basick.utilk.java.io

import com.mozhimen.basick.elemk.cons.CPath
import com.mozhimen.basick.utilk.kotlin.appendStrLineBreak
import java.io.IOException
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

    /**
     * cpu使用率
     * @return Float
     */
    @JvmStatic
    fun getCpuUsage(): Float {
        var randomAccessFile: RandomAccessFile? = null
        var line: String
        try {
            randomAccessFile = RandomAccessFile(CPath.SYSTEM_XBIN_WHICH, "r")
            line = randomAccessFile.readLine()
            var toks = line.split(" ".toRegex()).toTypedArray()
            val idle1 = toks[5].toLong()
            val cpu1 = toks[2].toLong() + toks[3].toLong() + toks[4].toLong() + toks[6].toLong() + toks[7].toLong() + toks[8].toLong()

            Thread.sleep(360)

            randomAccessFile.seek(0)
            line = randomAccessFile.readLine()
            toks = line.split(" ".toRegex()).toTypedArray()
            val idle2 = toks[5].toLong()
            val cpu2 = toks[2].toLong() + toks[3].toLong() + toks[4].toLong() + toks[6].toLong() + toks[7].toLong() + toks[8].toLong()
            return (cpu2 - cpu1).toFloat() / (cpu2 + idle2 - (cpu1 + idle1))
        } catch (ex: IOException) {
            ex.printStackTrace()
        } finally {
            randomAccessFile?.close()
        }
        return 0f
    }
}