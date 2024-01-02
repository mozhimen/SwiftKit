package com.mozhimen.basick.utilk.android.content

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.annotation.IdRes
import com.mozhimen.basick.elemk.android.content.cons.CApplicationInfo
import com.mozhimen.basick.utilk.bases.IUtilK

/**
 * @ClassName UtilKApplicationInfo
 * @Description TODO
 * @Author Mozhimen & Kolin
 * @Date 2023/4/18 11:24
 * @Version 1.0
 */
object UtilKApplicationInfo : IUtilK {

    @JvmStatic
    fun get(context: Context): ApplicationInfo =
        UtilKContext.getApplicationInfo(context)

    @JvmStatic
    fun getOfPackageInfo(context: Context): ApplicationInfo? =
        UtilKPackageInfo.getApplicationInfo(context)

    /**
     * 得到包名
     */
    @JvmStatic
    fun getPackageName(context: Context): String? =
        get(context).packageName

    /**
     * 得到包名
     */
    @JvmStatic
    fun getPackageName(applicationInfo: ApplicationInfo): String =
        applicationInfo.packageName

    /**
     * app的目标版本
     */
    @JvmStatic
    fun getTargetSdkVersion(context: Context): Int =
        get(context).targetSdkVersion

    @JvmStatic
    @IdRes
    fun getLabelRes(context: Context): Int =
        get(context).labelRes

    @JvmStatic
    fun getApplicationLabel(context: Context): String =
        getLabelRes(context).let { UtilKRes.getString(it) }

    @JvmStatic
    fun getFlags(context: Context): Int? =
        getOfPackageInfo(context)?.let { getFlags(it) }

    @JvmStatic
    fun getFlags(applicationInfo: ApplicationInfo): Int =
        applicationInfo.flags

    @JvmStatic
    fun getIcon(context: Context): Int =
        getIcon(get(context))

    @JvmStatic
    fun getIcon(applicationInfo: ApplicationInfo): Int =
        applicationInfo.icon

    ////////////////////////////////////////////////////////////////////

    /**
     * 和这个方法一样[UtilKPackageManager.getApplicationIcon]
     */
    @JvmStatic
    fun loadIcon(context: Context, packageManager: PackageManager): Drawable? =
        loadIcon(getOfPackageInfo(context), packageManager)

    /**
     * 和这个方法一样[UtilKPackageManager.getApplicationIcon]
     */
    @JvmStatic
    fun loadIcon(applicationInfo: ApplicationInfo?, packageManager: PackageManager): Drawable? =
        applicationInfo?.loadIcon(packageManager)

    ////////////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun isSystemApp(context: Context): Boolean =
        getOfPackageInfo(context)?.let { isSystemApp(it) } ?: false.also { Log.d(TAG, "isSystemApp: getApplicationInfo fail") }

    @JvmStatic
    fun isSystemUpdateApp(context: Context): Boolean =
        getOfPackageInfo(context)?.let { isSystemUpdateApp(it) } ?: false.also { Log.d(TAG, "isSystemUpdateApp: getApplicationInfo fail") }

    @JvmStatic
    fun isSystemOrSystemUpdateApp(context: Context): Boolean =
        getOfPackageInfo(context)?.let { isSystemOrSystemUpdateApp(it) } ?: false.also { Log.d(TAG, "isSystemOrSystemUpdateApp: getApplicationInfo fail") }

    @JvmStatic
    fun isUserApp(context: Context): Boolean =
        getOfPackageInfo(context)?.let { isUserApp(it) } ?: false.also { Log.d(TAG, "isUserApp: getApplicationInfo fail") }

    ////////////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun isSystemApp(applicationInfo: ApplicationInfo): Boolean =
        (getFlags(applicationInfo) and CApplicationInfo.FLAG_SYSTEM) != 0

    @JvmStatic
    fun isSystemUpdateApp(applicationInfo: ApplicationInfo): Boolean =
        (getFlags(applicationInfo) and CApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0

    @JvmStatic
    fun isSystemOrSystemUpdateApp(applicationInfo: ApplicationInfo): Boolean =
        isSystemApp(applicationInfo) || isSystemUpdateApp(applicationInfo)

    @JvmStatic
    fun isUserApp(applicationInfo: ApplicationInfo): Boolean =
        !isSystemApp(applicationInfo) && !isSystemUpdateApp(applicationInfo)
}