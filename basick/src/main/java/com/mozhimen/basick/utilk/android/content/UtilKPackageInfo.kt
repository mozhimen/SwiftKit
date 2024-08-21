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

    /////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun get_of0(context: Context, strPackageName: String): PackageInfo? =
        get(context, strPackageName, 0)//0

    @JvmStatic
    fun get_ofGET_CONFIGURATIONS(context: Context, strPackageName: String): PackageInfo? =
        get(context, strPackageName, CPackageManager.GET_CONFIGURATIONS)

    @JvmStatic
    fun get_ofGET_ACTIVITIES(context: Context, strPackageName: String): PackageInfo? =
        get(context, strPackageName, CPackageManager.GET_ACTIVITIES)

    /////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getApplicationInfo(context: Context, strPackageName: String, flags: Int): ApplicationInfo? =
        getApplicationInfo(get(context, strPackageName, flags))

    @JvmStatic
    fun getApplicationInfo(packageInfo: PackageInfo?): ApplicationInfo? =
        packageInfo?.applicationInfo

    @JvmStatic
    fun getRequestedPermissions(context: Context, strPackageName: String, flags: Int): Array<String>? =
        getRequestedPermissions(get(context, strPackageName, flags))

    @JvmStatic
    fun getRequestedPermissions(packageInfo: PackageInfo?): Array<String>? =
        packageInfo?.requestedPermissions

    @JvmStatic
    fun getPackageName(context: Context, strPackageName: String, flags: Int): String? =
        getPackageName(get(context, strPackageName, flags))

    @JvmStatic
    fun getPackageName(packageInfo: PackageInfo?): String? =
        packageInfo?.packageName

    /////////////////////////////////////////////////////////////////////////

    /**
     * 获取程序包名
     */
    @JvmStatic
    fun getVersionName(context: Context, strPackageName: String, flags: Int): String =
        get(context, strPackageName, flags)?.let { getVersionName(it) } ?: ""

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
    fun getVersionCode(context: Context, strPackageName: String, flags: Int): Int =
        get(context, strPackageName, flags)?.let { getVersionCode(it) } ?: 0

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
    fun hasPackage(context: Context, strPackageName: String, flags: Int): Boolean =
        try {
            get(context, strPackageName, flags) != null
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
}