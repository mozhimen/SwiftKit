package com.mozhimen.debugk.global

import android.content.Context
import com.mozhimen.underlayk.fpsk.FpsK
import com.mozhimen.basick.utilk.UtilKNet
import com.mozhimen.basick.utilk.UtilKSkip.start
import com.mozhimen.basick.utilk.UtilKTheme
import com.mozhimen.debugk.global.annors.DebugKToolAnnor
import com.mozhimen.debugk.global.uis.DebugKCrashKActivity
import com.mozhimen.debugk.global.uis.DebugKLogKActivity
import com.mozhimen.debugk.global.uis.DebugKParamsActivity

/**
 * @ClassName DebugKTools
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/1/18 15:12
 * @Version 1.0
 */
class DebugKTools {

    @DebugKToolAnnor("开启Https降级", "降级成Http,可以使用抓包工具,明文抓包")
    fun degrade2Http(context: Context) {
        UtilKNet.degrade2Http()
    }

    @DebugKToolAnnor("查看本地参数", "查看构建参数,设备参数,硬件参数等")
    fun checkDeviceParams(context: Context) {
        start<DebugKParamsActivity>(context)
    }

    @DebugKToolAnnor("查看CrashK日志", "可以一键分享给研发,迅速定位偶现问题")
    fun toggleCrash(context: Context) {
        start<DebugKCrashKActivity>(context)
    }

    @DebugKToolAnnor("查看LogK日志", "可以一键分享给研发,迅速定位偶现问题")
    fun toggleLog(context: Context) {
        start<DebugKLogKActivity>(context)
    }

    @DebugKToolAnnor("打开/关闭Fps", desc = "打开后可以查看页面实时的FPS")
    fun toggleFps(context: Context) {
        FpsK.instance.toggleView()
    }

    @DebugKToolAnnor("打开/关闭暗黑模式", desc = "打开暗黑模式在夜间使用更温和")
    fun toggleTheme(context: Context) {
        UtilKTheme.toggleMode()
    }
}