package com.mozhimen.basick.utilk

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Process
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
    private const val PKG_POWER = "sys.powerctl"

    private val _context = UtilKGlobal.instance.getApp()!!

    //重启
    fun setReboot() {
        UtilKCmd.setSystemProperties(PKG_POWER, "reboot")
    }

    //是否自启动
    fun isAutoRun(): Boolean =
        UtilKCmd.getSystemPropertiesBool(PKG_AUTO_RUN, false)

    //是否在主线程
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

    //重启APP
    fun restartApp(isValid: Boolean = true) {
        val intent = _context.packageManager.getLaunchIntentForPackage(_context.packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        _context.startActivity(intent)

        Process.killProcess(Process.myPid())
        exitProcess(if (isValid) 0 else 10)
    }
}