package com.mozhimen.basick.utilk.android.app

import android.app.ActivityManager.RunningAppProcessInfo
import android.content.Context
import com.mozhimen.basick.utilk.android.os.UtilKProcess
import com.mozhimen.basick.utilk.android.util.e
import com.mozhimen.basick.utilk.commons.IUtilK

/**
 * @ClassName UtilKRunningAppProcessInfo
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/10/28 23:20
 * @Version 1.0
 */
object UtilKRunningAppProcessInfo : IUtilK {
    @JvmStatic
    fun get(context: Context): RunningAppProcessInfo? {
        try {
            val runningAppProcessInfos = UtilKActivityManager.getRunningAppProcesses(context)
            if (runningAppProcessInfos.isEmpty()) return null
            for (runningAppProcessInfo in runningAppProcessInfos) {
                if (runningAppProcessInfo.pid == UtilKProcess.getMyPid() && runningAppProcessInfo.processName != null)
                    return runningAppProcessInfo
            }
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.e(TAG)
        }
        return null
    }

    @JvmStatic
    fun getProcessName(context: Context): String? =
        get(context)?.processName

    //////////////////////////////////////////////////////////////////////////////

    /**
     * 是否在主线程
     */
    @JvmStatic
    fun isSelfProcessName(context: Context): Boolean =
        context.packageName == getProcessName(context)
}