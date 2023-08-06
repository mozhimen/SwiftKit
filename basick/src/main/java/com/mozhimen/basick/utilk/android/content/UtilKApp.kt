package com.mozhimen.basick.utilk.android.content

import android.content.Context
import android.content.Intent
import android.os.Process
import android.util.Log
import com.mozhimen.basick.elemk.android.content.cons.CIntent
import com.mozhimen.basick.utilk.bases.BaseUtilK
import java.lang.IllegalArgumentException
import kotlin.system.exitProcess


/**
 * @ClassName UtilKActivity
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/26 16:42
 * @Version 1.0
 */
object UtilKApp : BaseUtilK() {
    /**
     * 重启App
     * @param isKillProcess Boolean
     */
    @JvmStatic
    fun restartApp(isKillProcess: Boolean, isValid: Boolean = true, context: Context = _context) {
        val intent: Intent = UtilKIntent.getLauncherActivity(context, UtilKContext.getPackageName(context)) ?: run {
            Log.e(TAG, "didn't exist launcher activity.");return
        }
        intent.addFlags(CIntent.FLAG_ACTIVITY_CLEAR_TOP or CIntent.FLAG_ACTIVITY_CLEAR_TASK)
        UtilKContextStart.startContext(context, intent)
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

    /////////////////////////////////////////////////////////////////////////////

    /**
     * isSystemApp
     * @return Boolean
     */
    @JvmStatic
    @Throws(IllegalArgumentException::class)
    fun isSystemApp(context: Context): Boolean =
        UtilKApplicationInfo.isSystemApp(context)

    /**
     * isSystemUpdateApp
     * @return Boolean
     */
    @JvmStatic
    fun isSystemUpdateApp(context: Context): Boolean =
        UtilKApplicationInfo.isSystemUpdateApp(context)

    /**
     * isUserApp
     * @return Boolean
     */
    @JvmStatic
    fun isUserApp(context: Context): Boolean =
        UtilKApplicationInfo.isUserApp(context)
}