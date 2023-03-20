package com.mozhimen.basick.utilk.os.thread

import android.content.Context
import android.os.Looper
import com.mozhimen.basick.utilk.content.UtilKContext
import com.mozhimen.basick.utilk.content.activity.UtilKActivity

/**
 * @ClassName UtilKThread
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/25 0:03
 * @Version 1.0
 */
object UtilKThread {
    /**
     * 是否在主线程
     * @param context Application
     * @return Boolean
     */
    @JvmStatic
    fun isMainProcess(context: Context): Boolean {
        val runningAppProcesses = UtilKActivity.getActivityManager(context).runningAppProcesses
        for (process in runningAppProcesses) {
            if (process.processName == UtilKContext.getPackageName(context)) {
                return true
            }
        }
        return false
    }

    /**
     * 是否是主线程
     * @return Boolean
     */
    @JvmStatic
    fun isMainThread(): Boolean {
        return Looper.getMainLooper().thread == Thread.currentThread()
    }

    /**
     * 是否是MainLooper
     * @return Boolean
     */
    @JvmStatic
    fun isMainLooper(): Boolean {
        return Looper.myLooper() == Looper.getMainLooper()
    }
}