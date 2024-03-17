package com.mozhimen.basick.utilk.android.content

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import androidx.annotation.IdRes
import com.mozhimen.basick.elemk.android.content.cons.CApplicationInfo
import com.mozhimen.basick.utilk.commons.IUtilK

/**
 * @ClassName UtilKApplicationInfo
 * @Description TODO
 * @Author Mozhimen & Kolin
 * @Date 2023/4/18 11:24
 * @Version 1.0
 */
object UtilKApplicationInfo : IUtilK {

    @JvmStatic
    fun getContext(context: Context): ApplicationInfo =
        UtilKContext.getApplicationInfo(context)

    @JvmStatic
    fun getPackInfo(context: Context): ApplicationInfo? =
        UtilKPackageInfo.getApplicationInfo(context)

    @JvmStatic
    fun getPackMgr(context: Context, strPackageName: String, flags: Int): ApplicationInfo =
        UtilKPackageManager.getApplicationInfo(context, strPackageName, flags)

    //////////////////////////////////////////////////////////////////////

    //得到包名
    @JvmStatic
    fun getPackageName(context: Context): String =
        getPackageName(getContext(context))

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
        getContext(context).targetSdkVersion

    @JvmStatic
    @IdRes
    fun getLabelRes(context: Context): Int =
        getContext(context).labelRes

    @JvmStatic
    fun getLabelResStr(context: Context): String =
        getLabelRes(context).let { UtilKRes.getString_ofContext(context, it) }

    @JvmStatic
    fun getFlags(context: Context): Int? =
        getPackInfo(context)?.let { getFlags(it) }

    @JvmStatic
    fun getFlags(applicationInfo: ApplicationInfo): Int =
        applicationInfo.flags

    @JvmStatic
    fun getIcon(context: Context): Int =
        getIcon(getContext(context))

    @JvmStatic
    fun getIcon(applicationInfo: ApplicationInfo): Int =
        applicationInfo.icon

    ////////////////////////////////////////////////////////////////////

    /**
     * 和这个方法一样[UtilKPackageManager.getApplicationIcon]
     */
    @JvmStatic
    fun loadIcon(context: Context, packageManager: PackageManager): Drawable? =
        loadIcon(getPackInfo(context), packageManager)

    /**
     * 和这个方法一样[UtilKPackageManager.getApplicationIcon]
     */
    @JvmStatic
    fun loadIcon(applicationInfo: ApplicationInfo?, packageManager: PackageManager): Drawable? =
        applicationInfo?.loadIcon(packageManager)

    ////////////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun isSystemApp(context: Context): Boolean =
        getPackInfo(context)?.let { isSystemApp(it) } ?: false.also { UtilKLogWrapper.dt(TAG, "isSystemApp: getApplicationInfo fail") }

    @JvmStatic
    fun isSystemUpdateApp(context: Context): Boolean =
        getPackInfo(context)?.let { isSystemUpdateApp(it) } ?: false.also { UtilKLogWrapper.dt(TAG, "isSystemUpdateApp: getApplicationInfo fail") }

    @JvmStatic
    fun isSystemOrSystemUpdateApp(context: Context): Boolean =
        getPackInfo(context)?.let { isSystemOrSystemUpdateApp(it) } ?: false.also { UtilKLogWrapper.dt(TAG, "isSystemOrSystemUpdateApp: getApplicationInfo fail") }

    @JvmStatic
    fun isUserApp(context: Context): Boolean =
        getPackInfo(context)?.let { isUserApp(it) } ?: false.also { UtilKLogWrapper.dt(TAG, "isUserApp: getApplicationInfo fail") }

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

    ////////////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun enabled(context: Context, strPackageName: String, flags: Int): Boolean =
        enabled(getPackMgr(context, strPackageName, flags))

    @JvmStatic
    fun enabled(applicationInfo: ApplicationInfo): Boolean =
        applicationInfo.enabled
}