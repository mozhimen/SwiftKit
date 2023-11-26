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
import com.mozhimen.basick.elemk.android.os.cons.CProcess
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
     * 得到图标
     */
    @JvmStatic
    fun getApplicationIcon(context: Context): Drawable? =
        UtilKApplicationInfo.get(context)?.let { get(context).getApplicationIcon(it) }

    /**
     * 得到图标
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
     * 获取所有安装程序包名
     */
    @JvmStatic
    @RequiresPermission(CPermission.REQUEST_INSTALL_PACKAGES)
    @AManifestKRequire(CPermission.REQUEST_INSTALL_PACKAGES)
    fun getInstalledPackages(context: Context, hasSystemPackages: Boolean = false): List<PackageInfo> {
        var installedPackages = getInstalledPackages(context, 0).toMutableList()
        if (installedPackages.isEmpty()) {
            installedPackages = getInstalledPackagesForce(context).toMutableList()
        }
        if (!hasSystemPackages) {
            val iterator = installedPackages.iterator()
            while (iterator.hasNext()) {
                val next = iterator.next()
                if (UtilKApplicationInfo.isSystemApp(next.applicationInfo))
                    iterator.remove()
            }
        }
        return installedPackages
    }

    /**
     * 强制获取软件包列表
     * @return 获取查询到的应用列表
     */
    private fun getInstalledPackagesForce(context: Context): List<PackageInfo> {
        val installedPackages = mutableListOf<PackageInfo>()
        val packageManager = get(context)
        for (uid in CProcess.SYSTEM_UID..CProcess.LAST_APPLICATION_UID) {
            val packagesForUid = try {
                packageManager.getPackagesForUid(uid)
            } catch (e: Exception) {
                null
            }
            packagesForUid?.forEach { packageName ->
                val packageInfo = try {
                    packageManager.getPackageInfo(packageName, 0)
                } catch (e: Exception) {
                    null
                }
                packageInfo?.let {
                    installedPackages.add(packageInfo)
                }
            }
        }
        return installedPackages
    }

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
     * 是否有匹配的包名
     */
    fun hasPackage(context: Context, packageName: String): Boolean =
        queryIntentActivities(context, UtilKIntentWrapper.getMainLauncher(packageName), 0).isNotEmpty()


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