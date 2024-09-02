package com.mozhimen.basick.utilk.android.app

import android.app.ActivityManager
import android.app.ActivityManager.MemoryInfo
import android.app.ActivityManager.RunningTaskInfo
import android.content.Context
import com.mozhimen.basick.utilk.android.content.UtilKContext
import com.mozhimen.basick.utilk.android.text.formatFileSize

/**
 * @ClassName UtilKActivityManager
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/3/20 23:23
 * @Version 1.0
 */
object UtilKActivityManager {
    @JvmStatic
    fun get(context: Context): ActivityManager =
        UtilKContext.getActivityManager(context)

    @JvmStatic
    fun getMemoryInfo(context: Context, memoryInfo: MemoryInfo) {
        get(context).getMemoryInfo(memoryInfo)
    }

    @JvmStatic
    fun getMemoryInfo(context: Context): MemoryInfo {
        val memoryInfo = MemoryInfo()
        getMemoryInfo(context, memoryInfo)
        return memoryInfo
    }

    @JvmStatic
    fun getRunningAppProcesses(context: Context): List<ActivityManager.RunningAppProcessInfo> =
        get(context).runningAppProcesses

    @JvmStatic
    fun getRegGlEsVersion(context: Context): Int =
        if (get(context).deviceConfigurationInfo.reqGlEsVersion >= 0x30000) 3 else 2

    @JvmStatic
    fun getRunningTasks(context: Context, maxCount: Int): List<RunningTaskInfo> =
        get(context).getRunningTasks(maxCount)
}