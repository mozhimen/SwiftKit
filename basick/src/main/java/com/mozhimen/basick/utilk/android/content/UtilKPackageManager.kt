package com.mozhimen.basick.utilk.android.content

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageInstaller
import android.content.pm.PackageManager
import android.content.pm.PermissionGroupInfo
import android.content.pm.PermissionInfo
import android.content.pm.ResolveInfo
import android.graphics.drawable.Drawable
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.content.cons.CPackageManager
import com.mozhimen.basick.lintk.annors.ADescription
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.elemk.cons.CStrPackage
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission


/**
 * @ClassName UtilKPackageManager
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/20 10:50
 * @Version 1.0
 */
@SuppressLint("InlinedApi")
@AManifestKRequire(CPermission.REQUEST_INSTALL_PACKAGES)
object UtilKPackageManager {

    @JvmStatic
    fun get(context: Context): PackageManager =
        UtilKContext.getPackageManager(context)

    @JvmStatic
    fun getPackageInfo(context: Context, packageName: String, flags: Int): PackageInfo? =
        get(context).getPackageInfo(packageName, flags)

    @JvmStatic
    fun getPackageArchiveInfo(context: Context, archiveFilePath: String, flags: Int): PackageInfo? =
        get(context).getPackageArchiveInfo(archiveFilePath, flags)

    @JvmStatic
    fun getPackageInstaller(context: Context): PackageInstaller =
        get(context).packageInstaller

    /**
     * 得到应用名
     */
    @JvmStatic
    fun getApplicationLabel(context: Context, applicationInfo: ApplicationInfo): String =
        get(context).getApplicationLabel(applicationInfo).toString()


    @JvmStatic
    fun getApplicationEnabledSetting(context: Context, packageName: String): Int =
        get(context).getApplicationEnabledSetting(packageName)

    /**
     * 得到图标信息
     */
    @JvmStatic
    fun getApplicationIcon(context: Context, applicationInfo: ApplicationInfo): Drawable =
        get(context).getApplicationIcon(applicationInfo)

    /**
     * 查询所有的符合Intent的Activities
     */
    @SuppressLint("QueryPermissionsNeeded")
    @JvmStatic
    fun queryIntentActivities(context: Context, intent: Intent, flags: Int): List<ResolveInfo> =
        get(context).queryIntentActivities(intent, flags)

    @SuppressLint("QueryPermissionsNeeded")
    @JvmStatic
    fun getInstalledPackages(context: Context, flags: Int): List<PackageInfo> =
        get(context).getInstalledPackages(flags)

    @JvmStatic
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

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 系统的下载组件是否可用
     */
    fun isDownloadComponentEnabled(context: Context): Boolean {
        try {
            val setting = getApplicationEnabledSetting(context, CStrPackage.COM_ANDROID_PROVIDERS_DOWNLOADS)
            if (setting == CPackageManager.COMPONENT_ENABLED_STATE_DISABLED || setting == CPackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER || setting == CPackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED)
                return false
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    /**
     * 是否有前置
     */
    @JvmStatic
    fun hasFrontCamera(context: Context): Boolean =
        hasSystemFeature(context, CPackageManager.FEATURE_CAMERA_FRONT)

    /**
     * 是否有后置
     */
    @JvmStatic
    fun hasBackCamera(context: Context): Boolean =
        hasSystemFeature(context, CPackageManager.FEATURE_CAMERA)

    /**
     * 是否有配置
     */
    @JvmStatic
    fun hasSystemFeature(context: Context, featureName: String): Boolean =
        get(context).hasSystemFeature(featureName)

    /**
     * 是否有包安装权限
     */
    @JvmStatic
    @RequiresApi(CVersCode.V_26_8_O)
    @TargetApi(CVersCode.V_26_8_O)
    @RequiresPermission(CPermission.REQUEST_INSTALL_PACKAGES)
    @ADescription(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES)
    fun canRequestPackageInstalls(context: Context): Boolean =
        get(context).canRequestPackageInstalls()
}