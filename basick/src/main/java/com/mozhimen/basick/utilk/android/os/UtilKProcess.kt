package com.mozhimen.basick.utilk.android.os

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Process
import com.mozhimen.basick.utilk.android.app.UtilKActivityManager
import com.mozhimen.basick.utilk.android.app.UtilKApplication
import com.mozhimen.basick.utilk.android.app.UtilKApplicationReflect
import com.mozhimen.basick.utilk.android.app.UtilKRunningAppProcessInfo
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
    @JvmStatic
    fun getCurrentProcessNameByApplication(): String? =
        UtilKApplication.getCurrentProcessName()

    @JvmStatic
    fun getCurrentProcessNameByActivityManager(): String? =
        UtilKRunningAppProcessInfo.getCurrentProcessName(_context)

    @JvmStatic
    fun getCurrentProcessNameByFile(): String? =
        UtilKReader.getCurrentProcessName()

    @JvmStatic
    fun getCurrentProcessNameByReflect(): String? {
        try {
            val fieldMLoadedApk = UtilKApplicationReflect.instance.get().javaClass.getField("mLoadedApk")
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
    @JvmStatic
    fun getCurrentProcessNameByReflect2(): String? {
        try {
            val methodCurrentProcessName: Method = Class.forName("android.app.ActivityThread", false, Application::class.java.classLoader).getDeclaredMethod("currentProcessName", *arrayOfNulls<Class<*>?>(0))
            methodCurrentProcessName.isAccessible = true
            val invoke: Any? = methodCurrentProcessName.invoke(null, arrayOfNulls<Any>(0))
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
     * 判断是否主进程
     */
    @JvmStatic
    fun isMainProcess(mainProcessName: String): Boolean {
        val currentProcessName = getCurrentProcessName() ?: return true
        return mainProcessName == currentProcessName
    }
}