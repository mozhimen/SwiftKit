package com.mozhimen.basick.utilk.android.content

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import com.mozhimen.basick.elemk.android.content.cons.CApplicationInfo
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

    ////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getFlags(context: Context): Int? =
        get(context)?.flags

    /**
     * 和这个方法一样[UtilKPackageManager.getApplicationIcon]
     */
    @JvmStatic
    fun loadIcon(context: Context, packageManager: PackageManager): Drawable? =
        loadIcon(get(context), packageManager)

    @JvmStatic
    fun loadIcon(applicationInfo: ApplicationInfo?, packageManager: PackageManager): Drawable? =
        applicationInfo?.loadIcon(packageManager)

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
     */
    @JvmStatic
    fun getTargetSdkVersion(context: Context): Int? =
        get(context)?.targetSdkVersion

    ////////////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @Throws(IllegalArgumentException::class)
    fun isSystemApp(context: Context): Boolean {
        val flags = getFlags(context)
        requireNotNull(flags)
        return (flags and CApplicationInfo.FLAG_SYSTEM) != 0
    }

    @JvmStatic
    @Throws(IllegalArgumentException::class)
    fun isSystemUpdateApp(context: Context): Boolean {
        val flags = getFlags(context)
        requireNotNull(flags)
        return (flags and CApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0
    }

    @JvmStatic
    @Throws(IllegalArgumentException::class)
    fun isUserApp(context: Context): Boolean =
        !isSystemApp(context) && !isSystemUpdateApp(context)
}