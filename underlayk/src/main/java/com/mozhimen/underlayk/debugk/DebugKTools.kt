package com.mozhimen.underlayk.debugk

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Process
import androidx.appcompat.app.AppCompatDelegate
import com.mozhimen.basick.utilk.UtilKGlobal
import com.mozhimen.basick.cachek.CacheKSP
import com.mozhimen.basick.fpsk.FpsKMonitor
import com.mozhimen.underlayk.BuildConfig
import com.mozhimen.underlayk.debugk.annors.DebugKAnnor
import com.mozhimen.basick.utilk.UtilKSkip.start
import com.mozhimen.basick.utilk.UtilKString
import com.mozhimen.basick.utilk.UtilKTheme

/**
 * @ClassName DebugKTools
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/1/18 15:12
 * @Version 1.0
 */
class DebugKTools {
    companion object {
        const val DEBUGK_SP_NAME = "debugk_sp"
        const val DEBUGK_SP_DEGRADE_HTTP = "debugk_sp_degrade_http"
    }

    /**
     * 构建版本 ： 1.0.1
     * @return String
     */
    fun buildVersion(): String = "构建版本: code:" + BuildConfig.VERSION_CODE + " name:" + BuildConfig.VERSION_NAME

    fun buildEnvironment(): String = "构建环境: " + if (BuildConfig.DEBUG) "测试环境" else "正式环境"

    fun buildTime(): String = "构建时间: " + BuildConfig.BUILD_TIME

    /**
     * 设备信息: 品牌-sdk-abi
     * @return String
     */
    fun buildDevice(): String = "设备信息: brand:" + Build.BRAND + " sdk:" + Build.VERSION.SDK_INT + " abi:" + Build.CPU_ABI

    fun buildSupportABIs(): String = "支持ABIs: " + UtilKString.getStringList(Build.SUPPORTED_ABIS)

    @DebugKAnnor("一键开启Https降级", "降级成Http,可以使用抓包工具,明文抓包")
    fun degrade2Http() {
        CacheKSP.instance.with(DEBUGK_SP_NAME).putBoolean(DEBUGK_SP_DEGRADE_HTTP, true)
        val context = UtilKGlobal.instance.getApp() ?: return
        context.startActivity(context.packageManager.getLaunchIntentForPackage(context.packageName))

        //杀掉当前进程,并主动启动新的启动页,以完成重启的动作
        Process.killProcess(Process.myPid())
    }

    @DebugKAnnor("查看Crash日志", "可以一键分享给研发,迅速定位偶现问题")
    fun toggleCrash(context: Context) {
        start<DebugKCrashKActivity>(context)
    }

    @DebugKAnnor("查看关键日志", "可以一键分享给研发,迅速定位偶现问题")
    fun toggleLog(context: Context) {
        start<DebugKLogKActivity>(context)
    }

    @DebugKAnnor(name = "打开/关闭Fps", desc = "打开后可以查看页面实时的FPS")
    fun toggleFps(context: Context) {
        FpsKMonitor.toggle()
    }

    @DebugKAnnor(name = "打开/关闭暗黑模式", desc = "打开暗黑模式在夜间使用更温和")
    fun toggleTheme(context: Context) {
        if (UtilKTheme.isLightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}