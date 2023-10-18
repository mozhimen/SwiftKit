package com.mozhimen.basick.utilk.java.io

import com.mozhimen.basick.elemk.cons.CPath
import com.mozhimen.basick.utilk.android.os.UtilKProcess
import com.mozhimen.basick.utilk.android.text.formatFileSize
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.kotlin.strFilePath2file
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

/**
 * @ClassName UtilKReader
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/8/6 0:45
 * @Version 1.0
 */
object UtilKReader : BaseUtilK() {
    @JvmStatic
    fun getCurrentProcessName(): String? {
        var fileReader: FileReader? = null
        var bufferedReader: BufferedReader? = null
        try {
            fileReader = FileReader("/proc/${UtilKProcess.getMyPid()}/cmdline".strFilePath2file())
            bufferedReader = BufferedReader(fileReader)
            return bufferedReader.readLine().trim { it <= ' ' }
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        } finally {
            bufferedReader?.close()
            fileReader?.close()
        }
        return null
    }

    @JvmStatic
    fun getMemorySize(): String? {
        var fileReader: FileReader? = null
        var bufferedReader: BufferedReader? = null
        try {
            fileReader = FileReader(CPath.PROC_MEMINFO)
            bufferedReader = BufferedReader(fileReader, 8192)
            val strLine: String = bufferedReader.readLine() // 读取mem info第一行，系统总内存大小
            val strs: Array<String> = strLine.split("\\s+".toRegex()).toTypedArray()
            /*for (num in strs)
                Log.e(strLine, num + "\t")*/
            val memorySize: Long = (Integer.valueOf(strs[1]).toInt() * 1024).toLong() // 获得系统总内存，单位是KB，乘以1024转换为Byte
            return memorySize.formatFileSize() // Byte转换为KB或者MB，内存大小规格化
        } catch (e: IOException) {
            e.printStackTrace()
            e.message?.et(TAG)
        } finally {
            bufferedReader?.close()
            fileReader?.close()
        }
        return null
    }
}