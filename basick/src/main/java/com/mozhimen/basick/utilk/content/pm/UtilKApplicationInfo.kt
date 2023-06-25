package com.mozhimen.basick.utilk.content.pm

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import java.lang.IllegalArgumentException

/**
 * @ClassName UtilKApplicationInfo
 * @Description TODO
 * @Author Mozhimen & Kolin
 * @Date 2023/4/18 11:24
 * @Version 1.0
 */
object UtilKApplicationInfo {

    @JvmStatic
    fun get(context: Context): ApplicationInfo? =
        UtilKPackageInfo.getApplicationInfo(context)

    @JvmStatic
    fun get(packageInfo: PackageInfo): ApplicationInfo =
        packageInfo.applicationInfo

    ////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getFlags(context: Context): Int? =
        get(context)?.flags

    /**
     * 和这个方法一样
     * @see UtilKPackageManager.getApplicationIcon
     */
    @JvmStatic
    fun loadIcon(context: Context, packageManager: PackageManager): Drawable? =
        get(context)?.loadIcon(packageManager)

    @JvmStatic
    fun loadIcon(applicationInfo: ApplicationInfo, packageManager: PackageManager): Drawable =
        applicationInfo.loadIcon(packageManager)

    /**
     * 得到包名
     */
    @JvmStatic
    fun getPackageName(context: Context): String? =
        get(context)?.packageName

    @JvmStatic
    fun getPackageName(applicationInfo: ApplicationInfo): String =
        applicationInfo.packageName

    /**
     * app的目标版本
     * @param context Context
     * @return Int
     */
    @JvmStatic
    fun getTargetSdkVersion(context: Context): Int? =
        get(context)?.targetSdkVersion

    ////////////////////////////////////////////////////////////////////

    @JvmStatic
    @Throws(IllegalArgumentException::class)
    fun isSystemApp(context: Context): Boolean {
        val flags = getFlags(context)
        requireNotNull(flags)
        return (flags and ApplicationInfo.FLAG_SYSTEM) != 0
    }

    @JvmStatic
    @Throws(IllegalArgumentException::class)
    fun isSystemUpdateApp(context: Context): Boolean {
        val flags = getFlags(context)
        requireNotNull(flags)
        return (flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0
    }

    @JvmStatic
    @Throws(IllegalArgumentException::class)
    fun isUserApp(context: Context): Boolean =
        !isSystemApp(context) && !isSystemUpdateApp(context)
}