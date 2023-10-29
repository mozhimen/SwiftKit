package com.mozhimen.basick.utilk.android.content

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.util.Log
import com.mozhimen.basick.elemk.android.content.cons.CPackageInfo
import com.mozhimen.basick.elemk.android.content.cons.CPackageManager
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.bases.BaseUtilK


/**
 * @ClassName UtilKPackageInfo
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/20 10:53
 * @Version 1.0
 */
object UtilKPackageInfo : BaseUtilK() {
    @JvmStatic
    fun get(context: Context): PackageInfo? =
        getOfInstallLocationAuto(context)

    @JvmStatic
    fun get(context: Context, flags: Int): PackageInfo? =
        UtilKPackageManager.getPackageInfo(context, UtilKContext.getPackageName(context), flags /*0*/)

    @JvmStatic
    fun getOfInstallLocationAuto(context: Context): PackageInfo? =
        get(context, CPackageInfo.INSTALL_LOCATION_AUTO)

    @JvmStatic
    fun getOfGetConfigurations(context: Context): PackageInfo? =
        get(context, CPackageManager.GET_CONFIGURATIONS)

    @JvmStatic
    fun getApplicationInfo(context: Context): ApplicationInfo? =
        get(context)?.let { getApplicationInfo(it) }

    @JvmStatic
    fun getApplicationInfo(packageInfo: PackageInfo?): ApplicationInfo? =
        packageInfo?.applicationInfo

    @JvmStatic
    fun getRequestedPermissions(context: Context): Array<String>? =
        getRequestedPermissions(get(context))

    @JvmStatic
    fun getRequestedPermissions(packageInfo: PackageInfo?): Array<String>? =
        packageInfo?.requestedPermissions

    /**
     * 获取程序包名
     */
    @JvmStatic
    fun getVersionName(context: Context): String =
        get(context)?.let { getVersionName(it) } ?: ""

    @JvmStatic
    fun getVersionName(packageInfo: PackageInfo): String =
        try {
            packageInfo.versionName ?: ""
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            Log.e(TAG, "getVersionName: NameNotFoundException ${e.message}")
            ""
        }

    /**
     * 获取程序版本号
     */
    @JvmStatic
    fun getVersionCode(context: Context): Int =
        get(context)?.let { getVersionCode(it) } ?: 0


    @JvmStatic
    fun getVersionCode(packageInfo: PackageInfo): Int =
        try {
            if (UtilKBuildVersion.isAfterV_28_9_P())
                packageInfo.longVersionCode.toInt()
            else packageInfo.versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            Log.e(TAG, "getVersionCode: NameNotFoundException ${e.message}")
            0
        }
}