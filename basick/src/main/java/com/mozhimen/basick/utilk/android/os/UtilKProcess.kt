package com.mozhimen.basick.utilk.android.os

import android.os.Process
import android.text.TextUtils
import com.mozhimen.basick.utilk.android.app.UtilKActivityManager
import com.mozhimen.basick.utilk.android.app.UtilKApplication
import com.mozhimen.basick.utilk.bases.BaseUtilK
import java.io.BufferedReader
import java.io.File
import java.io.FileReader


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
                if (runningAppProcessInfo.pid == getMyPid() && runningAppProcessInfo.processName != null) {
                    return runningAppProcessInfo.processName
                }
            }
        } catch (e: java.lang.Exception) {
            return ""
        }
        return ""
    }

    @JvmStatic
    fun getCurrentProcessNameByFile(): String {
        return try {
            val bufferedReader = BufferedReader(FileReader(File("/proc/" + Process.myPid() + "/" + "cmdline")))
            val processName = bufferedReader.readLine().trim { it <= ' ' }
            bufferedReader.close()
            processName
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    @JvmStatic
    fun getCurrentProcessNameByReflect(): String {
        var processName = ""
        try {
            val mLoadedApkField = UtilKApplication.instance.get().javaClass.getField("mLoadedApk")
            mLoadedApkField.isAccessible = true
            val loadedApk = mLoadedApkField[_context]
            val mActivityThreadField = loadedApk.javaClass.getDeclaredField("mActivityThread")
            mActivityThreadField.isAccessible = true
            val activityThread = mActivityThreadField[loadedApk]
            val getProcessNameMethod = activityThread.javaClass.getDeclaredMethod("getProcessName")
            processName = getProcessNameMethod.invoke(activityThread) as String
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return processName
    }
}