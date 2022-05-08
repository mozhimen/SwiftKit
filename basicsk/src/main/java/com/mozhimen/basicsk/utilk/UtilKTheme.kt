package com.mozhimen.basicsk.utilk

import android.content.res.Configuration

/**
 * @ClassName UtilKTheme
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/1 21:06
 * @Version 1.0
 */
object UtilKTheme {
    /**
     * 检测是否是浅色主题
     * @return Boolean
     */
    fun isLightMode(): Boolean {
        val mode = UtilKGlobal.instance.getApp()!!.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return mode == Configuration.UI_MODE_NIGHT_NO
    }
}