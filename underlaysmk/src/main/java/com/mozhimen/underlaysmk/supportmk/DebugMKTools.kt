package com.mozhimen.underlaysmk.supportmk

import android.content.Intent
import com.mozhimen.basicsmk.utilmk.UtilMKGlobal
import com.mozhimen.basicsmk.utilmk.UtilMKSP
import com.mozhimen.underlaysmk.BuildConfig

/**
 * @ClassName DebugMKTools
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/1/18 15:12
 * @Version 1.0
 */
object DebugMKTools {
    fun buildTime(): String {
        return "构建时间: " + BuildConfig.BUILD_TIME
    }

    private const val SP_NAME = "sp_debugmk"
    private const val DEGRADE_HTTP = "debugmk_degrade_http"

    @DebugMK("一键开启Https降级", "降级成Http,可以使用抓包工具,明文抓包")
    fun degrade2Http() {
        UtilMKSP.instance.with(SP_NAME).setBoolean(DEGRADE_HTTP, true)
        val context = UtilMKGlobal.instance.getApp() ?: return
        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)

        //杀掉当前进程,并主动启动新的启动页,以完成重启的动作
    }

    @DebugMK("查看Crash日志", "可以一键分享给开发同学,迅速定位偶现问题")
    fun crashLog() {

    }
}