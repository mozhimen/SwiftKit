package com.mozhimen.basick.utilk.android.content

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import androidx.annotation.IdRes
import com.mozhimen.basick.elemk.android.content.cons.CApplicationInfo
import com.mozhimen.basick.utilk.commons.IUtilK
import com.mozhimen.basick.utilk.wrapper.UtilKRes

/**
 * @ClassName UtilKApplicationInfo
 * @Description TODO
 * @Author Mozhimen & Kolin
 * @Date 2023/4/18 11:24
 * @Version 1.0
 */
object UtilKApplicationInfo : IUtilK {

    @JvmStatic
    fun get_ofCxt(context: Context): ApplicationInfo =
        UtilKContext.getApplicationInfo(context)

    @JvmStatic
    fun get_ofPkI(context: Context): ApplicationInfo? =
        UtilKPackageInfo.getApplicationInfo(context)

    @JvmStatic
    fun get_ofPkM(context: Context, strPackageName: String, flags: Int): ApplicationInfo =
        UtilKPackageManager.getApplicationInfo(context, strPackageName, flags)

    //////////////////////////////////////////////////////////////////////

    //得到包名
    @JvmStatic
    fun getPackageName(applicationInfo: ApplicationInfo): String =
        applicationInfo.packageName

    @JvmStatic
    fun getFlags(applicationInfo: ApplicationInfo): Int =
        applicationInfo.flags

    @JvmStatic
    fun getIcon(applicationInfo: ApplicationInfo): Int =
        applicationInfo.icon

    /**
     * 和这个方法一样[UtilKPackageManager.getApplicationIcon]
     */
    @JvmStatic
    fun loadIcon(applicationInfo: ApplicationInfo?, packageManager: PackageManager): Drawable? =
        applicationInfo?.loadIcon(packageManager)

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

    @JvmStatic
    fun enabled(applicationInfo: ApplicationInfo): Boolean =
        applicationInfo.enabled

    //////////////////////////////////////////////////////////////////////

    //得到包名
    @JvmStatic
    fun getPackageName_ofCxt(context: Context): String =
        getPackageName(get_ofCxt(context))

    //app的目标版本
    @JvmStatic
    fun getTargetSdkVersion_ofCxt(context: Context): Int =
        get_ofCxt(context).targetSdkVersion

    @JvmStatic
    @IdRes
    fun getLabelRes_ofCxt(context: Context): Int =
        get_ofCxt(context).labelRes

    @JvmStatic
    fun getLabelResStr_ofCxt(context: Context): String =
        getLabelRes_ofCxt(context).let { UtilKRes.getString_ofContext(context, it) }

    @JvmStatic
    fun getIcon_ofCxt(context: Context): Int =
        getIcon(get_ofCxt(context))

    ////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getFlags_ofPkI(context: Context): Int? =
        get_ofPkI(context)?.let { getFlags(it) }

    /**
     * 和这个方法一样[UtilKPackageManager.getApplicationIcon]
     */
    @JvmStatic
    fun loadIcon_ofPkI(context: Context, packageManager: PackageManager): Drawable? =
        loadIcon(get_ofPkI(context), packageManager)

    @JvmStatic
    fun isSystemApp_ofPkI(context: Context): Boolean =
        get_ofPkI(context)?.let { isSystemApp(it) } ?: false.also { UtilKLogWrapper.d(TAG, "isSystemApp: getApplicationInfo fail") }

    @JvmStatic
    fun isSystemUpdateApp_ofPkI(context: Context): Boolean =
        get_ofPkI(context)?.let { isSystemUpdateApp(it) } ?: false.also { UtilKLogWrapper.d(TAG, "isSystemUpdateApp: getApplicationInfo fail") }

    @JvmStatic
    fun isSystemOrSystemUpdateApp_ofPkI(context: Context): Boolean =
        get_ofPkI(context)?.let { isSystemOrSystemUpdateApp(it) } ?: false.also { UtilKLogWrapper.d(TAG, "isSystemOrSystemUpdateApp: getApplicationInfo fail") }

    @JvmStatic
    fun isUserApp_ofPkI(context: Context): Boolean =
        get_ofPkI(context)?.let { isUserApp(it) } ?: false.also { UtilKLogWrapper.d(TAG, "isUserApp: getApplicationInfo fail") }

    ////////////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun enabled_ofPkM(context: Context, strPackageName: String, flags: Int): Boolean =
        enabled(get_ofPkM(context, strPackageName, flags))
}