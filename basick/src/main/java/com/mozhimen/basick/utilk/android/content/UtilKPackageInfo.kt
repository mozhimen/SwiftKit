package com.mozhimen.basick.utilk.android.content

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.annotation.RequiresApi
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.elemk.android.content.cons.CPackageInfo
import com.mozhimen.basick.elemk.android.content.cons.CPackageManager
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.bases.BaseUtilK


/**
 * @ClassName UtilKPackageInfo
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/20 10:53
 * @Version 1.0
 */
fun PackageInfo.getVersionCode(): Int =
    UtilKPackageInfo.getVersionCode(this)

/////////////////////////////////////////////////////////////////////////

object UtilKPackageInfo : BaseUtilK() {
    @JvmStatic
    fun get(context: Context, strPackageName: String, flags: Int): PackageInfo? =
        UtilKPackageManager.getPackageInfo(context, strPackageName, flags)

    @JvmStatic
    fun get(context: Context, flags: Int): PackageInfo? =
        get(context, UtilKContext.getPackageName(context), flags /*0*/)

    @JvmStatic
    fun get(context: Context): PackageInfo? =
        get(context, 0)

    /////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    fun get_ofInstallLocationAuto(context: Context): PackageInfo? =
        get(context, CPackageInfo.INSTALL_LOCATION_AUTO)

    @JvmStatic
    fun get_ofGetConfigurations(context: Context): PackageInfo? =
        get(context, CPackageManager.GET_CONFIGURATIONS)

    /////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getApplicationInfo(context: Context): ApplicationInfo? =
        getApplicationInfo(get(context))

    @JvmStatic
    fun getApplicationInfo(packageInfo: PackageInfo?): ApplicationInfo? =
        packageInfo?.applicationInfo

    @JvmStatic
    fun getRequestedPermissions(context: Context): Array<String>? =
        getRequestedPermissions(get(context))

    @JvmStatic
    fun getRequestedPermissions(packageInfo: PackageInfo?): Array<String>? =
        packageInfo?.requestedPermissions

    @JvmStatic
    fun getPackageName(context: Context): String? =
        getPackageName(get(context))

    @JvmStatic
    fun getPackageName(packageInfo: PackageInfo?): String? =
        packageInfo?.packageName

    /////////////////////////////////////////////////////////////////////////

    /**
     * 获取程序包名
     */
    @JvmStatic
    fun getVersionName(context: Context): String =
        get(context)?.let { getVersionName(it) } ?: ""

    @JvmStatic
    fun getVersionName(packageInfo: PackageInfo?): String =
        try {
            packageInfo?.versionName ?: ""
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            UtilKLogWrapper.e(TAG, "getVersionName: NameNotFoundException ${e.message}")
            ""
        }

    /**
     * 获取程序版本号
     */
    @JvmStatic
    fun getVersionCode(context: Context): Int =
        get(context)?.let { getVersionCode(it) } ?: 0

    @JvmStatic
    fun getVersionCode(packageInfo: PackageInfo?): Int =
        try {
            packageInfo?.let {
                (if (UtilKBuildVersion.isAfterV_28_9_P())
                    it.longVersionCode.toInt()
                else it.versionCode)
            } ?: 0
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            UtilKLogWrapper.e(TAG, "getVersionCode: NameNotFoundException ${e.message}")
            0
        }

    /////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun hasPackage(context: Context, strPackageName: String): Boolean =
        try {
            get(context, strPackageName, CPackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
}