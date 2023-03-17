package com.mozhimen.basick.utilk.res

import android.content.res.Configuration


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
     * @return Configuration
     */
    @JvmStatic
    fun getConfiguration(): Configuration =
        UtilKRes.getSystemResource().configuration

    /**
     * 获取uiMode
     * @return Int
     */
    @JvmStatic
    fun getUiMode(): Int =
        getConfiguration().uiMode

    /**
     * 获取screenLayout
     * @return Int
     */
    @JvmStatic
    fun getScreenLayout(): Int =
        getConfiguration().screenLayout

    /**
     * 获取密度dp
     * @return Int
     */
    @JvmStatic
    fun getDensityDpi(): Int =
        getConfiguration().densityDpi

    /**
     * 获取dp宽
     * @return Int
     */
    @JvmStatic
    fun getScreenWidthDp(): Int =
        getConfiguration().screenWidthDp

    /**
     * 获取dp高
     * @return Int
     */
    @JvmStatic
    fun getScreenHeightDp(): Int =
        getConfiguration().screenHeightDp

    /**
     * 获取屏幕方向
     * @return Int
     */
    @JvmStatic
    fun getOrientation(): Int =
        getConfiguration().orientation
}