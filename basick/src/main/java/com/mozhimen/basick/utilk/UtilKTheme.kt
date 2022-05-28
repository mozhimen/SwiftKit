package com.mozhimen.basick.utilk

import android.content.res.Configuration
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate

/**
 * @ClassName UtilKTheme
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/1 21:06
 * @Version 1.0
 */
object UtilKTheme {
    private val TAG = "UtilKTheme>>>>>"

    /**
     * 检测是否是浅色主题
     * @return Boolean
     */
    fun isLightMode(): Boolean = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO

    fun isOSLightMode(): Boolean {
        val mode: Int = UtilKGlobal.instance.getApp()!!.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return mode == Configuration.UI_MODE_NIGHT_NO
    }
}