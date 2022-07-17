package com.mozhimen.basick.utilk

import android.content.Context
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
    fun isLightMode(): Boolean =
        AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO

    /**
     * 检测系统是否是浅色主题
     * @return Boolean
     */
    fun isOSLightMode(): Boolean =
        (UtilKGlobal.instance.getApp()!!.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_NO

    /**
     * 改变主题
     */
    fun toggleMode() {
        if (isLightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}