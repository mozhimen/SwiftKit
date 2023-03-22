package com.mozhimen.basick.utilk.os

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import com.mozhimen.basick.utilk.res.UtilKConfiguration

/**
 * @ClassName UtilKTheme
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/1 21:06
 * @Version 1.0
 */
object UtilKUiMode {
    private val TAG = "UtilKUiMode>>>>>"

    /**
     * 检测是否是浅色主题
     * @return Boolean
     */
    @JvmStatic
    fun isLightMode(): Boolean =
        AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO

    /**
     * 检测系统是否是浅色主题
     * @return Boolean
     */
    @JvmStatic
    fun isOSLightMode(): Boolean =
        (UtilKConfiguration.getUiMode() and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_NO

    /**
     * 改变主题
     */
    @JvmStatic
    fun toggleMode() {
        if (isLightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}