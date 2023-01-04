package com.mozhimen.basick.utilk

import android.Manifest
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Process
import android.util.Log
import com.mozhimen.basick.permissionk.annors.APermissionK
import com.mozhimen.basick.utilk.context.UtilKApplication
import kotlin.system.exitProcess


/**
 * @ClassName UtilKActivity
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/26 16:42
 * @Version 1.0
 */
object UtilKApp {
    private const val PKG_AUTO_RUN = "persist.sensepass.autorun"
    private const val PKG_POWER = "sys.powered"
    private const val TAG = "UtilKApp>>>>>"

    private val _context = UtilKApplication.instance.get()

    /**
     * 重启
     */
    @JvmStatic
    fun setReboot() {
        UtilKCmd.setSystemProperties(PKG_POWER, "reboot")
    }

    /**
     * 是否自启动
     * @return Boolean
     */
    @JvmStatic
    fun isAutoRun(): Boolean =
        UtilKCmd.getSystemPropertiesBool(PKG_AUTO_RUN, false)

    /**
     * 是否在主线程
     * @param app Application
     * @return Boolean
     */
    @JvmStatic
    fun isMainProcess(app: Application): Boolean {
        val activityManager = app.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningAppProcesses = activityManager.runningAppProcesses
        for (process in runningAppProcesses) {
            if (process.processName == app.packageName) {
                return true
            }
        }
        return false
    }

    /**
     * 重启APP
     * @param isValid Boolean
     */
    @JvmStatic
    fun restartAppWithStatus(isValid: Boolean = true) {
        val intent = _context.packageManager.getLaunchIntentForPackage(_context.packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        _context.startActivity(intent)

        Process.killProcess(Process.myPid())
        exitProcess(if (isValid) 0 else 10)
    }

    /**
     * 重启App
     * @param isKillProcess Boolean
     */
    @JvmStatic
    fun restartApp(isKillProcess: Boolean) {
        val intent: Intent? = UtilKIntent.getLaunchAppIntent(_context.packageName)
        if (intent == null) {
            Log.e(TAG, "Didn't exist launcher activity.")
            return
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        _context.startActivity(intent)
        if (!isKillProcess) return
        Process.killProcess(Process.myPid())
        exitProcess(0)
    }

    /**
     * 判断手机是否拥有Root权限:
     * 有root权限返回true, 否则返回false
     * @return Boolean
     */
    @JvmStatic
    @APermissionK(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun isRoot(): Boolean {
        var isRoot = false
        try {
            isRoot = !UtilKFile.isFileExist("/system/bin/su") || !UtilKFile.isFileExist("/system/xbin/su")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return isRoot
    }

    /**
     * isSystemApp
     * @return Boolean
     */
    @JvmStatic
    fun isSystemApp(): Boolean =
        (UtilKPackage.getPackageInfo().applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM) != 0

    /**
     * isSystemUpdateApp
     * @return Boolean
     */
    @JvmStatic
    fun isSystemUpdateApp(): Boolean =
        (UtilKPackage.getPackageInfo().applicationInfo.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0

    /**
     * isUserApp
     * @return Boolean
     */
    @JvmStatic
    fun isUserApp():Boolean =
        !isSystemApp() && !isSystemUpdateApp()
}