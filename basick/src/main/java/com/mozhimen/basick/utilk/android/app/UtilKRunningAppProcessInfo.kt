package com.mozhimen.basick.utilk.android.app

import android.app.ActivityManager.RunningAppProcessInfo
import android.content.Context
import com.mozhimen.basick.elemk.android.app.cons.CActivityManager
import com.mozhimen.basick.utilk.android.content.UtilKPackage
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
    fun get(context: Context): List<RunningAppProcessInfo> =
        UtilKActivityManager.getRunningAppProcesses(context)

    @JvmStatic
    fun get_ofCur(context: Context): RunningAppProcessInfo? {
        try {
            val runningAppProcessInfos = get(context).ifEmpty { return null }
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

    //////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getProcessName_ofCur(context: Context): String? =
        get_ofCur(context)?.processName

    //////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun isForeground(context: Context): Boolean {
        try {
            val runningAppProcessInfos = get(context).ifEmpty { return false }
            for (runningAppProcessInfo in runningAppProcessInfos) {
                if (runningAppProcessInfo.importance == CActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND)
                    return runningAppProcessInfo.processName == UtilKPackage.getPackageName()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.e(TAG)
        }
        return false
    }

}