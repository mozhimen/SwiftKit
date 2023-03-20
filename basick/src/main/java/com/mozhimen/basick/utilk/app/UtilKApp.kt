package com.mozhimen.basick.utilk.app

import android.content.Intent
import android.os.Process
import android.util.Log
import com.mozhimen.basick.utilk.content.*
import com.mozhimen.basick.utilk.content.UtilKContextStart
import com.mozhimen.basick.utilk.content.pm.UtilKPackageInfo
import com.mozhimen.basick.utilk.os.UtilKSystemProperties
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
        UtilKSystemProperties.setSystemProperties(PKG_POWER, "reboot")
    }

    /**
     * 是否自启动
     * @return Boolean
     */
    @JvmStatic
    fun isAutoRun(): Boolean =
        UtilKSystemProperties.getSystemPropertiesBool(PKG_AUTO_RUN, false)

    /**
     * 重启App
     * @param isKillProcess Boolean
     */
    @JvmStatic
    fun restartApp(isKillProcess: Boolean, isValid: Boolean = true) {
        val intent: Intent? = UtilKIntent.getLauncherActivity(UtilKContext.getPackageName(_context))
        if (intent == null) {
            Log.e(TAG, "didn't exist launcher activity.")
            return
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        UtilKContextStart.start(_context, intent)
        if (!isKillProcess) return
        exitApp(isValid)
    }

    /**
     * 退出App
     */
    @JvmStatic
    fun exitApp(isValid: Boolean = true) {
        Process.killProcess(Process.myPid())//杀掉当前进程,并主动启动新的启动页,以完成重启的动作
        exitProcess(if (isValid) 0 else 10)
    }

    /**
     * isSystemApp
     * @return Boolean
     */
    @JvmStatic
    fun isSystemApp(): Boolean =
        UtilKPackageInfo.isSystemApp()

    /**
     * isSystemUpdateApp
     * @return Boolean
     */
    @JvmStatic
    fun isSystemUpdateApp(): Boolean =
        UtilKPackageInfo.isSystemUpdateApp()

    /**
     * isUserApp
     * @return Boolean
     */
    @JvmStatic
    fun isUserApp(): Boolean =
        UtilKPackageInfo.isUserApp()
}