package com.mozhimen.debugk.base

import android.content.Context
import com.mozhimen.underlayk.fpsk.FpsK
import com.mozhimen.basick.utilk.net.UtilKNetConn
import com.mozhimen.basick.utilk.context.UtilKActivitySkip.start
import com.mozhimen.basick.utilk.UtilKTheme
import com.mozhimen.debugk.base.annors.ADebugKTool
import com.mozhimen.debugk.base.uis.DebugKCrashKActivity
import com.mozhimen.debugk.base.uis.DebugKLogKActivity
import com.mozhimen.debugk.base.uis.DebugKParamsActivity

/**
 * @ClassName DebugKTools
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/1/18 15:12
 * @Version 1.0
 */
class DebugKTools {

    @ADebugKTool("开启Https降级", "降级成Http,可以使用抓包工具,明文抓包")
    fun degrade2Http(context: Context) {
        UtilKNetConn.degrade2Http()
    }

    @ADebugKTool("查看本地参数", "查看构建参数,设备参数,硬件参数等")
    fun checkDeviceParams(context: Context) {
        start<DebugKParamsActivity>(context)
    }

    @ADebugKTool("查看CrashK日志", "可以一键分享给研发,迅速定位偶现问题")
    fun toggleCrash(context: Context) {
        start<DebugKCrashKActivity>(context)
    }

    @ADebugKTool("查看LogK日志", "可以一键分享给研发,迅速定位偶现问题")
    fun toggleLog(context: Context) {
        start<DebugKLogKActivity>(context)
    }

    @ADebugKTool("打开/关闭Fps", desc = "打开后可以查看页面实时的FPS")
    fun toggleFps(context: Context) {
        FpsK.instance.toggleView()
    }

    @ADebugKTool("打开/关闭暗黑模式", desc = "打开暗黑模式在夜间使用更温和")
    fun toggleTheme(context: Context) {
        UtilKTheme.toggleMode()
    }
}