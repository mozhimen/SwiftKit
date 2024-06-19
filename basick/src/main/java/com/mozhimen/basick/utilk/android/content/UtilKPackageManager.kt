package com.mozhimen.basick.utilk.android.content

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageInstaller
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PackageInfoFlags
import android.content.pm.PermissionGroupInfo
import android.content.pm.PermissionInfo
import android.content.pm.ResolveInfo
import android.graphics.drawable.Drawable
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.content.cons.CPackageInfo
import com.mozhimen.basick.elemk.android.content.cons.CPackageManager
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.lintk.annors.ADescription
import com.mozhimen.basick.lintk.optins.permission.OPermission_QUERY_ALL_PACKAGES
import com.mozhimen.basick.lintk.optins.permission.OPermission_REQUEST_INSTALL_PACKAGES
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.utilk.commons.IUtilK


/**
 * @ClassName UtilKPackageManager
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/20 10:50
 * @Version 1.0
 */
object UtilKPackageManager : IUtilK {

    @JvmStatic
    fun get(context: Context): PackageManager =
        UtilKContext.getPackageManager(context)

    @JvmStatic
    fun getPackageInfo(context: Context, strPackageName: String, flags: Int): PackageInfo? =
        try {
            get(context).getPackageInfo(strPackageName, flags)
        } catch (e: Exception) {
            e.printStackTrace()
            UtilKLogWrapper.e(TAG, "getPackageInfo: ", e)
            null
        }

    @JvmStatic
    fun getPackageArchiveInfo(context: Context, archiveFilePath: String, flags: Int): PackageInfo? =
        get(context).getPackageArchiveInfo(archiveFilePath, flags)

    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    fun getPackageInstaller(context: Context): PackageInstaller =
        get(context).packageInstaller

    @JvmStatic
    fun getApplicationInfo(context: Context, strPackageName: String): ApplicationInfo =
        getApplicationInfo(context, strPackageName, CPackageInfo.INSTALL_LOCATION_AUTO)

    @JvmStatic
    fun getApplicationInfo(context: Context, strPackageName: String, flags: Int): ApplicationInfo =
        get(context).getApplicationInfo(strPackageName, flags)

    /**
     * 得到应用名
     */
    @JvmStatic
    fun getApplicationLabel(context: Context, applicationInfo: ApplicationInfo): String =
        get(context).getApplicationLabel(applicationInfo).toString()

    @JvmStatic
    fun getApplicationEnabledSetting(context: Context, strPackageName: String): Int =
        get(context).getApplicationEnabledSetting(strPackageName)

    /**
     * 得到图标
     */
    @JvmStatic
    fun getApplicationIcon(context: Context): Drawable? =
        UtilKApplicationInfo.get_ofPkI(context)?.let { get(context).getApplicationIcon(it) }

    /**
     * 得到图标
     */
    @JvmStatic
    fun getApplicationIcon(context: Context, applicationInfo: ApplicationInfo): Drawable =
        get(context).getApplicationIcon(applicationInfo)

    /**
     * 查询所有的符合Intent的Activities
     */
    @JvmStatic
    @OPermission_QUERY_ALL_PACKAGES
    @RequiresPermission(CPermission.QUERY_ALL_PACKAGES)
    @SuppressLint("QueryPermissionsNeeded")
    fun queryIntentActivities(context: Context, intent: Intent, flags: Int): List<ResolveInfo> =
        get(context).queryIntentActivities(intent, flags)

    @JvmStatic
    @OPermission_QUERY_ALL_PACKAGES
    @RequiresPermission(CPermission.QUERY_ALL_PACKAGES)
    @SuppressLint("QueryPermissionsNeeded")
    fun getInstalledPackages(context: Context, flags: Int): List<PackageInfo> =
        get(context).getInstalledPackages(flags)

    @JvmStatic
    @RequiresApi(CVersCode.V_33_13_TIRAMISU)
    @OPermission_QUERY_ALL_PACKAGES
    @RequiresPermission(CPermission.QUERY_ALL_PACKAGES)
    @SuppressLint("QueryPermissionsNeeded")
    fun getInstalledPackages(context: Context, flags: PackageInfoFlags): List<PackageInfo> =
        get(context).getInstalledPackages(flags)

    @JvmStatic
    @OPermission_QUERY_ALL_PACKAGES
    @RequiresPermission(CPermission.QUERY_ALL_PACKAGES)
    fun getInstalledPackages(context: Context): List<PackageInfo> {
        val flags = CPackageManager.GET_ACTIVITIES or CPackageManager.GET_SERVICES
        val installedPackageInfos: List<PackageInfo> = if (UtilKBuildVersion.isAfterV_33_13_TIRAMISU()) {
            getInstalledPackages(context, PackageInfoFlags.of(flags.toLong()))
        } else {
            getInstalledPackages(context, flags)
        }
        return installedPackageInfos
    }


    @JvmStatic
    @OPermission_QUERY_ALL_PACKAGES
    @RequiresPermission(CPermission.QUERY_ALL_PACKAGES)
    fun getInstalledPackagesActivities(context: Context): List<PackageInfo> =
        getInstalledPackages(context, CPackageManager.GET_ACTIVITIES)

    @JvmStatic
    fun getPermissionInfo(context: Context, permName: String, flags: Int): PermissionInfo =
        get(context).getPermissionInfo(permName, flags)

    @JvmStatic
    fun getAllPermissionGroups(context: Context, flags: Int): List<PermissionGroupInfo> =
        get(context).getAllPermissionGroups(flags)

    @JvmStatic
    fun getActivityInfo(context: Context, component: ComponentName, flags: Int): ActivityInfo =
        get(context).getActivityInfo(component, flags)

    @JvmStatic
    fun getActivityInfo(context: Context, packageClazzName: String, activityClazzName: String): ActivityInfo =
        getActivityInfo(context, ComponentName(packageClazzName, activityClazzName), CPackageManager.GET_ACTIVITIES)

    @JvmStatic
    fun getLaunchIntentForPackage(context: Context, strPackageName: String): Intent? =
        get(context).getLaunchIntentForPackage(strPackageName)

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 是否有配置
     */
    @JvmStatic
    fun hasSystemFeature(context: Context, featureName: String): Boolean =
        get(context).hasSystemFeature(featureName)

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 是否有包安装权限
     */
    @JvmStatic
    @RequiresPermission(CPermission.REQUEST_INSTALL_PACKAGES)
    @OPermission_REQUEST_INSTALL_PACKAGES
    @ADescription(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES)
    fun canRequestPackageInstalls(context: Context): Boolean =
        if (UtilKBuildVersion.isAfterV_26_8_O())
            get(context).canRequestPackageInstalls()
        else true
}