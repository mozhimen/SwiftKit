package com.mozhimen.basick.utilk.androidx.appcompat

import androidx.appcompat.app.AppCompatDelegate
import com.mozhimen.basick.elemk.androidx.appcompat.cons.CAppCompatDelegate

/**
 * @ClassName UtilKAppCompatDelegate
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/30 16:21
 * @Version 1.0
 */
object UtilKAppCompatDelegate {
    @JvmStatic
    fun getDefaultNightMode(): Int =
            AppCompatDelegate.getDefaultNightMode()

    ////////////////////////////////////////////////////////////////

    /**
     * 检测是否是浅色主题
     * @return Boolean
     */
    @JvmStatic
    fun isLightMode(): Boolean =
            getDefaultNightMode() == CAppCompatDelegate.MODE_NIGHT_NO

    @JvmStatic
    fun isDarkMode(): Boolean =
            getDefaultNightMode() == CAppCompatDelegate.MODE_NIGHT_YES

    ////////////////////////////////////////////////////////////////

    @JvmStatic
    fun applyDefaultNightMode(mode: Int) {
        AppCompatDelegate.setDefaultNightMode(mode)
    }

    @JvmStatic
    fun applyLightMode() {
        if (isLightMode()) return
        applyDefaultNightMode(CAppCompatDelegate.MODE_NIGHT_NO)
    }

    @JvmStatic
    fun applyDarkMode() {
        if (isDarkMode()) return
        applyDefaultNightMode(CAppCompatDelegate.MODE_NIGHT_YES)
    }

    @JvmStatic
    fun toggleMode() {
        if (isLightMode()) applyDarkMode() else applyLightMode()
    }
}