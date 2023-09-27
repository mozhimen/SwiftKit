package com.mozhimen.basick.utilk.android.os

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Process
import com.mozhimen.basick.utilk.android.app.UtilKActivityManager
import com.mozhimen.basick.utilk.android.app.UtilKApplication
import com.mozhimen.basick.utilk.android.content.UtilKContext
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.io.UtilKReader
import java.lang.reflect.Method


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
    fun getCurrentProcessName(): String? {
        var processName = getCurrentProcessNameByApplication()
        if (!processName.isNullOrEmpty())
            return processName
        processName = getCurrentProcessNameByFile()
        if (!processName.isNullOrEmpty())
            return processName
        processName = getCurrentProcessNameByActivityManager()
        if (!processName.isNullOrEmpty())
            return processName
        processName = getCurrentProcessNameByReflect()
        if (!processName.isNullOrEmpty())
            return processName
        return getCurrentProcessNameByReflect2()
    }

    /**
     * 通过Application新的API获取进程名，无需反射，无需IPC，效率最高。
     */
    private fun getCurrentProcessNameByApplication(): String? {
        if (UtilKBuildVersion.isAfterV_28_9_P())
            return Application.getProcessName()
        return null
    }

    @JvmStatic
    fun getCurrentProcessNameByActivityManager(): String? {
        try {
            val runningAppProcessInfos = UtilKActivityManager.getRunningAppProcesses(_context)
            if (runningAppProcessInfos.isEmpty()) return null
            for (runningAppProcessInfo in runningAppProcessInfos) {
                if (runningAppProcessInfo.pid == getMyPid() && runningAppProcessInfo.processName != null)
                    return runningAppProcessInfo.processName
            }
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        }
        return null
    }

    @JvmStatic
    fun getCurrentProcessNameByFile(): String? =
        UtilKReader.getCurrentProcessName()

    @JvmStatic
    fun getCurrentProcessNameByReflect(): String? {
        try {
            val fieldMLoadedApk = UtilKApplication.instance.get().javaClass.getField("mLoadedApk")
            fieldMLoadedApk.isAccessible = true
            val loadedApk = fieldMLoadedApk[_context]

            val fieldMActivityThread = loadedApk.javaClass.getDeclaredField("mActivityThread")
            fieldMActivityThread.isAccessible = true
            val activityThread = fieldMActivityThread[loadedApk]

            val methodGetProcessName = activityThread.javaClass.getDeclaredMethod("getProcessName")
            return methodGetProcessName.invoke(activityThread) as? String?
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        }
        return null
    }

    /**
     * 通过反射ActivityThread获取进程名，避免了ipc
     */
    @SuppressLint("DiscouragedPrivateApi", "PrivateApi")
    private fun getCurrentProcessNameByReflect2(): String? {
        try {
            val declaredMethod: Method = Class.forName("android.app.ActivityThread", false, Application::class.java.classLoader).getDeclaredMethod("currentProcessName", *arrayOfNulls<Class<*>?>(0))
            declaredMethod.isAccessible = true
            val invoke: Any? = declaredMethod.invoke(null, arrayOfNulls<Any>(0))
            if (invoke != null && invoke is String)
                return invoke
        } catch (e: Throwable) {
            e.printStackTrace()
            e.message?.et(TAG)
        }
        return null
    }

    //////////////////////////////////////////////////////////////////////////////

    /**
     * 是否在主线程
     */
    @JvmStatic
    fun isMainProcess(context: Context): Boolean {
        for (process in UtilKActivityManager.get(context).runningAppProcesses)
            if (process.processName == UtilKContext.getPackageName(context))
                return true
        return false
    }

    /**
     * 判断是否主进程
     */
    @JvmStatic
    fun isMainProcess(mainProcessName: String): Boolean {
        val currentProcessName = getCurrentProcessName() ?: return true
        return mainProcessName == currentProcessName
    }
}