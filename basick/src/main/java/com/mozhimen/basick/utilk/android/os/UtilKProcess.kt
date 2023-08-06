package com.mozhimen.basick.utilk.android.os

import android.os.Process
import android.text.TextUtils
import com.mozhimen.basick.utilk.android.app.UtilKActivityManager
import com.mozhimen.basick.utilk.android.app.UtilKApplication
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.io.UtilKReader


/**
 * @ClassName UtilKProcess
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/14 17:32
 * @Version 1.0
 */
object UtilKProcess : BaseUtilK() {
    @JvmStatic
    fun getMyPid(): Int =
        Process.myPid()

    @JvmStatic
    fun getCurrentProcessName(): String {
        var name: String = getCurrentProcessNameByFile()
        if (!TextUtils.isEmpty(name)) return name
        name = getCurrentProcessNameByAms()
        if (!TextUtils.isEmpty(name)) return name
        name = getCurrentProcessNameByReflect()
        return name
    }

    @JvmStatic
    fun getCurrentProcessNameByAms(): String {
        try {
            val runningAppProcessInfos = UtilKActivityManager.getRunningAppProcesses(_context)
            if (runningAppProcessInfos.isEmpty()) return ""
            for (runningAppProcessInfo in runningAppProcessInfos) {
                if (runningAppProcessInfo.pid == getMyPid() && runningAppProcessInfo.processName != null)
                    return runningAppProcessInfo.processName
            }
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        }
        return ""
    }

    @JvmStatic
    fun getCurrentProcessNameByFile(): String =
        UtilKReader.getCurrentProcessName()

    @JvmStatic
    fun getCurrentProcessNameByReflect(): String {
        try {
            val fieldMLoadedApk = UtilKApplication.instance.get().javaClass.getField("mLoadedApk")
            fieldMLoadedApk.isAccessible = true
            val loadedApk = fieldMLoadedApk[_context]

            val fieldMActivityThread = loadedApk.javaClass.getDeclaredField("mActivityThread")
            fieldMActivityThread.isAccessible = true
            val activityThread = fieldMActivityThread[loadedApk]

            val methodGetProcessName = activityThread.javaClass.getDeclaredMethod("getProcessName")
            return methodGetProcessName.invoke(activityThread) as String
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        }
        return ""
    }
}