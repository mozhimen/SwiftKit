package com.mozhimen.basick.utilk.android.content

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import com.mozhimen.basick.elemk.android.content.cons.CConfiguration
import com.mozhimen.basick.elemk.androidx.appcompat.cons.CAppCompatDelegate


/**
 * @ClassName UtilKConfiguration
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/17 17:21
 * @Version 1.0
 */
object UtilKConfiguration {
    /**
     * 获取系统Configuration
     */
    @JvmStatic
    fun getSys(): Configuration =
        UtilKResources.getSysConfiguration()

    @JvmStatic
    fun getApp(context: Context): Configuration =
        UtilKResources.getAppConfiguration(context)

    //////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getSysUiMode(): Int =
        getSys().uiMode

    @JvmStatic
    fun getSysScreenLayout(): Int =
        getSys().screenLayout

    /**
     * 获取密度dp
     */
    @JvmStatic
    fun getSysDensityDpi(): Int =
        getSys().densityDpi

    /**
     * 获取dp宽
     */
    @JvmStatic
    fun getSysScreenWidthDp(): Int =
        getSys().screenWidthDp

    /**
     * 获取dp高
     */
    @JvmStatic
    fun getSysScreenHeightDp(): Int =
        getSys().screenHeightDp

    /**
     * 获取屏幕方向
     */
    @JvmStatic
    fun getSysOrientation(): Int =
        getSys().orientation

    @JvmStatic
    fun getSysUiModeAndNightMask(): Int =
        getSysUiMode() and CConfiguration.UiMode.NIGHT_MASK

    /**
     * 检测系统是否是浅色主题
     */
    @JvmStatic
    fun isSysLightMode(): Boolean =
        getSysUiModeAndNightMask() == CConfiguration.UiMode.NIGHT_NO

    @JvmStatic
    fun isSysDarkMode(): Boolean =
        getSysUiModeAndNightMask() == CConfiguration.UiMode.NIGHT_YES

    /**
     * 是否为竖屏
     */
    @JvmStatic
    fun isSysOrientationPortrait(): Boolean =
        getSysOrientation() == CConfiguration.Orientation.PORTRAIT

    @JvmStatic
    fun isSysOrientationLandscape(): Boolean =
        getSysOrientation() == CConfiguration.Orientation.LANDSCAPE

    ////////////////////////////////////////////////////////////////////

    /**
     * 获取自定义Configuration
     */
    @JvmStatic
    fun getApp(resources: Resources): Configuration =
        UtilKResources.getConfiguration(resources)

    @JvmStatic
    fun getAppOrientation(resources: Resources): Int =
        getApp(resources).orientation

    @JvmStatic
    fun isAppOrientationPortrait(resources: Resources): Boolean =
        getAppOrientation(resources) == CConfiguration.Orientation.PORTRAIT

    @JvmStatic
    fun isAppOrientationLandscape(resources: Resources): Boolean =
        getAppOrientation(resources) == CConfiguration.Orientation.LANDSCAPE

    ////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getAppUiMode(resources: Resources): Int =
        getApp(resources).uiMode

    @JvmStatic
    fun getAppUiMode(context: Context): Int =
        getApp(context).uiMode

    @JvmStatic
    fun isLightMode(context: Context): Boolean =
        !isNightMode(context)

    @JvmStatic
    fun isNightMode(context: Context): Boolean =
        getAppUiMode(context) and Configuration.UI_MODE_NIGHT_MASK == CConfiguration.UiMode.NIGHT_YES
}