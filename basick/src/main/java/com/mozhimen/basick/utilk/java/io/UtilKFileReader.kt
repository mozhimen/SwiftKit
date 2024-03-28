package com.mozhimen.basick.utilk.java.io

import com.mozhimen.basick.elemk.cons.CPath
import com.mozhimen.basick.utilk.android.os.UtilKProcess
import com.mozhimen.basick.utilk.android.text.formatFileSize
import com.mozhimen.basick.utilk.android.util.e
import com.mozhimen.basick.utilk.commons.IUtilK
import com.mozhimen.basick.utilk.kotlin.strFilePath2file
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException

/**
 * @ClassName UtilKFileReader
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/3/28
 * @Version 1.0
 */
object UtilKFileReader : IUtilK {

    @JvmStatic
    fun get(file: File): FileReader =
        FileReader(file)

    @JvmStatic
    fun get(strFilePathName: String): FileReader =
        FileReader(strFilePathName)

    /////////////////////////////////////////////////////////////

    @JvmStatic
    fun getCurrentProcessName(): String? =
        try {
            readLine_use("/proc/${UtilKProcess.getMyPid()}/cmdline".strFilePath2file())?.trim { it <= ' ' }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

    @JvmStatic
    fun getMemorySize(): String? {
        try {
            val strLine: String = readLine_use(CPath.PROC_MEMINFO, 8192) ?: return null// 读取mem info第一行，系统总内存大小
            val strs: Array<String> = strLine.split("\\s+".toRegex()).toTypedArray()
            val memorySize: Long = (Integer.valueOf(strs[1]).toInt() * 1024).toLong() // 获得系统总内存，单位是KB，乘以1024转换为Byte
            return memorySize.formatFileSize() // Byte转换为KB或者MB，内存大小规格化
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    @JvmStatic
    fun readLine_use(strFilePathName: String, bufferSize: Int = 1024): String? =
        readLine_use(get(strFilePathName), bufferSize)

    @JvmStatic
    fun readLine_use(file: File, bufferSize: Int = 1024): String? =
        readLine_use(get(file), bufferSize)

    @JvmStatic
    fun readLine_use(fileReader: FileReader, bufferSize: Int = 1024): String? =
        try {
            UtilKBufferedReader.readLine_use(UtilKBufferedReader.get(fileReader, bufferSize))
        } catch (e: IOException) {
            e.printStackTrace()
            null
        } finally {
            fileReader.close()
        }
}