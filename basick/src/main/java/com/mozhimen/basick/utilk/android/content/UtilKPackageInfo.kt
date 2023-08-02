package com.mozhimen.basick.utilk.android.content

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.util.Log
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
    @JvmOverloads
    fun get(context: Context, flags: Int = PackageInfo.INSTALL_LOCATION_AUTO): PackageInfo? =
        UtilKPackageManager.getPackageInfo(context, UtilKContext.getPackageName(context), flags /*0*/)

    @JvmStatic
    fun getApplicationInfo(context: Context): ApplicationInfo? =
        get(context)?.applicationInfo

    @JvmStatic
    fun getRequestedPermissions(context: Context): Array<String>? =
        get(context)?.requestedPermissions

    /**
     * 获取程序包名
     * @return String
     */
    @JvmStatic
    fun getVersionName(context: Context): String {
        return getVersionName(get(context) ?: return "")
    }

    @JvmStatic
    fun getVersionName(packageInfo: PackageInfo): String {
        return try {
            packageInfo.versionName ?: ""
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            Log.e(TAG, "getVersionName: NameNotFoundException ${e.message}")
            ""
        }
    }

    /**
     * 获取程序版本号
     * @return Int
     */
    @JvmStatic
    fun getVersionCode(context: Context): Int {
        return getVersionCode(get(context) ?: return 0)
    }

    @JvmStatic
    fun getVersionCode(packageInfo: PackageInfo): Int {
        return try {
            if (UtilKBuildVersion.isAfterV_28_9_P()) {
                packageInfo.longVersionCode.toInt()
            } else {
                packageInfo.versionCode
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            Log.e(TAG, "getVersionCode: NameNotFoundException ${e.message}")
            0
        }
    }
}