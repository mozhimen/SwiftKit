package com.mozhimen.basick.utilk.android.content

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.content.cons.CPackageManager
import com.mozhimen.basick.elemk.android.os.cons.CProcess
import com.mozhimen.basick.elemk.cons.CStrPackage
import com.mozhimen.basick.lintk.optins.permission.OPermission_QUERY_ALL_PACKAGES
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.kotlin.UtilKStrClazz


/**
 * @ClassName UtilKPackage
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/21 16:19
 * @Version 1.0
 */
object UtilKPackage : BaseUtilK() {

    @JvmStatic
    fun getVersionCode(): Int =
        UtilKPackageInfo.getVersionCode(_context)

    @JvmStatic
    fun getPackageName(): String =
        UtilKContext.getPackageName(_context)

    @JvmStatic
    fun getVersionName(): String =
        UtilKPackageInfo.getVersionName(_context)

    @JvmStatic
    fun getRequestedPermissionsStr(): String =
        UtilKPackageInfo.getRequestedPermissions(_context).contentToString()

    /**
     * 获取所有安装程序包名
     */
    @JvmStatic
    @OPermission_QUERY_ALL_PACKAGES
    @RequiresPermission(CPermission.QUERY_ALL_PACKAGES)
    fun getInstalledPackages(context: Context, hasSystemPackages: Boolean = false): List<PackageInfo> {
        var installedPackages = UtilKPackageManager.getInstalledPackages(context).toMutableList()
        if (installedPackages.isEmpty()) {
            installedPackages = getInstalledPackages_ofForce(context).toMutableList()
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
    fun getInstalledPackages_ofForce(context: Context): List<PackageInfo> {
        val installedPackages = mutableListOf<PackageInfo>()
        val packageManager = UtilKPackageManager.get(context)
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

    @JvmStatic
    fun isPackageInstalled(context: Context, strPackageName: String, flags: Int): Boolean {
        return try {
            UtilKApplicationInfo.enabled_ofPkM(context, strPackageName, flags)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            false
        }
    }

    @JvmStatic
    fun isPackageInstalled(context: Context, strPackageName: String): Boolean =
        isPackageInstalled(context, strPackageName, 0)

    /**
     * 系统的下载组件是否可用
     */
    @JvmStatic
    fun isDownloadComponentEnabled(context: Context): Boolean {
        try {
            val setting = UtilKPackageManager.getApplicationEnabledSetting(context, CStrPackage.COM_ANDROID_PROVIDERS_DOWNLOADS)
            if (setting == CPackageManager.COMPONENT_ENABLED_STATE_DISABLED || setting == CPackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER || setting == CPackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED)
                return false
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    @JvmStatic
    @OPermission_QUERY_ALL_PACKAGES
    @RequiresPermission(CPermission.QUERY_ALL_PACKAGES)
    fun hasPackage_ofQuery(context: Context, strPackageName: String): Boolean =
        UtilKPackageManager.queryIntentActivities(context, UtilKIntentWrapper.getMainLauncher_ofPackage(strPackageName), 0).isNotEmpty()

    @JvmStatic
    fun hasPackage(context: Context, strPackageName: String): Boolean =
        UtilKPackageInfo.hasPackage(context, strPackageName)

    @JvmStatic
    fun hasPackage_ofClazz(strPackageNameWithActivity: String): Boolean =
        UtilKStrClazz.isStrClassPackageExists(strPackageNameWithActivity)

    /**
     * 是否有前置
     */
    @JvmStatic
    fun hasFrontCamera(context: Context): Boolean =
        UtilKPackageManager.hasSystemFeature(context, CPackageManager.FEATURE_CAMERA_FRONT)

    /**
     * 是否有后置
     */
    @JvmStatic
    fun hasBackCamera(context: Context): Boolean =
        UtilKPackageManager.hasSystemFeature(context, CPackageManager.FEATURE_CAMERA)
}