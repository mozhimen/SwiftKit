package com.mozhimen.underlaysmk.supportmk

import com.mozhimen.underlaysmk.BuildConfig

/**
 * @ClassName DebugMKTools
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/1/18 15:12
 * @Version 1.0
 */
class DebugMKTools {
    fun buildTime(): String {
        return "构建时间: " + BuildConfig.BUILD_TIME
    }

    @DebugMK("一键开启Https降级", "降级成Http,可以使用抓包工具,明文抓包")
    fun degrade2Http() {
        return
    }
}