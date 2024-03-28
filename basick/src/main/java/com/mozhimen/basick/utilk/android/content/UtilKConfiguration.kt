package com.mozhimen.basick.utilk.android.content

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import com.mozhimen.basick.elemk.android.content.cons.CConfiguration
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import java.util.Locale


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
    fun get_ofSys(): Configuration =
        UtilKResources.getConfiguration_ofSys()

    @JvmStatic
    fun get_ofApp(context: Context): Configuration =
        UtilKResources.getConfiguration_ofApp(context)

    //////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getUiMode_ofSys(): Int =
        get_ofSys().uiMode

    @JvmStatic
    fun getScreenLayout_ofSys(): Int =
        get_ofSys().screenLayout

    /**
     * 获取密度dp
     */
    @JvmStatic
    fun getDensityDpi_ofSys(): Int =
        get_ofSys().densityDpi

    /**
     * 获取dp宽
     */
    @JvmStatic
    fun getScreenWidthDp_ofSys(): Int =
        get_ofSys().screenWidthDp

    /**
     * 获取dp高
     */
    @JvmStatic
    fun getScreenHeightDp_ofSys(): Int =
        get_ofSys().screenHeightDp

    /**
     * 获取屏幕方向
     */
    @JvmStatic
    fun getOrientation_ofSys(): Int =
        get_ofSys().orientation

    @JvmStatic
    fun getUiModeAndNightMask_ofSys(): Int =
        getUiMode_ofSys() and CConfiguration.UiMode.NIGHT_MASK

    /**
     * 检测系统是否是浅色主题
     */
    @JvmStatic
    fun isUiModelLight_ofSys(): Boolean =
        getUiModeAndNightMask_ofSys() == CConfiguration.UiMode.NIGHT_NO

    @JvmStatic
    fun isUiModeDark_ofSys(): Boolean =
        getUiModeAndNightMask_ofSys() == CConfiguration.UiMode.NIGHT_YES

    /**
     * 是否为竖屏
     */
    @JvmStatic
    fun isOrientationPortrait_ofSys(): Boolean =
        getOrientation_ofSys() == CConfiguration.Orientation.PORTRAIT

    @JvmStatic
    fun isOrientationLandscape_ofSys(): Boolean =
        getOrientation_ofSys() == CConfiguration.Orientation.LANDSCAPE

    ////////////////////////////////////////////////////////////////////

    /**
     * 获取自定义Configuration
     */
    @JvmStatic
    fun get_ofApp(resources: Resources): Configuration =
        UtilKResources.getConfiguration(resources)

    @JvmStatic
    fun getOrientation_ofApp(resources: Resources): Int =
        get_ofApp(resources).orientation

    @JvmStatic
    fun isOrientationPortrait_ofApp(resources: Resources): Boolean =
        getOrientation_ofApp(resources) == CConfiguration.Orientation.PORTRAIT

    @JvmStatic
    fun isOrientationLandscape_ofApp(resources: Resources): Boolean =
        getOrientation_ofApp(resources) == CConfiguration.Orientation.LANDSCAPE

    ////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getUiMode_ofApp(resources: Resources): Int =
        get_ofApp(resources).uiMode

    @JvmStatic
    fun getUiMode_ofApp(context: Context): Int =
        get_ofApp(context).uiMode

    @JvmStatic
    fun isLightMode_ofApp(context: Context): Boolean =
        !isNightMode_ofApp(context)

    @JvmStatic
    fun isNightMode_ofApp(context: Context): Boolean =
        getUiMode_ofApp(context) and Configuration.UI_MODE_NIGHT_MASK == CConfiguration.UiMode.NIGHT_YES

    ////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun setLocale(configuration: Configuration, locale: Locale) {
        if (UtilKBuildVersion.isAfterV_17_42_J1()) {
            configuration.setLocale(locale)
        } else {
            configuration.locale = locale
        }
    }
}