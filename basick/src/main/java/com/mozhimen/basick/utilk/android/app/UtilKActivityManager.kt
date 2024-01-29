package com.mozhimen.basick.utilk.android.app

import android.app.ActivityManager
import android.app.ActivityManager.MemoryInfo
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

    ////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getRunningAppProcesses(context: Context): List<ActivityManager.RunningAppProcessInfo> =
            get(context).runningAppProcesses
}