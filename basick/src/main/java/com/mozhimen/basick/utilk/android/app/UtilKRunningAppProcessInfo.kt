package com.mozhimen.basick.utilk.android.app

import android.app.ActivityManager.RunningAppProcessInfo
import android.content.Context
import com.mozhimen.basick.utilk.android.content.UtilKContext
import com.mozhimen.basick.utilk.android.os.UtilKProcess
import com.mozhimen.basick.utilk.android.util.et

/**
 * @ClassName UtilKRunningAppProcessInfo
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/10/28 23:20
 * @Version 1.0
 */
object UtilKRunningAppProcessInfo {
    @JvmStatic
    fun get(context: Context):List<RunningAppProcessInfo> =
        UtilKActivityManager.get(context).runningAppProcesses

    @JvmStatic
    fun getCurrentProcessName(context: Context): String? {
        try {
            val runningAppProcessInfos = UtilKActivityManager.getRunningAppProcesses(context)
            if (runningAppProcessInfos.isEmpty()) return null
            for (runningAppProcessInfo in runningAppProcessInfos) {
                if (runningAppProcessInfo.pid == UtilKProcess.getMyPid() && runningAppProcessInfo.processName != null)
                    return runningAppProcessInfo.processName
            }
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(UtilKProcess.TAG)
        }
        return null
    }

    //////////////////////////////////////////////////////////////////////////////

    /**
     * 是否在主线程
     */
    @JvmStatic
    fun isMainProcess(context: Context): Boolean {
        for (process in get(context))
            if (process.processName == UtilKContext.getPackageName(context))
                return true
        return false
    }
}