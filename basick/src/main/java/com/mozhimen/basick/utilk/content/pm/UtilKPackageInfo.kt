package com.mozhimen.basick.utilk.content.pm

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.content.UtilKContext


/**
 * @ClassName UtilKPackageInfo
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/20 10:53
 * @Version 1.0
 */
object UtilKPackageInfo : BaseUtilK() {
    /**
     * getPackageInfo
     * @return PackageInfo
     */
    @JvmStatic
    fun get(context: Context): PackageInfo =
        UtilKPackageManager.get(context).getPackageInfo(UtilKContext.getPackageName(context), PackageInfo.INSTALL_LOCATION_AUTO/*0*/)

    @JvmStatic
    fun getPackageArchiveInfo(apkPathWithName: String, context: Context): PackageInfo? =
        UtilKPackageManager.get(context).getPackageArchiveInfo(apkPathWithName, PackageManager.GET_ACTIVITIES)


    /**
     * 获取flags
     * @return Int
     */
    @JvmStatic
    fun getFlags(context: Context): Int =
        get(context).applicationInfo.flags

    /**
     * isSystemApp
     * @return Boolean
     */
    @JvmStatic
    fun isSystemApp(context: Context): Boolean =
        (getFlags(context) and ApplicationInfo.FLAG_SYSTEM) != 0

    /**
     * isSystemUpdateApp
     * @return Boolean
     */
    @JvmStatic
    fun isSystemUpdateApp(context: Context): Boolean =
        (getFlags(context) and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0

    /**
     * isUserApp
     * @return Boolean
     */
    @JvmStatic
    fun isUserApp(context: Context): Boolean =
        !isSystemApp(context) && !isSystemUpdateApp(context)

    /**
     * 获取程序包名
     * @return String
     */
    @JvmStatic
    fun getVersionName(context: Context): String {
        return getVersionName(get(context))
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
        return getVersionCode(get(context))
    }

    @JvmStatic
    fun getVersionCode(packageInfo: PackageInfo): Int {
        return try {
            if (Build.VERSION.SDK_INT >= CVersionCode.V_28_9_P) {
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