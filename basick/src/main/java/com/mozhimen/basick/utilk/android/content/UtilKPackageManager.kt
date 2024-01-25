package com.mozhimen.basick.utilk.android.content

import android.annotation.TargetApi
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageInstaller
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
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
import com.mozhimen.basick.elemk.android.os.cons.CProcess
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.elemk.cons.CStrPackage
import com.mozhimen.basick.lintk.annors.ADescription
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion


/**
 * @ClassName UtilKPackageManager
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/20 10:50
 * @Version 1.0
 */
object UtilKPackageManager {

    @JvmStatic
    fun get(context: Context): PackageManager =
        UtilKContext.getPackageManager(context)

    @JvmStatic
    fun getPackageInfo(context: Context, strPackageName: String, flags: Int): PackageInfo? =
        get(context).getPackageInfo(strPackageName, flags)

    @JvmStatic
    fun getPackageArchiveInfo(context: Context, archiveFilePath: String, flags: Int): PackageInfo? =
        get(context).getPackageArchiveInfo(archiveFilePath, flags)

    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    fun getPackageInstaller(context: Context): PackageInstaller =
        get(context).packageInstaller

    @JvmStatic
    fun getApplicationInfo(context: Context, strPackageName: String): ApplicationInfo =
        get(context).getApplicationInfo(strPackageName, CPackageInfo.INSTALL_LOCATION_AUTO)

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
        UtilKApplicationInfo.getOfPackageInfo(context)?.let { get(context).getApplicationIcon(it) }

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
    fun queryIntentActivities(context: Context, intent: Intent, flags: Int): List<ResolveInfo> =
        get(context).queryIntentActivities(intent, flags)

    @JvmStatic
    fun getInstalledPackages(context: Context, flags: Int): List<PackageInfo> =
        get(context).getInstalledPackages(flags)


    @RequiresApi(CVersCode.V_33_13_TIRAMISU)
    fun getInstalledPackages(context: Context, flags: PackageInfoFlags): List<PackageInfo> =
        get(context).getInstalledPackages(flags)

    @JvmStatic
    fun getInstalledPackages(context: Context): List<PackageInfo> {
        val flags = CPackageManager.GET_ACTIVITIES or CPackageManager.GET_SERVICES
        val packageInfos: List<PackageInfo> = if (UtilKBuildVersion.isAfterV_33_13_TIRAMISU()) {
            getInstalledPackages(context, PackageInfoFlags.of(flags.toLong()))
        } else {
            getInstalledPackages(context, flags)
        }
        return packageInfos
    }


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

    @JvmStatic
    fun getLaunchIntentForPackage(context: Context, strPackageName: String): Intent? =
        get(context).getLaunchIntentForPackage(strPackageName)

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 获取所有安装程序包名
     */
    @JvmStatic
    fun getInstalledPackages(context: Context, hasSystemPackages: Boolean = false): List<PackageInfo> {
        var installedPackages = getInstalledPackages(context).toMutableList()
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
    @JvmStatic
    fun getInstalledPackagesForce(context: Context): List<PackageInfo> {
        val installedPackages = mutableListOf<PackageInfo>()
        val packageManager = get(context)
        for (uid in CProcess.SYSTEM_UID..CProcess.LAST_APPLICATION_UID) {
            val packagesForUid = try {
                packageManager.getPackagesForUid(uid)
            } catch (e: Exception) {
                null
            }
            packagesForUid?.forEach { strPackageName ->
                val packageInfo = try {
                    packageManager.getPackageInfo(strPackageName, 0)
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

    @JvmStatic
    fun isPackageInstalled(context: Context, strPackageName: String): Boolean {
        return try {
            get(context).getApplicationInfo(strPackageName, 0).enabled
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            false
        }
    }

    /**
     * 是否有匹配的包名
     */
    @JvmStatic
    fun hasPackageOfQuery(context: Context, strPackageName: String): Boolean =
        queryIntentActivities(context, UtilKIntentWrapper.getMainLauncher(strPackageName), 0).isNotEmpty()

    @JvmStatic
    fun hasPackage(context: Context, strPackageName: String): Boolean =
        try {
            get(context).getPackageInfo(strPackageName, CPackageManager.GET_ACTIVITIES)
            true
        } catch (e: NameNotFoundException) {
            false
        }

    /**
     * 是否有包安装权限
     */
    @JvmStatic
    @RequiresApi(CVersCode.V_26_8_O)
    @TargetApi(CVersCode.V_26_8_O)
    @RequiresPermission(CPermission.REQUEST_INSTALL_PACKAGES)
    @AManifestKRequire(CPermission.REQUEST_INSTALL_PACKAGES)
    @ADescription(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES)
    fun canRequestPackageInstalls(context: Context): Boolean =
        get(context).canRequestPackageInstalls()
}