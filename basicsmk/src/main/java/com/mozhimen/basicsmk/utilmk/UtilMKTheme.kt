package com.mozhimen.basicsmk.utilmk

import android.content.res.Configuration

/**
 * @ClassName UtilMKTheme
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/1 21:06
 * @Version 1.0
 */
object UtilMKTheme {
    //检测是否是浅色主题
    fun isLightMode(): Boolean {
        val mode = UtilMKGlobal.instance.getApp()!!.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return mode == Configuration.UI_MODE_NIGHT_NO
    }
}