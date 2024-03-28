package com.mozhimen.basick.utilk.kotlin

import android.annotation.SuppressLint
import android.app.Application
import com.mozhimen.basick.utilk.android.app.UtilKApplication
import com.mozhimen.basick.utilk.android.app.UtilKApplicationWrapper
import com.mozhimen.basick.utilk.android.app.UtilKRunningAppProcessInfo
import com.mozhimen.basick.utilk.android.util.e
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.io.UtilKFileReader
import java.lang.reflect.Method

/**
 * @ClassName UtilKStrProcess
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/1/30 9:47
 * @Version 1.0
 */
object UtilKStrProcess : BaseUtilK() {
    @JvmStatic
    fun getStrProcessNameCurrent(): String? {
        var processName = getStrProcessNameCurrentOfApplication()
        if (!processName.isNullOrEmpty())
            return processName
        processName = getStrProcessNameCurrentOfFile()
        if (!processName.isNullOrEmpty())
            return processName
        processName = getStrProcessNameCurrentOfActivityManager()
        if (!processName.isNullOrEmpty())
            return processName
        processName = getStrProcessNameCurrentOfReflect()
        if (!processName.isNullOrEmpty())
            return processName
        return getStrProcessNameCurrentOfReflect2()
    }

    /**
     * 通过Application新的API获取进程名，无需反射，无需IPC，效率最高。
     */
    @JvmStatic
    fun getStrProcessNameCurrentOfApplication(): String? =
        UtilKApplication.getProcessName()

    @JvmStatic
    fun getStrProcessNameCurrentOfFile(): String? =
        UtilKFileReader.getCurrentProcessName()

    @JvmStatic
    fun getStrProcessNameCurrentOfActivityManager(): String? =
        UtilKRunningAppProcessInfo.getProcessName(_context)

    @JvmStatic
    fun getStrProcessNameCurrentOfReflect(): String? {
        try {
            val fieldMLoadedApk = UtilKApplicationWrapper.instance.get().javaClass.getField("mLoadedApk")
            fieldMLoadedApk.isAccessible = true
            val loadedApk = fieldMLoadedApk[_context]

            val fieldMActivityThread = loadedApk.javaClass.getDeclaredField("mActivityThread")
            fieldMActivityThread.isAccessible = true
            val activityThread = fieldMActivityThread[loadedApk]

            val methodGetProcessName = activityThread.javaClass.getDeclaredMethod("getProcessName")
            return methodGetProcessName.invoke(activityThread) as? String?
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.e(TAG)
        }
        return null
    }

    /**
     * 通过反射ActivityThread获取进程名，避免了ipc
     */
    @SuppressLint("DiscouragedPrivateApi", "PrivateApi")
    @JvmStatic
    fun getStrProcessNameCurrentOfReflect2(): String? {
        try {
            val methodCurrentProcessName: Method = Class.forName("android.app.ActivityThread", false, Application::class.java.classLoader).getDeclaredMethod("currentProcessName", *arrayOfNulls<Class<*>?>(0))
            methodCurrentProcessName.isAccessible = true
            val invoke: Any? = methodCurrentProcessName.invoke(null, arrayOfNulls<Any>(0))
            if (invoke != null && invoke is String)
                return invoke
        } catch (e: Throwable) {
            e.printStackTrace()
            e.message?.e(TAG)
        }
        return null
    }

    /**
     * 判断是否主进程
     */
    @JvmStatic
    fun isMainProcess(mainProcessName: String): Boolean {
        val currentProcessName = getStrProcessNameCurrent() ?: return true
        return mainProcessName == currentProcessName
    }
}