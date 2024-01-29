package com.mozhimen.basick.utilk.android.app

import android.app.ActivityManager.MemoryInfo
import android.content.Context
import com.mozhimen.basick.utilk.android.text.formatFileSize
/**
 * @ClassName UtilKMemoryInfo
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/1/29 23:08
 * @Version 1.0
 */
object UtilKMemoryInfo {
    @JvmStatic
    fun get(context: Context): MemoryInfo =
        UtilKActivityManager.getMemoryInfo(context)

    @JvmStatic
    fun get(context: Context, memoryInfo: MemoryInfo) {
        UtilKActivityManager.getMemoryInfo(context, memoryInfo)
    }

    @JvmStatic
    fun getAvailMem(context: Context): Long =
        getAvailMem(get(context))

    @JvmStatic
    fun getAvailMem(memoryInfo: MemoryInfo): Long =
        memoryInfo.availMem

    @JvmStatic
    fun getAvailMemSizeStr(context: Context): String =
        getAvailMemSizeStr(get(context))

    @JvmStatic
    fun getAvailMemSizeStr(memoryInfo: MemoryInfo): String =
        getAvailMem(memoryInfo).formatFileSize()

    @JvmStatic
    fun getTotalMen(context: Context): Long =
        getTotalMen(get(context))

    @JvmStatic
    fun getTotalMen(memoryInfo: MemoryInfo): Long =
        memoryInfo.totalMem

    @JvmStatic
    fun getTotalMenSizeStr(context: Context): String =
        getTotalMenSizeStr(get(context))

    @JvmStatic
    fun getTotalMenSizeStr(memoryInfo: MemoryInfo): String =
        getTotalMen(memoryInfo).formatFileSize()
}

