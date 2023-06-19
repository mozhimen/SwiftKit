package com.mozhimen.basick.utilk.content

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import com.mozhimen.basick.utilk.content.pm.UtilKPackageInfo

/**
 * @ClassName UtilKApplicationInfo
 * @Description TODO
 * @Author Mozhimen & Kolin
 * @Date 2023/4/18 11:24
 * @Version 1.0
 */
object UtilKApplicationInfo {
    @JvmStatic
    fun get(packageInfo: PackageInfo): ApplicationInfo =
        packageInfo.applicationInfo

    /**
     * 获取ApplicationInfo
     * @return ApplicationInfo
     */
    @JvmStatic
    fun get(context: Context): ApplicationInfo =
        UtilKPackageInfo.get(context).applicationInfo

    /**
     * 和这个方法一样
     * @see UtilKPackageManager.getApplicationLabel
     * @param applicationInfo ApplicationInfo
     * @param packageManager PackageManager
     * @return Drawable
     */
    @JvmStatic
    fun loadIcon(applicationInfo: ApplicationInfo, packageManager: PackageManager): Drawable =
        applicationInfo.loadIcon(packageManager)

    /**
     * 得到包名
     * @param applicationInfo ApplicationInfo
     * @return String
     */
    @JvmStatic
    fun getPackageName(applicationInfo: ApplicationInfo): String =
        applicationInfo.packageName

    /**
     * app的目标版本
     * @param context Context
     * @return Int
     */
    @JvmStatic
    fun getTargetSdkVersion(context: Context): Int =
        get(context).targetSdkVersion
}