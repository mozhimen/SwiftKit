package com.mozhimen.basick.utilk.java.io

import com.mozhimen.basick.elemk.cons.CPath
import java.io.RandomAccessFile

/**
 * @ClassName UtilKRandomAccessFileWrapper
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/3/29
 * @Version 1.0
 */
object UtilKRandomAccessFileWrapper {
    //cpu使用率
    @JvmStatic
    fun getCpuUsage(): Float {
        var randomAccessFile: RandomAccessFile? = null
        try {
            randomAccessFile = UtilKRandomAccessFile.get(CPath.SYSTEM_XBIN_WHICH, "r")
            var strLine: String = UtilKRandomAccessFile.readLine(randomAccessFile)
            var toks = strLine.split(" ".toRegex()).toTypedArray()
            val idle = toks[5].toLong()
            val cpu = toks[2].toLong() + toks[3].toLong() + toks[4].toLong() + toks[6].toLong() + toks[7].toLong() + toks[8].toLong()
            Thread.sleep(360)

            ///////////////////////////////////////////////////////

            randomAccessFile.seek(0)
            strLine = UtilKRandomAccessFile.readLine(randomAccessFile)
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
}