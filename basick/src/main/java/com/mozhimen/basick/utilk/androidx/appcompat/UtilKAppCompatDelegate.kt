package com.mozhimen.basick.utilk.androidx.appcompat

import androidx.appcompat.app.AppCompatDelegate
import com.mozhimen.basick.elemk.cons.CAppComDeleg

/**
 * @ClassName UtilKAppCompatDelegate
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/30 16:21
 * @Version 1.0
 */
object UtilKAppCompatDelegate {
    @JvmStatic
    fun setDefaultNightMode(mode: Int) {
        AppCompatDelegate.setDefaultNightMode(mode)
    }

    @JvmStatic
    fun getDefaultNightMode(): Int =
        AppCompatDelegate.getDefaultNightMode()

    /**
     * 检测是否是浅色主题
     * @return Boolean
     */
    @JvmStatic
    fun isLightMode(): Boolean =
        getDefaultNightMode() == CAppComDeleg.MODE_NIGHT_NO

    @JvmStatic
    fun isDarkMode(): Boolean =
        getDefaultNightMode() == CAppComDeleg.MODE_NIGHT_YES

    @JvmStatic
    fun setLightMode() {
        if (isLightMode()) return
        setDefaultNightMode(CAppComDeleg.MODE_NIGHT_NO)
    }

    @JvmStatic
    fun setDarkMode() {
        if (isDarkMode()) return
        setDefaultNightMode(CAppComDeleg.MODE_NIGHT_YES)
    }

    /**
     * 改变主题
     */
    @JvmStatic
    fun toggleMode() {
        if (isLightMode()) setDarkMode() else setLightMode()
    }
}