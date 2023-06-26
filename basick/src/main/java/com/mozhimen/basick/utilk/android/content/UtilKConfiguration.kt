package com.mozhimen.basick.utilk.android.content

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import com.mozhimen.basick.utilk.android.telephony.UtilKTelephonyManager


/**
 * @ClassName UtilKConfiguration
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/17 17:21
 * @Version 1.0
 */
object UtilKConfiguration {
    /**
     * 获取自定义Configuration
     * @return Configuration
     */
    @JvmStatic
    fun get(resources: Resources): Configuration =
        resources.configuration

    /**
     * 获取系统Configuration
     * @return Configuration
     */
    @JvmStatic
    fun get(): Configuration =
        UtilKResource.getSystemConfiguration()

    /**
     * 获取uiMode
     * @return Int
     */
    @JvmStatic
    fun getUiMode(): Int =
        get().uiMode

    /**
     * 获取screenLayout
     * @return Int
     */
    @JvmStatic
    fun getScreenLayout(): Int =
        get().screenLayout

    /**
     * 获取密度dp
     * @return Int
     */
    @JvmStatic
    fun getDensityDpi(): Int =
        get().densityDpi

    /**
     * 获取dp宽
     * @return Int
     */
    @JvmStatic
    fun getScreenWidthDp(): Int =
        get().screenWidthDp

    /**
     * 获取dp高
     * @return Int
     */
    @JvmStatic
    fun getScreenHeightDp(): Int =
        get().screenHeightDp

    /**
     * 获取屏幕方向
     * @return Int
     */
    @JvmStatic
    fun getOrientation(): Int =
        get().orientation

    /**
     * 是否为竖屏
     * @return Boolean
     */
    @JvmStatic
    fun isOrientationPortrait(): Boolean =
        getOrientation() == Configuration.ORIENTATION_PORTRAIT

    /**
     * 是否是平板
     * @return Boolean
     */
    @JvmStatic
    fun isPad(context: Context): Boolean =
        if (UtilKTelephonyManager.isHasTelephone(context)) {        //如果能打电话那可能是平板或手机，再根据配置判断
            //能打电话可能是手机也可能是平板
            (getScreenLayout() and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE)
        } else true //不能打电话一定是平板
}